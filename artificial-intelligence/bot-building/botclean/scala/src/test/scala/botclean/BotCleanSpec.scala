package botclean

import org.junit.runner.RunWith
import org.scalatest.FlatSpec

import BotClean._

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class BotCleanSpec extends FlatSpec {
  val sampleBoard = Array(
    "b---d",
    "-d--d",
    "--dd-",
    "­­­--d--",
    "----d")

  behavior of "nextAction"
  it should "work for sample" in {
    assert(nextAction(Position(0,0), sampleBoard) == Some(RIGHT), "sample action #01")
  }
}
