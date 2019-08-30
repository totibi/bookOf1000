package io.company.app.frontend.views.cms

import io.company.app.frontend.routing.{CMSPageContentState, RoutingState}
import io.company.app.frontend.services.UserContextService
import io.company.app.shared.model.cms.{CMSMessage, CMSPage}
import io.udash._
import io.udash.bootstrap.button.{UdashButton, UdashButtonGroup, UdashButtonToolbar}
import io.udash.bootstrap.form.UdashInputGroup
import io.udash.bootstrap.utils.UdashIcons
import io.udash.css.CssView
import io.udash.properties.seq

import scala.concurrent.ExecutionContext

class CMSPageContentViewFactory(
	application: Application[RoutingState],
	userService: UserContextService,
	pageID     : Int
) extends ViewFactory[CMSPageContentState] {
	
	import scala.concurrent.ExecutionContext.Implicits.global
	
	override def create(): (View, Presenter[CMSPageContentState]) = {
		val pageByID = CMSPage.defaultList.find(_.id == pageID).get
		val model = ModelProperty[CMSPage](pageByID)
		val presenter = new CMSPageContentPresenter(model, application)
		val view = new CMSPageContentView(presenter)
		(view, presenter)
	}
}

class CMSPageContentPresenter(
	selectedPage: ModelProperty[CMSPage],
	application  : Application[RoutingState]
)(implicit ec: ExecutionContext) extends Presenter[CMSPageContentState] {
	override def handleState(state: CMSPageContentState): Unit = {}
	
	val messagesProp: seq.SeqProperty[CMSMessage, CastableProperty[CMSMessage]] = selectedPage.subSeq(_.messages)
	
	def addMsg(): Unit = {
		messagesProp.append(new CMSMessage(""))
	}
	
}

class CMSPageContentView(
	presenter: CMSPageContentPresenter
) extends FinalView with CssView {
	
	import scalatags.JsDom.short._
	
	override def getTemplate = {
		div(
			UdashButtonToolbar(
				UdashButtonGroup()(
					UdashButton()(
						i(UdashIcons.FontAwesome.plus),
						*.onclick := { () => presenter.addMsg() }
					).render
				).render
			).render,
			repeat(presenter.messagesProp) {
				message =>
					UdashInputGroup()(
						UdashInputGroup.input(
							TextArea(message.asModel.subProp(_.content))(*.placeholder := "New message").render
						)
					).render
			}
		)
	}
}