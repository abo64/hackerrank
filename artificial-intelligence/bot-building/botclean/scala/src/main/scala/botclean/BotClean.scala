package botclean

object BotClean {
  def main(args: Array[String]) = {
    val pos = Console.readLine
    val board = new Array[String](5)
    for (i <- 0 until 5) {
      board.update(i, Console.readLine)
    }
    nextMove(pos, board)
  }

  def nextMove(pos: String, board: Array[String]) = {
    val botCoordinates = pos.split(' ').map(_.toInt)
    val botPosition = Position(botCoordinates(0), botCoordinates(1))
    val action =
      nextAction(botPosition, board) getOrElse (throw new Exception("should not happen"))
    Console.println(action)
  }

  def nextAction(botPosition: Position, board: Seq[String]): Option[Action] = {
    def dirtyCellToAction(dirtyCell: Position): Action =
      (math.signum(dirtyCell.row - botPosition.row),
       math.signum(dirtyCell.col - botPosition.col)) match {
          case (0, 0)  => CLEAN
          case (1, _)  => DOWN
          case (-1, _) => UP
          case (_, 1)  => RIGHT
          case (_, -1) => LEFT
        }

    val dirtyCells = findAllDirtyCells(board)
    val nextDirtyCell = //findClosestDirtyCell(botPosition, dirtyCells)
      findNextDirtyCell(botPosition, dirtyCells)
    nextDirtyCell map dirtyCellToAction
  }

  def findNextDirtyCell(botPosition: Position, dirtyCells: Seq[Position]): Option[Position] = {
    def inSameRow(cell: Position): Boolean =
      cell.row == botPosition.row

    val closestDirtyCellInSameRow =
       findClosestDirtyCell(botPosition, dirtyCells filter inSameRow)
    closestDirtyCellInSameRow orElse findClosestDirtyCell(botPosition, dirtyCells)
  }

  def findAllDirtyCells(board: Seq[String]): Seq[Position] = {
    val (rows, cols) = (board.size, board(0).size)
    for {
      row <- 0 until rows
      col <- 0 until cols
      dirtyCell = Position(row, col) if board(row)(col) == 'd'
    } yield dirtyCell
  }

  // this might not be the best strategy to clean up the whole board
  def findClosestDirtyCell(from: Position, dirtyCells: Seq[Position]): Option[Position] = {
    def distance(to: Position): Int =
      math.abs(from.row - to.row) + math.abs(from.col - to.col)
    implicit val ordering: Ordering[Position] = Ordering.by(distance)
    dirtyCells.sorted.headOption
  }

  case class Position(row: Int, col: Int)

  sealed trait Action
  case object UP extends Action
  case object DOWN extends Action
  case object LEFT extends Action
  case object RIGHT extends Action
  case object CLEAN extends Action
}