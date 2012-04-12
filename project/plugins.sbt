// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers ++= Seq(Resolver.url("Play Ivy Repo", new java.net.URL("http://download.playframework.org/ivy-releases/"))(Resolver.ivyStylePatterns))

resolvers += "Anorm" at "http://download.playframework.org/ivy-releases/"

libraryDependencies ++= Seq(
"org.squeryl" %% "squeryl" % "0.9.5-RC1",
"junit" % "junit" % "4.7",
"play" %% "anorm" % "2.0-RC1-SNAPSHOT")

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % Option(System.getProperty("play.version")).getOrElse("2.0"))

