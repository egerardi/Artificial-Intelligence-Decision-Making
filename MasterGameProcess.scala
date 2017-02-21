import java.lang;
import javax.swing.JFrame

/** Author: Eric Gerardi
 *  
 *  The MasterGameProcess Object supports experiments with agents.  This object uses a 
 *  Discrete Time Process (while loop) that asks for a decision from the DecisionFactory,
 *  updates the graphical user interface grid (Framework), and returns a signal (success/fail)
 *  back to the DecisionFactory.
 *  
 */

object MasterGameProcess {
   var decision : Int = _;
   var changeRow : Int = _;
   var changeCol : Int = _;
   var strDirection : String = _;
   var signal : Int = _;
   var isPlay : Boolean = false;
   var isPause = false;
   
   //Create Program Window
   var frame = new JFrame();
   
   def main(args: Array[String]) {
       var foundPortal: Boolean = false;
       var numberOfMoves: Int = 0;
       
       //Create and Initialize Framework
       var framework = new Framework(16,16);
       framework.setPlayerPosition(5, 5, 5, 5);
       framework.setTextJLabel_Location();
       
       while (! isPlay) //Prevent Game from starting until User presses play
       {
         Thread.sleep(30);
       }
       
       
       while (! foundPortal) {
         
           decision = DecisionFactory.Decision();  // Get Decision
           //Store change in row or col (using changeRow and changeCol)
           if (decision == 0) //   0 - stay (don't move)
           {
               changeRow = 0;
               changeCol = 0;
               strDirection = "Stay";
           }
           else if (decision == 1) //   1 - move up
           {
               changeRow = -1;
               changeCol = 0;
               strDirection = "Move Up";
           }
           else if (decision == 2) //   2 - move down
           {
               changeRow = 1;
               changeCol = 0;
               strDirection = "Move Down";
           }
           else if (decision == 3) //   3 - move left
           {
               changeRow = 0;
               changeCol = -1;
               strDirection = "Move Left";
           }
           else // (decision == 4) //   4 - move right
           {
               changeRow = 0;
               changeCol = 1;
               strDirection = "Move Right";
           }
           
           
           if (framework.getGridPiece(PlayerPosition.getRow() + changeRow, PlayerPosition.getCol() + changeCol) == GameboardPiece.getWall())  //If Hit Wall
           {
               signal = -1;
           }
           else if (framework.getGridPiece(PlayerPosition.getRow() + changeRow, PlayerPosition.getCol() + changeCol) != GameboardPiece.getPortal()) //If NOT Portal
           {
               signal = 1; //Successful Move
               if (!(PlayerPosition.getRow() == PlayerPosition.getRow() + changeRow && PlayerPosition.getCol() == PlayerPosition.getCol() + changeCol)) //If Stay
               {
                   framework.setPlayerPosition(PlayerPosition.getRow(), PlayerPosition.getCol(), PlayerPosition.getRow() + changeRow, PlayerPosition.getCol() + changeCol);
               }
           }
           else //If Portal
           {
               framework.setPlayerPosition(PlayerPosition.getRow(), PlayerPosition.getCol(), -1, -1);
               signal = 2; //Portal Signal
               foundPortal = true;
           }
           
           DecisionFactory.receiveSignal(signal); //Send signal (success/fail) to DecisionFactory
           
           numberOfMoves = numberOfMoves + 1; //Increment number of moves
           
           //Refresh/Update Current Framework Statistics
           framework.setTextJLabel_MoveDirection(strDirection);
           framework.setTextJLabel_Location();
           framework.setTextJLabel_Signal(signal);
           framework.setTextJLabel_MoveCount(numberOfMoves.toString());
           
           while (isPause) //Used to Pause Game
           {
             Thread.sleep(30);
           }
           
           
           Thread.sleep(20);
       }
   }
   
   //Used by Framework to modify isPlay
   def pushPlay () {
       isPlay = true;
   }
   
   //Used by Framework to modify isPause
   def pushPause () {
       isPause = !isPause;
   }
   
   //Used by Framework to get isPlay
   def getIsPlay () : Boolean = {
       return isPlay;
   }
   
}