package eastwood.dataweave

import eastwood.Base
import io.gatling.core.Predef._

import scala.concurrent.duration._

class DataWeave extends Simulation {

  def base = new Base

  val dataweaveScenario = scenario("DataWeave Base").during(5 minutes) {
    exec(
      base.request.createSession,
      base.request.dataWeaveStart,
      base.request.dataWeaveExecute,
      base.request.dataWeaveStop,
      base.request.deleteSession
    )
  }

  setUp(
    dataweaveScenario.inject(
      atOnceUsers(5)
    ).protocols(base.httpConf))
}
