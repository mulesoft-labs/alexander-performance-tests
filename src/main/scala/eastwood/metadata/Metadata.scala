/**
 * (c) 2003-2016 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master
 * Subscription Agreement (or other Terms of Service) separately entered
 * into between you and MuleSoft. If such an agreement is not in
 * place, you may not use the software.
 */
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
