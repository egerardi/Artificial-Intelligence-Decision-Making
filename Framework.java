import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	
	private static JLabel [][] grid; //Game board grid
	private int gridPieceDimension = 25; //Size of grid piece (m by m)
	private int numRows;
	private int numCols;
	
	//Labels to display current game statistics
	JLabel moveDirection;
	JLabel location;
	JLabel signal;
	JLabel moveCount;
	JLabel moveCount_SecondRun;
	//Buttons for user to start and pause the game
	JButton playButton;
	JButton pauseButton;
	
	public Framework (int nRows, int nCols) {
		
		numRows = nRows;
		numCols = nCols;
		
		initalizeGrid(numRows, numCols);
		
		setPortal(4,14);
		
		initalizeStatisticsTextLabels();
		
		initalizePlayButton();
		
		initalizePauseButton();
	}
	
	/** Creates the grid.
	 * 	Set each grid piece location, image icon, mouse listener.
	 * 
	 * @param numRows
	 * @param numCols
	 */
	private void initalizeGrid (int numRows, int numCols) {
		grid = new JLabel[numRows][numCols]; //Grid of JLabels
		
		for (int row = 0; row < numRows; row++) 
		{
			for (int col = 0; col < numCols; col++)
			{
				grid[row][col] = new JLabel();
				grid[row][col].setBounds(gridPieceDimension*col, gridPieceDimension*row, gridPieceDimension, gridPieceDimension); //Set grid piece location
				
				if (row == numRows - 1 || col == numCols - 1 || row == 0 || col == 0 ) //If row or col is the border
				{
					grid[row][col].setIcon(new ImageIcon( GameboardPiece.getWall() )); //Set grid piece to wall
				}
				else
				{
					grid[row][col].setIcon(new ImageIcon( GameboardPiece.getEmpty() )); //Set grid piece to empty
				}
				
				grid[row][col].setName( "r" + Integer.toString(row) + "c" + Integer.toString(col) ); //Set grid piece name
				
				grid[row][col].addMouseListener(new MouseAdapter() { //Add mouse listener
	                @Override
	                public void mouseClicked(MouseEvent e) { //When grid piece clicked
	                	switchBetweenWallandEmpty(e);
	                }
	            });
						
				MasterGameProcess.frame().add(grid[row][col]); //Add piece to frame
			}
		}
		
		MasterGameProcess.frame().setSize((numCols + 1) * gridPieceDimension - 8, (numRows + 8) * gridPieceDimension); //Set frame size
		MasterGameProcess.frame().setLayout(null); //Set frame to no layout
		MasterGameProcess.frame().setVisible(true); //Set frame visible
		MasterGameProcess.frame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set frame close operation
	}
	
	//Set the portal position (with class instantiation)
	private void setPortal (int row, int col) {
        grid[row][col].setIcon(new ImageIcon( GameboardPiece.getPortal() ));
    }
	
	//Set (new) player position
	public void setPlayerPosition (int rowOld, int colOld, int row, int col) {
		grid[rowOld][colOld].setIcon(new ImageIcon( GameboardPiece.getEmpty() )); //Remove icon from player old position
		if (row >= 0 && col >= 0) //if positive row and col
		{
			grid[row][col].setIcon(new ImageIcon( GameboardPiece.getPlayer() ));  //Set player icon in new position
		}
        PlayerPosition.setRow(row); //Set player new row
        PlayerPosition.setCol(col); //Set player new column
    }
	
	//Get grid piece icon
	public String getGridPiece (int row, int col) {
		return grid[row][col].getIcon().toString();
	}
	
	
	//Initialize statistics text labels
	private void initalizeStatisticsTextLabels () {
		moveDirection = new JLabel("Move Direction: Player Start"); //Initialize
		moveDirection.setBounds(0, gridPieceDimension*numRows, gridPieceDimension*numCols, gridPieceDimension); //Set location
		MasterGameProcess.frame().add(moveDirection); //Add to frame
		
		
		location = new JLabel("Location: R " + PlayerPosition.getRow() + " C " + PlayerPosition.getCol()); //Initialize
		location.setBounds(0, (gridPieceDimension*numRows) + gridPieceDimension, gridPieceDimension*numCols, gridPieceDimension); //Set location
		MasterGameProcess.frame().add(location); //Add to frame
		
		
		signal = new JLabel("Signal: 0"); //Initialize
		signal.setBounds(0, (gridPieceDimension*numRows) + (gridPieceDimension * 2), gridPieceDimension*numCols, gridPieceDimension); //Set location
		MasterGameProcess.frame().add(signal); //Add to frame
		
		
		moveCount = new JLabel("Move Count: 0"); //Initialize
		moveCount.setBounds(0, (gridPieceDimension*numRows) + (gridPieceDimension * 3), gridPieceDimension*(numCols / 2), gridPieceDimension); //Set location
		MasterGameProcess.frame().add(moveCount); //Add to frame
		
		moveCount_SecondRun = new JLabel("Move Count Second Run: 0"); //Initialize
		moveCount_SecondRun.setBounds(gridPieceDimension*(numCols / 2) + 1, (gridPieceDimension*numRows) + (gridPieceDimension * 3), gridPieceDimension*(numCols / 2), gridPieceDimension); //Set location
		MasterGameProcess.frame().add(moveCount_SecondRun); //Add to frame
	}
	
	//Update move direction label
	public void setTextJLabel_MoveDirection (String s) {
		moveDirection.setText("Move Direction: " + s);
	}
	
	//Update location label
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
	
	//Update signal label
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
	
	//Update move count label
	public void setTextJLabel_MoveCount (String s) {
		moveCount.setText("Move Count: " + s);
	}
	
	//Update move count second run label
	public void setTextJLabel_MoveCount_SecondRun (String s) {
		moveCount_SecondRun.setText("Move Count Second Run: " + s);
	}
	
	//Initialize play button
	private void initalizePlayButton () {
		ImageIcon playIcon = new ImageIcon("src/images/playButton.png");
		playButton = new JButton(playIcon); //Initialize with icon
		playButton.setBounds(0, (gridPieceDimension*numRows) + (gridPieceDimension * 4), 150, 50); //Set location
		playButton.addActionListener(new ActionListener() { //Add action listener
	         public void actionPerformed(ActionEvent e) { //On click
	        	 MasterGameProcess.pushPlay();
	         }          
	    });
		MasterGameProcess.frame().add(playButton);
	}
	
	//Initialize pause button
	private void initalizePauseButton () {
		ImageIcon pauseIcon = new ImageIcon("src/images/pauseButton.png");
		pauseButton = new JButton(pauseIcon); //Initialize with icon
		pauseButton.setBounds(160, (gridPieceDimension*numRows) + (gridPieceDimension * 4), 150, 50); //Set location
		pauseButton.addActionListener(new ActionListener() { //Add action listener
	         public void actionPerformed(ActionEvent e) { //On click
	        	 MasterGameProcess.pushPause();
	         }          
	    });
		MasterGameProcess.frame().add(pauseButton);
	}
	
	//Toggle grid piece between Wall and Empty icon
	private void switchBetweenWallandEmpty (MouseEvent e) {
		if (MasterGameProcess.getIsPlay() == false) //If MasterGameProcess is not in Play mode
		{
			if (((JLabel)e.getSource()).getIcon().toString() == GameboardPiece.getWall()) //If grid piece is a Wall icon
	    	{
				((JLabel)e.getSource()).setIcon(new ImageIcon( GameboardPiece.getEmpty() )); //Set as an Empty icon
	    	}
	    	else if (((JLabel)e.getSource()).getIcon().toString() == GameboardPiece.getEmpty() ) //If grid piece is an Empty icon
	    	{
	    		((JLabel)e.getSource()).setIcon(new ImageIcon( GameboardPiece.getWall() )); //Set as a Wall icon
	    	}
	    	else {
	    		
	    	}
		}
	}
	
}
