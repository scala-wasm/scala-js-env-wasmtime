addSbtPlugin("io.github.scala-wasm" % "sbt-scalajs" % "1.22.1-wasm.6")

libraryDependencies +=
  "io.github.scala-wasm" %% "scalajs-env-wasmtime" % sys.props("scalajs-env-wasmtime.version")
