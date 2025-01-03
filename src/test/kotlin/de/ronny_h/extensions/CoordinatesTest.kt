package de.ronny_h.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoordinatesTest {

    @ParameterizedTest
    @MethodSource("provideCoordinatesForPlus")
    fun `Coordinates are added`(first: Coordinates, second: Coordinates, result: Coordinates) {
        assertThat(first + second).isEqualTo(result)
    }

    private fun provideCoordinatesForPlus(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(Coordinates(1, 1), Coordinates(0, 0), Coordinates(1, 1)),
            Arguments.of(Coordinates(0, 0), Coordinates(1, 1), Coordinates(1, 1)),
            Arguments.of(Coordinates(1, 2), Coordinates(3, 4), Coordinates(4, 6)),
        )
    }

    @ParameterizedTest
    @MethodSource("provideCoordinatesForMinus")
    fun `Coordinates are subtracted`(first: Coordinates, second: Coordinates, result: Coordinates) {
        assertThat(first - second).isEqualTo(result)
    }

    private fun provideCoordinatesForMinus(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(Coordinates(1, 1), Coordinates(0, 0), Coordinates(1, 1)),
            Arguments.of(Coordinates(0, 0), Coordinates(1, 1), Coordinates(-1, -1)),
            Arguments.of(Coordinates(3, 5), Coordinates(2, 1), Coordinates(1, 4)),
        )
    }

    @ParameterizedTest
    @MethodSource("provideCoordinatesForScalarMultiplication")
    fun `Multiplication with a scalar`(scalar: Int, coordinates: Coordinates, result: Coordinates) {
        assertThat(scalar * coordinates).isEqualTo(result)
        assertThat(coordinates * scalar).isEqualTo(result)
    }

    private fun provideCoordinatesForScalarMultiplication(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(0, Coordinates(5, 7), Coordinates(0, 0)),
            Arguments.of(7, Coordinates(0, 0), Coordinates(0, 0)),
            Arguments.of(3, Coordinates(5, 7), Coordinates(15, 21)),
            Arguments.of(-3, Coordinates(5, 7), Coordinates(-15, -21)),
        )
    }
}
