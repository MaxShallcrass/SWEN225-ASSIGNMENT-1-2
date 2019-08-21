import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class CluedoUI extends JFrame implements ActionListener, MouseListener, KeyListener {
	private int size = 100;
	private Board gameBoard;
	private ArrayList<Player> players;
	private Envelope envelope;
	private int turn;
	private JPanel jBottom;
	private Player player;
	private boolean nextTurn = false;
	private boolean startedGame=false;
	private static int guiSize = 600;
	private final int BOARDX = 720;
	private final int BOARDY = 750;

	public static void main(String args[]) {
		new CluedoUI();

	}

	/**
	 * Starts the game, calling methods for adding players, letting them choose
	 * there players and building the GUI
	 */
	public CluedoUI() {
		this.setTitle("Cludeo");
		numPlayers();
		getNames();
		deal();
		selectCharacters();
		addPlayersToGameBoard();
		startGUI();
		addMouseListener(this);
		addKeyListener(this);
		turn = 0;
		startedGame = true;
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		runGame();

	}

	/**
	 * Constructs and builds the GUI with all the panels
	 */
	public void startGUI() {
		this.setVisible(true);
		this.setSize(guiSize, guiSize);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Window confirmation for closing game
		addWindowListener(new WindowAdapter() { 
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Cluedo",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		// Setting up menue bar options
		JMenuBar mb = new JMenuBar();
		JMenu acc = new JMenu("Accusation");
		JMenu sug = new JMenu("Suggestion");
		JMenu shrtct = new JMenu("Shortcut Keys");
		JMenu tur = new JMenu("Turn");
		JMenuItem mTur = new JMenuItem("Next Players Turn");
		JMenuItem mAcc = new JMenuItem("Make Accusation");
		JMenuItem mSug = new JMenuItem("Make Suggestion");
		JMenuItem mShrtct = new JMenuItem("Shortcut Key info");
		mShrtct.addActionListener(this);
		mAcc.addActionListener(this);
		mSug.addActionListener(this);
		mTur.addActionListener(this);
		shrtct.add(mShrtct);
		acc.add(mAcc);
		tur.add(mTur);
		sug.add(mSug);
		mb.add(tur);
		mb.add(acc);
		mb.add(sug);
		mb.add(shrtct);
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

		//Panel for cards in the current players hand
		JPanel jHand = new JPanel();
		jHand.addKeyListener(this);
		jHand.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jHand.setBackground(Color.blue);
		top.add(jHand);
		//Panel for dice roll
		JPanel jDice = new JPanel();
		jDice.addKeyListener(this);
		jDice.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jDice.setBackground(Color.blue);
		bot.add(jDice);
	}

	/**
	 * Method to run a suggestion call
	 */
	public void makeSuggestion() {
		ArrayList<String> characters = new ArrayList<String>(Arrays.asList("Miss Scarlett", "Colonel Mustard",
				"Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
		ArrayList<String> weapons = new ArrayList<String>(
				Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"));
		ArrayList<String> rooms = new ArrayList<String>(Arrays.asList("Kitchen", "Ballroom", "Conservatory",
				"Billiard Room", "Library", "Study", "Hall", "Lounge", "Dining Room"));
		// Getting the number of players in the game
		String room = gameBoard.getCellAt(player.getLoc()).getRoom();
		player.setLastRoom(room);
		String character = null;
		String weapon = null;
		while (character == null) {
			try {
				character = (String) JOptionPane.showInputDialog(null, "What character do you suggest?",""+""+player.getName()+" ("+player.getCharacter()+")",
						JOptionPane.QUESTION_MESSAGE, null, characters.toArray(), characters.toArray());
			} catch (Exception e) {
			}
		}
		while (weapon == null) {
			try {
				weapon = (String) JOptionPane.showInputDialog(null, "What weapon do you sugest?",""+""+player.getName()+" ("+player.getCharacter()+")",
						JOptionPane.QUESTION_MESSAGE, null, weapons.toArray(), weapons.toArray());
			} catch (Exception e) {
			}
		}
		//move items
		Player pMove = null;
		for(Player p : players) {
			if(p.getCharacter().equals(character)) {
				pMove = p;
			}
		}
		player.makesSuggestion(room);
		gameBoard.movePlayerWeaponToRoom(pMove,room,weapon);
		Suggestion sug = new Suggestion(new RoomCard(room), new WeaponCard(weapon), new CharacterCard(character));
		//refute
		JOptionPane.showMessageDialog(this, "The refute stage begins");
		refute(sug);
		player.resetSuggestion();

	}
	
	/**
	 * Method to handle the refute process
	 */
	public void refute(Suggestion s) {
		// find player after this player
		int turnR = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(player)) {
				turnR = i + 1;// +1 for the next player
			}
		}
		// loop through the remaining players
		while (true) {
			// wraparound for getting next refute player
			if (turnR == players.size()) {
				turnR = 0;
			}
			Player playerR = players.get(turnR);
			if (playerR.equals(player)) { // finished refutes
				JOptionPane.showMessageDialog(this, "No one was able to refute!");
				break;
			}

			// Add possible refutes to list and then control flow off size of list (0 or >0)
			ArrayList<Card> possRefutes = new ArrayList<Card>();
			for (Card playerCard : playerR.getHand().getCards()) {
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
				String refuteS = null;
				while (refuteS == null) {
					try {
						refuteS = (String) JOptionPane.showInputDialog(null, "What card do you want to refute with?",
								"" + "" + playerR.getName() + " (" + playerR.getCharacter() + ")",
								JOptionPane.QUESTION_MESSAGE, null, strPossRefutes.toArray(), strPossRefutes.toArray());
					} catch (Exception e) {
					}
				}
				System.out.println(playerR.getName());
				JOptionPane.showMessageDialog(this, "Refuted by " + "" + playerR.getName() + " ("
						+ playerR.getCharacter() + ")" + " with: " + refuteS);
				break;
			} else {
				JOptionPane.showMessageDialog(this, "Player " + "" + playerR.getName() + " (" + playerR.getCharacter()
						+ ")" + " unable to refute.");
			}
			turnR++;
		}
	}
	
	/**
	 * Method run to make accusation call
	 */
	public void makeAccusation() {
		ArrayList<String> characters = new ArrayList<String>(Arrays.asList("Miss Scarlett", "Colonel Mustard",
				"Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
		ArrayList<String> weapons = new ArrayList<String>(
				Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"));
		ArrayList<String> rooms = new ArrayList<String>(Arrays.asList("Kitchen", "Ballroom", "Conservatory",
				"Billiard Room", "Library", "Study", "Hall", "Lounge", "Dining Room"));
		// Getting the number of players in the game
		String room = null;
		String character = null;
		String weapon = null;
		while (room == null) {
			try {
				room = (String) JOptionPane.showInputDialog(null, "What room do you accuse?",
						"" + "" + player.getName() + " (" + player.getCharacter() + ")", JOptionPane.QUESTION_MESSAGE,
						null, rooms.toArray(), rooms.toArray());
			} catch (Exception e) {
			}
		}
		while (character == null) {
			try {
				character = (String) JOptionPane.showInputDialog(null, "What character do you accuse?",
						"" + "" + player.getName() + " (" + player.getCharacter() + ")", JOptionPane.QUESTION_MESSAGE,
						null, characters.toArray(), characters.toArray());
			} catch (Exception e) {
			}
		}
		while (weapon == null) {
			try {
				weapon = (String) JOptionPane.showInputDialog(null, "What weapon do you accuse?",
						"" + "" + player.getName() + " (" + player.getCharacter() + ")", JOptionPane.QUESTION_MESSAGE,
						null, weapons.toArray(), weapons.toArray());
			} catch (Exception e) {
			}
		}
		Accusation acus = new Accusation(new RoomCard(room), new WeaponCard(weapon), new CharacterCard(character));
		if (acus.testAccusation(envelope)) {
			JOptionPane.showMessageDialog(this, "Congragulations!! " + "" + "" + player.getName() + " ("
					+ player.getCharacter() + ") You have won");
			System.exit(0);

		} else {
			player.losesGame();
			JOptionPane.showMessageDialog(this, "You have lost");
		}
		// checking if only one player is left that hasnt lost
		int numPlayersLost = 0;
		for (Player p : players) {
			if (p.hasLost())
				numPlayersLost++;
		}
		if (numPlayersLost == players.size() - 1) { // We have a winner!!
			Player winner = null;
			for (Player p : players) // gets winner
				if (!p.hasLost())
					winner = p;
			JOptionPane.showMessageDialog(this, "Congragulations!! " + "" + "" + winner.getName() + " ("
					+ winner.getCharacter() + ") You have won");

			System.exit(0);
		}

	}

	/**
	 * Starts the game, initializing the number of players and the names/characters
	 */
	private void runGame() {
		boolean gameOver = false;
		player = getPlayerTurn();
		displayBottomForPlayer();
		player.newTurn();
	}

	


	/**
	 * Displays which players turn it is, their hand, buttons and dice
	 * 
	 * @param player
	 */
	public void displayBottomForPlayer() {
		// showing whos turn it is
		JPanel top = (JPanel) jBottom.getComponent(0);
		JPanel bot = (JPanel) jBottom.getComponent(1);
		//getting hand
		JPanel j2 = (JPanel) (top.getComponent(0));
		j2.removeAll();
		System.out.println(player.getHand().getCards());
		for (Card c : player.getHand().getCards()) {
			j2.add(c);
		}
		// showing dice
		JPanel j3 = (JPanel) (bot.getComponent(0));
		j3.removeAll();
		player.setMovesLeft(0);
		j3.add(randDie());
		j3.add(randDie());
		this.setVisible(true);
		this.repaint();
	}

	/**
	 * Returns a JLabel with icon of dice roll and adds amount of moves left to the
	 * player
	 * 
	 * @return
	 */
	public JLabel randDie() {
		Random rand = new Random();
		int roll = rand.nextInt(6) + 1;
		player.setMovesLeft(player.getMovesLeft()+roll); //setting moves left for a player
		JLabel j = new JLabel();
		ImageIcon ii = new ImageIcon("resource/dice/" + roll + ".jpg");
		Image image = ii.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ii = new ImageIcon(image);
		j.setIcon(ii);
		return j;
	}

	/**
	 * Get and return player who's turn it is
	 * 
	 * @return Player
	 */
	public Player getPlayerTurn() {
		player = players.get(turn);
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
		JOptionPane.showMessageDialog(this, ""+player.getName()+" ("+player.getCharacter()+")"+", it is your turn!");
		return player;
	}

	/**
	 * The initial game board is just a background, now that we have players it can
	 * be updated to contain the actual game sate
	 */
	public void addPlayersToGameBoard() {
		ArrayList<String> chosenCharacters = new ArrayList<String>();
		ArrayList<String> freeCharacters = new ArrayList<String>(Arrays.asList("Miss Scarlett", "Colonel Mustard",
				"Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
		// adding chosen characters to array
		for (Player p : players) {
			chosenCharacters.add(p.getCharacter());
		}
		// removing freeCharacters form chosenCharacters
		for (String s : chosenCharacters) {
			freeCharacters.remove(s);
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
	 * method to set player names
	 */
	public void getNames() {
		int count = 0;
		for (Player p : players) {
			count++;
			String name = null;
			while (name == null&& name!="")
		name=	JOptionPane.showInputDialog(null, "Player " + count + " please enter your name: ", "Player Name", 1);
			p.setName(name);
		}
	}

	/**
	 * method to select which characters each player is playing
	 */
	public void selectCharacters() {
		// Picking each players character
		String[] charOptions = { "Miss Scarlett", "Colonel Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock",
				"Professor Plum" };
		for (Player p : players) {
			String playerChoice = null;
			// Getting the number of players in the game
			while (playerChoice == null) {
				try {
					playerChoice = (String) JOptionPane.showInputDialog(null, ""+p.getName()+ " select your character:", "Cluedo",
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
	 * Creates the game board and adds weapons to it.
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
				gameBoard.getCellAt(new Location(x, y)).addKeyListener(this);
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
	 * Moves player to clicked cell if its possible
	 */
	private void movePlayer(Player p, Cell to) {
		if (player.getMovesLeft() != 0) {
			ArrayList<Cell> pathWay = gameBoard.movePlayerMany(p.getLoc(), to.getLoc(), player.getMovesLeft());
			if (pathWay != null) {
				player.setMovesLeft(player.getMovesLeft() - pathWay.size() + 1); // takes off moves made by the player
				for (int i = 0; i < pathWay.size() - 1; i++) {
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					gameBoard.movePlayer(pathWay.get(i).getLoc(), pathWay.get(i + 1).getLoc());
				}
			}
		}
	}

	/**
	 * Get for gui size so that cell can construct at right scale
	 * 
	 * @return int
	 */
	public static int getGuiSize() {
		return guiSize;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Make Accusation")) {
			if(!player.hasLost()&&player.getMovesLeft()!=-1) {
				player.setMovesLeft(-1);
				makeAccusation();
				return;
			}
			JOptionPane.showMessageDialog(this, "You currently cannot make an accusation");
		}
		if (e.getActionCommand().equals("Make Suggestion")&&player.getMovesLeft()!=-1) {
			//check validity 
			if(gameBoard.getCellAt(player.getLoc()).isRoom() && !player.hasLost()) {
				if(!player.getLastRoom().equals(gameBoard.getCellAt(player.getLoc()).getRoom())) {
					player.setMovesLeft(-1);
					makeSuggestion();
					return;
				}
			}
			JOptionPane.showMessageDialog(this, "You currently cannot make a suggestion");
		}
		if (e.getActionCommand().equals("Next Players Turn")) {
			runGame();

		}
		if (e.getActionCommand().equals("Shortcut Key info")) {
			JOptionPane.showMessageDialog(this, "Keys: \n A = Make assumption \n N = Next turn \n  S = Make suggestion");

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
			movePlayer(player, c);
			c.resetSelectedCell();
		}
	}

	/**
	 * Code for key events - shorcut keys for Game play
	 * 
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (startedGame) {
			if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') { // Doing assumption
				if (!player.hasLost() && player.getMovesLeft() != -1) {
					player.setMovesLeft(-1);
					makeAccusation();
					return;
				}
				JOptionPane.showMessageDialog(this, "You currently cannot make an accusation");
			}
			if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') { // doing suggestion
				if (gameBoard.getCellAt(player.getLoc()).isRoom() && !player.hasLost()) {
					if(!player.getLastRoom().equals(gameBoard.getCellAt(player.getLoc()).getRoom())) {
						player.setMovesLeft(-1);
						makeSuggestion();
						return;
					}
				}
				JOptionPane.showMessageDialog(this, "You currently cannot make a suggestion");
			}

			if (e.getKeyChar() == 'n' || e.getKeyChar() == 'N') { // starts next turn
				runGame();
			}
		}
	}
}