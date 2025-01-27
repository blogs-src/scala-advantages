package transformer

import core.AST.*

import extractor_lib.Extractor

class EnumsAstTest extends munit.FunSuite {

  test("Simple AST enums") {
    val obj = Extractor()
    val src =
      """
        |   enum Priority(val id: String) extends Model:
        |      case Low         extends Priority("low")
        |      case MediumLow   extends Priority("medium_low")
        |      case Medium      extends Priority("medium")
        |      case MediumHigh  extends Priority("medium_high")
        |      case High        extends Priority("high")
        |""".stripMargin
    val result = obj.processEnumSrc(src)
    assertEquals(
      result,
      Some(SimpleEnum("Priority", List(
        EnumCasePair("Low", "low"),
        EnumCasePair("MediumLow", "medium_low"),
        EnumCasePair("Medium", "medium"),
        EnumCasePair("MediumHigh", "medium_high"),
        EnumCasePair("High", "high"),
      )))
    )
  }

}
