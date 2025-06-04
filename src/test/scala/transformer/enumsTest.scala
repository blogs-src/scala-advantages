package transformer

import core.AST.*

class EnumsTest extends munit.FunSuite {

  test("Simple enums") {
    val obj: Interface.EnumTransformer = Impl.EnumTransformer()
    val result = obj.simpleEnum(SimpleEnum(
      "Priority",
      List(
        EnumCasePair("Low", "low"),
        EnumCasePair("MediumLow", "medium_low"),
        EnumCasePair("Medium", "medium"),
        EnumCasePair("MediumHigh", "medium_high"),
        EnumCasePair("High", "high"))))
    assertEquals(
      result,
      """
        |enum Priority {
        |  LOW = 0;
        |  MEDIUM_LOW = 1;
        |  MEDIUM = 2;
        |  MEDIUM_HIGH = 3;
        |  HIGH = 4;
        |}
        |""".stripMargin)
  }

}
