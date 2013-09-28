package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(column: Int, row: Int): Int = {
    pascalRow(row)(column)
  }

  def pascalRow(row: Int): List[Int] = {
    if (row == 0)
    {
      List(1)
    }
    else
    {
      val rowAbove =  pascalRow(row-1)
      val rowA = List(0) ::: rowAbove
      val rowB = rowAbove ::: List(0)
      (rowA,rowB).zipped.map(_+_)
    }
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    depthBalanceCheck(0, chars)
  }

  def depthBalanceCheck(depthSoFar:Int, chars: List[Char]): Boolean = {
    if (depthSoFar < 0)
    {
      return false
    }
    chars.headOption match {
      case Some('(') => depthBalanceCheck(depthSoFar+1,chars.tail)
      case Some(')') => depthBalanceCheck(depthSoFar-1,chars.tail)
      case None => depthSoFar == 0
      case _ => depthBalanceCheck(depthSoFar, chars.tail)
    }
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
