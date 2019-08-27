package io.company.app.frontend.views.cms

import io.company.app.frontend.routing.{CMSContainerState, RoutingState}
import io.company.app.frontend.services.UserContextService
import io.company.app.shared.model.cms.CMSPage
import io.udash._
import io.udash.core.{Presenter, View}
import io.udash.css.CssView
import io.udash.properties.seq

import scala.concurrent.ExecutionContext

class CMSContainerViewFactory(
	application: Application[RoutingState],
	userService: UserContextService
) extends ViewFactory[CMSContainerState.type] {
	
	import scala.concurrent.ExecutionContext.Implicits.global
	
	override def create(): (View, Presenter[CMSContainerState.type]) = {
		
		val model = SeqProperty(CMSPage.defaultList)
		val presenter = new CMSContainerPresenter(model, application)
		val view = new CMSContainerView(model, presenter)
		(view, presenter)
	}
}

class CMSContainerPresenter(
	pages        : SeqProperty[CMSPage],
	application  : Application[RoutingState]
)(implicit ec : ExecutionContext) extends Presenter[CMSContainerState.type] {
	override def handleState(state: CMSContainerState.type): Unit = {
	
	}
}

class CMSContainerView(
	pages    : seq.SeqProperty[CMSPage, CastableProperty[CMSPage]],
	presenter: CMSContainerPresenter
) extends ContainerView with CssView {
	
	import scalatags.JsDom.short._
	
	override def getTemplate = {
		val leftPart = div(
			childViewContainer
		).render
		val pagesTitles = div(
			repeat(pages) {
				page =>
					div(
						b(span(page.asModel.subProp(_.title).get)),
						br
					).render
			}
		).render
		val rightPart = div(
			pagesTitles
		).render
		div(leftPart, rightPart)
	}
}