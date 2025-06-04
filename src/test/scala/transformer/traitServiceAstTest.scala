package transformer

import core.AST.*
import core.AST.AstType.{Basic, IO, Union}
import extractor_lib.Extractor

class TraitServiceAstTest extends munit.FunSuite {
"""
  |    def updateCampaign(id: String, userId: String, payload: CampaignUpdate): Future[Campaign | ResultError]
  |    |""".stripMargin

  test("Based trait AST service") {
    val obj = Extractor()
    val src =
      """
        |trait CampaignService:
        |    def createCampaign(id: String, payload: Campaign): Future[Done | ResultError]
        |""".stripMargin
    val result = obj.processServiceTraitSrc(src)
//    println(result)
    assertEquals(
      result,
      Some(SimpleService("CampaignService",
                      List(
                        Method("createCampaign",

                          Some(
                            List(
                              Parameter("id", Basic("String")),
                              Parameter("payload", Basic("Campaign")),
                            )

                          ),


                          IO("Future", Union(Basic("Done"), Basic("ResultError")))),
//                        Method("updateCampaign", IO("Future", Union(Basic("Campaign"), Basic("ResultError")))),
                      ))))
  }

}
