resolvers += "Sonatype Central Snapshots" at
  "https://central.sonatype.com/repository/maven-snapshots/"

addSbtPlugin("io.github.scala-wasm" % "sbt-scalajs" % "1.20.2-wasm.2-SNAPSHOT")
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.11.2")

libraryDependencies +=
  "org.scala-js" %% "scalajs-js-envs" % "1.5.0+3-1bf7184b-SNAPSHOT"

Compile / unmanagedSourceDirectories +=
  baseDirectory.value.getParentFile / "scalajs-env-wasmtime/src/main/scala"
