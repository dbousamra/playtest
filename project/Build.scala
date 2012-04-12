import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "playtest"
  val appVersion = "0.1"

  resolvers += Resolver.url("Typesafe Ivy Releases", new java.net.URL("http://repo.typesafe.com/typesafe/ivy-snapshots/"))(Patterns("[organisation]/[module]/[revision]/ivy-[revision].xml"::Nil,
    "[organisation]/[module]/[revision]/[artifact]-[revision].[ext]"::Nil,
    false) )

  val appDependencies = Seq(
	"org.squeryl" %% "squeryl" % "0.9.5-RC1",
    "postgresql" % "postgresql" % "8.4-702.jdbc4",
	"play" %% "anorm" % "2.0-RC4")

  


  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings( // Add your own project settings here      
  )

}
            
