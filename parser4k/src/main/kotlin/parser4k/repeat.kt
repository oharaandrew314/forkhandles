package parser4k

fun <T> repeat(parser: Parser<T>, atLeast: Int = 0, atMost: Int = Int.MAX_VALUE) = Parser<List<T>> { input ->
    val payload = ArrayList<T>()
    var nextInput = input
    while (true) {
        val output = parser.parse(nextInput) ?: break
        nextInput = output.nextInput
        payload.add(output.payload)
        if (payload.size == atMost) break
    }
    if (payload.size >= atLeast) Output(payload, nextInput) else null
}

fun <T> Parser<T>.repeat(atLeast: Int = 0, atMost: Int = Int.MAX_VALUE) = repeat(this, atLeast, atMost)

fun <T> zeroOrMore(parser: Parser<T>): Parser<List<T>> = repeat(parser, atLeast = 0)

fun <T> Parser<T>.zeroOrMore() = zeroOrMore(this)

fun <T> oneOrMore(parser: Parser<T>): Parser<List<T>> = repeat(parser, atLeast = 1)

fun <T> Parser<T>.oneOrMore() = oneOrMore(this)

fun <T> optional(parser: Parser<T>): Parser<T?> = repeat(parser, atLeast = 0, atMost = 1).map { it.firstOrNull() }

fun <T> Parser<T>.optional() = optional(this)
