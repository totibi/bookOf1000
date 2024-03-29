package io.company.app.frontend.views.login

import io.company.app.frontend.services.TranslationsService
import io.company.app.shared.css.LoginPageStyles
import io.company.app.shared.i18n.Translations
import io.udash._
import io.udash.bootstrap.alert.UdashAlert
import io.udash.bootstrap.button.{ButtonStyle, UdashButton}
import io.udash.bootstrap.form.UdashForm
import io.udash.bootstrap.tooltip.UdashPopover
import io.udash.bootstrap.utils.UdashIcons.FontAwesome
import io.udash.component.ComponentId
import io.udash.css._
import io.udash.i18n._
import org.scalajs.dom.raw.Event

class LoginPageView(
  model: ModelProperty[LoginPageModel],
  presenter: LoginPagePresenter,
  translationsService: TranslationsService
) extends FinalView with CssView {

  import translationsService._
  import scalatags.JsDom.all._

  private val errorsAlert = UdashAlert.danger(ComponentId("alerts"),
    repeat(model.subSeq(_.errors)) { error =>
      div(translatedDynamic(error.get)(_.apply())).render
    }
  )

  private val infoIcon = span(
    LoginPageStyles.infoIcon,
    span(
      FontAwesome.Modifiers.stack,
      span(FontAwesome.info, FontAwesome.Modifiers.stack1x),
      span(FontAwesome.circleThin, FontAwesome.Modifiers.stack2x)
    )
  ).render

  // infoIcon - translated popover
  UdashPopover.i18n(content = _ => Translations.Auth.info, trigger = Seq(UdashPopover.HoverTrigger))(infoIcon)

  private val usernameInput = UdashForm.textInput(
    ComponentId("username"), Some(UdashForm.validation(model.subProp(_.username)))
  )(
    // translated label and info icon
    translatedDynamic(Translations.Auth.usernameFieldLabel)(_.apply()), " ", infoIcon
  )(
    model.subProp(_.username),
    translatedAttrDynamic(Translations.Auth.usernameFieldPlaceholder, "placeholder")(_.apply())
  )

  private val passwordInput = UdashForm.passwordInput(
    ComponentId("password"), Some(UdashForm.validation(model.subProp(_.password)))
  )(
    // translated label
    translatedDynamic(Translations.Auth.passwordFieldLabel)(_.apply())
  )(
    model.subProp(_.password),
    translatedAttrDynamic(Translations.Auth.passwordFieldPlaceholder, "placeholder")(_.apply())
  )

  // Button from Udash Bootstrap wrapper
  private val submitButton = UdashButton(
    buttonStyle = ButtonStyle.Primary,
    block = true, componentId = ComponentId("login")
  )(translatedDynamic(Translations.Auth.submitButton)(_.apply()), tpe := "submit")

  // disable button when data is invalid
  model.valid.streamTo(submitButton.disabled, initUpdate = true) {
    case Valid => false
    case _ => true
  }

  def getTemplate: Modifier = div(
    LoginPageStyles.container,

    showIf(model.subProp(_.errors).transform(_.nonEmpty))(
      errorsAlert.render
    ),

    UdashForm(
      (_: Event) => {
        if (!model.subProp(_.waitingForResponse).get) presenter.login()
        true // prevent default callback call
      }
    )(
      componentId = ComponentId("login-from"),

      usernameInput,
      passwordInput,

      // submit button or spinner
      showIfElse(model.subProp(_.waitingForResponse))(
        div(
          LoginPageStyles.textCenter,
          span(FontAwesome.spinner, FontAwesome.Modifiers.spin, FontAwesome.Modifiers.x3)
        ).render,
        submitButton.render
      )
    ).render
  )
}
