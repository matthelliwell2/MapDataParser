package org.matthelliwell.terasology.osheightmap

import com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder

/**
 * This allows the list of points in the build to be added to incrementally so that we don't go around trying
 * to copy large array. It also doesn't check for duplicates, again to reduce memory usage for large data set.
 */
class IncrementalDelaunayTriangulationBuilder : DelaunayTriangulationBuilder() {
}