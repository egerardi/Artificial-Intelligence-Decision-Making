//package mazeRunner
import Array._

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
  var lasLastSignal = -999;
  var lastMove = 0      // of possibility utility
  var lastLastMove = 0;
  var didFirstDown = false;
  var hitFirstDown = false;
  var alongWall = 0; //Same integers as lastMove, but 0 == Not along wall
  var upDownLeftRight = ofDim[Int](4,5);
      upDownLeftRight(0)(0) = 0;
      upDownLeftRight(0)(1) = 1;
      upDownLeftRight(0)(2) = 2;
      upDownLeftRight(0)(3) = 3;
      upDownLeftRight(0)(4) = 4;
      upDownLeftRight(1)(0) = 0;
      upDownLeftRight(1)(1) = 1;
      upDownLeftRight(1)(2) = 2;
      upDownLeftRight(1)(3) = 4;
      upDownLeftRight(1)(4) = 3;
      upDownLeftRight(2)(0) = 0;
      upDownLeftRight(2)(1) = 4;
      upDownLeftRight(2)(2) = 3;
      upDownLeftRight(2)(3) = 2;
      upDownLeftRight(2)(4) = 1;
      upDownLeftRight(3)(0) = 0;
      upDownLeftRight(3)(1) = 4;
      upDownLeftRight(3)(2) = 3;
      upDownLeftRight(3)(3) = 1;
      upDownLeftRight(3)(4) = 2;
  
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
    noBackToBackRepeat();
//    zigzagWalk();
    //_sweepRoomModelDecision()
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

  private def noBackToBackRepeat() : Int = {
    
      var temp = lastMove;
      lastMove = Random.nextInt(5);
      
      while (lastMove == lastLastMove)
      {
          lastMove = Random.nextInt(5);
          if (lastMove != lastLastMove)
          {
              lastLastMove = temp;
              return lastMove;
          }
      }
      lastLastMove = temp;
      return lastMove;
  }
  
  // Internal decision function, spiral walk
//  private def zigzagWalk() : Int = {
//
//    var temp = lastMove;
//    var directionCounter = 0;
//    
//    if (lastSignal == -1 && lasLastSignal == -1)
//    {
//        directionCounter = directionCounter + 1;
//        
//        println("Hi" + directionCounter);
//        if (lastMove == 1)
//        {
//            lastMove = 2;
//        }
//        else if (lastMove == 2)
//        {
//            lastMove = 1;
//        }
//        else if (lastMove == 3)
//        {
//            lastMove = 4;
//        }
//        else if (lastMove == 4)
//        {
//            lastMove = 3;
//        }
//        else
//        {
//          
//        }   
//    }
//    else
//    {
//        if (lastSignal == -999) //Beginning move
//        {
//            lastMove = upDownLeftRight(directionCounter)(3); //Left
//        }
//        else
//        {
//            if (lastSignal == -1) //If last movement decision failed
//            {
//                if (didFirstDown == false)
//                {
//                    lastMove = upDownLeftRight(directionCounter)(2); //Down
//                    didFirstDown = true;
//                }
//                else if (didFirstDown == true && hitFirstDown == false)
//                {
//                    lastMove = upDownLeftRight(directionCounter)(1);  //Up
//                    hitFirstDown = true;
//                }
//                else if (lastMove == upDownLeftRight(directionCounter)(1) || lastMove == upDownLeftRight(directionCounter)(2)) //If lastMove was Up or Down
//                {
//                    lastMove = upDownLeftRight(directionCounter)(4); //Right
//                }
//                else 
//                {
//                  
//                }           
//            }
//            else 
//            {
//                if (lastMove == upDownLeftRight(directionCounter)(4))
//                {
//                    if (lastLastMove == upDownLeftRight(directionCounter)(1)) //If lastMove right && lastLastMove up
//                    {
//                        lastMove = upDownLeftRight(directionCounter)(2); //Down
//                    }
//                    else //(lastLastMove == 2) //If lastMove right && lastLastMove down
//                    {
//                        lastMove = upDownLeftRight(directionCounter)(1);  //Up
//                    }
//                }  
//            }
//        }
//    }
//    lastLastMove = temp;
//
//    // Returns newly created move:
//    lastMove
//  }  
}
