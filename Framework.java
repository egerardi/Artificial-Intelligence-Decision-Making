import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Framework {
	
	private static JLabel [][] grid;
	private int gridPieceDimension = 25;
	
	public Framework (int numRows, int numCols) {
		
		initalizeGrid(numRows, numCols);
		
		setPortal(numRows - 2, numCols - 1);
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
		
		MasterGameProcess.frame().setSize((numCols + 1) * gridPieceDimension - 8, (numRows + 7) * gridPieceDimension);
		MasterGameProcess.frame().setLayout(null);
		MasterGameProcess.frame().setVisible(true);
		MasterGameProcess.frame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setPortal (int row, int col) { //Set the portal (with class instantiation)
        grid[row][col].setIcon(new ImageIcon( GameboardPiece.getPortal() ));
    }
	
	public void setPlayerPosition (int rowOld, int colOld, int row, int col) {
		grid[rowOld][colOld].setIcon(new ImageIcon( GameboardPiece.getEmpty() )); //Remove player old position
		grid[row][col].setIcon(new ImageIcon( GameboardPiece.getPlayer() ));  //Set player new position
    }
	
	
}
