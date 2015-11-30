package saveprincess

object SavePrincess {
  def main(args: Array[String]) = {
    val m = Console.readLine.toInt
    val grid = new Array[String](m)
    for (i <- 0 until m) {
      grid.update(i, Console.readLine)
    }
    displayPathtoPrincess(m, grid)
  }

  /* Refer to Output format section for more details */
  def displayPathtoPrincess(m: Int, grid: Array[String]) = {
    val robot = getPosition(grid, 'm')
    val princess = getPosition(grid, 'p')
    val path  = getPath(robot, princess)
    path foreach (Console.println(_))
  }

  case class Position(row: Int, col: Int)

  sealed trait Move
  case object UP extends Move
  case object DOWN extends Move
  case object LEFT extends Move
  case object RIGHT extends Move

  def nextMove(goal: Position)(current: Position): Option[(Move, Position)] =
    (math.signum(goal.row - current.row), math.signum(goal.col - current.col)) match {
      case (0, 0) => None
      case (1, _) => Some((DOWN, current.copy(row = current.row + 1)))
      case (-1, _) => Some((UP, current.copy(row = current.row - 1)))
      case (_, 1) => Some((RIGHT, current.copy(col = current.col + 1)))
      case (_, -1) => Some((LEFT, current.copy(col = current.col - 1)))
    }

  def getPath(start: Position, goal: Position): Seq[Move] = {
    unfoldRight(start)(nextMove(goal))
  }

  def unfoldRight[A,B](seed: B)(f: B => Option[(A,B)]): Seq[A] =
    f(seed) match {
      case None => Seq()
      case Some((a, b)) => a +: unfoldRight(b)(f)
    }

  def getPosition(grid: Array[String], who: Char): Position = {
    val row = grid indexWhere (_.contains(who))
    val col = grid(row) indexOf who
    Position(row, col)
  }
}