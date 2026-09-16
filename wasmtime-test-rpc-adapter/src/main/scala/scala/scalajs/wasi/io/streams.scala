package scala.scalajs.wasi.io

package object streams {

  // Type definitions
  type Error = scala.scalajs.wasi.io.error.Error

  type Pollable = scala.scalajs.wasi.io.poll.Pollable

  /** An error for input-stream and output-stream operations.
    */
  @scala.scalajs.wit.annotation.WitVariant(
    scala.scalajs.wit.annotation.WitScope("wasi", "io", "streams", "0.2.12"),
    "stream-error"
  )
  sealed trait StreamError
  object StreamError {
    @scala.scalajs.wit.annotation.WitName("last-operation-failed")
    final class LastOperationFailed(@scala.scalajs.wit.annotation.WitName("value") val value: Error)
        extends StreamError {
      override def equals(other: Any): Boolean = other match {
        case that: LastOperationFailed => this.value == that.value
        case _                         => false
      }
      override def hashCode(): Int = {
        value.hashCode()
      }
      override def toString(): String = "LastOperationFailed(" + value + ")"
    }
    object LastOperationFailed {
      def apply(value: Error): LastOperationFailed = new LastOperationFailed(value)
      def unapply(arg: LastOperationFailed): Some[Error] = Some(arg.value)
    }
    @scala.scalajs.wit.annotation.WitName("closed")
    object Closed extends StreamError {
      override def toString(): String = "Closed"
    }
  }

  // Resources
  /** An input bytestream.
    *
    * `input-stream`s are *non-blocking* to the extent practical on underlying platforms. I/O
    * operations always return promptly; if fewer bytes are promptly available than requested, they
    * return the number of bytes promptly available, which could even be zero. To wait for data to
    * be available, use the `subscribe` function to obtain a `pollable` which can be polled for
    * using `wasi:io/poll`.
    */
  @scala.scalajs.wit.annotation.WitResourceImport(
    scala.scalajs.wit.annotation.WitScope("wasi", "io", "streams", "0.2.12"),
    "input-stream"
  )
  final class InputStream private () extends Object {

    /** Perform a non-blocking read from the stream.
      *
      * When the source of a `read` is binary data, the bytes from the source are returned verbatim.
      * When the source of a `read` is known to the implementation to be text, bytes containing the
      * UTF-8 encoding of the text are returned.
      *
      * This function returns a list of bytes containing the read data, when successful. The
      * returned list will contain up to `len` bytes; it may return fewer than requested, but not
      * more. The list is empty when no bytes are available for reading at this time. The pollable
      * given by `subscribe` will be ready when more bytes are available.
      *
      * This function fails with a `stream-error` when the operation encounters an error, giving
      * `last-operation-failed`, or when the stream is closed, giving `closed`.
      *
      * When the caller gives a `len` of 0, it represents a request to read 0 bytes. If the stream
      * is still open, this call should succeed and return an empty list, or otherwise fail with
      * `closed`.
      *
      * The `len` parameter is a `u64`, which could represent a list of u8 which is not possible to
      * allocate in wasm32, or not desirable to allocate as as a return value by the callee. The
      * callee may return a list of bytes less than `len` in size while more bytes are available for
      * reading.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("read")
    def read(
        @scala.scalajs.wit.annotation.WitName("len") len: scala.scalajs.wit.unsigned.ULong
    ): scala.scalajs.wit.Result[Array[scala.scalajs.wit.unsigned.UByte], StreamError] =
      scala.scalajs.wit.native

    /** Read bytes from a stream, after blocking until at least one byte can be read. Except for
      * blocking, behavior is identical to `read`.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("blocking-read")
    def blockingRead(
        @scala.scalajs.wit.annotation.WitName("len") len: scala.scalajs.wit.unsigned.ULong
    ): scala.scalajs.wit.Result[Array[scala.scalajs.wit.unsigned.UByte], StreamError] =
      scala.scalajs.wit.native

    /** Skip bytes from a stream. Returns number of bytes skipped.
      *
      * Behaves identical to `read`, except instead of returning a list of bytes, returns the number
      * of bytes consumed from the stream.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("skip")
    def skip(
        @scala.scalajs.wit.annotation.WitName("len") len: scala.scalajs.wit.unsigned.ULong
    ): scala.scalajs.wit.Result[scala.scalajs.wit.unsigned.ULong, StreamError] =
      scala.scalajs.wit.native

    /** Skip bytes from a stream, after blocking until at least one byte can be skipped. Except for
      * blocking behavior, identical to `skip`.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("blocking-skip")
    def blockingSkip(
        @scala.scalajs.wit.annotation.WitName("len") len: scala.scalajs.wit.unsigned.ULong
    ): scala.scalajs.wit.Result[scala.scalajs.wit.unsigned.ULong, StreamError] =
      scala.scalajs.wit.native

    /** Create a `pollable` which will resolve once either the specified stream has bytes available
      * to read or the other end of the stream has been closed. The created `pollable` is a child
      * resource of the `input-stream`. Implementations may trap if the `input-stream` is dropped
      * before all derived `pollable`s created with this function are dropped.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("subscribe")
    def subscribe(): Pollable = scala.scalajs.wit.native
    @scala.scalajs.wit.annotation.WitResourceDrop
    def close(): Unit = scala.scalajs.wit.native
  }
  object InputStream {}

  /** An output bytestream.
    *
    * `output-stream`s are *non-blocking* to the extent practical on underlying platforms. Except
    * where specified otherwise, I/O operations also always return promptly, after the number of
    * bytes that can be written promptly, which could even be zero. To wait for the stream to be
    * ready to accept data, the `subscribe` function to obtain a `pollable` which can be polled for
    * using `wasi:io/poll`.
    *
    * Dropping an `output-stream` while there's still an active write in progress may result in the
    * data being lost. Before dropping the stream, be sure to fully flush your writes.
    */
  @scala.scalajs.wit.annotation.WitResourceImport(
    scala.scalajs.wit.annotation.WitScope("wasi", "io", "streams", "0.2.12"),
    "output-stream"
  )
  final class OutputStream private () extends Object {

    /** Check readiness for writing. This function never blocks.
      *
      * Returns the number of bytes permitted for the next call to `write`, or an error. Calling
      * `write` with more bytes than this function has permitted will trap.
      *
      * When this function returns 0 bytes, the `subscribe` pollable will become ready when this
      * function will report at least 1 byte, or an error.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("check-write")
    def checkWrite(): scala.scalajs.wit.Result[scala.scalajs.wit.unsigned.ULong, StreamError] =
      scala.scalajs.wit.native

    /** Perform a write. This function never blocks.
      *
      * When the destination of a `write` is binary data, the bytes from `contents` are written
      * verbatim. When the destination of a `write` is known to the implementation to be text, the
      * bytes of `contents` are transcoded from UTF-8 into the encoding of the destination and then
      * written.
      *
      * Precondition: check-write gave permit of Ok(n) and contents has a length of less than or
      * equal to n. Otherwise, this function will trap.
      *
      * returns Err(closed) without writing if the stream has closed since the last call to
      * check-write provided a permit.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("write")
    def write(
        @scala.scalajs.wit.annotation.WitName("contents") contents: Array[
          scala.scalajs.wit.unsigned.UByte
        ]
    ): scala.scalajs.wit.Result[Unit, StreamError] = scala.scalajs.wit.native

    /** Perform a write of up to 4096 bytes, and then flush the stream. Block until all of these
      * operations are complete, or an error occurs.
      *
      * Returns success when all of the contents written are successfully flushed to output. If an
      * error occurs at any point before all contents are successfully flushed, that error is
      * returned as soon as possible. If writing and flushing the complete contents causes the
      * stream to become closed, this call should return success, and subsequent calls to
      * check-write or other interfaces should return stream-error::closed.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("blocking-write-and-flush")
    def blockingWriteAndFlush(
        @scala.scalajs.wit.annotation.WitName("contents") contents: Array[
          scala.scalajs.wit.unsigned.UByte
        ]
    ): scala.scalajs.wit.Result[Unit, StreamError] = scala.scalajs.wit.native

    /** Request to flush buffered output. This function never blocks.
      *
      * This tells the output-stream that the caller intends any buffered output to be flushed. the
      * output which is expected to be flushed is all that has been passed to `write` prior to this
      * call.
      *
      * Upon calling this function, the `output-stream` will not accept any writes (`check-write`
      * will return `ok(0)`) until the flush has completed. The `subscribe` pollable will become
      * ready when the flush has completed and the stream can accept more writes.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("flush")
    def flush(): scala.scalajs.wit.Result[Unit, StreamError] = scala.scalajs.wit.native

    /** Request to flush buffered output, and block until flush completes and stream is ready for
      * writing again.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("blocking-flush")
    def blockingFlush(): scala.scalajs.wit.Result[Unit, StreamError] = scala.scalajs.wit.native

    /** Create a `pollable` which will resolve once the output-stream is ready for more writing, or
      * an error has occurred. When this pollable is ready, `check-write` will return `ok(n)` with
      * n>0, or an error.
      *
      * If the stream is closed, this pollable is always ready immediately.
      *
      * The created `pollable` is a child resource of the `output-stream`. Implementations may trap
      * if the `output-stream` is dropped before all derived `pollable`s created with this function
      * are dropped.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("subscribe")
    def subscribe(): Pollable = scala.scalajs.wit.native

    /** Write zeroes to a stream.
      *
      * This should be used precisely like `write` with the exact same preconditions (must use
      * check-write first), but instead of passing a list of bytes, you simply pass the number of
      * zero-bytes that should be written.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("write-zeroes")
    def writeZeroes(
        @scala.scalajs.wit.annotation.WitName("len") len: scala.scalajs.wit.unsigned.ULong
    ): scala.scalajs.wit.Result[Unit, StreamError] = scala.scalajs.wit.native

    /** Perform a write of up to 4096 zeroes, and then flush the stream. Block until all of these
      * operations are complete, or an error occurs.
      *
      * Functionality is equivelant to `blocking-write-and-flush` with contents given as a list of
      * len containing only zeroes.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("blocking-write-zeroes-and-flush")
    def blockingWriteZeroesAndFlush(
        @scala.scalajs.wit.annotation.WitName("len") len: scala.scalajs.wit.unsigned.ULong
    ): scala.scalajs.wit.Result[Unit, StreamError] = scala.scalajs.wit.native

    /** Read from one stream and write to another.
      *
      * The behavior of splice is equivalent to:
      *   1. calling `check-write` on the `output-stream`
      *   2. calling `read` on the `input-stream` with the smaller of the `check-write` permitted
      *      length and the `len` provided to `splice`
      *   3. calling `write` on the `output-stream` with that read data.
      *
      * Any error reported by the call to `check-write`, `read`, or `write` ends the splice and
      * reports that error.
      *
      * This function returns the number of bytes transferred; it may be less than `len`.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("splice")
    def splice(
        @scala.scalajs.wit.annotation.WitName("src") src: scala.scalajs.wit.Borrow[InputStream],
        @scala.scalajs.wit.annotation.WitName("len") len: scala.scalajs.wit.unsigned.ULong
    ): scala.scalajs.wit.Result[scala.scalajs.wit.unsigned.ULong, StreamError] =
      scala.scalajs.wit.native

    /** Read from one stream and write to another, with blocking.
      *
      * This is similar to `splice`, except that it blocks until the `output-stream` is ready for
      * writing, and the `input-stream` is ready for reading, before performing the `splice`.
      */
    @scala.scalajs.wit.annotation.WitResourceMethod("blocking-splice")
    def blockingSplice(
        @scala.scalajs.wit.annotation.WitName("src") src: scala.scalajs.wit.Borrow[InputStream],
        @scala.scalajs.wit.annotation.WitName("len") len: scala.scalajs.wit.unsigned.ULong
    ): scala.scalajs.wit.Result[scala.scalajs.wit.unsigned.ULong, StreamError] =
      scala.scalajs.wit.native
    @scala.scalajs.wit.annotation.WitResourceDrop
    def close(): Unit = scala.scalajs.wit.native
  }
  object OutputStream {}

}
