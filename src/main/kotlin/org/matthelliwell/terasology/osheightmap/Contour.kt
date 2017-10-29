package org.matthelliwell.terasology.osheightmap

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.MultiLineString
import org.geotools.feature.type.BasicFeatureTypes
import org.geotools.geometry.jts.JTS
import org.opengis.feature.simple.SimpleFeature

/**
 * This represents a contour read from a shape file. It contains the height and list of contour points read from the
 * shape file and then provides additional methods for interpolating on the coutour
 */
class Contour(feature: SimpleFeature) {

    private val multiLineString: MultiLineString = feature.getAttribute(BasicFeatureTypes.GEOMETRY_ATTRIBUTE_NAME) as MultiLineString
    private val height: Double = feature.getAttribute("PROP_VALUE") as Double

    /**
     * Returns a set of coordinates after smoothing between the contour points. Note that the Z coord represent height
     * and not depth
     */
    fun getSmoothedContour(): Array<Coordinate> {
        val coordinates = JTS.smooth(multiLineString, 0.0).coordinates
        coordinates.forEachIndexed {i, _ -> coordinates[i].z = height}

        return coordinates
    }
}