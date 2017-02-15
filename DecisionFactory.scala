//package mazeRunner

import scala.util.Random
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
  var lastLastMove = 0;
  var didFirstDown = false;
  var hitFirstDown = false;

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
    zigzagWalk()
    //_sweepRoomModelDecision()
  }

  // Signal channel for receiving results about decisions and the environment:
  //   Signals are:
  //   -999 - starting round
  //   -1   - last movement decision failed
  //   1    - last movement decision worked
  def receiveSignal( sig: Int ) = {
    sig match {
      case -1 => { if (logLevel == 1) println("SIG REC: wall collision") }
      case 1  => { if (logLevel == 1) println("SIG REC: successful move"); }
      case 2  => { if (logLevel == 1) println("SIG REC: PORTALATED!!"); }
      case _  => { ;; }
    }

    // Record latest signal in attribute:
    lastSignal = sig 
  }  

  // Internal decision function, spiral walk
  private def zigzagWalk() : Int = {

    var temp = lastMove;
    
    if (lastSignal == -999) //Beginning move
    {
        lastMove = 3; //Left
    }
    else
    {
        if (lastSignal == -1) //If last movement decision failed
        {
            if (didFirstDown == false)
            {
                lastMove = 2; //Down
                didFirstDown = true;
            }
            else if (didFirstDown == true && hitFirstDown == false)
            {
                lastMove = 1;  //Up
                hitFirstDown = true;
            }
            else if (lastMove == 1 || lastMove == 2) //If lastMove was Up or Down
            {
                lastMove = 4; //Right
            }
            else 
            {
              
            }            
        }
        else 
        {
            if (lastMove == 4)
            {
                if (lastLastMove == 1) //If lastMove right && lastLastMove up
                {
                    lastMove = 2; //Down
                }
                else //(lastLastMove == 2) //If lastMove right && lastLastMove down
                {
                    lastMove = 1;  //Up
                }
            }  
        }
    }
    lastLastMove = temp;

    // Returns newly created move:
    lastMove
  }
  
}
