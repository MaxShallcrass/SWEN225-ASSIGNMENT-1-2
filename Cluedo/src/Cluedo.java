
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Cluedo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cluedo Associations
  private Envelope envelope;
  private Board board;
  private ArrayList<String> characters = new ArrayList<String>(Arrays.asList("Miss Scarlett","Colonel Mustard",
			"Mrs. White","Mr. Green","Mrs. Peacock","Professor Plum"));
  private ArrayList<String> weapons = new ArrayList<String>(Arrays.asList("Candlestick","Dagger","Lead Pipe","Revolver","Rope","Spanner"));
  private ArrayList<String> rooms = new ArrayList<String>(Arrays.asList("Kitchen","Ballroom","Conservatory","Billiard Room","Library",
		  											"Study","Hall","Lounge","Dining Room"));
  
  //

  //------------------------
  // CONSTRUCTOR
  //------------------------
  /**
   * Constructor for Cluedo. Runs the main game mechanics. 
   */
  public Cluedo() {
	  //Get num players and create players
	  int numPlayers = Integer.parseInt(ask("How many players are there? ","Error - please enter 2,3 or 4",new ArrayList<String>(
			  Arrays.asList("2","3","4"))));
	  ArrayList<Player> players = new ArrayList<Player>();
	  for(int i = 0;i < numPlayers;i++) {
		  players.add(new Player(i+1));
	  }
	  
	  //Set names and characters for each player 
	  ArrayList<Player> computerPlayers = charactersAndNames(players,numPlayers);
	  
	  
	  //Creating envelope and dealing hands
	  Envelope e = deal(players);
	 
	  //doSuggestion(players,players.get(0),"Lounge");
	  
	 // doSuggestion(players,players.get(0),"Lounge");
	  
	  //Create new board
	  Board board = new Board(players,computerPlayers);
	  
	  //Play Game
	  playCluedo(players,board);
  }
  
  /**
   * method to create envelope and deal cards 
   * @param players
   * @return envelope
   */
  public Envelope deal(ArrayList<Player> players) {
	  //Create deck of cards as arraylist of super type card
	  ArrayList<Card> deck = new ArrayList<Card>();
	  
	  //make envelope
	  Random rand = new Random();
	  int roomIndex = rand.nextInt((9- 0) + 0) + 0;
	  int weaponIndex = rand.nextInt((5- 0) + 0) + 0;
	  int characterIndex = rand.nextInt((5- 0) + 0) + 0;
	  Envelope envelope = new Envelope(new RoomCard(rooms.get(roomIndex)),new WeaponCard(weapons.get(weaponIndex)),
			  					new CharacterCard(characters.get(characterIndex)));
	  characters.remove(characterIndex);
	  rooms.remove(roomIndex);
	  weapons.remove(weaponIndex);
	  
	  //construct new subclass cards and add to deck
	  for(String c : characters) {
		  deck.add(new CharacterCard(c));
	  }
	  for(String w : weapons) {
		  deck.add(new WeaponCard(w));
	  }
	  for(String r : rooms) {
		  deck.add(new RoomCard(r));
	  }
	  //shuffle deck 
	  Collections.shuffle(deck);
	  //deal
	  int turn = 0;
	  for(Card c : deck) {
		  Player player = players.get(turn);
		  player.getHand().add(c);
		  turn++;
		  if(turn == players.size()) {
			  turn = 0;
		  }
	  }
	  return envelope;
  }
  
  /**
   * main method that loops player turns 
   * @param players
   * @param board
   */
  public void playCluedo(ArrayList<Player> players, Board board) {
	  //Control Variables
	  boolean gameOver = false;
	  int turn = 0;
	  
	  //Main Loop
	  while(!gameOver) {
		  System.out.println(); // visual spacing for output
		  System.out.println(); // visual spacing for output
		  //Determine player
		  Player player = players.get(turn);
		  player.newTurn();
		  //Moving 
		  int steps = diceRoll();
		  board.displayBoard();
		  System.out.println(""+player.getName()+" it's your turn, you have a dice roll of "+steps);
		  while(steps != 0) {
			  String md = ask("What direction do you want to move (w-a-s-d)? ",
					  "Error - please enter w , a , s or d",new ArrayList<String>(
					  Arrays.asList("w","a","s","d")));
			  steps = board.movePlayer(player.getLocation(),md,steps);
			  if(steps == 0) {System.out.println("What");}
			  board.displayBoard();
			  System.out.println("Remaining moves : "+steps);

		  }
		  //IF IN ROOM CAN MAKE SUGGESTION OR CAN MAKE ACCUSATION ANYWHERE
		  Cell cell = board.getCellAt(player.getLocation());
		  String decision = "";
		  if(cell.isRoom()) {
			  decision = ask("You can now make accusation(a), suggestion(s) or do nothing (n)",
					  				"Error please enter: a , s or n ",
					  				new ArrayList<String>(Arrays.asList("a","s","n")));
		  }else {
			  decision = ask("You can now make accusation (a) or do nothing (n)",
		  				"Error please enter: a or n ",
		  				new ArrayList<String>(Arrays.asList("a","n")));
		  }
		  if(decision.equals("a")) {
			  doAccusation();
		  }else if(decision.equals("s")) {
			  doSuggestion(players,player,cell.getRoom());
		  }
		  //next players turn 
		  turn++;
		  if(turn == players.size()) {
			  turn = 0;
		  } 
	  }
	  
	  //Clean up
		  
		
  }
  
  
  public boolean doAccusation() {
	  //Create suggestion
	  System.out.println(); // visual spacing for output
	  String weapon = cleanString(ask("What weapon do you want to accuse CAPITALISED??"+weapons,
				"Error please enter a weapon",weapons));
	  String character = cleanString(ask("What character do you want to accuse CAPITALISED??"+characters,
				"Error please enter a character",characters));
	  String room = cleanString(ask("What room do you want to accuse CAPITALISED??"+rooms,
				"Error please enter a room",rooms));
	  Accusation acus = new Accusation(new RoomCard(room),new WeaponCard(weapon),new CharacterCard(character));
	  return false;
  }
  
  
  public void doSuggestion(ArrayList<Player> players,Player player,String room) { //FIX CAPITALISED 
	  //Create suggestion
	  System.out.println(); // visual spacing for output
	  String weapon = cleanString(ask("What weapon do you want to suggest CAPITALISED??"+weapons,
				"Error please enter a weapon",weapons));
	  String character = cleanString(ask("What character do you want to suggest CAPITALISED??"+characters,
				"Error please enter a character",characters));
	  Suggestion sug = new Suggestion(new RoomCard(room),new WeaponCard(weapon),new CharacterCard(character));
	  //Move character and weapon to room
	  board.movePlayerWeaponToRoom(player,room,weapon);
	  //Allow for players to refute
	  for(Player p : players) {
		  if(!p.equals(player)) {
			  System.out.println(); // visual spacing for output
			  String refuteYN = ask(""+p.getName()+" would you like to refute y/n ? ",
					  "Please enter Y or N",new ArrayList<String>(Arrays.asList("y","n")));
			  if(refuteYN.equals("y")) {
				  boolean correctRefute = false;
				  String refuteString = "";
				  while(!correctRefute) {
					  System.out.println("These are your cards: "+p.getHand().toList());
					  refuteString = ask("What card would you like to refute with? ",
								"Error please enter a correct card",p.getHand().toList());
					  if(sug.refutedBy(refuteString)) {
						  correctRefute = true;
					  }
					  if(!correctRefute) {
						  System.out.println("");
					  }
				  }
				  System.out.println("Suggestion refuted with : "+refuteString);
				  return; //BREAKS LOOP 
			  }
			  //next player
		  }
	  }
  }

  
  /**
   * method to get a dice roll, e.g. sum of 2 random numbers 1- 6
   * @return int 
   */
  public int diceRoll() {
		Random rand = new Random();
		int dice1 = rand.nextInt((7- 1) + 1) + 1;
		int dice2 = rand.nextInt((7- 1) + 1) + 1;
		//return dice1 + dice2;
		return 12; //REMEMBER TO REMOVE-----------------------------------
  }
  
  /**
   * printers what happening for players 
   */
  public void pState(ArrayList<Player> players,int numPlayers) {
	  for(int i =0;i<numPlayers;i++) {
		  Player p = players.get(i+1);
		  System.out.println("Player "+(i+1)+" named: "+p.getName()+" playing: "+p.getCharacter());
	  }
  }
  
  /**
   * method to set name of players and the character they're playing 
   * @param players
   * @param numPlayers
   * @return computer Players 
   */
  public ArrayList<Player> charactersAndNames(ArrayList<Player> players,int numPlayers) {
	  ArrayList<String> indexChoices = new ArrayList<String>(Arrays.asList("0","1","2","3","4","5"));
	  ArrayList<String> characterArray = new ArrayList<String>(Arrays.asList("Miss Scarlett","Colonel Mustard",
			  								"Mrs. White","Mr. Green","Mrs. Peacock","Professor Plum"));
	  //repeats for each players 
	  for(int reps = 0; reps < numPlayers; reps++) {
		  Player player = players.get(reps);
		  player.setName(askPlayer("Player "+(reps+1)+" please enter your name: ")+ " (" +(reps+1)+ ")");
		  String strChoice = ask("The available characters are:"+characterArray+"\n"+""+player.getName()+" please select a character (0 first name, "
					+ "1 for second ... etc: ","Input error, please enter 0,1...etc to select character",indexChoices);
		  indexChoices.remove(indexChoices.size()-1); //remove final element
		  int choice = Integer.parseInt(strChoice);
		  player.setCharacter(characterArray.get(choice));
		  characterArray.remove(choice);
	  }
	  ArrayList<Player> computerPlayers = new ArrayList<Player>();
	  for(int i=0;i<characterArray.size();i++) {
		  Player p = new Player(numPlayers+i+1);
		  String c = characterArray.get(i);
		  p.setCharacter(c);
		  p.setName(c);
		  computerPlayers.add(p);
	  }
	  return computerPlayers;
  }
  
  /**
   * method to ask for input from user
   * @param question
   * @param errorMsg
   * @param targetValues
   * @return answer - result
   */
  public String ask(String question,String errorMsg,ArrayList<String> targetValues) {
	  boolean found = false;
	  String result = "";
	  while(!found) {
		  result = askPlayer(question);
		  for(String tv : targetValues) {
			  if(result.equals(tv)) {
				  found = true;
			  }
		  }
		  if(!found) {
			  System.out.println(errorMsg);
			  System.out.println(result);
			  System.out.println("From : "+targetValues);
		  }
	  }
	  return result;
  }
  
  public String cleanString(String str) {
	  return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }
  

  //------------------------
  // INTERFACE
  //------------------------
  /*
   * potential work in progress
   */
  public void helpMessage() {
	  
	  System.out.println();
  }
  
  
  
  /**
   * Getter for envelope 
   * @return Envelope
   */
  public Envelope getEnvelope() {
    return envelope;
  }
  /**
   * getter for Board 
   * @return Board 
   */
  public Board getBoard() {
    return board;
  }
  /**
   * Asks the player for input 
   * @param args
   */
  public String askPlayer(String s) {
	  System.out.print(s);
	  Scanner sc = new Scanner(System.in);
	  String ans = sc.nextLine();
	  return ans;
  }
  
  
  public static void main(String args[]) {
	  new Cluedo();
  }
  
  
  

}

















