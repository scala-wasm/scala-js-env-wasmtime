package testproject

import componentmodel.wasi.cli.stdout
import scala.scalajs.wit.unsigned.UByte

object Console {
  def println(s: String): Unit = {
    val out = stdout.getStdout()
    try {
      out.blockingWriteAndFlush((s + "\n").getBytes().asInstanceOf[Array[UByte]])
    } finally {
      out.close()
    }
  }
}
