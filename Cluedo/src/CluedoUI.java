import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CluedoUI extends JFrame implements ActionListener, MouseListener {

	private int size = 100;
	private Board gameBoard;
	// private Jthis this;
	private ArrayList<Player> players;
	private Envelope envelope;
	private int turn;
	private JPanel jBottom;
	private Player player;

	// board.setMaximumSize(new Dimension(720, 750));w
	private static int guiSize = 600;
	private final int BOARDX = 720;
	private final int BOARDY = 750;
	// private ArrayList<play>

	// pathfinding

	public static void main(String args[]) {
		new CluedoUI();

	}

	public CluedoUI() {
		this.setTitle("Cludeo");
		numPlayers();
		deal();
		selectCharacters();
		addPlayersToGameBoard();
		startGUI();
		addMouseListener(this);
		runGame();

	}

	public void startGUI() {
		this.setVisible(true);
		this.setSize(guiSize, guiSize);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		JMenuBar mb = new JMenuBar();
		JMenu acc = new JMenu("Accusation");
		JMenu sug = new JMenu("Suggestion");
		JMenuItem mAcc = new JMenuItem("Make Accusation");
		JMenuItem mSug = new JMenuItem("Make Suggestion");
		mAcc.addActionListener(this);
		mSug.addActionListener(this);
		acc.add(mAcc);
		sug.add(mSug);
		mb.add(acc);
		mb.add(sug);
		this.setJMenuBar(mb);

		// this.getRootPane().setLayout(new BorderLayout());
		JPanel jBoard = createBoard(gameBoard);
		this.add(jBoard, BorderLayout.CENTER);/// .getRootPane()

		jBottom = new JPanel();
		jBottom.setLayout(new FlowLayout(FlowLayout.LEFT));
		jBottom.setSize(guiSize, (int) (guiSize * 0.2));
		jBottom.setBackground(Color.blue);
		this.add(jBottom, BorderLayout.SOUTH);
		this.pack();

		JPanel top = new JPanel();
		JPanel bot = new JPanel();
		top.setBackground(Color.blue);
		bot.setBackground(Color.blue);
		jBottom.add(top, BorderLayout.NORTH);
		jBottom.add(bot, BorderLayout.SOUTH);

		/*
		 * JLabel label = new JLabel(""); top.add(label);
		 */

		JPanel jHand = new JPanel();
		jHand.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// jHand.setSize(guiSize/2,(int)(guiSize*0.2));
		jHand.setBackground(Color.blue);
		top.add(jHand);

		JPanel jDice = new JPanel();
		jDice.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// jHand.setSize(guiSize/2,(int)(guiSize*0.2));
		jDice.setBackground(Color.blue);
		bot.add(jDice);

		// JOptionPane

	}

	/**
	 * method to run a suggestion call
	 */
	public void makeSuggestion() {
		System.out.println("suggestion made");
	}

	/**
	 * method run to make accusation call
	 */
	public void makeAccusation() {
		ArrayList<String> roomC = new ArrayList<String>();
		ArrayList<String> charC = new ArrayList<String>();
		ArrayList<String> weapC = new ArrayList<String>();
		ArrayList<Card> acc = new ArrayList<Card>();
		for(Card c : player.getHand().getCards()) {
			if(c instanceof CharacterCard) {
				charC.add(c.getName());
			}
			if(c instanceof WeaponCard) {
				weapC.add(c.getName());
			}
			if(c instanceof RoomCard) {
				roomC.add(c.getName());
			}
		}
		String numPlayers = null;
		// Getting the number of players in the game
		while (numPlayers == null) {
			try {
				numPlayers = (String) JOptionPane.showInputDialog(null, "What are the number of players?", "Cluedo",
						JOptionPane.QUESTION_MESSAGE, null, roomC.toArray(), roomC.toArray());
			} catch (Exception e) {
			}
		}
		
		
	}

	/**
	 * Starts the game, intialising the number of players and the names/characters
	 */
	private void runGame() {
		boolean gameOver = false;

		//////// Testing for Pathfinding\\\\\\\
		Player test = players.get(0);
		test.setLoc(new Location(6, 2));
		gameBoard.getCellAt(new Location(6, 2)).setPlayer(test);

		turn = 0; // index of player whos turn it is
		while (!gameOver) {
			player = getPlayerTurn();
			displayBottomForPlayer(player);

		}

		// Location l = new Location(5, 5);
		// gameBoard.getCellAt(l).setWeapon("Ca");

		// System.out.println(n);
		// JOptionPane numPlayers = new JOptionPane(arg0, arg1, arg2, arg3, arg4)

	}

	/**
	 * displays which players turn it is, their hand, buttons and dice
	 * 
	 * @param player
	 */
	public void displayBottomForPlayer(Player player) {
		// showing whos turn it is
		JPanel top = (JPanel) jBottom.getComponent(0);
		JPanel bot = (JPanel) jBottom.getComponent(1);
		//JLabel j1 = (JLabel) (top.getComponent(0));
		// showing text
		// j1.setText("Turn of: "+player.getCharacter());
		// showing hand
		
		JPanel j2 = (JPanel) (top.getComponent(0));
		j2.removeAll();
		for (Card c : player.getHand().getCards()) {
			j2.add(c);
		}
		// showing dice
		JPanel j3 = (JPanel) (bot.getComponent(0));
		j3.add(randDie());
		j3.add(randDie());
		this.setVisible(true);
		while (true) {
		}

	}
	
	/**
	 * returns a JLabel with icon of dice roll
	 * @return
	 */
	public JLabel randDie() {
		Random rand = new Random();
		int roll = rand.nextInt(5) + 1;
		JLabel j = new JLabel();
		ImageIcon ii = new ImageIcon("resource/dice/" + roll + ".jpg");
		Image image = ii.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH); 
		ii = new ImageIcon(image);
		j.setIcon(ii);
		return j;
	}

	/**
	 * get and return player whos turn it is
	 * 
	 * @return Player
	 */
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
		if (turn == players.size()) {
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
		board.setSize(guiSize / BOARDX * BOARDY, 750 * 1);
		board.setMaximumSize(new Dimension(guiSize / BOARDX * BOARDY, 750 * 1));
		board.setLayout(new GridLayout(25, 24, 1, 1));
		board.setBackground(Color.black);

		gameBoard.addWeapons();
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				gameBoard.getCellAt(new Location(x, y)).addMouseListener(this);
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
	 * Moves player
	 */
	private void movePlayer(Player p, Cell to) {

		gameBoard.movePlayerMany(p.getLoc(), to.getLoc(), 10);

	}

	/**
	 * geet for gui size so that cell can construct at right scale
	 * 
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Make Accusation")){
			makeAccusation();
		}
		if(e.getActionCommand().equals("Make Suggestion")){
			makeSuggestion();
		}
		

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * For pathfinding to find the currently selected cell to move to
	 * 
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		Cell c = gameBoard.getCellAt(new Location(0, 0)).getSelected();
		if (c != null) {
			Location cLoc = c.getLoc();
			System.out.println("XPos: " + cLoc.getX() + " YPos: " + cLoc.getY());
			movePlayer(players.get(0), c);

			c.resetSelectedCell();
		}
	}
}