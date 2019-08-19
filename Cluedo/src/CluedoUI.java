import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CluedoUI {

	// private JFrame frame;

	private int size = 100;
	private Board gameBoard;
	private JFrame frame;
	private ArrayList<Player> players;
	private Envelope envelope;
	int turn;
	
	//board.setMaximumSize(new Dimension(720, 750));
	private static int guiSize = 500;
	private final int BOARDX = 720;
	private final int BOARDY = 750;
	// private ArrayList<play>

	public static void main(String args[]) {
		new CluedoUI();

	}

	public CluedoUI() {
		frame = new JFrame("Cluedo");
		gameBoard = new Board(null, null);
		startGUI(gameBoard);

	}

	public void startGUI(Board gameBoard) {

		frame.setVisible(true);
		frame.setSize(guiSize, guiSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		JMenuBar mb = new JMenuBar();
		JMenu sg = new JMenu("Start Game");
		JMenu eg = new JMenu("End Game");
		mb.add(sg);
		mb.add(eg);
		frame.setJMenuBar(mb);

		// frame.getRootPane().setLayout(new BorderLayout());
		JPanel jBoard = createBoard(gameBoard);
		frame.add(jBoard, BorderLayout.CENTER);/// .getRootPane()
		
		JPanel jBottom = new JPanel();
		jBottom.setSize(guiSize,(int)(guiSize*0.2));
		jBottom.setBackground(Color.red);
		frame.add(jBottom,BorderLayout.SOUTH);
		frame.pack();

		JLabel label = new JLabel("Hello there");
		jBottom.add(label);
		
		// JOptionPane
		startGame();
	}

	/**
	 * Starts the game, intialising the number of players and the names/characters
	 */
	private void startGame() {
		// setup
		numPlayers();
		selectCharacters();
		deal();
		addPlayersToGameBoard();
		// game loop
		boolean gameOver = false;
		turn = 0; // index of player whos turn it is
		while (!gameOver) {
			Player player = getPlayerTurn();
		}

		// Location l = new Location(5, 5);
		// gameBoard.getCellAt(l).setWeapon("Ca");

		// System.out.println(n);
		// JOptionPane numPlayers = new JOptionPane(arg0, arg1, arg2, arg3, arg4)

	}
	
	public Player getPlayerTurn() {
		Player player = players.get(turn);
		// choosing a player that can play
		while (player.hasLost()) {
			if (++turn == players.size())
				turn = 0;
			player = players.get(turn);
		}
		player.newTurn(); // for keeping track of players movements
		
		turn++;
		if(turn == players.size()) {
			turn = 0;
		}
		return player;
	}

	/**
	 * the initial game board is just a background, now that we have players it can
	 * be updated to contain the actual game sate
	 */
	public void addPlayersToGameBoard() {
		ArrayList<String> chosenCharacters = new ArrayList<String>();
		ArrayList<String> freeCharacters = new ArrayList<String>(Arrays.asList("Miss Scarlett", "Colonel Mustard",
				"Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
		// removing freeCharacters form chosenCharacters
		for (String s : chosenCharacters) {
			for (int f = 0; f < freeCharacters.size(); f++) {
				if (s.equals(freeCharacters.get(f))) {
					freeCharacters.remove(f);
				}
			}
		}
		ArrayList<Player> computerPlayers = new ArrayList<Player>();
		for (String s : freeCharacters) {
			Player p = new Player(-1);
			p.setCharacter(s);
			computerPlayers.add(p);
		}
		gameBoard = new Board(players, computerPlayers);
	}

	/**
	 * method to get input from player which is number of people playing game
	 */
	public void numPlayers() {
		String[] numOptions = { "3", "4", "5", "6" };
		String numPlayers = null;
		// Getting the number of players in the game
		while (numPlayers == null) {
			try {
				numPlayers = (String) JOptionPane.showInputDialog(null, "What are the number of players?", "Cluedo",
						JOptionPane.QUESTION_MESSAGE, null, numOptions, numOptions[0]);
			} catch (Exception e) {
			}
		}
		players = new ArrayList<Player>();
		// Creating player list
		for (int i = 0; i < Integer.parseInt(numPlayers); i++) {
			if (players == null) {
				System.out.println("null");
			}
			players.add(new Player(-1));
		}
	}

	/**
	 * method to select which characters each player is playing
	 */
	public void selectCharacters() {
		// Picking each players character
		String[] charOptions = { "Miss Scarlett", "Colonel Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock",
				"Professor Plum" };
		int count = 0;
		for (Player p : players) {
			count++;
			String playerChoice = null;
			// Getting the number of players in the game
			while (playerChoice == null) {
				try {
					playerChoice = (String) JOptionPane.showInputDialog(null, "Player " + count + " ?", "Cluedo",
							JOptionPane.QUESTION_MESSAGE, null, charOptions, charOptions[0]);
					if (playerChoice.equals("")) {
						playerChoice = null;
					}
				} catch (Exception e) {
				}
			}
			// remove player choice
			for (int i = 0; i < charOptions.length; i++) {
				if (charOptions[i].equals(playerChoice)) {
					charOptions[i] = "";
				}

			}
			// set players character
			p.setCharacter(playerChoice);
		}
	}

	/**
	 * 
	 */
	public String ask(String s) {
		String ans = null;
		try {
			while (ans == null) {
				ans = (String) JOptionPane.showInputDialog(s);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return ans;
	}

	/**
	 * 
	 */
	public JPanel createBoard(Board gameBoard) {
		JPanel board = new JPanel();
		board.setSize(guiSize/BOARDX*BOARDY, 750*1);
		board.setMaximumSize(new Dimension(guiSize/BOARDX*BOARDY, 750*1));
		board.setLayout(new GridLayout(25, 24, 1, 1));
		board.setBackground(Color.black);

		gameBoard.addWeapons();
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				board.add(gameBoard.getCellAt(new Location(x, y)));
			}
		}
		return board;
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
	 * geet for gui size so that cell can construct at right scale
	 * @return int 
	 */
	public static int getGuiSize() {
		return guiSize;
	}
	
	
	/**
	 * Create cells in the Tetris visualization. They use the Game to chose their
	 * color.
	 */
	public JLabel cell(int x, int y, Board b) {
		return new JLabel(new ImageIcon("resource/boardtiles/dagger.jpg"));
	}
}