package de.ronny_h.aoc.extensions.grids


open class SimpleCharGrid(input: List<String>, nullElement: Char = '#') : Grid<Char>(input, nullElement) {
    override fun Char.toElementType() = this
}
