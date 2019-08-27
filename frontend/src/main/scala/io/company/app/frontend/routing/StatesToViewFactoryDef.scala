package io.company.app.frontend.routing

import io.company.app.frontend.ApplicationContext
import io.company.app.frontend.views.RootViewFactory
import io.company.app.frontend.views.cms.CMSContainerViewFactory
import io.company.app.frontend.views.login.LoginPageViewFactory
import io.udash._

class StatesToViewFactoryDef extends ViewFactoryRegistry[RoutingState] {
	def matchStateToResolver(state: RoutingState): ViewFactory[_ <: RoutingState] =
		state match {
			case RootState => new RootViewFactory
			case LoginPageState => new LoginPageViewFactory(
				ApplicationContext.userService, ApplicationContext.application, ApplicationContext.translationsService
			)
			case CMSContainerState => new CMSContainerViewFactory(ApplicationContext.application, ApplicationContext.userService)
		}
}