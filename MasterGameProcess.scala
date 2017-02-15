import java.lang;

object MasterGameProcess {
   var decision : Int = _;
   var changeRow : Int = _;
   var changeCol : Int = _;
   
   def main(args: Array[String]) {
       var foundPortal: Boolean = false;
       var numberOfMoves: Int = 0;
//       var playerPosition = new PlayerPosition();
       
//       var framework = new Framework(20,40);
//       
//       framework.setPlayerPosition(0, 0, 18, 2);
//       
//       println("Player Start");
//       framework.printGrid();
       //Thread.sleep(500);
       
       var framework = new Framework(20,40);
       
       
//       while (! foundPortal) {
//           println();  
//         
//           decision = DecisionFactory.Decision();  // Get Decision
//           //Store change in row or col (using changeRow and changeCol)
//           if (decision == 0) //   0 - stay (don't move)
//           {
//               changeRow = 0;
//               changeCol = 0;
//           }
//           else if (decision == 1) //   1 - move up
//           {
//               changeRow = -1;
//               changeCol = 0;
//               println("Move Up");
//           }
//           else if (decision == 2) //   2 - move down
//           {
//               changeRow = 1;
//               changeCol = 0;
//               println("Move Down");
//           }
//           else if (decision == 3) //   3 - move left
//           {
//               changeRow = 0;
//               changeCol = -1;
//               println("Move Left");
//           }
//           else // (decision == 4) //   4 - move right
//           {
//               changeRow = 0;
//               changeCol = 1;
//               println("Move Right");
//           }
//           
//           playerPosition = framework.getPlayerPosition();
//           
//           if (framework.getGridPiece(playerPosition.row + changeRow, playerPosition.col + changeCol) == GameboardPiece.getWall())  //If Hit Wall
//           {
//               DecisionFactory.receiveSignal(-1); //Wall Signal
//           }
//           else if (framework.getGridPiece(playerPosition.row + changeRow, playerPosition.col + changeCol) != GameboardPiece.getPortal()) //If NOT Portal
//           {
//               DecisionFactory.receiveSignal(1); //Wall Signal
//               if (!(playerPosition.row == playerPosition.row + changeRow && playerPosition.col == playerPosition.col + changeCol)) //If Stay
//               {
//                   framework.setPlayerPosition(playerPosition.row, playerPosition.col, playerPosition.row + changeRow, playerPosition.col + changeCol);
//               }
//           }
//           else //If Portal
//           {
//               framework.setPlayerPosition(playerPosition.row, playerPosition.col, playerPosition.row, playerPosition.col);
//               DecisionFactory.receiveSignal(2); //Portal Signal
//               foundPortal = true;
//           }
//         
//           numberOfMoves = numberOfMoves + 1;
//           
//           //framework.printGrid();
//           
//           //Thread.sleep(500);
//       }
//       
//       println();
//       println();
//       
//       println("PLayer Found the Portal in " + numberOfMoves + " moves.");
   }
}