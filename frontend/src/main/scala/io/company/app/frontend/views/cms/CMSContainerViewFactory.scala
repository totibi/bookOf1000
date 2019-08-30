package io.company.app.frontend.views.cms

import io.company.app.frontend.routing.{CMSContainerState, CMSPageContentState, RoutingState}
import io.company.app.frontend.services.UserContextService
import io.company.app.shared.model.cms.CMSPage
import io.udash._
import io.udash.bootstrap.BootstrapStyles
import io.udash.bootstrap.navs.UdashNav
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
	pages: SeqProperty[CMSPage],
	application: Application[RoutingState]
)(implicit ec: ExecutionContext) extends Presenter[CMSContainerState.type] {
	override def handleState(state: CMSContainerState.type): Unit = {
	
	}
	
	def viewPage(page: CMSPage): Unit = {
		application.goTo(CMSPageContentState(page.id))
	}
}

class CMSContainerView(
	pages: seq.SeqProperty[CMSPage, CastableProperty[CMSPage]],
	presenter: CMSContainerPresenter
) extends ContainerView with CssView {
	
	import scalatags.JsDom.short._
	
	override def getTemplate = {
		val selected = Property(pages.elemProperties.head.get)
		val rightPart = div(
			BootstrapStyles.Grid.colLg1,
			childViewContainer
		).render
		val pagesTitlesNav = div(
			UdashNav()(pages)(
				elemFactory = (page) => a(
					*.href := "",
					*.onclick := { () =>
						selected.set(page.get)
						presenter.viewPage(page.get)
					}
				)(page.asModel.subProp((_.title)).get).render,
				isActive = page => page.combine(selected)((page, selected) =>
					page.title == selected.title
				)
			).render
		).render
		val leftPart = div(
			BootstrapStyles.Grid.colMd1,
			pagesTitlesNav
		).render
		div(
			BootstrapStyles.containerFluid,
			leftPart, rightPart
		)
	}
}