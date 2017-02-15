/** Author: Eric Gerardi
 *  
 *  The GameboardPiece Object stores the String (PNG) for
 *  each individual game board piece.
 */

object GameboardPiece {
    private val wall = "src/images/wall.png";
    private val portal = "src/images/portal.png";
    private val player = "src/images/character.png";
    private val empty = "src/images/boardpiece.png";
    

    def getWall() : String = {
    	return wall;
    }
    def getPortal() : String = {
    	return portal;
    }
    def getPlayer() : String = {
    	return player;
    }
    def getEmpty() : String = {
    	return empty;
    }
}