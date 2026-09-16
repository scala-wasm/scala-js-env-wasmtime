package scala.scalajs.wasi.sockets

package object instance_network {

  // Type definitions
  type Network = scala.scalajs.wasi.sockets.network.Network

  // Functions
  /** Get a handle to the default network.
    */
  @scala.scalajs.wit.annotation.WitImport(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "instance-network", "0.2.12"),
    "instance-network"
  )
  def instanceNetwork(): Network = scala.scalajs.wit.native

}
