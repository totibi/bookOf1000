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
    case CMSPageContentState(pageID) => s"/content/${pageID}"
  }
  
	private val url2State: PartialFunction[String, RoutingState] = {
		case "/" => CMSContainerState
		case "/content" / pageID if CMSPage.defaultList.exists(_.id.toString.eq(pageID)) => CMSPageContentState(pageID.toInt)
	}
}