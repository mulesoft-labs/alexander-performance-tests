/**
 * (c) 2003-2016 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master
 * Subscription Agreement (or other Terms of Service) separately entered
 * into between you and MuleSoft. If such an agreement is not in
 * place, you may not use the software.
 */
package eastwood.autocomplete

import eastwood.Base
import io.gatling.core.Predef._

import scala.concurrent.duration._

class Autocomplete extends Simulation {

  def base = new Base

  val autocompleteScenario = scenario("Autocomplete Base").during(5 minutes) {
    exec(
      base.request.createSession,
      base.request.autocomplete,
      base.request.deleteSession
    )
  }

  setUp(
    autocompleteScenario.inject(
      atOnceUsers(5)
    ).protocols(base.httpConf))
}
