import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._

object Dependencies {
	val versionOfScala = "2.12.8"
	
	// Udash
	val udashVersion = "0.8.0-RC8"
	val udashJQueryVersion = "3.0.1"
	
	// Backend
	val avsystemCommonsVersion = "1.36.0"
	val jettyVersion = "9.4.19.v20190610"
	val springVersion = "4.3.24.RELEASE"
	val logbackVersion = "1.2.3"
	
	// JS dependencies
	val bootstrapVersion = "3.3.7"
	
	// Testing
	val scalatestVersion = "3.0.8"
	val scalamockVersion = "4.1.0"
	
	// Dependencies for both frontend and backend
	// Those have to be cross-compilable
	val crossDeps = Def.setting(Seq(
		"io.udash" %%% "udash-core" % udashVersion,
		"io.udash" %%% "udash-rpc" % udashVersion,
		"io.udash" %%% "udash-rest" % udashVersion,
		"io.udash" %%% "udash-i18n" % udashVersion,
		"io.udash" %%% "udash-css" % udashVersion,
		"io.udash" %%% "udash-auth" % udashVersion,
	))
	
	// Dependencies compiled to JavaScript code
	val frontendDeps = Def.setting(Seq(
		"io.udash" %%% "udash-core" % udashVersion,
		"io.udash" %%% "udash-rpc" % udashVersion,
		"io.udash" %%% "udash-i18n" % udashVersion,
		"io.udash" %%% "udash-css" % udashVersion,
		"io.udash" %%% "udash-auth" % udashVersion,
		
		// type-safe wrapper for Twitter Bootstrap
		"io.udash" %%% "udash-bootstrap" % udashVersion,
	))
	
	val tinyMCEVersion = "5.0.12"
	// JavaScript libraries dependencies
	// Those will be added into frontend-deps.js
	val frontendJSDeps = Def.setting(Seq(
		
		// tinyMCE WYSIWYG html editor
		"org.webjars.npm" % "tinymce" % tinyMCEVersion /
			"tinymce.js" minified "tinymce.min.js",
		"org.webjars.npm" % "tinymce" % tinyMCEVersion /
			"themes/silver/theme.js" minified "themes/silver/theme.min.js",
		
		// "jquery.js" is provided by "udash-jquery" dependency
		"org.webjars" % "bootstrap" % bootstrapVersion /
			"bootstrap.js" minified "bootstrap.min.js" dependsOn "jquery.js",
	
	))
	
	val frontCSSDep = Def.setting(Seq(
		"lib/bootstrap/less/bootstrap.less",
		"lib/font-awesome/less/font-awesome.less",
		"lib/tinymce/skins/ui/oxide/skin.min.css",
		"lib/tinymce/skins/ui/oxide/content.min.css",
		"lib/tinymce/skins/content/default/content.css"
	))
	
	val npmDeps = Def.setting(Seq(
		"tinymce" -> tinyMCEVersion,
		"bootstrap" -> bootstrapVersion,
		"popper.js" -> "1.14.7",
		"jquery" -> "3.3.1"
	))
	
	// Dependencies for JVM part of code
	val backendDeps = Def.setting(Seq(
		"io.udash" %% "udash-rpc" % udashVersion,
		"io.udash" %% "udash-rest" % udashVersion,
		"io.udash" %% "udash-i18n" % udashVersion,
		"io.udash" %% "udash-css" % udashVersion,
		
		"org.eclipse.jetty" % "jetty-server" % jettyVersion,
		"org.eclipse.jetty" % "jetty-rewrite" % jettyVersion,
		"org.eclipse.jetty.websocket" % "websocket-server" % jettyVersion,
		
		"org.springframework" % "spring-core" % springVersion,
		"org.springframework" % "spring-beans" % springVersion,
		"org.springframework" % "spring-context-support" % springVersion,
		
		// support for HOCON beans configuration
		"com.avsystem.commons" %% "commons-spring" % avsystemCommonsVersion,
		
		// server logging backend
		"ch.qos.logback" % "logback-classic" % logbackVersion,
	))
	
	// Test dependencies
	val crossTestDeps = Def.setting(Seq(
		"org.scalatest" %%% "scalatest" % scalatestVersion,
		"org.scalamock" %%% "scalamock" % scalamockVersion
	).map(_ % Test))
}
