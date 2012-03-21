import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "playtest"
  val appVersion = "0.1"

  val appDependencies = Seq(
    "postgresql" % "postgresql" % "8.4-702.jdbc4")

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings( // Add your own project settings here      
  )

}
            
