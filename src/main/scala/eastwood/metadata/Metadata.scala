package eastwood.metadata

import eastwood.Base
import io.gatling.core.Predef._

import scala.concurrent.duration._

class Metadata extends Simulation {

  def base = new Base

  val metadataScenario = scenario("Metadata Propagation Base").during(5 minutes) {
    exec(
      base.request.createSession,
      base.request.metadataPropagation,
      base.request.deleteSession
    )
  }

  setUp(
    metadataScenario.inject(
      atOnceUsers(5)
    ).protocols(base.httpConf))
}
