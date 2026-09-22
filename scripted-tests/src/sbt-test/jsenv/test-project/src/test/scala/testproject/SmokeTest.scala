package testproject

import org.junit.Assert._
import org.junit.Test

class SmokeTest {
  @Test
  def addition(): Unit =
    assertEquals(42, 40 + 2)

  @Test
  def stringConcat(): Unit =
    assertEquals("hello wasmtime", "hello " + "wasmtime")
}
