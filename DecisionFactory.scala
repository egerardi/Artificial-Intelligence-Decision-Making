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
    var lastMove = 0      // of possibility utility
    var currentX : Int = 0;
    var currentY : Int = 0;
    var tempX : Int = 0;
    var tempY : Int = 0;
    var isBackToParent : Boolean = false;
    var attemptNum : Int = 1;
    
    //Array Buffer for stack of Vertices
    var stack = ArrayBuffer[Vertex]();
    var v : Vertex = new Vertex(0,0,0,0);
    stack.append(v); //Add first vertex (Player start is considered 0,0)
    
    //Array Buffer for stack of Vertices
    var visited = ArrayBuffer[Vertex]();
    visited.append(v); //Add first vertex (Player start is considered 0,0)
        
    
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
        if (lastSignal == 2) //If lastSignal == Portaled
        {
            attemptNum = attemptNum + 1; 
        }
         
        if (attemptNum == 1) //If this is the first attempt
        {
            blindGraph_DepthFirstSearch();
        }
        else
        {
            for ( i <- stack)
            {
                println(i.getX() + " " + i.getY() + " 		Parent " + i.getParentX() + " " + i.getParentY());
            }
            return 0;
        }
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
      lastSignal = sig 
    }  
  
    
    /** Internal decision function
     *  Uses Depth First Search to blindly build the graph
     */
    def blindGraph_DepthFirstSearch () : Int = {       
      
        if (lastSignal == -999) //Last Move: None (Start)
        {
            addSurroundingVertices();
            
            moveToLastInStack();
        }
        else if (lastSignal == -1) //Last Move: Fail
        {
            pop();
            
            if (currentX == stack.last.getX() && currentY == stack.last.getY()) //If Current Position == Last in stack
            {
                moveToParent();
                pop();
            }
            else 
            {
                moveToLastInStack();
            }
        }
        else //Last Move: Success
        {
            //Save new Current position
            currentX = tempX;
            currentY = tempY;
            
            if (isBackToParent)
            {
                if ( currentX != stack.last.getX() || currentY != stack.last.getY() ) //If Current Position != Last in stack
                {
                    moveToLastInStack();
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
                    moveToLastInStack();
                }
            }
        }
        
        lastMove // Returns newly created move
    }
    

    /**	Decides how to move character to the last Vertex in the stack
     * 	Stores decision in lastMove
     *	Stores current X and Y in temp X and Y
     */
    def moveToLastInStack () = {
        isBackToParent = false;
        
        //Move Character according to last Vertex
        if (stack.last.getX() == currentX && stack.last.getY() == currentY + 1)
        {
            lastMove = 1; //Move Up
            tempX = currentX;
            tempY = currentY + 1;
        }
        else if (stack.last.getX() == currentX && stack.last.getY() == currentY - 1)
        {
            lastMove = 2; //Move Down
            tempX = currentX;
            tempY = currentY - 1;
        }
        else if (stack.last.getX() == currentX - 1 && stack.last.getY() == currentY)
        {
            lastMove = 3; //Move Left
            tempX = currentX - 1;
            tempY = currentY;
        }
        else if (stack.last.getX() == currentX + 1 && stack.last.getY() == currentY)
        {
            lastMove = 4; //Move Right
            tempX = currentX + 1;
            tempY = currentY;
        }
        else {
            lastMove = 0;
        }
    }
    
    
    //Removes last element from stack
    def pop () = {
        stack.remove(stack.length - 1);
    }
    
    
    /**	Decides how to move character back to parent Vertex
     *  Stores decision in lastMove
     *	Stores current X and Y in temp X and Y
     */
    def moveToParent () = {
        isBackToParent = true;
        
        //Move Character according to last Vertex
        if (stack.last.getParentX() == currentX && stack.last.getParentY() == currentY + 1)
        {
            lastMove = 1; //Move Up
            tempX = currentX;
            tempY = currentY + 1;
        }
        else if (stack.last.getParentX() == currentX && stack.last.getParentY() == currentY - 1)
        {
            lastMove = 2; //Move Down
            tempX = currentX;
            tempY = currentY - 1;
        }
        else if (stack.last.getParentX() == currentX - 1 && stack.last.getParentY() == currentY)
        {
            lastMove = 3; //Move Left
            tempX = currentX - 1;
            tempY = currentY;
        }
        else if (stack.last.getParentX() == currentX + 1 && stack.last.getParentY() == currentY)
        {
            lastMove = 4; //Move Right
            tempX = currentX + 1;
            tempY = currentY;
        }
        else {
            lastMove = 0;
        }       
     
    }
    
    
    /**	Attempts to add (to the stack) each of the Vertices surrounding the current Vertex
    *		Returns number of added Vertices
    */
    def addSurroundingVertices () : Int = {
        var numAddedVertices : Int = 0;
      
        //Add all surrounding Vertices (of the current one)
        if ( !( stack.exists { a => a.getX() == currentX && a.getY() == currentY + 1 } ) && !( visited.exists { b => b.getX() == currentX && b.getY() == currentY + 1 } ) ) //If grid Vertex Up 1 square is not in the stack
        {
            var v : Vertex = new Vertex(currentX, currentY + 1, currentX, currentY); //Create Vertex
            stack.append(v); //Append Vertex
            numAddedVertices = numAddedVertices + 1;
            
            visited.append(v);
        }
        if ( !( stack.exists { a => a.getX() == currentX + 1 && a.getY() == currentY } ) && !( visited.exists { b => b.getX() == currentX + 1 && b.getY() == currentY } ) ) //If grid Vertex Right 1 square is not in the stack
        {
            var v : Vertex = new Vertex(currentX + 1, currentY, currentX, currentY); //Create Vertex
            stack.append(v); //Append Vertex
            numAddedVertices = numAddedVertices + 1;
            
            visited.append(v);
        }
        if ( !( stack.exists { a => a.getX() == currentX && a.getY() == currentY - 1 } ) && !( visited.exists { b => b.getX() == currentX && b.getY() == currentY - 1 } ) ) //If grid Vertex Down 1 square is not in the stack
        {
            var v : Vertex = new Vertex(currentX, currentY - 1, currentX, currentY); //Create Vertex
            stack.append(v); //Append Vertex
            numAddedVertices = numAddedVertices + 1;
            
            visited.append(v);
        }
        if ( !( stack.exists { a => a.getX() == currentX - 1 && a.getY() == currentY } ) && !( visited.exists { b => b.getX() == currentX - 1 && b.getY() == currentY } ) ) //If grid Vertex Left 1 square is not in the stack
        {
            var v : Vertex = new Vertex(currentX - 1, currentY, currentX, currentY); //Create Vertex
            stack.append(v); //Append Vertex
            numAddedVertices = numAddedVertices + 1;
            
            visited.append(v);
        }
        
        return numAddedVertices;
    }
    
    
    /**	Vertex or graph point
    *		Stores x and y axis
    *		And Parent x and y axis
    */
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


