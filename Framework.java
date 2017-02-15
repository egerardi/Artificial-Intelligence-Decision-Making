import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Framework extends JFrame {
	
	private JLabel [][] grid;
	private int gridPieceDimension = 25;
	
	public Framework (int numRows, int numCols) {
		
		grid = new JLabel[numRows][numCols];
		
		for (int row = 0; row < numRows; row++) 
		{
			for (int col = 0; col < numCols; col++)
			{
				grid[row][col] = new JLabel();
				grid[row][col].setBounds(gridPieceDimension*col, gridPieceDimension*row, gridPieceDimension, gridPieceDimension);
				
				if (row == numRows - 1 || col == numCols - 1 || row == 0 || col == 0 ) //If row or col is the border, set to wall
				{
					grid[row][col].setIcon(new ImageIcon(GameboardPiece.getWall()));
				}
				else
				{
					grid[row][col].setIcon(new ImageIcon(GameboardPiece.getEmpty()));
				}
				
				grid[row][col].setName( "r" + Integer.toString(row) + "c" + Integer.toString(col) );
				this.add(grid[row][col]);
			}
		}
		
		setSize((numCols + 1) * gridPieceDimension - 8, (numRows + 7) * gridPieceDimension);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}
