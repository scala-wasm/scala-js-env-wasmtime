package scala.scalajs.wasi.sockets

package object network {

  // Type definitions
  /** Error codes.
    *
    * In theory, every API can return any error code. In practice, API's typically only return the
    * errors documented per API combined with a couple of errors that are always possible:
    *   - `unknown`
    *   - `access-denied`
    *   - `not-supported`
    *   - `out-of-memory`
    *   - `concurrency-conflict`
    *
    * See each individual API for what the POSIX equivalents are. They sometimes differ per API.
    */
  @scala.scalajs.wit.annotation.WitEnum(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "network", "0.2.12"),
    "error-code"
  )
  sealed trait ErrorCode
  object ErrorCode {
    @scala.scalajs.wit.annotation.WitName("unknown")
    object Unknown extends ErrorCode {
      override def toString(): String = "Unknown"
    }
    @scala.scalajs.wit.annotation.WitName("access-denied")
    object AccessDenied extends ErrorCode {
      override def toString(): String = "AccessDenied"
    }
    @scala.scalajs.wit.annotation.WitName("not-supported")
    object NotSupported extends ErrorCode {
      override def toString(): String = "NotSupported"
    }
    @scala.scalajs.wit.annotation.WitName("invalid-argument")
    object InvalidArgument extends ErrorCode {
      override def toString(): String = "InvalidArgument"
    }
    @scala.scalajs.wit.annotation.WitName("out-of-memory")
    object OutOfMemory extends ErrorCode {
      override def toString(): String = "OutOfMemory"
    }
    @scala.scalajs.wit.annotation.WitName("timeout")
    object Timeout extends ErrorCode {
      override def toString(): String = "Timeout"
    }
    @scala.scalajs.wit.annotation.WitName("concurrency-conflict")
    object ConcurrencyConflict extends ErrorCode {
      override def toString(): String = "ConcurrencyConflict"
    }
    @scala.scalajs.wit.annotation.WitName("not-in-progress")
    object NotInProgress extends ErrorCode {
      override def toString(): String = "NotInProgress"
    }
    @scala.scalajs.wit.annotation.WitName("would-block")
    object WouldBlock extends ErrorCode {
      override def toString(): String = "WouldBlock"
    }
    @scala.scalajs.wit.annotation.WitName("invalid-state")
    object InvalidState extends ErrorCode {
      override def toString(): String = "InvalidState"
    }
    @scala.scalajs.wit.annotation.WitName("new-socket-limit")
    object NewSocketLimit extends ErrorCode {
      override def toString(): String = "NewSocketLimit"
    }
    @scala.scalajs.wit.annotation.WitName("address-not-bindable")
    object AddressNotBindable extends ErrorCode {
      override def toString(): String = "AddressNotBindable"
    }
    @scala.scalajs.wit.annotation.WitName("address-in-use")
    object AddressInUse extends ErrorCode {
      override def toString(): String = "AddressInUse"
    }
    @scala.scalajs.wit.annotation.WitName("remote-unreachable")
    object RemoteUnreachable extends ErrorCode {
      override def toString(): String = "RemoteUnreachable"
    }
    @scala.scalajs.wit.annotation.WitName("connection-refused")
    object ConnectionRefused extends ErrorCode {
      override def toString(): String = "ConnectionRefused"
    }
    @scala.scalajs.wit.annotation.WitName("connection-reset")
    object ConnectionReset extends ErrorCode {
      override def toString(): String = "ConnectionReset"
    }
    @scala.scalajs.wit.annotation.WitName("connection-aborted")
    object ConnectionAborted extends ErrorCode {
      override def toString(): String = "ConnectionAborted"
    }
    @scala.scalajs.wit.annotation.WitName("datagram-too-large")
    object DatagramTooLarge extends ErrorCode {
      override def toString(): String = "DatagramTooLarge"
    }
    @scala.scalajs.wit.annotation.WitName("name-unresolvable")
    object NameUnresolvable extends ErrorCode {
      override def toString(): String = "NameUnresolvable"
    }
    @scala.scalajs.wit.annotation.WitName("temporary-resolver-failure")
    object TemporaryResolverFailure extends ErrorCode {
      override def toString(): String = "TemporaryResolverFailure"
    }
    @scala.scalajs.wit.annotation.WitName("permanent-resolver-failure")
    object PermanentResolverFailure extends ErrorCode {
      override def toString(): String = "PermanentResolverFailure"
    }
  }

  @scala.scalajs.wit.annotation.WitEnum(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "network", "0.2.12"),
    "ip-address-family"
  )
  sealed trait IpAddressFamily
  object IpAddressFamily {
    @scala.scalajs.wit.annotation.WitName("ipv4")
    object Ipv4 extends IpAddressFamily {
      override def toString(): String = "Ipv4"
    }
    @scala.scalajs.wit.annotation.WitName("ipv6")
    object Ipv6 extends IpAddressFamily {
      override def toString(): String = "Ipv6"
    }
  }

  @scala.scalajs.wit.annotation.WitAlias(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "network", "0.2.12"),
    "ipv4-address"
  )
  type Ipv4Address = scala.scalajs.wit.Tuple4[
    scala.scalajs.wit.unsigned.UByte,
    scala.scalajs.wit.unsigned.UByte,
    scala.scalajs.wit.unsigned.UByte,
    scala.scalajs.wit.unsigned.UByte
  ]

  @scala.scalajs.wit.annotation.WitAlias(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "network", "0.2.12"),
    "ipv6-address"
  )
  type Ipv6Address = scala.scalajs.wit.Tuple8[
    scala.scalajs.wit.unsigned.UShort,
    scala.scalajs.wit.unsigned.UShort,
    scala.scalajs.wit.unsigned.UShort,
    scala.scalajs.wit.unsigned.UShort,
    scala.scalajs.wit.unsigned.UShort,
    scala.scalajs.wit.unsigned.UShort,
    scala.scalajs.wit.unsigned.UShort,
    scala.scalajs.wit.unsigned.UShort
  ]

  @scala.scalajs.wit.annotation.WitVariant(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "network", "0.2.12"),
    "ip-address"
  )
  sealed trait IpAddress
  object IpAddress {
    @scala.scalajs.wit.annotation.WitName("ipv4")
    final class Ipv4(
        @scala.scalajs.wit.annotation.WitName("value") val value: scala.scalajs.wit.Tuple4[
          scala.scalajs.wit.unsigned.UByte,
          scala.scalajs.wit.unsigned.UByte,
          scala.scalajs.wit.unsigned.UByte,
          scala.scalajs.wit.unsigned.UByte
        ]
    ) extends IpAddress {
      override def equals(other: Any): Boolean = other match {
        case that: Ipv4 => this.value == that.value
        case _          => false
      }
      override def hashCode(): Int = {
        value.hashCode()
      }
      override def toString(): String = "Ipv4(" + value + ")"
    }
    object Ipv4 {
      def apply(
          value: scala.scalajs.wit.Tuple4[
            scala.scalajs.wit.unsigned.UByte,
            scala.scalajs.wit.unsigned.UByte,
            scala.scalajs.wit.unsigned.UByte,
            scala.scalajs.wit.unsigned.UByte
          ]
      ): Ipv4 = new Ipv4(value)
      def unapply(arg: Ipv4): Some[scala.scalajs.wit.Tuple4[
        scala.scalajs.wit.unsigned.UByte,
        scala.scalajs.wit.unsigned.UByte,
        scala.scalajs.wit.unsigned.UByte,
        scala.scalajs.wit.unsigned.UByte
      ]] = Some(arg.value)
    }
    @scala.scalajs.wit.annotation.WitName("ipv6")
    final class Ipv6(
        @scala.scalajs.wit.annotation.WitName("value") val value: scala.scalajs.wit.Tuple8[
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort
        ]
    ) extends IpAddress {
      override def equals(other: Any): Boolean = other match {
        case that: Ipv6 => this.value == that.value
        case _          => false
      }
      override def hashCode(): Int = {
        value.hashCode()
      }
      override def toString(): String = "Ipv6(" + value + ")"
    }
    object Ipv6 {
      def apply(
          value: scala.scalajs.wit.Tuple8[
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort
          ]
      ): Ipv6 = new Ipv6(value)
      def unapply(arg: Ipv6): Some[scala.scalajs.wit.Tuple8[
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort
      ]] = Some(arg.value)
    }
  }

  @scala.scalajs.wit.annotation.WitRecord(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "network", "0.2.12"),
    "ipv4-socket-address"
  )
  final class Ipv4SocketAddress(
      @scala.scalajs.wit.annotation.WitName("port") val port: scala.scalajs.wit.unsigned.UShort,
      @scala.scalajs.wit.annotation.WitName("address") val address: scala.scalajs.wit.Tuple4[
        scala.scalajs.wit.unsigned.UByte,
        scala.scalajs.wit.unsigned.UByte,
        scala.scalajs.wit.unsigned.UByte,
        scala.scalajs.wit.unsigned.UByte
      ]
  ) {
    override def equals(other: Any): Boolean = other match {
      case that: Ipv4SocketAddress => this.port == that.port && this.address == that.address
      case _                       => false
    }
    override def hashCode(): Int = {
      var result = 1
      result = 31 * result + port.hashCode()
      result = 31 * result + address.hashCode()
      result
    }
    override def toString(): String = "Ipv4SocketAddress(" + port + ", " + address + ")"
  }
  object Ipv4SocketAddress {
    def apply(
        port: scala.scalajs.wit.unsigned.UShort,
        address: scala.scalajs.wit.Tuple4[
          scala.scalajs.wit.unsigned.UByte,
          scala.scalajs.wit.unsigned.UByte,
          scala.scalajs.wit.unsigned.UByte,
          scala.scalajs.wit.unsigned.UByte
        ]
    ): Ipv4SocketAddress = new Ipv4SocketAddress(port, address)
    def unapply(arg: Ipv4SocketAddress): Some[
      (
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.Tuple4[
            scala.scalajs.wit.unsigned.UByte,
            scala.scalajs.wit.unsigned.UByte,
            scala.scalajs.wit.unsigned.UByte,
            scala.scalajs.wit.unsigned.UByte
          ]
      )
    ] = Some((arg.port, arg.address))
  }

  @scala.scalajs.wit.annotation.WitRecord(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "network", "0.2.12"),
    "ipv6-socket-address"
  )
  final class Ipv6SocketAddress(
      @scala.scalajs.wit.annotation.WitName("port") val port: scala.scalajs.wit.unsigned.UShort,
      @scala.scalajs.wit.annotation.WitName(
        "flow-info"
      ) val flowInfo: scala.scalajs.wit.unsigned.UInt,
      @scala.scalajs.wit.annotation.WitName("address") val address: scala.scalajs.wit.Tuple8[
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort,
        scala.scalajs.wit.unsigned.UShort
      ],
      @scala.scalajs.wit.annotation.WitName("scope-id") val scopeId: scala.scalajs.wit.unsigned.UInt
  ) {
    override def equals(other: Any): Boolean = other match {
      case that: Ipv6SocketAddress =>
        this.port == that.port && this.flowInfo == that.flowInfo && this.address == that.address && this.scopeId == that.scopeId
      case _ => false
    }
    override def hashCode(): Int = {
      var result = 1
      result = 31 * result + port.hashCode()
      result = 31 * result + flowInfo.hashCode()
      result = 31 * result + address.hashCode()
      result = 31 * result + scopeId.hashCode()
      result
    }
    override def toString(): String =
      "Ipv6SocketAddress(" + port + ", " + flowInfo + ", " + address + ", " + scopeId + ")"
  }
  object Ipv6SocketAddress {
    def apply(
        port: scala.scalajs.wit.unsigned.UShort,
        flowInfo: scala.scalajs.wit.unsigned.UInt,
        address: scala.scalajs.wit.Tuple8[
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UShort
        ],
        scopeId: scala.scalajs.wit.unsigned.UInt
    ): Ipv6SocketAddress = new Ipv6SocketAddress(port, flowInfo, address, scopeId)
    def unapply(arg: Ipv6SocketAddress): Some[
      (
          scala.scalajs.wit.unsigned.UShort,
          scala.scalajs.wit.unsigned.UInt,
          scala.scalajs.wit.Tuple8[
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort,
            scala.scalajs.wit.unsigned.UShort
          ],
          scala.scalajs.wit.unsigned.UInt
      )
    ] = Some((arg.port, arg.flowInfo, arg.address, arg.scopeId))
  }

  @scala.scalajs.wit.annotation.WitVariant(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "network", "0.2.12"),
    "ip-socket-address"
  )
  sealed trait IpSocketAddress
  object IpSocketAddress {
    @scala.scalajs.wit.annotation.WitName("ipv4")
    final class Ipv4(@scala.scalajs.wit.annotation.WitName("value") val value: Ipv4SocketAddress)
        extends IpSocketAddress {
      override def equals(other: Any): Boolean = other match {
        case that: Ipv4 => this.value == that.value
        case _          => false
      }
      override def hashCode(): Int = {
        value.hashCode()
      }
      override def toString(): String = "Ipv4(" + value + ")"
    }
    object Ipv4 {
      def apply(value: Ipv4SocketAddress): Ipv4 = new Ipv4(value)
      def unapply(arg: Ipv4): Some[Ipv4SocketAddress] = Some(arg.value)
    }
    @scala.scalajs.wit.annotation.WitName("ipv6")
    final class Ipv6(@scala.scalajs.wit.annotation.WitName("value") val value: Ipv6SocketAddress)
        extends IpSocketAddress {
      override def equals(other: Any): Boolean = other match {
        case that: Ipv6 => this.value == that.value
        case _          => false
      }
      override def hashCode(): Int = {
        value.hashCode()
      }
      override def toString(): String = "Ipv6(" + value + ")"
    }
    object Ipv6 {
      def apply(value: Ipv6SocketAddress): Ipv6 = new Ipv6(value)
      def unapply(arg: Ipv6): Some[Ipv6SocketAddress] = Some(arg.value)
    }
  }

  // Resources
  /** An opaque resource that represents access to (a subset of) the network. This enables
    * context-based security for networking. There is no need for this to map 1:1 to a physical
    * network interface.
    */
  @scala.scalajs.wit.annotation.WitResourceImport(
    scala.scalajs.wit.annotation.WitScope("wasi", "sockets", "network", "0.2.12"),
    "network"
  )
  final class Network private () extends Object {
    @scala.scalajs.wit.annotation.WitResourceDrop
    def close(): Unit = scala.scalajs.wit.native
  }
  object Network {}

}
