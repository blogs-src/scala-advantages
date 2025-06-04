package transformer

import core.AST.*

class TraitServiceTest extends munit.FunSuite {

  test("Simple trait service".ignore) {
    val obj: Interface.TraitServiceTransformer = Impl.SimpleServiceTransformer()
    val result = obj.service(SimpleService(
      "CampaignService",
      List(
        Method(name = "createCampaign",
               resType = AstType.Union(a = AstType.Basic("Done"),
                                       b = AstType.Basic("ResultError"))))))
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
