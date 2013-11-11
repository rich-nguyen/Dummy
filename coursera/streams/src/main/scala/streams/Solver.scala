package streams

import common._

/**
 * This component implements the solver for the Bloxorz game
 */
trait Solver extends GameDef {

  /**
   * Returns `true` if the block `b` is at the final position
   */
  def done(b: Block): Boolean = {
    b.b1 == goal && b.isStanding
  }

  /**
   * This function takes two arguments: the current block `b` and
   * a list of moves `history` that was required to reach the
   * position of `b`.
   * 
   * The `head` element of the `history` list is the latest move
   * that was executed, i.e. the last move that was performed for
   * the block to end up at position `b`.
   * 
   * The function returns a stream of pairs: the first element of
   * the each pair is a neighboring block, and the second element
   * is the augmented history of moves required to reach this block.
   * 
   * It should only return valid neighbors, i.e. block positions
   * that are inside the terrain.
   */
  def neighborsWithHistory(b: Block, history: List[Move]): Stream[(Block, List[Move])] = {
    val neighbours: List[(Block, Move)] = b.legalNeighbors

    neighbours.collect {
      case (block, move) => (block, move :: history)
    }.toStream
  }

  /**
   * This function returns the list of neighbors without the block
   * positions that have already been explored. We will use it to
   * make sure that we don't explore circular paths.
   */
  def newNeighborsOnly(neighbours: Stream[(Block, List[Move])],
                       explored: Set[Block]): Stream[(Block, List[Move])] = {
    neighbours.filter {
      case (block, _) => !explored.contains(block)
    }.toStream
  }

  def extend( b:Block, history: List[Move], explored: Set[Block] ) : Stream[(Block, List[Move])] = {
    newNeighborsOnly(neighborsWithHistory(b,history),explored)
  }

  /**
   * The function `from` returns the stream of all possible paths
   * that can be followed, starting at the `head` of the `initial`
   * stream.
   * 
   * The blocks in the stream `initial` are sorted by ascending path
   * length: the block positions with the shortest paths (length of
   * move list) are at the head of the stream.
   * 
   * The parameter `explored` is a set of block positions that have
   * been visited before, on the path to any of the blocks in the
   * stream `initial`. When search reaches a block that has already
   * been explored before, that position should not be included a
   * second time to avoid cycles.
   * 
   * The resulting stream should be sorted by ascending path length,
   * i.e. the block positions that can be reached with the fewest
   * amount of moves should appear first in the stream.
   * 
   * Note: the solution should not look at or compare the lengths
   * of different paths - the implementation should naturally
   * construct the correctly sorted stream.
   */
  def from(initial: Stream[(Block, List[Move])],
           explored: Set[Block]): Stream[(Block, List[Move])] = {
    if (initial.isEmpty) {
      Stream.empty
    } else {
      // For each block in initial, extend the move list by one.
      val nextGen = for {
        (block, history) <- initial
        next <- extend(block,history,explored)
      } yield { next }

      // Include new found blocks into the 'explored' set.
      val newExploredBlocks = nextGen.map(_._1)

      initial #::: from(nextGen, explored ++ newExploredBlocks)
    }
  }

  /**
   * The stream of all paths that begin at the starting block.
   */
  lazy val pathsFromStart: Stream[(Block, List[Move])] = {
    from( Stream((startBlock,Nil)), Set.empty)
  }

  /**
   * Returns a stream of all possible pairs of the goal block along
   * with the history how it was reached.
   */
  lazy val pathsToGoal: Stream[(Block, List[Move])] = {
    pathsFromStart.filter{ case (block, _) => done(block) }
  }

  /**
   * The (or one of the) shortest sequence(s) of moves to reach the
   * goal. If the goal cannot be reached, the empty list is returned.
   *
   * Note: the `head` element of the returned list should represent
   * the first move that the player should perform from the starting
   * position.
   */
  lazy val solution: List[Move] = {
    pathsToGoal.headOption.map{_._2}.getOrElse((Nil))
  }
}
/*
class localTest extends GameDef with Solver with StringParserTerrain {
  /**
   * This method applies a list of moves `ls` to the block at position
   * `startPos`. This can be used to verify if a certain list of moves
   * is a valid solution, i.e. leads to the goal.
   */
  def solve(ls: List[Move]): Block =
    ls.foldLeft(startBlock) { case (block, move) => move match {
      case Left => block.left
      case Right => block.right
      case Up => block.up
      case Down => block.down
    }
    }

  val level =
    """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin

  val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
}*/
