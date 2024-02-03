val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "easydelta",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "com.github.scopt" %% "scopt" % "4.1.0",
      ("io.delta" %% "delta-core" % "2.1.0").cross(CrossVersion.for3Use2_13),
      ("org.apache.spark" %% "spark-sql" % "3.4.2" % "provided").cross(CrossVersion.for3Use2_13),
      "com.lihaoyi" %% "os-lib" % "0.9.2",
      "org.yaml" % "snakeyaml" % "2.2",
      "org.scalameta" %% "munit" % "0.7.29" % Test
  )
)
Compile / run := Defaults.runTask(Compile / fullClasspath, Compile / run / mainClass, Compile / run / runner).evaluated
