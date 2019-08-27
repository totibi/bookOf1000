package io.company.app.shared.model.cms

import io.udash.properties.HasModelPropertyCreator

case class CMSPage(title: String, messages: Seq[CMSMessage] = Nil) {
	
}

object CMSPage extends HasModelPropertyCreator[CMSPage]{
	
	def defaultList: Seq[CMSPage] = Seq(
		CMSPage("default1"),
		CMSPage("default2"),
		CMSPage("default3"),
	)
}
