package de.ronny_h.aoc.extensions

class PrefixTree {

    /**
     * Calculates the number of possibilities the [word] can be produced by concatenating the provided [tokens].
     */
    fun insert(word: String, tokens: Set<String>): Long {

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
