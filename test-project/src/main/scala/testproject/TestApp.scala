package testproject

import scala.scalajs.wit
import scala.scalajs.wit.annotation.WitImplementation

import componentmodel.exports.wasi.cli.Run

@WitImplementation
object TestApp extends Run {
  override def run(): wit.Result[Unit, Unit] = {
    println("hello from scalajs-env-wasmtime")
    new wit.Ok(())
  }
}

object MainApp {
  def main(args: Array[String]): Unit = ()
}
