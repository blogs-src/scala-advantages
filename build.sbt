lazy val scala3Version = "3.7.1"
lazy val scala2Version = "2.13.16"
lazy val scalametaVersion = "4.13.6"
lazy val munitVersion = "1.1.1"
lazy val munitCatsEffectVersion = "2.1.0"
//lazy val munitVersion = "1.0.0"
lazy val catsVersion = "2.13.0"
lazy val guavaVersion = "33.4.0-jre"

lazy val core_model = (project in file("core-model"))
  .settings(
    name := "core-model",
    scalaVersion := scala3Version,
    version := "0.1.0-SNAPSHOT",
    organization := "com.example",
    organizationName := "com.example")

lazy val root = (project in file("."))
  .settings(
    name := "scala-advantages",
    scalaVersion := scala3Version,
    version := "0.1.0-SNAPSHOT",
    organization := "com.example",
    organizationName := "com.example",
    libraryDependencies ++= Seq(
      "org.scalameta"   %% "munit"     % munitVersion % Test,
      "com.google.guava" % "guava"     % guavaVersion,
      "org.typelevel"   %% "cats-core" % catsVersion,
//      "com.example"   %% "core-model" % "0.1.0-SNAPSHOT",
//      ("com.example"   %% "extractor-lib" % "0.1.0-SNAPSHOT").cross(CrossVersion.for3Use2_13),
//      ("org.typelevel"  %% "cats-core" % catsVersion).cross(CrossVersion.for3Use2_13),
    ))
  .dependsOn(extractor_lib)
//  .aggregate(extractor_lib)
  .dependsOn(core_model)
  .aggregate(core_model)

lazy val extractor_lib = (project in file("extractor-lib"))
  .settings(
    name := "extractor-lib",
    scalaVersion := scala2Version,
    version := "0.1.0-SNAPSHOT",
    organization := "com.example",
    organizationName := "com.example",
    scalacOptions += "-Ytasty-reader",
    libraryDependencies ++= Seq(
      "org.scalameta"   %% "scalameta" % scalametaVersion,
//      "org.scalameta" %% "munit" % munitVersion % Test,
      "com.google.guava" % "guava"     % guavaVersion,
      "org.typelevel"   %% "cats-core" % catsVersion % Provided,
//      ("com.example"   %% "core-model" % "0.1.0-SNAPSHOT").cross(CrossVersion.for2_13Use3),
    ))
  .dependsOn(core_model)
  .aggregate(core_model)

addCommandAlias("ll", "projects")
addCommandAlias("cd", "project")
addCommandAlias("c", "compile")
addCommandAlias("t", "test")
addCommandAlias("r", "run")
addCommandAlias("stc", "scalafmtSbtCheck; scalafmtCheckAll")
addCommandAlias("stf", "scalafmtSbt; scalafmtAll")
addCommandAlias("rl", "reload plugins; update; reload return")
