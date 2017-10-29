package org.matthelliwell.terasology.osheightmap

/**
 * This represents a 2D array of fixed sized with non-zero based indexes.
 */
class Array2D<T>(val xMin: Int, val yMin: Int, val array: Array<Array<T>>) {

    companion object {
        inline operator fun <reified T> invoke(xWidth: Int, yWidth: Int, xMin: Int, yMin: Int, initialValue: T) =
                Array2D(xMin, yMin, Array(xWidth, { Array(yWidth, { _ -> initialValue }) }))
    }

    operator fun get(x: Int, y: Int): T {
        return array[x - xMin][y - yMin]
    }

    operator fun set(x: Int, y: Int, t: T) {
        array[x - xMin][y - yMin] = t
    }

    inline fun forEach(operation: (T) -> Unit) {
        array.forEach { it.forEach { operation.invoke(it) } }
    }

    inline fun forEachIndexed(operation: (x: Int, y: Int, value: T) -> Unit) {
        array.forEachIndexed { x, p -> p.forEachIndexed { y, value -> operation(x + xMin, y + yMin, value) } }
    }
}