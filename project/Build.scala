import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "false-friends"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.squeryl"		%% "squeryl" 			% "0.9.5",   
      "mysql" % "mysql-connector-java" % "5.1.18"
    )    

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
