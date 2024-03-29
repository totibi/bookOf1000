package io.company.app.frontend.routing

import io.company.app.shared.model.cms.CMSPage
import io.udash._

sealed abstract class RoutingState(val parentState: Option[ContainerRoutingState]) extends State {
	override type HierarchyRoot = RoutingState
}

sealed abstract class ContainerRoutingState(parentState: Option[ContainerRoutingState])
	extends RoutingState(parentState) with ContainerState

sealed abstract class FinalRoutingState(parentState: Option[ContainerRoutingState])
	extends RoutingState(parentState) with FinalState

case object RootState extends ContainerRoutingState(None)
case object LoginPageState extends FinalRoutingState(Some(RootState))
case object CMSContainerState extends ContainerRoutingState(Some(RootState))
case class CMSPageContentState(pageTitle: String) extends FinalRoutingState(Some(CMSContainerState))
