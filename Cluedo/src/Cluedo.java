import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Controls gameplay of cluedo and all input/output information to players. Sets
 * up the game of how many players and deals out cards. Runs the players turns
 * and does accusations, suggestions, refutes and if the game is over/won
 *
 */
public class Cluedo {

	private Envelope envelope;
	private Board board;
	private boolean testing;
	private Scanner testSc;
	private ArrayList<Player> players;
	// Character, weapon and room names
	private ArrayList<String> characters = new ArrayList<String>(Arrays.asList("Miss Scarlett", "Colonel Mustard",
			"Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
	private ArrayList<String> weapons = new ArrayList<String>(
			Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"));
	private ArrayList<String> rooms = new ArrayList<String>(Arrays.asList("Kitchen", "Ballroom", "Conservatory",
			"Billiard Room", "Library", "Study", "Hall", "Lounge", "Dining Room"));
	private boolean gameOver = false;
	Player winner;

	/**
	 * Constructor for Cluedo. Runs and sets up the main game mechanics.
	 * 
	 * @param testing shouldnt ask for input ever
	 */
	public Cluedo(Boolean t,String testingString) {
		testing = t;
		if(testing) {
			testSc = new Scanner(testingString);
		}
		System.out.println("------------------------------------------!!!!!WELCOME TO CLUEDO!!!!!------------------------------------------\n");
		helpMessage();// displays game information
		// Get num players and create players
		int numPlayers = Integer.parseInt(ask("How many players are there? ", "Error - please enter 3 to 6",
				new ArrayList<String>(Arrays.asList("3", "4", "5", "6"))));
		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player(i + 1));
		}

		// Set names and characters for each player
		ArrayList<Player> computerPlayers = charactersAndNames(numPlayers);
		// Creating envelope and dealing hands
		deal();
		// Create new board
		board = new Board(players, computerPlayers);
		// Play Game
		if(!testing) {
			playCluedo();
		}
	}

	/**
	 * Method to create envelope and deal cards to players
	 * 
	 * @param players
	 * @return envelope
	 */
	private void deal() {
		// Create deck of cards as arraylist of super type card
		ArrayList<Card> deck = new ArrayList<Card>();

		// Make copies of main lists so that we can edit them without changing global
		// variables
		ArrayList<String> charactersClone = new ArrayList<String>(Arrays.asList("Miss Scarlett", "Colonel Mustard",
				"Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
		ArrayList<String> weaponsClone = new ArrayList<String>(
				Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"));
		ArrayList<String> roomsClone = new ArrayList<String>(Arrays.asList("Kitchen", "Ballroom", "Conservatory",
				"Billiard Room", "Library", "Study", "Hall", "Lounge", "Dining Room"));

		// make envelope
		Random rand = new Random();
		int roomIndex = rand.nextInt((8 - 0) + 0) + 0;
		int weaponIndex = rand.nextInt((5 - 0) + 0) + 0;
		int characterIndex = rand.nextInt((5 - 0) + 0) + 0;
		envelope = new Envelope(new RoomCard(roomsClone.get(roomIndex)), new WeaponCard(weaponsClone.get(weaponIndex)),
				new CharacterCard(charactersClone.get(characterIndex)));
		charactersClone.remove(characterIndex);
		roomsClone.remove(roomIndex);
		weaponsClone.remove(weaponIndex);

		// construct new subclass cards and add to deck
		for (String c : charactersClone) {
			deck.add(new CharacterCard(c));
		}
		for (String w : weaponsClone) {
			deck.add(new WeaponCard(w));
		}
		for (String r : roomsClone) {
			deck.add(new RoomCard(r));
		}
		// shuffle deck
		Collections.shuffle(deck);
		// deal
		int turn = 0;
		for (Card c : deck) {
			Player player = players.get(turn);
			player.getHand().add(c);
			turn++;
			if (turn == players.size()) {
				turn = 0;
			}
		}
	}

	/**
	 * Main method that loops player turns and runs the game
	 * 
	 * @param players
	 * @param board
	 */
	public void playCluedo() {
		// Control Variables
		Player player;
		int turn = 0; // simple variable to store player turn - stored as index of player list
		// Main Loop
		while (canPlay() && !gameOver) {
			clearScreen();
			// Determine first player
			player = players.get(turn);
			// choosing a player that can play
			while (player.hasLost()) {
				if (++turn == players.size())
					turn = 0;
				player = players.get(turn);
			}
			player.newTurn(); // for keeping track of players movements
			// Moving
			int steps = diceRoll();
			board.displayBoard();
			System.out.println("" + player.getName() + " it's your turn, you have a dice roll of " + steps);
			System.out.println("Your cards are: " + player.getHand().toList().toString());
			while (steps != 0) {
				String md = ask("What direction do you want to move (w-a-s-d)? ", "Error - please enter w , a , s or d",
						new ArrayList<String>(Arrays.asList("w", "a", "s", "d")));
				steps = board.movePlayer(player.getLocation(), md, steps);
				System.out.println("Remaining moves : " + steps);
			}
			// If in a room make a suggestion (unless same room as last turn) or can make an
			// accusation anywhere
			Cell cell = board.getCellAt(player.getLocation());
			String decision = "";
			if (cell.isRoom() && player.canSuggest(cell.getRoom())) {
				decision = ask("You can now make accusation(a), suggestion(s) or do nothing (n)",
						"Error please enter: a , s or n ", new ArrayList<String>(Arrays.asList("a", "s", "n")));
			} else {
				decision = ask("You can now make accusation (a) or do nothing (n)", "Error please enter: a or n ",
						new ArrayList<String>(Arrays.asList("a", "n")));
			}
			if (decision.equals("a")) {
				System.out.println("Your cards are: " + player.getHand().toList().toString());
				gameOver = doAccusation();
				if (!gameOver) {
					player.losesGame();
				} else {
					winner = player;
				}
			} else if (decision.equals("s")) {
				player.makesSuggestion(cell.getRoom());
				System.out.println("Your cards are: " + player.getHand().toList().toString());
				doSuggestion(player, cell.getRoom());
			}
			player.resetSuggestion();
			// next players turn
			turn++;
			if (turn == players.size()) {
				turn = 0;
			}
		}
		// Clean up - games over.
		System.out.println("GAME OVER\n" + winner.getName() +  " wins the game!");
		System.exit(0);
	}

	/**
	 * Method to do an accusation, returns true if accusation is correct, false
	 * otherwise
	 * 
	 * @return boolean, whether accusation was successful or not
	 */
	public boolean doAccusation() {
		System.out.println(); // visual spacing for output
		String weapon = cleanString(ask("Weapons: " + weapons + "\nWhat weapon do you want to accuse:",
				"Error please enter a weapon", weapons));
		String character = cleanString(ask("Characters: " + characters + "\nWhat character do you want to accuse:",
				"Error please enter a character", characters));
		String room = cleanString(ask("Rooms: " + rooms + "\nWhat room do you want to accuse:", "Error please enter a room", rooms));
		Accusation acus = new Accusation(new RoomCard(room), new WeaponCard(weapon), new CharacterCard(character));

		clearScreen();
		askPlayer("Acusation made - show all: \n" + "Weapon: " + weapon + "\nCharacter: " + character + "\nRoom: "
				+ room + "\nPress enter to continue your turn and reveal the envelope:");
		clearScreen();
		
		System.out.println("\nEnvelope contains:\n" + envelope.toString());
		
		if (acus.testAccusation(envelope)) {
			System.out.println("\n*******************************************************");
			System.out.println("\n******************CORRECT ACCUSATION*******************");
			System.out.println("\n*******************************************************");
			return true;
		}
		System.out.println("\n*******************************************************");
		System.out.println("\n****************INCORRECT ACCUSATION*******************");
		System.out.println("\n*******************************************************");
		askPlayer("Sorry but your game is over and you are only allowed to refute\nPress enter to continue:");

		return false;
	}

	/**
	 * Method to execute a suggestion made by a player, this involves creating
	 * suggestion and refuting
	 * 
	 * @param player
	 * @param room
	 */
	public void doSuggestion(Player player, String room) {
		// Create suggestion
		System.out.println(); // visual spacing for output
		String weapon = cleanString(ask("Weapons: " + weapons + "\nWhat weapon do you want to suggest?",
				"Error please enter a weapon", weapons));
		String character = cleanString(ask("Characers: " + characters + "\nWhat character do you want to suggest?",
				"Error please enter a character", characters));
		Suggestion sug = new Suggestion(new RoomCard(room), new WeaponCard(weapon), new CharacterCard(character));
		// Move character and weapon to room
		board.movePlayerWeaponToRoom(player, room, weapon);
		clearScreen();
		System.out.println("Weapon " + weapon + ", and character " + character + ", moved to room " + room);
		refute(sug, player);
	}

	/**
	 * Makes players that can refute a suggestion refute A refute is where they show
	 * a card that was suggested to be the murder circumstances to prove it wrong
	 * 
	 * @param s                   - Suggestion, cards suggested to be the murder
	 *                            circumstance
	 * @param playerThatSuggested
	 */
	public void refute(Suggestion s, Player playerThatSuggested) {
		// find player after this player
		int turn = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(playerThatSuggested)) {
				turn = i + 1;// +1 for the next player
			}
		}
		// loop through the remaining players
		while (true) {
			// wraparound for getting next refute player
			if (turn == players.size()) {
				turn = 0;
			}
			Player player = players.get(turn);
			if (player.equals(playerThatSuggested)) { // finished refutes
				System.out.println("Refutes finished.");
				break;
			}

			// Add possible refutes to list and then control flow off size of list (0 or >0)
			ArrayList<Card> possRefutes = new ArrayList<Card>();
			for (Card playerCard : player.getHand().getCards()) {
				for (Card suggestionCard : s.getCards()) {
					if (playerCard.equals(suggestionCard)) {
						possRefutes.add(playerCard);
					}
				}
			}
			if (possRefutes.size() != 0) {
				ArrayList<String> strPossRefutes = new ArrayList<String>();
				for (Card c : possRefutes) {
					strPossRefutes.add(c.getName());
				}
				askPlayer(player.getName()  + " turn to refute. Press enter to continue:");
				System.out.println("Cards suggested: \n" + s.toString());
				System.out.println("Cards " + player.getName() + " can refute with: " + strPossRefutes);
				String cardToRefute = cleanString(ask("What card woud you like to refute with? ",
						"Error please enter a card from: ", strPossRefutes));
				clearScreen();
				System.out.println("For " + playerThatSuggested.getName() 
						+ " to see: " + player.getName()  + " has refuted with: " + cardToRefute);
				askPlayer("Press enter to continue:");
				clearScreen();
			} else {
				System.out.println("Player " + player.getName() + player.getNumber() + " unable to refute.");
				askPlayer("Press enter to continue:");
			}
			turn++;
		}
	}

	/**
	 * method to get a dice roll, e.g. sum of 2 random numbers 1- 6
	 * 
	 * @return int
	 */
	public int diceRoll() {
		Random rand = new Random();
		int dice1 = rand.nextInt(5) + 1;
		int dice2 = rand.nextInt(5) + 1;
		return dice1 + dice2;
	}

	/**
	 * print what happening for players
	 */
	public void pState(int numPlayers) {
		for (int i = 0; i < numPlayers; i++) {
			Player p = players.get(i + 1);
			System.out.println("Player " + (i + 1) + " named: " + p.getName() + " playing: " + p.getCharacter());
		}
	}

	/**
	 * Method to set name of players and the character they're playing
	 * 
	 * @param players
	 * @param numPlayers
	 * @return computer Players
	 */
	public ArrayList<Player> charactersAndNames(int numPlayers) {
		ArrayList<String> indexChoices = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5"));
		ArrayList<String> characterArray = new ArrayList<String>(Arrays.asList("Mrs. White", "Mr. Green",
				"Mrs. Peacock", "Professor Plum", "Miss Scarlett", "Colonel Mustard"));
		// repeats for each players
		for (int reps = 0; reps < numPlayers; reps++) {
			Player player = players.get(reps);
			player.setName(askPlayer("Player " + (reps + 1) + " please enter your name: ") + " (" + (reps + 1) + ")");
			player.setCharacter(characterArray.get(reps));
			characterArray.remove(reps);
			System.out.println("You are " + player.getName());
		}
		ArrayList<Player> computerPlayers = new ArrayList<Player>();
		for (int i = 0; i < characterArray.size(); i++) {
			Player p = new Player(numPlayers + i + 1);
			String c = characterArray.get(i);
			p.setCharacter(c);
			p.setName(c);
			computerPlayers.add(p);
		}
		return computerPlayers;
	}

	/**
	 * Method to ask for input from user, has a list of target values, a question
	 * and an error msg
	 * 
	 * @param question
	 * @param errorMsg
	 * @param targetValues
	 * @return answer - result
	 */
	public String ask(String question, String errorMsg, ArrayList<String> targetValues) {
		boolean found = false;
		String result = "";
		while (!found) {
			result = askPlayer(question);
			for (String tv : targetValues) {
				if (result.equals(tv)) {
					found = true;
				}
			}
			if (!found) {
				System.out.println(errorMsg);
				System.out.println(result);
				System.out.println("From : " + targetValues);
			}
		}
		return result;
	}

	/**
	 * gets the string into the correct format e.g. each words capitalised rest
	 * lower case
	 * 
	 * @param str
	 * @return cleaned string
	 */
	public String cleanString(String s) {
		String[] stringA = s.split(" ");
		String str1 = stringA[0];
		if (stringA.length > 1) {
			String str2 = stringA[1];
			String s1 = str1.substring(0, 1).toUpperCase() + str1.substring(1).toLowerCase();
			String s2 = str2.substring(0, 1).toUpperCase() + str2.substring(1).toLowerCase();
			return s1 + " " + s2;
		}
		return str1.substring(0, 1).toUpperCase() + str1.substring(1).toLowerCase();
	}

	/**
	 * Checks if game is over - if all players apart from one have made accusations
	 * and sets the winner to the last player
	 * 
	 * @return boolean
	 */
	private boolean canPlay() {
		int playersStillIn = 0;
		for (Player p : players)
			if (!p.hasLost())
				playersStillIn++;

		if (playersStillIn > 1)
			return true;

		for (Player p : players)
			if (!p.hasLost())
				winner = p;
		return false;
	}

	/**
	 * Method to display game information
	 */
	public void helpMessage() {
		System.out.println("------------------------Game Information-------------------------------\n" + 
				"Doors: Only way to enter and except a room is through a doorway.\n" + 
				"Doorways are marked with two characters with the first being D\n" + 
				"and the second letter is direction of where the entrance faces\n" + 
				"e.g DW is a door to a room that can only be entered through the\n" + 
				"west tile.\n" + 
				"Be careful with your move decisions!\n" + 
				"Players cannot move into a tile that have already previously visited\n" + 
				"this turn\n" + 
				"-------Board Keys--------\n" + 
				"Weapons:\n" + 
				"Ca = Candlestick\n" + 
				"Dg = Dagger\n" + 
				"Lp = Lead Pipe\n" + 
				"Rv = Revolver\n" + 
				"Rp= Rope\n" + 
				"Sp= Spanner\n" + 
				"Tiles:\n" + 
				"! = empty tile(non-accessible)\n" + 
				"# = general floor tile/corridor tile\n" + 
				"D = door tile\n" + 
				"alpabet letter = room tile\n" + 
				"1-6 =players\n" +
				"--------------------------------------------------------------------------------\n"
				+"");
	}

	/**
	 * method to clear screen so that other players cannot see turn information
	 */
	public void clearScreen() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
		board.displayBoard(); 
	}
	
	/**
	 * getter for players - for testing
	 * @return arraylist of players
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}

	/**
	 * Asks the player for input
	 * 
	 * @param args
	 */
	public String askPlayer(String s) {
		String ans;
		if(!testing) {
			System.out.print(s);
			Scanner sc = new Scanner(System.in);
			ans = sc.nextLine();
		}else {
			ans = testSc.next();
		}
		return ans;
	}

	public static void main(String args[]) {
		new Cluedo(false,"");
	}
}
