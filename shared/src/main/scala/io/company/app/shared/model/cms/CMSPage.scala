package io.company.app.shared.model.cms

import io.udash.properties.HasModelPropertyCreator

case class CMSPage(id: Int, title: String, messages: Seq[CMSMessage] = Nil) {
	
}

object CMSPage extends HasModelPropertyCreator[CMSPage]{
	
	def defaultList: Seq[CMSPage] = Seq(
		CMSPage(0, "default1"),
		CMSPage(1, "default2"),
		CMSPage(2, "default3"),
	)
}
