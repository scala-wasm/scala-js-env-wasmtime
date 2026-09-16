# scalajs-env-wasmtime

`scalajs-env-wasmtime` is a standalone Wasm environment for Scala.js (a `JSEnv`) running [wasmtime](https://wasmtime.dev/).

This repository contains:

- `scalajs-env-wasmtime-input`: the Wasm component `Input` type
- `scalajs-env-wasmtime`: the `WasmtimeEnv` `JSEnv` implementation
- `wasmtime-test-rpc-adapter`: an internal build-only subproject for the test RPC bridge

## Prequirements

`WasmtimeEnv` expects these external tools to be installed:

- `wasmtime` to execute the generated component
- `wac` for `startWithCom`, which composes the adapter component used by the Scala.js test RPC bridge

## Setup

Add the library dependency:

```scala
libraryDependencies +=
  "io.github.scala-wasm" %% "scalajs-env-wasmtime" % "<version>"
```

Then configure your Scala.js project to emit Wasm components.

## Public WIT contract

`WasmtimeEnv.startWithCom` (used by the Scala.js test framework bridge) composes your component with the internal RPC adapter via `wac`. For that composition, your component's world **must import** the `scalajs:test-rpc/rpc` interface:

```wit
package scalajs:test-rpc;

interface rpc {
  init: func();
  send: func(msg: string);
  poll: func() -> option<string>;
}
```

The adapter component shipped in this library's resources exports exactly this interface.
