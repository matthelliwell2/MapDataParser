package org.matthelliwell.terasology.osheightmap

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.triangulate.quadedge.Vertex
import java.lang.Double.isNaN

/**
 * This uses Delaunay triangulation to calculate a height (Z coordinate) at any given point.
 */
// TODO add spot heights
class HeightGenerator {
    private val builder = DelaunayTriangulationBuilder()

    fun add(contour: Contour) {
        builder.add(contour.getSmoothedContour())
        generate()
    }

    fun generate() {
        val envelope = builder.envelope
        val heightMap = Array2D(envelope.width.toInt() + 1, envelope.height.toInt() + 1, envelope.minX.toInt(), envelope.minY.toInt(), Int.MIN_VALUE)

        heightMap.forEachIndexed({ x, y, _ -> heightMap[x, y] = getHeightFromPoint(x, y) })
    }

    private fun getHeightFromPoint(x: Int, y: Int): Int {
        val point =  Coordinate(x.toDouble(), y.toDouble())
        val edge = builder.subdivision.locate(point)
        val interpHeight = Vertex(point.x, point.y).interpolateZValue(edge.orig(), edge.dest(), edge.oNext().dest())
        return if (isNaN(interpHeight)) {
            Int.MIN_VALUE
        } else {
            interpHeight.toInt()
        }
    }
}