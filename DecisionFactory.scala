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
    }  
  
    
    // Internal decision function, Wall Run
    private def WallRun() : Int = {
//        println(queue.last.getX() + " " + queue.last.getY());
        
        println(lastSignal);
        if (lastSignal == -1)
        {
            queue.remove(queue.length - 1);
        }
      
        lastLastMove = lastMove;
      
        //Get Character Current X, Y
        var currentX : Int = queue.last.getX();
        var currentY : Int = queue.last.getY();
        
        //Add all surrounding Vertices (of the current one)
        if ( !( queue.exists { x => x.getX() == currentX } && queue.exists { y => y.getY() == currentY + 1 } ) ) //If grid Vertex 1 Up does not exist
        {
            var v : Vertex = new Vertex(currentX, currentY + 1); //Create Vertex
            queue.append(v); //Append Vertex
        }
        if ( !( queue.exists { x => x.getX() == currentX + 1 } && queue.exists { y => y.getY() == currentY } ) ) //If grid Vertex 1 Right does not exist
        {
            var v : Vertex = new Vertex(currentX + 1, currentY); //Create Vertex
            queue.append(v); //Append Vertex
        }
        if ( !( queue.exists { x => x.getX() == currentX } && queue.exists { y => y.getY() == currentY - 1 } ) ) //If grid Vertex 1 Down does not exist
        {
            var v : Vertex = new Vertex(currentX, currentY - 1); //Create Vertex
            queue.append(v); //Append Vertex
        }
        if ( !( queue.exists { x => x.getX() == currentX - 1} && queue.exists { y => y.getY() == currentY } ) ) //If grid Vertex 1 Left does not exist
        {
            var v : Vertex = new Vertex(currentX - 1, currentY); //Create Vertex
            queue.append(v); //Append Vertex
        }
        
        //Move Character according to last Vertex
        if (queue.last.getX() == currentX && queue.last.getY() == currentY + 1)
        {
            lastMove = 1; //Move Up
        }
        else if (queue.last.getX() == currentX && queue.last.getY() == currentY - 1)
        {
            lastMove = 2; //Move Down
        }
        else if (queue.last.getX() == currentX - 1 && queue.last.getY() == currentY)
        {
            lastMove = 3; //Move Left
        }
        else //if (queue.last.getX() == currentX + 1 && queue.last.getY() == currentY)
        {
            lastMove = 4; //Move Right
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
}


