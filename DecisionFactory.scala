//package mazeRunner
import Array._
import java.util.Stack;

import scala.util.Random
//import scala.scalajs.js
import scala.collection.mutable.ArrayBuffer  // For mutable, scalable arrays

/** Author: Eric Gerardi
 *  
 *  The DecisionFactory Class encompasses the algorithm to simulate
 *  artificial intelligence in an agent.  The agent's objective
 *  is to traverse a two-dimensional room to reach the portal.
 */


// Main decision factory object:
object DecisionFactory {

    // Mutable object attributes:
    var lastSignal = -999 // note: last signal of -999 means the event has just started
    var lasLastSignal = -999;
    var lastMove = 0      // of possibility utility
    var lastLastMove = 0;
    var alongWall = 0; //Same integers as lastMove, but 0 == Not along wall
    var directionCounter : Int = 0;
    var upDownLeftRight = ofDim[Int](4,5);
    var currentX : Int = 0;
    var currentY : Int = 0;
    var tempX : Int = _;
    var tempY : Int = _;
    
    
    //Array Buffer for queue of Vertices
    var queue = ArrayBuffer[Vertex]();
    //Add first vertex (Player start is considered 0,0)
    var v : Vertex = new Vertex(0,0);
    queue.append(v);
    
    // Log level (to console)
    var logLevel = 1
  
    // Main Decision engine:
    //   Direction choices to return:
    //   0 - stay (don't move)
    //   1 - move up
    //   2 - move down
    //   3 - move left
    //   4 - move right
    def Decision() : Int = {
      WallRun();
    }
  
    // Signal channel for receiving results about decisions and the environment:
    //   Signals are:
    //   -999 - starting round
    //   -1   - last movement decision failed
    //   1    - last movement decision worked
    def receiveSignal( sig: Int ) = {
  //    sig match {
  //      case -1 => { if (logLevel == 1) println("SIG REC: wall collision") }
  //      case 1  => { if (logLevel == 1) println("SIG REC: successful move"); }
  //      case 2  => { if (logLevel == 1) println("SIG REC: PORTALATED!!"); }
  //      case _  => { ;; }
  //    }
  
      // Record latest signal in attribute:
      lasLastSignal = lastSignal;
      lastSignal = sig 
      println("Signal: " + lastSignal);
      println();
    }  
  
    
    // Internal decision function, Wall Run
    private def WallRun() : Int = {
        
        lastLastMove = lastMove;
      
        var failedX : Int = -999;
        var failedY : Int = -999;
        var numAddedVertices : Int = 0;
      
        if (lastSignal == -1 || (lastMove == 0 && !queue.isEmpty))  //If last move failed (Wall)   or    if lastMove was stay AND queue != empty
        {
            failedX = queue.last.getX();
            failedY = queue.last.getY();
            println("Failed: " + queue.last.getX() + " " + queue.last.getY());
            queue.remove(queue.length - 1);
        }
        else if (lastSignal == -999) { }
        else //Last move was a success
        {
            //Get Character Current X, Y
            currentX = tempX;
            currentY = tempY;
        }
//        println("Current: " + queue.last.getX() + " " + queue.last.getY());
        
        
        if (lastSignal != -1)
        {
            //Add all surrounding Vertices (of the current one)
            if ( !( queue.exists { a => a.getX() == currentX && a.getY() == currentY + 1 } ) ) //If grid Vertex 1 Up is not in the queue
            {
                var v : Vertex = new Vertex(currentX, currentY + 1); //Create Vertex
                queue.append(v); //Append Vertex
                numAddedVertices = numAddedVertices + 1;
            }
            if ( !( queue.exists { a => a.getX() == currentX + 1 && a.getY() == currentY } ) ) //If grid Vertex 1 Right is not in the queue
            {
                var v : Vertex = new Vertex(currentX + 1, currentY); //Create Vertex
                queue.append(v); //Append Vertex
                numAddedVertices = numAddedVertices + 1;
            }
            if ( !( queue.exists { a => a.getX() == currentX && a.getY() == currentY - 1 } ) ) //If grid Vertex 1 Down is not in the queue
            {
                var v : Vertex = new Vertex(currentX, currentY - 1); //Create Vertex
                queue.append(v); //Append Vertex
                numAddedVertices = numAddedVertices + 1;
            }
            if ( !( queue.exists { a => a.getX() == currentX - 1 && a.getY() == currentY } ) ) //If grid Vertex 1 Left is not in the queue
            {
                var v : Vertex = new Vertex(currentX - 1, currentY); //Create Vertex
                queue.append(v); //Append Vertex
                numAddedVertices = numAddedVertices + 1;
            }
        }
        
        //Move Character according to last Vertex
        if (queue.last.getX() == currentX && queue.last.getY() == currentY + 1)
        {
            lastMove = 1; //Move Up
            tempX = currentX;
            tempY = currentY + 1;
        }
        else if (queue.last.getX() == currentX && queue.last.getY() == currentY - 1)
        {
            lastMove = 2; //Move Down
            tempX = currentX;
            tempY = currentY - 1;
        }
        else if (queue.last.getX() == currentX - 1 && queue.last.getY() == currentY)
        {
            lastMove = 3; //Move Left
            tempX = currentX - 1;
            tempY = currentY;
        }
        else if (queue.last.getX() == currentX + 1 && queue.last.getY() == currentY)
        {
            lastMove = 4; //Move Right
            tempX = currentX + 1;
            tempY = currentY;
        }
        else {
            lastMove = 0; //Stay
        }
        
        
        for ( i <- queue)
        {
            println(i.getX() + " " + i.getY());
        }
        println();
//        println(queue.last.getX() + " " + queue.last.getY());
        
      
      
        // Returns newly created move:
        lastMove
    }
    
    class Vertex ( xAxis : Int, yAxis : Int) {
        private var x : Int = xAxis;
        private var y : Int = yAxis;
        
        def getX () : Int = {
          return x;
        }
        def setX (newX : Int) {
          x = newX;
        }
        
        def getY () : Int = {
          return y;
        }
        def setY (newY : Int) {
          y = newY;
        }
        
    }
}


