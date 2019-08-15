import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class CluedoUI {
	
	//private JFrame frame;
	
	
	public static void main(String args[]) {
		
			JPanel board=createBoard();
		
		//GridBagLayout gridbag = new GridBagLayout();
      //  GridBagConstraints c = new GridBagConstraints();
		
			JFrame frame = new JFrame("My First GUI");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.getRootPane().setSize(1200,1200);
	      // frame.setResizable(false);
			//frame.getRootPane().setLayout(new GridLayout(arg0, arg1));
			frame.getRootPane().add(board, BorderLayout.CENTER);
	     //  JButton button = new JButton("Press");
	      // frame.getContentPane().add(button); // Adds Button to content pane of frame
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowOpened(WindowEvent e) {
					board.requestFocus();
				}
			});
			frame.pack();
			
			frame.setVisible(true);
	       
	       
	     //  Cluedo cludeo = new Cluedo(false,"");
	}
	
	/**
	 * 
	 */
	public static JPanel createBoard() {
		JPanel board = new JPanel();
		board.setSize(720,750);

		board.setLayout(new GridLayout(24, 25));
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				board.add(new FloorCell(new Location(x, y), 'a')) ;
			}
		}
		
		return board;
	}
	
	/**
	 * Create cells in the Tetris visualization. They use the Game to chose their
	 * color.
	 */
	public static JPanel cell(int col, int row, String image){
		JPanel cell = new JPanel() ;
		ImageIcon pic = new ImageIcon("resource/corridor.jpg");
		cell.add(new JLabel(pic));
		//cell.setBackground(Color.blue);
	return cell;
	}
	/**
	 * Gets the image for the specified key
	 */
	private void getImage() {}
	
	//private JFrame createBoard() {
	//	Jframe board= new 
	//}
}
