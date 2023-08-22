# Mock4k

<a href="https://mvnrepository.com/artifact/dev.forkhandles"><img alt="Download" src="https://img.shields.io/maven-central/v/dev.forkhandles/forkhandles-bom"></a>
[![.github/workflows/build.yaml](https://github.com/fork-handles/forkhandles/actions/workflows/build.yaml/badge.svg)](https://github.com/fork-handles/forkhandles/actions/workflows/build.yaml)
<a href="https://codecov.io/gh/fork-handles/forkhandles"><img src="https://codecov.io/gh/fork-handles/forkhandles/branch/trunk/graph/badge.svg"/></a>
<a href="http//www.apache.org/licenses/LICENSE-2.0"><img alt="GitHub license" src="https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat"></a>
<a href="http://kotlinlang.org"><img alt="kotlin" src="https://img.shields.io/badge/kotlin-1.8-blue.svg"></a>
<a href="https://codebeat.co/projects/github-com-fork-handles-forkhandles-trunk"><img alt="codebeat badge" src="https://codebeat.co/badges/5b369ed4-af27-46f4-ad9c-a307d900617e"></a>

Super-basic, super-simple and super-fast Mocking library. Use when you really don't want to import an entire mocking library or want some simple replacement of an interface.

### Core features:
-  ✅ Simple
-  ✅ Fast
- ❌ Verification
- ❌ Relaxed-mode
- ❌ Argument capture
- ❌ Partials
- ❌ Spies
- ❌ Any evil Powermock nonsense

### Other core features!
- ❌ Wannabe-functional
- ❌ Multiplatform
- ❌ Spring-support
- ❌ Coroutines
- ❌ Annotations

## Installation

In Gradle, install the ForkHandles BOM and then this module in the dependency block:

```kotlin
implementation(platform("dev.forkhandles:forkhandles-bom:X.Y.Z"))
implementation("dev.forkhandles:mock4k")
```

## Usage
Import from `dev.forkhandles.mock4k`:

```kotlin
interface Wallet {
    fun pay(item: String, coins: Int): Int
}

class AppleStore(private val wallet: Wallet) {
    fun buyMacBook() = wallet.pay("MacBook", 9999)
    fun seeGenius() {}
}

class AppleStoreTest {
    @Test
    fun `mock call`() {
        val appleStore = AppleStore(object : Wallet by mock() {
            override fun pay(item: String, coins: Int): Int {
                assertThat(item, equalTo("MacBook"))
                assertThat(coins, equalTo(9999))
                return 3
            }
        })
        assertThat(appleStore.buyMacBook(), equalTo(3))
    }

    @Test
    fun `fails on unexpected call`() {
        try {
            val appleStore = AppleStore(mock())
            appleStore.buyMacBook()
            fail("didn't throw")
        } catch (e: UnstubbedCall) {
            assertThat(e.message, equalTo("Unstubbed call: Wallet.pay(MacBook, 9999)"))
        }
    }
}
```