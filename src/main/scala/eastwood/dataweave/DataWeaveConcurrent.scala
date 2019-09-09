package eastwood.dataweave

import eastwood.Base
import io.gatling.core.Predef._

import scala.concurrent.duration._

class DataWeaveConcurrent extends Simulation {

  def base = new Base

  val dataweaveConcurrentScenario = scenario("DataWeave Multiple Execute Scenario").during(5 minutes) {
    exec(
      base.request.createSession,
      base.request.dataWeaveStart,
      base.request.dataWeaveExecute,
      base.request.dataWeaveExecute,
      base.request.dataWeaveExecute,
      base.request.dataWeaveExecute,
      base.request.dataWeaveExecute,
      base.request.dataWeaveStop,
      base.request.deleteSession
    )
  }

  setUp(
    dataweaveConcurrentScenario.inject(
      atOnceUsers(5)
    ).protocols(base.httpConf))
}
