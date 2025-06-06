package de.ronny_h.aoc.extensions

class PrefixTree {

    fun insert(word: String, tokens: List<String>): Long {

        lateinit var insertRec: (String) -> Long

        insertRec = { word: String ->
            if (word.isEmpty()) {
                1L
            } else {
                var successes = 0L
                for (token in tokens) {
                    if (!word.startsWith(token)) {
                        continue
                    }
                    successes += insertRec(word.substring(token.length))
                }
                successes
            }
        }.memoize()

        return insertRec(word)
    }
}
