package io.company.app.shared.model.cms

import io.udash.properties.HasModelPropertyCreator

case class CMSMessage(content: String)

object CMSMessage extends HasModelPropertyCreator[CMSMessage]
