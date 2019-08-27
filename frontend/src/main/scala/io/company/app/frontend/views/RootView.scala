package io.company.app.frontend.views

import io.company.app.frontend.routing.RootState
import io.company.app.shared.css.GlobalStyles
import io.udash._
import io.udash.bootstrap.{BootstrapStyles, UdashBootstrap}
import io.udash.css.CssView

class RootViewFactory() extends StaticViewFactory[RootState.type](
  () => new RootView()
)

class RootView() extends ContainerView with CssView {
  import scalatags.JsDom.all._

  // ContainerView contains default implementation of child view rendering
  // It puts child view into `childViewContaainer`
  override def getTemplate: Modifier = div(
    // loads Bootstrap and FontAwesome styles from CDN
    UdashBootstrap.loadBootstrapStyles(),
    UdashBootstrap.loadFontAwesome(),

    BootstrapStyles.container,
    div(
      GlobalStyles.floatRight
    ),
    h1("playerground"),
    childViewContainer
  )
}
