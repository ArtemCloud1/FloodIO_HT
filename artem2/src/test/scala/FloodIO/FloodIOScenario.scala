package FloodIO

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class FloodIOScenario extends Simulation {

	val th_min = 1
	val th_max = 2
	val users = 5
  val duration = 60
val BaseUrl ="https://challenge.flood.io"

	val httpProtocol = http
		.baseUrl(BaseUrl)
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""",""".*css.*""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:97.0) Gecko/20100101 Firefox/97.0")
		.disableFollowRedirect
	val headers_0 = Map(
		"Sec-Fetch-Dest" -> "document",
		"Sec-Fetch-Mode" -> "navigate",
		"Sec-Fetch-Site" -> "none",
		"Sec-Fetch-User" -> "?1",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Origin" -> "https://challenge.flood.io",
		"Sec-Fetch-Dest" -> "document",
		"Sec-Fetch-Mode" -> "navigate",
		"Sec-Fetch-Site" -> "same-origin",
		"Sec-Fetch-User" -> "?1",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_5 = Map(
		"Accept" -> "*/*",
		"Sec-Fetch-Dest" -> "empty",
		"Sec-Fetch-Mode" -> "cors",
		"Sec-Fetch-Site" -> "same-origin",
		"X-Requested-With" -> "XMLHttpRequest")



	val scn = scenario("FloodIo")


		.exec(http("Open home page")
			.get("/")
			.headers(headers_0)
		.check(regex("name=\"authenticity_token\" type=\"hidden\" value=\"(.*?)\"").find.saveAs("auth_token"))
		.check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id"))
			.check(status.is(200)))

		.pause(th_min , th_max)

		.exec(http("Click start button")
			.post("/start")
			.headers(headers_1)
			.formParam("utf8", "✓")
			.formParam("authenticity_token", "${auth_token}")
			.formParam("challenger[step_id]", "${step_id}")
			.formParam("challenger[step_number]", "1")
			.formParam("commit", "Start")
		.check(status.is(302)))

		.pause(th_min , th_max)

		.exec(http("Select Age")
		.get("/step/2")
			.headers(headers_1)
			.check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id"))
		.check(status.is(200)))

		.pause(th_min , th_max)

		.exec(http("Click next")
			.post("/start")
			.headers(headers_1)
			.formParam("utf8", "✓")
			.formParam("authenticity_token", "${auth_token}")
			.formParam("challenger[step_id]", "${step_id}")
			.formParam("challenger[step_number]", "2")
			.formParam("challenger[age]", "27")
			.formParam("commit", "Next")
			.check(status.is(302)))

		.pause(th_min , th_max)


		.exec(http("Select Max Order")
			.get("/step/3")
			.headers(headers_1)
			.check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id"))
			.check(css(".collection_radio_buttons").findAll.transform(arg=>arg.map(_.toInt).max).saveAs("largest_order"))
			.check(regex(".*order_selected.*value=\"(.*?)\"").find.saveAs("order_selected"))
			.check(status.is(200)))

		.pause(th_min , th_max)

		.exec(http("Post max order")
			.post("/start")
			.headers(headers_1)
			.formParam("utf8", "✓")
			.formParam("authenticity_token", "${auth_token}")
			.formParam("challenger[step_id]", "${step_id}")
			.formParam("challenger[step_number]", "3")
			.formParam("challenger[largest_order]", "${largest_order}")
			.formParam("challenger[order_selected]", "${order_selected}")
			.formParam("commit", "Next")
		.check(status.is(302)))

		.pause(th_min , th_max)

		.exec(http("Click next")
			.get("/step/4")
			.headers(headers_0)
			.check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id"))
			.check(status.is(200)))

		.pause(th_min , th_max)

		.exec(http("POST Click next")
			.post("/start")
			.headers(headers_1)
			.formParam("utf8", "✓")
			.formParam("authenticity_token", "${auth_token}")
			.formParam("challenger[step_id]", "${step_id}")
			.formParam("challenger[step_number]", "4")
			.formParam("challenger[order_9]", "1645196750")
			.formParam("challenger[order_4]", "1645196750")
			.formParam("challenger[order_5]", "1645196750")
			.formParam("challenger[order_3]", "1645196750")
			.formParam("challenger[order_6]", "1645196750")
			.formParam("challenger[order_6]", "1645196750")
			.formParam("challenger[order_14]", "1645196750")
			.formParam("challenger[order_13]", "1645196750")
			.formParam("challenger[order_8]", "1645196750")
			.formParam("challenger[order_13]", "1645196750")
			.formParam("commit", "Next")
			.resources(http("Get one time token")
			.get("/code")
				.headers(headers_5)
				.check(jsonPath("$.code").find.saveAs("codeToken")))
			.check(status.is(302)))

		.pause(th_min , th_max)

		.exec(http("Enter one time token")
			.get("/step/5")
			.headers(headers_1)
			.check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id"))
			.check(status.is(200)))

		.pause(th_min , th_max)

		.exec(http("Click next")
			.post("/start")
			.headers(headers_1)
			.formParam("utf8", "✓")
			.formParam("authenticity_token", "${auth_token}")
			.formParam("challenger[step_id]", "${step_id}")
			.formParam("challenger[step_number]", "5")
			.formParam("challenger[one_time_token]", "${codeToken}")
			.formParam("commit", "Next")
			.check(status.is(302)))

	setUp(scn.inject(rampUsers(users).during(duration))).protocols(httpProtocol)
}