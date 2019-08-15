import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
		// GridBagLayout gridbag = new GridBagLayout();
				// GridBagConstraints c = new GridBagConstraints();

			
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.getRootPane().setLayout(new BorderLayout());
				JPanel board = createBoard(gameBoard);
				frame.getRootPane().add(board, BorderLayout.CENTER);
				
				frame.setPreferredSize(new Dimension(800, 800));
				frame.pack();
				frame.setResizable(true);
				frame.setVisible(true);
	}
	
	/**
	 * 
	 */
	public  JPanel createBoard(Board gameBoard) {
		JPanel board = new JPanel();
		//board.setSize(720,750);
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
	/**
	 * Gets the image for the specified key
	 */
	private void getImage() {}
	
	//private JFrame createBoard() {
	//	Jframe board= new 
	//}
}
