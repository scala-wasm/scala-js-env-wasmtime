resolvers += "Sonatype Central Snapshots" at
  "https://central.sonatype.com/repository/maven-snapshots/"

addSbtPlugin("io.github.scala-wasm" % "sbt-scalajs" % "1.22.1-wasm.6")
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.11.2")
