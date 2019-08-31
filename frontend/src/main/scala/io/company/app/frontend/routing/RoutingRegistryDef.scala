package io.company.app.frontend.routing

import io.company.app.shared.model.cms.CMSPage
import io.udash._

class RoutingRegistryDef extends RoutingRegistry[RoutingState] {
	def matchUrl(url: Url): RoutingState =
		url2State.applyOrElse(
			"/" + url.value.stripPrefix("/").stripSuffix("/"),
			(_: String) => LoginPageState
		)
	
	def matchState(state: RoutingState): Url =
		Url(state2Url.apply(state))
  
  private val state2Url: PartialFunction[RoutingState, String] = {
    case CMSContainerState => "/"
    case LoginPageState => "/login"
    case CMSPageContentState(pageTitle) => s"/content/${pageTitle}"
  }
  
	private val url2State: PartialFunction[String, RoutingState] = {
		case "/" => CMSContainerState
		case "/content" / pageTitle if CMSPage.defaultList.exists(_.title.toString.eq(pageTitle)) => CMSPageContentState(pageTitle)
	}
}