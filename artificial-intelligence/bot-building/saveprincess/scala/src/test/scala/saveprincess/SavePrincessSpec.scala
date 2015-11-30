package saveprincess

import org.junit.runner.RunWith
import org.scalatest.FlatSpec

import SavePrincess._

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class SavePrincessSpec extends FlatSpec {

  val sampleGrid = Array(
    "---",
    "-m-",
    "p--­­")

  val otherGrid = Array(
    "----p",
    "-----­­",
    "--m--",
    "-----­­",
    "-----­­")

  val randomGrid = Array(
    "-----",
    "-p---­­",
    "-----",
    "----m",
    "-----­­")

  behavior of "getPosition"
  it should "work for sample grid" in {
    assert(getPosition(sampleGrid, 'p') == Position(2,0), "princess")
    assert(getPosition(sampleGrid, 'm') == Position(1,1), "bot")
  }
  it should "work for other grid" in {
    assert(getPosition(otherGrid, 'p') == Position(0,4), "princess")
    assert(getPosition(otherGrid, 'm') == Position(2,2), "bot")
  }
  it should "work for random positions" in {
    assert(getPosition(randomGrid, 'p') == Position(1,1), "princess")
    assert(getPosition(randomGrid, 'm') == Position(3,4), "bot")
  }

  behavior of "getPath"
  it should "work for sample grid" in {
    assert(getPath(Position(1,1), Position(2,0)) == Seq(DOWN, LEFT))
  }
  it should "work for bigger grid" in {
    assert(getPath(Position(2,2), Position(0,4)) == Seq(UP,UP,RIGHT,RIGHT))
  }
  it should "work for random positions" in {
    assert(getPath(Position(3,4), Position(1,1)) == Seq(UP,UP,LEFT,LEFT,LEFT))
  }
}
