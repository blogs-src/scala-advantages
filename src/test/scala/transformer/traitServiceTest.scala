package transformer

import core.AST.*
import core.AST.AstType.Basic

class TraitServiceTest extends munit.FunSuite {

  test("Simple trait service".ignore) {
    val obj: Interface.TraitServiceTransformer = Impl.SimpleServiceTransformer()
    val result = obj.service(SimpleService(
      "CampaignService",
      List(
        Method(name = "createCampaign",
               params = Some(List(Parameter("a", Basic("Int")), Parameter("a", Basic("Int")))),
               resType = AstType.Union(a = Basic("Done"),
                                       b = Basic("ResultError"))))))
    assertEquals(
      result,
      """
        message CreateCampaignResponse {
          google.protobuf.StringValue id = 1;
        }
        message CreateCampaignRequest {
          google.protobuf.Int64Value advertiserId = 1;
          google.protobuf.StringValue name = 2;
          infrastructure.campaigns.Budget budget = 3;
          infrastructure.campaigns.Status status = 4;
        }
        service CampaignGrpcService {
          rpc CreateCampaign(CreateCampaignRequest) returns (CreateCampaignResponse);
        }
        """.stripMargin)
  }

}
