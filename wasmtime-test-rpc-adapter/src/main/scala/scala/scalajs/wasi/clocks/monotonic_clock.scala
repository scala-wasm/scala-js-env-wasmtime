package scala.scalajs.wasi.clocks

package object monotonic_clock {

  // Type definitions
  type Pollable = scala.scalajs.wasi.io.poll.Pollable

  @scala.scalajs.wit.annotation.WitAlias(
    scala.scalajs.wit.annotation.WitScope("wasi", "clocks", "monotonic-clock", "0.2.12"),
    "instant"
  )
  type Instant = scala.scalajs.wit.unsigned.ULong

  @scala.scalajs.wit.annotation.WitAlias(
    scala.scalajs.wit.annotation.WitScope("wasi", "clocks", "monotonic-clock", "0.2.12"),
    "duration"
  )
  type Duration = scala.scalajs.wit.unsigned.ULong

  // Functions
  /** Read the current value of the clock.
    *
    * The clock is monotonic, therefore calling this function repeatedly will produce a sequence of
    * non-decreasing values.
    *
    * For completeness, this function traps if it's not possible to represent the value of the clock
    * in an `instant`. Consequently, implementations should ensure that the starting time is low
    * enough to avoid the possibility of overflow in practice.
    */
  @scala.scalajs.wit.annotation.WitImport(
    scala.scalajs.wit.annotation.WitScope("wasi", "clocks", "monotonic-clock", "0.2.12"),
    "now"
  )
  def now(): Instant = scala.scalajs.wit.native

  /** Query the resolution of the clock. Returns the duration of time corresponding to a clock tick.
    */
  @scala.scalajs.wit.annotation.WitImport(
    scala.scalajs.wit.annotation.WitScope("wasi", "clocks", "monotonic-clock", "0.2.12"),
    "resolution"
  )
  def resolution(): Duration = scala.scalajs.wit.native

  /** Create a `pollable` which will resolve once the specified instant has occurred.
    */
  @scala.scalajs.wit.annotation.WitImport(
    scala.scalajs.wit.annotation.WitScope("wasi", "clocks", "monotonic-clock", "0.2.12"),
    "subscribe-instant"
  )
  def subscribeInstant(@scala.scalajs.wit.annotation.WitName("when") when: Instant): Pollable =
    scala.scalajs.wit.native

  /** Create a `pollable` that will resolve after the specified duration has elapsed from the time
    * this function is invoked.
    */
  @scala.scalajs.wit.annotation.WitImport(
    scala.scalajs.wit.annotation.WitScope("wasi", "clocks", "monotonic-clock", "0.2.12"),
    "subscribe-duration"
  )
  def subscribeDuration(@scala.scalajs.wit.annotation.WitName("when") when: Duration): Pollable =
    scala.scalajs.wit.native

}
