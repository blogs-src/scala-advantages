package transformer

import core.AST.*

class TraitServiceTest extends munit.FunSuite {

  test("Simple trait service") {
    val obj: Interface.TraitServiceTransformer = Impl.SimpleServiceTransformer()
    val result = obj.service(SimpleService(
      "CampaignService",
      List(
        Method(name="createCampaign", resType=AstType.Union(a=AstType.Basic("Done"),b=AstType.Basic("ResultError"))),
      ),
    ))
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
        |""".stripMargin,
    )
  }

}
