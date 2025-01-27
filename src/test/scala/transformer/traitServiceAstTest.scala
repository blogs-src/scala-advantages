package transformer

import core.AST.*
import extractor_lib.Extractor

class TraitServiceAstTest extends munit.FunSuite {

  test("Based trait AST service".ignore.pending("AST model for service definition")) {
    val obj = Extractor()
    val src =
      """
        |trait CampaignService:
        |    def createCampaign(id: String, payload: Campaign): Future[Done | ResultError]
        |    def updateCampaign(id: String, userId: String, payload: CampaignUpdate): Future[Campaign | ResultError]
        |    def getCampaign(id: String, userId: String): Future[Campaign | ResultError]
        |    def getCampaign(id: String): Future[Campaign | ResultError]
        |    def addFlight(id: String, flightId: String, userId: String): Future[Done | ResultError]
        |    def getAllFlights(id: String, userId: String): Future[CampaignFlights | ResultError]
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
