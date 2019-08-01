
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
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
  
  //

  //------------------------
  // CONSTRUCTOR
  //------------------------
  /**
   * Constructor for Cluedo. Runs the main game mechanics. 
   */
  public Cluedo() {
	  //Get num players and create players
	  int numPlayers = getNumPlayers();
	  ArrayList<Player> players = new ArrayList<Player>();
	  for(int i = 0;i < numPlayers;i++) {
		  players.add(new Player(i+1));
	  }
	  
	  //Set names and characters for each player 
	  ArrayList<Player> computerPlayers = charactersAndNames(players,numPlayers);
	 
	  //Create new board
	  Board board = new Board(players,computerPlayers);
	  
	  //Play Game
	  playCluedo(players,board);
  }
  
  
  public void playCluedo(ArrayList<Player> players, Board board) {
	  //Control Variables
	  boolean gameOver = false;
	  int turn = 0;
	  
	  //Main Loop
	  while(!gameOver) {
		  //Determine player
		  Player player = players.get(turn);
		  //Moving 
		  int steps = diceRoll();
		  System.out.println(""+player.getName()+" it's your turn, you have a dice roll of"+steps);
		  //MOVE PLAYER STEPS 
		  //IF IN ROOM CAN MAKE SUGGESTION OR CAN MAKE ACCUSATION ANYWHERE
		  
		  
	  }
	  
	  //Clean up
		  
		
  }
  
  
  /**
   * method to get a dice roll, e.g. sum of 2 random numbers 1- 6
   * @return int 
   */
  public int diceRoll() {
		Random rand = new Random();
		int dice1 = rand.nextInt((7- 1) + 1) + 1;
		int dice2 = rand.nextInt((7- 1) + 1) + 1;
		return dice1 + dice2;
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
	  ArrayList<String> characterArray = new ArrayList<String>(Arrays.asList("Miss Scarlett","Colonel Mustard",
			  								"Mrs. White","Mr. Green","Mrs. Peacock","Professor Plum"));
	  //repeats for each players 
	  for(int reps = 0; reps < numPlayers; reps++) {
		  Player player = players.get(reps+1);
		  player.setName(askPlayer("Player "+(reps+1)+" please enter your name: "));
		  boolean found = false;
		  int choice = -1;
		  //loops until input is satisfactory 
		  while(!found) {
			  System.out.println("Available characters : "+characterArray);
			  String strNumPlayers = askPlayer(""+player.getName()+" please select a character (0 for first anem, "
			  						+ "1 for next ... etc ");
			  int numErrorMsg = 0;
			  try {
				 found = true;
			     choice = Integer.parseInt(strNumPlayers);
			  }
			  catch (NumberFormatException e) {
			     found = false;
			     choice = -1;
			     System.out.println("Input error, please enter 0,1...etc to select character");
			     numErrorMsg++;
			  }
			  if(choice < 0 || choice > 5) {
				  if(numErrorMsg == 0) {
					  System.out.println("Input error, please enter 0,1...etc to select character");
				  }
				  choice = -1;
				  found = false;
			  }
		  }
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
   * Method to get the number of players.
   * Has checking to make sure they give good inputs 
   * @return int
   */
  public int getNumPlayers() {
	  boolean foundNum = false;
	  int numPlayers = -1;
	  //loops until we get the input we want 
	  while(!foundNum) {
		  String strNumPlayers = askPlayer("How many people are playing? ");
		  int numErrorMsg = 0;
		  try {
			 foundNum = true;
		     numPlayers = Integer.parseInt(strNumPlayers);
		  }
		  catch (NumberFormatException e) {
		     foundNum = false;
		     numPlayers = -1;
		     System.out.println("Input error, please enter 2 , 3 or 4");
		     numErrorMsg++;
		  }
		  if(numPlayers < 2 || numPlayers > 4) {
			  if(numErrorMsg == 0) {
				  System.out.println("Input error, please enter 2 , 3 or 4");
			  }
			  numPlayers = -1;
			  foundNum = false;
		  }
	  }
	  return numPlayers;
  }
  
  //lalala
  public void ask() {
	  
  }
  

  //------------------------
  // INTERFACE
  //------------------------
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
	  String ans = sc.next();
	  return ans;
  }
  
  
  public static void main(String args[]) {
	  new Cluedo();
  }
  
  
  

}

















