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
    _randomWalkDecision()
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

  // Internal decision function, random walk (including stays):
  private def _randomWalkDecision() : Int = {
    // Creates new move and stores it in lastMove attribute:
    lastMove = Random.nextInt(5)

    // Returns newly created move:
    lastMove
  }
  
//  def main(args: Array[String]) {
//      println("Decision: " + Decision() );
//  }

}
