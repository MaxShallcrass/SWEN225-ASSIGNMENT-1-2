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

	public static void main(String args[]) {
new CluedoUI();
		
	}
	
	public CluedoUI() {
		JFrame frame=new JFrame();
		Board gameBoard=new Board(null, null);
		startGUI(frame, gameBoard);

	}
	
	public void startGUI(JFrame frame,Board gameBoard) {
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.getRootPane().setLayout(new BorderLayout());
				JPanel board = createBoard(gameBoard);
				frame.getRootPane().add(board, BorderLayout.CENTER);	
				frame.setMaximumSize(new Dimension(800, 800));
				frame.pack();
				frame.setResizable(false);
				frame.setVisible(true);
				//JOptionPane
				startGame();
	}
	/**
	 * Starts the game, intialising the number of players
	 * and the names/characters
	 */
	private void startGame() {
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
		
		for(int i=0; i< Integer.parseInt(numPlayers); i++) {
			
		}
		
		
       // System.out.println(n);
		//JOptionPane numPlayers = new JOptionPane(arg0, arg1, arg2, arg3, arg4)
		
		
		
	}
	
	/**
	 * 
	 */
	public  JPanel createBoard(Board gameBoard) {
		JPanel board = new JPanel();
		board.setSize(720,750);
		board.setMaximumSize(new Dimension(720, 750));
		board.setLayout(new GridLayout(25, 24,1,1));
		board.setBackground(Color.black);

		gameBoard.addWeapons();
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				board.add(gameBoard.getCellAt(new Location(x, y))) ;
			}
		}
		return board;
	}
	
	/**
	 * Create cells in the Tetris visualization. They use the Game to chose their
	 * color.
	 */
	public  JLabel cell(int x, int y, Board b) {
        return new JLabel(new ImageIcon("resource/boardtiles/dagger.jpg")) ;
    }
}