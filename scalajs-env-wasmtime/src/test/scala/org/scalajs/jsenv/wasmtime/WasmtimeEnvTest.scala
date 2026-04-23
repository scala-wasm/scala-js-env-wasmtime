package org.scalajs.jsenv.wasmtime

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path}
import java.nio.file.attribute.PosixFilePermissions

import scala.concurrent.Await
import scala.concurrent.duration._

import org.junit.Assert._
import org.junit.Test

import org.scalajs.jsenv.{Input, RunConfig, UnsupportedInputException}

class WasmtimeEnvTest {
  @Test
  def rejectsMixedInputs(): Unit = {
    val component = Files.createTempFile("wasmtime-env-test-", ".wasm")
    val script = Files.createTempFile("wasmtime-env-test-", ".js")

    val t = interceptUnsupportedInput {
      WasmtimeEnv.resolveInput(
        Seq(
          WasmtimeInput.WasmComponent(component),
          Input.Script(script)
        )
      )
    }

    assertTrue(t.getMessage.contains("WasmtimeInput.WasmComponent"))
  }

  @Test
  def rejectsMissingComponentInput(): Unit = {
    val t = interceptUnsupportedInput {
      WasmtimeEnv.resolveInput(Nil)
    }

    assertTrue(t.getMessage.contains("exactly one"))
  }

  @Test
  def rejectsMultipleComponentInputs(): Unit = {
    val first = Files.createTempFile("wasmtime-env-test-", ".wasm")
    val second = Files.createTempFile("wasmtime-env-test-", ".wasm")

    val t = interceptUnsupportedInput {
      WasmtimeEnv.resolveInput(
        Seq(
          WasmtimeInput.WasmComponent(first),
          WasmtimeInput.WasmComponent(second)
        )
      )
    }

    assertTrue(t.getMessage.contains("exactly one"))
  }

  @Test
  def startUsesConfiguredExecutableAndEnv(): Unit = {
    val capture = Files.createTempFile("wasmtime-env-capture-", ".txt")
    val executable = writeCaptureScript(capture)
    val component = Files.createTempFile("wasmtime-env-test-", ".wasm")

    val env = new WasmtimeEnv(
      WasmtimeEnv
        .Config()
        .withExecutable(executable.toAbsolutePath.normalize.toString)
        .withArgs(List("--from-config"))
        .withEnv(
          Map(
            "CONFIG_ENV" -> "config",
            "SHARED_ENV" -> "config"
          )
        )
    )

    val run = env.start(
      Seq(WasmtimeInput.WasmComponent(component)),
      RunConfig().withEnv(
        Map(
          "RUN_CONFIG_ENV" -> "run",
          "SHARED_ENV" -> "run"
        )
      )
    )

    Await.result(run.future, 10.seconds)

    val lines = Files.readAllLines(capture, StandardCharsets.UTF_8)
    assertTrue(lines.contains("arg=--from-config"))
    assertTrue(lines.contains(s"arg=${component.toAbsolutePath.normalize}"))
    assertTrue(lines.contains("env:CONFIG_ENV=config"))
    assertTrue(lines.contains("env:RUN_CONFIG_ENV=run"))
    assertTrue(lines.contains("env:SHARED_ENV=run"))
  }

  @Test
  def packagesAdapterResource(): Unit = {
    val stream =
      getClass.getResourceAsStream("/org/scalajs/jsenv/wasmtime/test-rpc/adapter.wasm")
    assertNotNull("adapter resource should be packaged", stream)
    try {
      assertTrue(stream.read() >= 0)
    } finally {
      stream.close()
    }
  }

  private def interceptUnsupportedInput(body: => Any): UnsupportedInputException = {
    try {
      body
      fail("expected UnsupportedInputException")
      throw new AssertionError("unreachable")
    } catch {
      case t: UnsupportedInputException => t
    }
  }

  private def writeCaptureScript(capture: Path): Path = {
    val script = Files.createTempFile("wasmtime-env-capture-", ".sh")
    val content = {
      s"""#!/bin/sh
         |{
         |  for arg in "$$@"; do
         |    printf 'arg=%s\n' "$$arg"
         |  done
         |  printf 'env:CONFIG_ENV=%s\n' "$$CONFIG_ENV"
         |  printf 'env:RUN_CONFIG_ENV=%s\n' "$$RUN_CONFIG_ENV"
         |  printf 'env:SHARED_ENV=%s\n' "$$SHARED_ENV"
         |} > '${capture.toAbsolutePath.normalize}'
         |""".stripMargin
    }

    Files.write(script, content.getBytes(StandardCharsets.UTF_8))
    Files.setPosixFilePermissions(script, PosixFilePermissions.fromString("rwxr-xr-x"))
    script
  }
}
