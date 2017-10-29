package org.matthelliwell.terasology.osheightmap

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Array2DTest {
    @Test
    fun shouldInitialiseValues() {
        // given
        val initialValue = 2

        // when
        val testSubject = Array2D(5, 3, 0, 0, initialValue)

        // then
        testSubject.forEach{ assertThat(it).isEqualTo(initialValue) }
    }

    @Test
    fun shouldCreateArrayWithCorrectSize() {
        // when
        val testSubject = Array2D(5, 3, -5, 10, 0)

        // then
        assertThat(testSubject.array.size).isEqualTo(5)
        assertThat(testSubject.array[0].size).isEqualTo(3)
    }

    @Test
    fun shouldGetAndSetWithMinValues() {
        // given
        val testSubject = Array2D(5, 3, -3, 10, 0)

        // when
        testSubject[-3, 10] = 7

        // then
        assertThat(testSubject[-3, 10]).isEqualTo(7)
    }

    @Test
    fun shouldIterateWithCorrectIndices() {
        // given
        val testSubject = Array2D(5, 3, 3, -1, 4)

        // when
        val result: MutableList<String> = mutableListOf()
        testSubject.forEachIndexed({x, y, v -> result.add("x=$x, y=$y, value=$v")})

        // then
        assertThat(result[0]).isEqualTo("x=3, y=-1, value=4")
        assertThat(result[1]).isEqualTo("x=3, y=0, value=4")
        assertThat(result[2]).isEqualTo("x=3, y=1, value=4")
        assertThat(result[3]).isEqualTo("x=4, y=-1, value=4")
        assertThat(result[4]).isEqualTo("x=4, y=0, value=4")
        assertThat(result[5]).isEqualTo("x=4, y=1, value=4")
        assertThat(result[6]).isEqualTo("x=5, y=-1, value=4")
        assertThat(result[7]).isEqualTo("x=5, y=0, value=4")
        assertThat(result[8]).isEqualTo("x=5, y=1, value=4")
        assertThat(result[9]).isEqualTo("x=6, y=-1, value=4")
        assertThat(result[10]).isEqualTo("x=6, y=0, value=4")
        assertThat(result[11]).isEqualTo("x=6, y=1, value=4")
        assertThat(result[12]).isEqualTo("x=7, y=-1, value=4")
        assertThat(result[13]).isEqualTo("x=7, y=0, value=4")
        assertThat(result[14]).isEqualTo("x=7, y=1, value=4")
    }
}