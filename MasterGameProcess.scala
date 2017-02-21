import java.lang;
import javax.swing.JFrame

object MasterGameProcess {
   var decision : Int = _;
   var changeRow : Int = _;
   var changeCol : Int = _;
   //var playerPosition : PlayerPosition = _;
   var strDirection : String = _;
   var signal : Int = _;
   var isPlay : Boolean = false;
   var isPause = false;
   
   var frame = new JFrame();
   
   def main(args: Array[String]) {
       var foundPortal: Boolean = false;
       var numberOfMoves: Int = 0;
       
       var framework = new Framework(16,16);
       framework.setPlayerPosition(5, 5, 5, 5);
       framework.setTextJLabel_Location();
       
       while (! isPlay)
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
           
           DecisionFactory.receiveSignal(signal);
           
           numberOfMoves = numberOfMoves + 1;
           
           framework.setTextJLabel_MoveDirection(strDirection);
           framework.setTextJLabel_Location();
           framework.setTextJLabel_Signal(signal);
           framework.setTextJLabel_MoveCount(numberOfMoves.toString());
           
//           isPause = true;
           
           while (isPause)
           {
             Thread.sleep(30);
           }
           
           
           Thread.sleep(20);
       }
   }
   
   def pushPlay () {
       isPlay = true;
   }
   
   def pushPause () {
       isPause = !isPause;
   }
   
   def getIsPlay () : Boolean = {
       return isPlay;
   }
   
}