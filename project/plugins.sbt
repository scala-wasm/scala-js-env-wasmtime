resolvers += "Sonatype Central Snapshots" at
  "https://central.sonatype.com/repository/maven-snapshots/"

addSbtPlugin("io.github.scala-wasm" % "sbt-scalajs" % "1.22.1-wasm.5")
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.11.2")

libraryDependencies +=
  "org.scala-js" %% "scalajs-js-envs" % "1.6.0"

Compile / unmanagedSourceDirectories ++= Seq(
  baseDirectory.value.getParentFile / "scalajs-env-wasmtime-input/src/main/scala",
  baseDirectory.value.getParentFile / "scalajs-env-wasmtime/src/main/scala"
)
