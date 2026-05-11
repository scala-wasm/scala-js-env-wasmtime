import org.scalajs.linker.interface.ModuleKind
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

val Scala212 = "2.12.21"
val Scala3 = "3.8.3"

inThisBuild(
  Seq(
    organization := "io.github.scala-wasm",
    scalaVersion := Scala212,
    crossScalaVersions := Seq(Scala212, Scala3),
    versionScheme := Some("semver-spec"),
    homepage := Some(url("https://github.com/scala-wasm/scala-js-env-wasmtime")),
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "tanishiking",
        "Rikito Taniguchi",
        "tanishiking@users.noreply.github.com",
        url("https://github.com/tanishiking")
      )
    ),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/scala-wasm/scala-js-env-wasmtime"),
        "scm:git:git@github.com:scala-wasm/scala-js-env-wasmtime.git",
        Some("scm:git:git@github.com:scala-wasm/scala-js-env-wasmtime.git")
      )
    ),
    resolvers += "Sonatype Central Snapshots" at
      "https://central.sonatype.com/repository/maven-snapshots/"
  )
)

val commonSettings = Def.settings(
  scalacOptions ++= Seq("-deprecation", "-feature", "-Werror"),
  apiURL := {
    val name = moduleName.value
    val v = version.value
    Some(url(s"https://www.scala-js.org/api/$name/$v/"))
  },
  autoAPIMappings := true,
  pomIncludeRepository := { _ => false }
)

lazy val root = project
  .in(file("."))
  .aggregate(`scalajs-env-wasmtime`, `wasmtime-test-rpc-adapter`, `test-project`)
  .settings(
    scalacOptions ++= Seq("-deprecation", "-feature", "-Werror"),
    publish / skip := true
  )

lazy val `scalajs-env-wasmtime` = project
  .in(file("scalajs-env-wasmtime"))
  .settings(
    commonSettings,
    name := "scalajs-env-wasmtime",
    libraryDependencies ++= Seq(
      "org.scala-js" %% "scalajs-js-envs" % "1.6.0",
      "junit" % "junit" % "4.13.2" % Test,
      "com.novocode" % "junit-interface" % "0.11" % Test
    ),
    Compile / resourceGenerators += Def.task {
      (`wasmtime-test-rpc-adapter` / Compile / fastLinkJS).value

      val fastSource = {
        (`wasmtime-test-rpc-adapter` / Compile / fastLinkJS / scalaJSLinkerOutputDirectory).value /
          "main.wasm"
      }

      val targetDir = {
        (Compile / resourceManaged).value /
          "org" / "scalajs" / "jsenv" / "wasmtime" / "test-rpc"
      }
      val defaultTarget = targetDir / "adapter.wasm"
      val fastTarget = targetDir / "adapter-fastopt.wasm"

      IO.createDirectory(targetDir)
      IO.copyFile(fastSource, fastTarget)
      IO.copyFile(fastSource, defaultTarget)

      Seq(defaultTarget, fastTarget)
    }.taskValue
  )

lazy val `wasmtime-test-rpc-adapter` = project
  .in(file("wasmtime-test-rpc-adapter"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    commonSettings,
    name := "wasmtime-test-rpc-adapter",
    scalaVersion := Scala212,
    crossScalaVersions := Seq(Scala212),
    publish / skip := true,
    Test / test := {},
    scalaJSUseMainModuleInitializer := false,
    scalaJSWitDirectory := baseDirectory.value / "wit",
    scalaJSWitWorld := Some("test-rpc-adapter"),
    scalaJSLinkerConfig ~= { config =>
      val witDir = file("wasmtime-test-rpc-adapter/wit").getAbsolutePath
      config
        .withExperimentalUseWebAssembly(true)
        .withWasmFeatures(_.withWitDirectory(Some(witDir)))
        .withWasmFeatures(_.withWitWorld(Some("test-rpc-adapter")))
        .withModuleKind(ModuleKind.WasmComponent)
    },
    Compile / fastLinkJS / scalaJSLinkerOutputDirectory := target.value / "adapter-fastopt"
  )

lazy val `test-project` = project
  .in(file("test-project"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    commonSettings,
    publish / skip := true,
    name := "scalajs-env-wasmtime-test-project",
    crossScalaVersions := Seq(Scala212),
    Test / test := {},
    scalaJSUseMainModuleInitializer := true,
    scalaJSWitDirectory := baseDirectory.value / "wit",
    scalaJSWitWorld := Some("testproject"),
    scalaJSLinkerConfig ~= { config =>
      val witDir = file("test-project/wit").getAbsolutePath
      config
        .withExperimentalUseWebAssembly(true)
        .withModuleKind(ModuleKind.WasmComponent)
        .withWasmFeatures(_.withWitDirectory(Some(witDir)))
        .withWasmFeatures(_.withWitWorld(Some("testproject")))
    },
    jsEnv := new org.scalajs.jsenv.wasmtime.WasmtimeEnv(),
    Compile / jsEnvInput := {
      (Compile / fastLinkJS).value
      val linkerOutputDir =
        (Compile / fastLinkJS / scalaJSLinkerOutputDirectory).value.toPath
      Seq(
        org.scalajs.jsenv.wasmtime.WasmtimeInput.WasmComponent(linkerOutputDir.resolve("main.wasm"))
      )
    }
  )
