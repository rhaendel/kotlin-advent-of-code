package de.ronny_h.extensions

class PrefixTree {

    fun insert(word: String, tokens: List<String>): Boolean {

        lateinit var insertRec: (String) -> Boolean

        insertRec = { word: String ->
            if (word.isEmpty()) {
                true
            } else {
                var success = false
                for (token in tokens) {
                    if (!word.startsWith(token)) {
                        continue
                    }
                    if (insertRec(word.substring(token.length))) {
                        success = true
                        break
                    }
                }
                success
            }
        }.memoize()

        return insertRec(word)
    }
}
