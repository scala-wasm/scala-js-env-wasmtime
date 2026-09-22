import org.scalajs.ir.WitScope
import org.scalajs.linker.interface.ESVersion
import org.scalajs.linker.interface.WasmComponentModuleInitializerExport
import org.scalajs.linker.interface.WasmComponentModuleInitializerExport._

ThisBuild / scalaVersion := "2.12.21"
ThisBuild / crossScalaVersions := Seq("2.12.21", "2.13.18")
ThisBuild / organization := "io.github.scala-wasm"

enablePlugins(ScalaJSPlugin, ScalaJSJUnitPlugin)

name := "scalajs-env-wasmtime-test-project"
publish / skip := true
scalaJSUseMainModuleInitializer := true
scalaJSWitDirectory := baseDirectory.value / "wit"
scalaJSWitWorld := Some("testproject")
scalaJSLinkerConfig ~= { config =>
  config
    .withESFeatures(_.withESVersion(ESVersion.ES2022).withUseWebAssembly(true))
    .withModuleKind(ModuleKind.WasmComponent)
    .withWasmFeatures(
      _.withModuleInitializerExport(
        Some(
          WasmComponentModuleInitializerExport(
            scope = WitScope.Interface("wasi", "cli", "run", Some("0.2.0")),
            functionName = "run",
            resultType = ResultType.ResultUnitUnit
          )
        )
      )
    )
}
jsEnv := new org.scalajs.jsenv.wasmtime.WasmtimeEnv()
