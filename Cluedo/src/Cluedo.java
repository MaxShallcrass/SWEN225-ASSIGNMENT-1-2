
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
  private ArrayList<Player> players;
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
	  players = new ArrayList<Player>();
	  for(int i = 0;i < numPlayers;i++) {
		  players.add(new Player(i+1));
	  }
	  
	  //Set names and characters for each player 
	  ArrayList<Player> computerPlayers = charactersAndNames(numPlayers);
	  
	  
	  //Creating envelope and dealing hands
	  Envelope envelope = deal();
	  System.out.println(envelope.toString());
	 
	  //doSuggestion(players,players.get(0),"Lounge");
	  
	 // doSuggestion(players,players.get(0),"Lounge");
	  
	  //Create new board
	  board = new Board(players,computerPlayers);
	  //Play Game
	  playCluedo();
  }
  
  /**
   * method to create envelope and deal cards 
   * @param players
   * @return envelope
   */
  public Envelope deal() {
	  //Create deck of cards as arraylist of super type card
	  ArrayList<Card> deck = new ArrayList<Card>();
	  
	  //Make copies of main lists so that we can edit them without changing global variables
	  ArrayList<String> charactersClone = new ArrayList<String>(Arrays.asList("Miss Scarlett","Colonel Mustard",
				"Mrs. White","Mr. Green","Mrs. Peacock","Professor Plum"));
	  ArrayList<String> weaponsClone = new ArrayList<String>(Arrays.asList("Candlestick","Dagger","Lead Pipe","Revolver","Rope","Spanner"));
	  ArrayList<String> roomsClone = new ArrayList<String>(Arrays.asList("Kitchen","Ballroom","Conservatory","Billiard Room","Library",
			  											"Study","Hall","Lounge","Dining Room"));
	  
	  //make envelope
	  Random rand = new Random();
	  int roomIndex = rand.nextInt((9- 0) + 0) + 0;
	  int weaponIndex = rand.nextInt((5- 0) + 0) + 0;
	  int characterIndex = rand.nextInt((5- 0) + 0) + 0;
	  Envelope e = new Envelope(new RoomCard(roomsClone.get(roomIndex)),new WeaponCard(weaponsClone.get(weaponIndex)),
			  					new CharacterCard(charactersClone.get(characterIndex)));
	  charactersClone.remove(characterIndex);
	  roomsClone.remove(roomIndex);
	  weaponsClone.remove(weaponIndex);
	  
	  //construct new subclass cards and add to deck
	  for(String c : charactersClone) {
		  deck.add(new CharacterCard(c));
	  }
	  for(String w : weaponsClone) {
		  deck.add(new WeaponCard(w));
	  }
	  for(String r : roomsClone) {
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
	  return e;
  }
  
  /**
   * main method that loops player turns 
   * @param players
   * @param board
   */
  public void playCluedo() {
	  //Control Variables
	  boolean gameOver = false;
	  int turn = 0; //simple variable to store player turn - stored as index of player list
	  Player winner = null; //variable to store winner 
	  
	  //Main Loop
	  while(!gameOver) {
		  clearScreen();
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
			  clearScreen();
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
			  gameOver = doAccusation();
			  if(!gameOver) {
				  player.losesGame();
			  }else {
				  winner = player;
			  }
		  }else if(decision.equals("s")) {
			  doSuggestion(player,cell.getRoom());
		  }
		  //next players turn 
		  turn++;
		  if(turn == players.size()) {
			  turn = 0;
		  } 
	  }
	  //Clean up - games over. 
	  System.out.println("Games over, winner is : "+winner.getName());
		  
		
  }
  
  /**
   * method to do an accusation, returns true if accusation is correct, false otherwise
   * @return boolen, wether accusation was successful or not
   */
  public boolean doAccusation() {
	  //Create suggestion
	  System.out.println(); // visual spacing for output
	  String weapon = cleanString(ask("Weapons: "+weapons+"\n What weapon do you want to accuse?",
				"Error please enter a weapon",weapons));
	  String character = cleanString(ask("Characters: "+"\n What character do you want to accuse?",
				"Error please enter a character",characters));
	  String room = cleanString(ask("What room do you want to accuse CAPITALISED??"+rooms,
				"Error please enter a room",rooms));
	  Accusation acus = new Accusation(new RoomCard(room),new WeaponCard(weapon),new CharacterCard(character));
	  if(acus.testAccusation(envelope)) {
		  return true;
	  }
	  return false;
  }
  
  
  /**
   * method to execute a suggestion made by a player, this involves creating suggestion and refuting
   * @param players
   * @param player
   * @param room
   */
  public void doSuggestion(Player player,String room) { 
	  //Create suggestion
	  System.out.println(); // visual spacing for output
	  String weapon = cleanString(ask("Weapons: "+weapons+"\n What weapon do you want to suggest?",
				"Error please enter a weapon",weapons));
	  String character = cleanString(ask("Characers: "+characters+"\n What character do you want to suggest?",
				"Error please enter a character",characters));
	  Suggestion sug = new Suggestion(new RoomCard(room),new WeaponCard(weapon),new CharacterCard(character));
	  //Move character and weapon to room
	  board.movePlayerWeaponToRoom(player,room,weapon);
	  refute(sug,player);
  }
  
  /**
   * 
   */
  public void refute(Suggestion s,Player playerThatSuggested) {
	  //find player after this player
	  int turn = -1;
	  int count =0;
	  for(Player p : players) {
		  if(p.equals(playerThatSuggested)) {
			  turn = count - 1 ;//-1 to avoid the count ++
		  }
		  count++;
	  }
	  turn++; //get player  ahead
	//wrap around for turn
	  if(turn == players.size()) {
		  turn -= players.size();
	  }
	  //loop through the remaining players 
	  for(int i=turn+1;i<players.size();i++) {
		  //wraparound for getting player
		  if(i == players.size()) {
			  i -= players.size();
		  }
		  Player player = players.get(i);
		  //Add possible refutes to list and then control flow off size of list (0 or >0)
		  ArrayList<Card> possRefutes = new ArrayList<Card>();
		  for(Card playerCard : player.getHand().getCards()) {
			  for(Card suggestionCard : s.getCards()) {
				  if(playerCard.equals(suggestionCard)) {
					  possRefutes.add(playerCard);
				  }
			  }
		  }
		  if(possRefutes.size() != 0) {
			  ArrayList<String> strPossRefutes = new ArrayList<String>();
			  for(Card c : possRefutes) {
				  strPossRefutes.add(c.getName());
			  }
			  System.out.println("Cards "+player.getName()+" can refute with: "+strPossRefutes);
			  String cardToRefute = cleanString(ask("What card woud you like to refute with? ",
						"Error please enter a card from: ",strPossRefutes));
			  System.out.println(player.getName()+" has refuted with: "+cardToRefute);
			  askPlayer("Press any key to continue");
			  break;
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
  public void pState(int numPlayers) {
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
  public ArrayList<Player> charactersAndNames(int numPlayers) {
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
   * method to ask for input from user, has a list of target values, a question and an error msg
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
  
  /**
   * gets the string into the correct format e.g. each words capitalised rest lower case
   * @param str
   * @return cleaned string 
   */
  public String cleanString(String s) {
	  String[] stringA = s.split(" ");
	  String str1 = stringA[0];
	  if(stringA.length > 1) {
		  String str2 = stringA[1];
		  String s1 = str1.substring(0, 1).toUpperCase() + str1.substring(1).toLowerCase();
		  String s2 = str2.substring(0, 1).toUpperCase() + str2.substring(1).toLowerCase();
		  return s1+" "+s2;
	  }
	  return str1.substring(0, 1).toUpperCase() + str1.substring(1).toLowerCase();
	  //return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
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
   * method to clear screen so that other players cannot see turn informaiton
   */
  public void clearScreen() {
	  for (int i = 0; i < 50; ++i) System.out.println();
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

















