/** Author: Eric Gerardi
 *  
 *  Stores x and y coordinates
 */

public class PlayerPosition {
	private static int row;
	private static int col;
	
	public static int getRow() {
		return row;
	}
	public static void setRow(int row) {
		PlayerPosition.row = row;
	}
	public static int getCol() {
		return col;
	}
	public static void setCol(int col) {
		PlayerPosition.col = col;
	}
	

}
