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
    var tempX : Int = 0;
    var tempY : Int = 0;
    var threeToPop : Int = 0;
    var isBackToParent : Boolean = false;
    
    
    //Array Buffer for queue of Vertices
    var queue = ArrayBuffer[Vertex]();
    //Add first vertex (Player start is considered 0,0)
    var v : Vertex = new Vertex(0,0,0,0);
    queue.append(v);
    
    //Array Buffer for queue of Vertices
    var visited = ArrayBuffer[Vertex]();
    //Add first vertex (Player start is considered 0,0)
    visited.append(v);
        
    
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
      blindGraph_DepthFirstSearch();
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
  
    
    // Internal decision function
    def blindGraph_DepthFirstSearch () : Int = {    
              
      
        if (lastSignal == -999) //Last Move: None (Start)
        {
            addSurroundingVertices();
            
            moveToLastInQueue();
        }
        else if (lastSignal == -1) //Last Move: Fail
        {
            pop();
            
            if (currentX == queue.last.getX() && currentY == queue.last.getY()) //If Current Position == Last in queue
            {
                moveToParent();
                pop();
            }
            else 
            {
                moveToLastInQueue();
            }
        }
        else //Last Move: Success
        {          
            currentX = tempX;
            currentY = tempY;
          
            println("Current: " + queue.last.getX() + " " + queue.last.getY());
            
            if (isBackToParent)
            {
                if ( currentX != queue.last.getX() || currentY != queue.last.getY() ) //If Current Position != Last in queue
                {
                    moveToLastInQueue();
                }
                else 
                {
                    moveToParent();
                    pop();
                }
            }
            else 
            {         
                val numAddedVertices : Int = addSurroundingVertices();
                
                if (numAddedVertices == 0) //If there are no vertices to add
                {
                    moveToParent();
                    pop();
                }
                else 
                {
                    moveToLastInQueue();
                }
            }
            
            
        }
        // Returns newly created move:
        lastMove
    }
    

    
    def moveToLastInQueue () = {
        isBackToParent = false;
        
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
            lastMove = 0;
        }
    }
    
    
    //Removes last element from queue
    def pop () = {
        queue.remove(queue.length - 1);
        
//        for ( i <- queue)
//        {
//            println(i.getX() + " " + i.getY() + " Parent " + i.getParentX() + " " + i.getParentY());
//        }
//        println("---");
    }
    
    def moveToParent () = {
        isBackToParent = true;
        
        //Move Character according to last Vertex
        if (queue.last.getParentX() == currentX && queue.last.getParentY() == currentY + 1)
        {
            lastMove = 1; //Move Up
            tempX = currentX;
            tempY = currentY + 1;
        }
        else if (queue.last.getParentX() == currentX && queue.last.getParentY() == currentY - 1)
        {
            lastMove = 2; //Move Down
            tempX = currentX;
            tempY = currentY - 1;
        }
        else if (queue.last.getParentX() == currentX - 1 && queue.last.getParentY() == currentY)
        {
            lastMove = 3; //Move Left
            tempX = currentX - 1;
            tempY = currentY;
        }
        else if (queue.last.getParentX() == currentX + 1 && queue.last.getParentY() == currentY)
        {
            lastMove = 4; //Move Right
            tempX = currentX + 1;
            tempY = currentY;
        }
        else {
            lastMove = 0;
        }       
     
    }
    
    
    //Attempts to add (to the queue) each of the Vertices surrounding the current Vertex
    //Returns number of added Vertices
    def addSurroundingVertices () : Int = {
        var numAddedVertices : Int = 0;
      
        //Add all surrounding Vertices (of the current one)
        if ( !( queue.exists { a => a.getX() == currentX && a.getY() == currentY + 1 } ) && !( visited.exists { b => b.getX() == currentX && b.getY() == currentY + 1 } ) ) //If grid Vertex Up 1 square is not in the queue
        {
            var v : Vertex = new Vertex(currentX, currentY + 1, currentX, currentY); //Create Vertex
            queue.append(v); //Append Vertex
            numAddedVertices = numAddedVertices + 1;
            
            visited.append(v);
        }
        if ( !( queue.exists { a => a.getX() == currentX + 1 && a.getY() == currentY } ) && !( visited.exists { b => b.getX() == currentX + 1 && b.getY() == currentY } ) ) //If grid Vertex Right 1 square is not in the queue
        {
            var v : Vertex = new Vertex(currentX + 1, currentY, currentX, currentY); //Create Vertex
            queue.append(v); //Append Vertex
            numAddedVertices = numAddedVertices + 1;
            
            visited.append(v);
        }
        if ( !( queue.exists { a => a.getX() == currentX && a.getY() == currentY - 1 } ) && !( visited.exists { b => b.getX() == currentX && b.getY() == currentY - 1 } ) ) //If grid Vertex Down 1 square is not in the queue
        {
            var v : Vertex = new Vertex(currentX, currentY - 1, currentX, currentY); //Create Vertex
            queue.append(v); //Append Vertex
            numAddedVertices = numAddedVertices + 1;
            
            visited.append(v);
        }
        if ( !( queue.exists { a => a.getX() == currentX - 1 && a.getY() == currentY } ) && !( visited.exists { b => b.getX() == currentX - 1 && b.getY() == currentY } ) ) //If grid Vertex Left 1 square is not in the queue
        {
            var v : Vertex = new Vertex(currentX - 1, currentY, currentX, currentY); //Create Vertex
            queue.append(v); //Append Vertex
            numAddedVertices = numAddedVertices + 1;
            
            visited.append(v);
        }
        
//        for ( i <- queue)
//        {
//            println(i.getX() + " " + i.getY() + " Parent " + i.getParentX() + " " + i.getParentY());
//        }
        
        return numAddedVertices;
    }
    
    
    //Vertex or graph point
    //Stores x and y axis
    //And Parent x and y axis
    class Vertex ( xAxis : Int, yAxis : Int, xParent : Int, yParent : Int) {
        private var x : Int = xAxis;
        private var y : Int = yAxis;
        private var parentX : Int = xParent;
        private var parentY : Int = yParent;
        
        
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
        
        
        def getParentX () : Int = {
          return parentX;
        }
        def getParentY () : Int = {
          return parentY;
        }
    }
}


