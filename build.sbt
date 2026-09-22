import org.scalajs.linker.interface.ESVersion
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
  .aggregate(
    `scalajs-env-wasmtime-input`,
    `scalajs-env-wasmtime`,
    `wasmtime-test-rpc-adapter`
  )
  .settings(
    scalacOptions ++= Seq("-deprecation", "-feature", "-Werror"),
    publish / skip := true
  )

lazy val `scalajs-env-wasmtime-input` = project
  .in(file("scalajs-env-wasmtime-input"))
  .settings(
    commonSettings,
    name := "scalajs-env-wasmtime-input",
    version := "0.1.0",
    // 0.1.0 is already on maven central and sbt-scalajs (wasm) uses it
    // releasing new vesrion may cause eviction error
    // even if the content is the same.
    // publish this only when the content has really changed.
    publish / skip := true,
    libraryDependencies +=
      "org.scala-js" %% "scalajs-js-envs" % "1.6.0"
  )

lazy val `scalajs-env-wasmtime` = project
  .in(file("scalajs-env-wasmtime"))
  .dependsOn(`scalajs-env-wasmtime-input`)
  .settings(
    commonSettings,
    name := "scalajs-env-wasmtime",
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.13.2" % Test,
      "com.novocode" % "junit-interface" % "0.11" % Test
    ),
    Compile / resourceGenerators += Def.task {
      (`wasmtime-test-rpc-adapter` / Compile / fullLinkJS).value

      val fullSource = {
        (`wasmtime-test-rpc-adapter` / Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value /
          "main.wasm"
      }

      val targetDir = {
        (Compile / resourceManaged).value /
          "org" / "scalajs" / "jsenv" / "wasmtime" / "test-rpc"
      }
      val defaultTarget = targetDir / "adapter.wasm"

      IO.createDirectory(targetDir)
      IO.copyFile(fullSource, defaultTarget)

      Seq(defaultTarget)
    }.taskValue
  )

lazy val `scripted-tests` = project
  .in(file("scripted-tests"))
  .enablePlugins(ScriptedPlugin)
  .settings(
    scalaVersion := Scala212,
    crossScalaVersions := Seq(Scala212),
    publish / skip := true,
    scriptedLaunchOpts += "-Dscalajs-env-wasmtime.version=" + version.value,
    scriptedDependencies := (`scalajs-env-wasmtime` / publishLocal).value
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
      config
        .withESFeatures(_.withESVersion(ESVersion.ES2022).withUseWebAssembly(true))
        .withModuleKind(ModuleKind.WasmComponent)
    },
    Compile / fullLinkJS / scalaJSLinkerOutputDirectory := target.value / "adapter-opt"
  )
