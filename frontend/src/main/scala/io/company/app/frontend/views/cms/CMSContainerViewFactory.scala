package io.company.app.frontend.views.cms

import io.company.app.frontend.routing.{CMSContainerState, RoutingState}
import io.udash._
import io.udash.core.{Presenter, View}
import io.udash.css.CssView

import scala.concurrent.ExecutionContext

class CMSContainerViewFactory(
	application: Application[RoutingState]
) extends ViewFactory[CMSContainerState.type] {
	
	import scala.concurrent.ExecutionContext.Implicits.global
	
	override def create(): (View, Presenter[CMSContainerState.type]) = {
		val presenter = new CMSContainerPresenter(application)
		val view = new CMSContainerView(presenter)
		(view, presenter)
	}
}

class CMSContainerPresenter(
	application: Application[RoutingState]
)(implicit ec : ExecutionContext) extends Presenter[CMSContainerState.type] {
	override def handleState(state: CMSContainerState.type): Unit = {}
}

class CMSContainerView(
	presenter: CMSContainerPresenter
) extends ContainerView with CssView {
	
	import scalatags.JsDom.all._
	
	override def getTemplate = {
		h1("CMS")
	}
}