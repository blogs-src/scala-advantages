package transformer

import core.AST.*
import core.AST.AstType.{Basic, IO, Union}
import extractor_lib.Extractor

class TraitServiceAstTest_ extends munit.FunSuite {

  test("Based trait AST service") {
    val obj = Extractor()
    val src =
      """
        |trait CampaignService:
        |    def createCampaign(id: String, payload: Campaign): Future[Done | ResultError]
        |    def updateCampaign(id: String, userId: String, payload: CampaignUpdate): Future[Campaign | ResultError]
        |""".stripMargin
    val result = obj.processServiceTraitSrc(src)
    assertEquals(
      result,
      Some(SimpleService("CampaignService",
                      List(
                        Method("createCampaign", IO("Future", Union(Basic("Done"), Basic("ResultError")))),
                        Method("updateCampaign", IO("Future", Union(Basic("Campaign"), Basic("ResultError")))),
                      ))))
  }

}
