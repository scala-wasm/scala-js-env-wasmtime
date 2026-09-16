package org.scalajs.jsenv.wasmtime

import java.nio.file.Path

import org.scalajs.jsenv.Input

object WasmtimeInput {
  final case class WasmComponent(component: Path) extends Input
}
