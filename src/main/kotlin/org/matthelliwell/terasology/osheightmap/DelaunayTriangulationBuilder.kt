package org.matthelliwell.terasology.osheightmap

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Envelope
import com.vividsolutions.jts.triangulate.IncrementalDelaunayTriangulator
import com.vividsolutions.jts.triangulate.quadedge.QuadEdgeSubdivision
import com.vividsolutions.jts.triangulate.quadedge.Vertex


/**
 * This is just a cut down copy of com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder.
 */
class DelaunayTriangulationBuilder {

    private val tolerance = 0.0

    private val siteCoords: MutableList<Coordinate> = mutableListOf()

    val subdivision: QuadEdgeSubdivision by lazy {
        val subdivision = QuadEdgeSubdivision(envelope, tolerance)
        val triangulator = IncrementalDelaunayTriangulator(subdivision)
        triangulator.insertSites(siteCoords.distinct().map { c -> Vertex(c) })
        subdivision
    }

    val envelope: Envelope by lazy {
        val envelope = Envelope()
        siteCoords.forEach { coord -> envelope.expandToInclude(coord.x, coord.y) }
        envelope
    }

    /**
     * Sets the sites (vertices) which will be triangulated
     * from a collection of [Coordinate]s.
     *
     * @param coords a collection of Coordinates.
     */
    fun add(coords: Array<Coordinate>) {
        siteCoords.addAll(coords)
    }
}
