import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/** Author: Eric Gerardi
 *  
 *  The Framework class modifies MasterGameProcess.frame to display a visual grid (Swing)
 *  for the user to observe the actions from the DecisionFactory Class
 */

public class Framework {
	
	private static JLabel [][] grid;
	private int gridPieceDimension = 25;
	private int numRows;
	private int numCols;
	
	JLabel moveDirection;
	JLabel location;
	JLabel signal;
	JLabel moveCount;
	JButton playButton;
	JButton pauseButton;
	JButton stopButton;
	
	public Framework (int nRows, int nCols) {
		
		numRows = nRows;
		numCols = nCols;
		
		initalizeGrid(numRows, numCols);
		
		setPortal(numRows - 3, numCols - 1);
		
		initalizeJLabels();
		
		initalizeButtons();
	}
	
	private void initalizeGrid (int numRows, int numCols) {
		grid = new JLabel[numRows][numCols];
		
		for (int row = 0; row < numRows; row++) 
		{
			for (int col = 0; col < numCols; col++)
			{
				grid[row][col] = new JLabel();
				grid[row][col].setBounds(gridPieceDimension*col, gridPieceDimension*row, gridPieceDimension, gridPieceDimension);
				
				if (row == numRows - 1 || col == numCols - 1 || row == 0 || col == 0 ) //If row or col is the border, set to wall
				{
					grid[row][col].setIcon(new ImageIcon( GameboardPiece.getWall() ));
				}
				else
				{
					grid[row][col].setIcon(new ImageIcon( GameboardPiece.getEmpty() ));
				}
				
				grid[row][col].setName( "r" + Integer.toString(row) + "c" + Integer.toString(col) );
				MasterGameProcess.frame().add(grid[row][col]);
			}
		}
		
		MasterGameProcess.frame().setSize((numCols + 1) * gridPieceDimension - 8, (numRows + 8) * gridPieceDimension);
		MasterGameProcess.frame().setLayout(null);
		MasterGameProcess.frame().setVisible(true);
		MasterGameProcess.frame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setPortal (int row, int col) { //Set the portal (with class instantiation)
        grid[row][col].setIcon(new ImageIcon( GameboardPiece.getPortal() ));
    }
	
	public void setPlayerPosition (int rowOld, int colOld, int row, int col) {
		grid[rowOld][colOld].setIcon(new ImageIcon( GameboardPiece.getEmpty() )); //Remove player old position
		if (row >= 0 && col >= 0) //if positive row and col
		{
			grid[row][col].setIcon(new ImageIcon( GameboardPiece.getPlayer() ));  //Set player new position
		}
        PlayerPosition.setRow(row);
        PlayerPosition.setCol(col);
    }
	
	public String getGridPiece (int row, int col) {
		return grid[row][col].getIcon().toString();
	}
	
	
	
	private void initalizeJLabels () {
		moveDirection = new JLabel("Move Direction: Player Start");
		moveDirection.setBounds(0, gridPieceDimension*numRows, gridPieceDimension*numCols, gridPieceDimension);
		MasterGameProcess.frame().add(moveDirection);
		
		
		location = new JLabel("Location: R " + PlayerPosition.getRow() + " C " + PlayerPosition.getCol());
		location.setBounds(0, (gridPieceDimension*numRows) + gridPieceDimension, gridPieceDimension*numCols, gridPieceDimension);
		MasterGameProcess.frame().add(location);
		
		
		signal = new JLabel("Signal: 0");
		signal.setBounds(0, (gridPieceDimension*numRows) + (gridPieceDimension * 2), gridPieceDimension*numCols, gridPieceDimension);
		MasterGameProcess.frame().add(signal);
		
		
		moveCount = new JLabel("Move Count: 0");
		moveCount.setBounds(0, (gridPieceDimension*numRows) + (gridPieceDimension * 3), gridPieceDimension*numCols, gridPieceDimension);
		MasterGameProcess.frame().add(moveCount);
	}
	
	public void setTextJLabel_MoveDirection (String s) {
		moveDirection.setText("Move Direction: " + s);
	}
	
	public void setTextJLabel_Location () {
		if (PlayerPosition.getRow() >= 0 && PlayerPosition.getCol() >= 0)
		{
			location.setText("Location: R " + PlayerPosition.getRow() + " C " + PlayerPosition.getCol());
		}
		else
		{
			location.setText("Location: Portaled");
		}
	}
	
	public void setTextJLabel_Signal (int i) {
		String text = null;
		switch (i) {
			case -1 : text = "Wall Collision";
				break;
			case 1: text = "Successful Move";
				break;
			case 2: text = "PORTALATED!!";
				break;
			default: text = "No Signal";
				break;
		}
		
		signal.setText("Signal: " + text);
	}
	
	public void setTextJLabel_MoveCount (String s) {
		moveCount.setText("Move Count: " + s);
	}
	
	private void initalizeButtons () {
		ImageIcon playIcon = new ImageIcon("src/images/playButton.png");
		playButton = new JButton(playIcon);
		playButton.setBounds(0, (gridPieceDimension*numRows) + (gridPieceDimension * 4), 150, 50);
		playButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 MasterGameProcess.pushPlay();
	        	 moveCount.setText("Move Count: HELLO FROM PLAY BUTTON");
	         }          
	    });
		MasterGameProcess.frame().add(playButton);
	}
	
	
}
