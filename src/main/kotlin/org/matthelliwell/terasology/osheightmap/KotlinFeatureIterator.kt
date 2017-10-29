package org.matthelliwell.terasology.osheightmap

import org.geotools.data.DataStore
import org.geotools.data.FileDataStoreFinder
import org.geotools.data.simple.SimpleFeatureIterator
import org.opengis.feature.simple.SimpleFeature
import java.io.Closeable
import java.io.File

/**
 * Reads features from an ERSI shape file and returns an iterator on the features. Make sure you close the iterator
 * once finished with it otherwise the shape files will stay open.
 */
fun iterator(file: File): KotlinFeatureIterator {
    println("Reading file ${file.name}")
    val dataStore = FileDataStoreFinder.getDataStore(file)
    val featureSource = dataStore.featureSource

    return KotlinFeatureIterator(dataStore, featureSource.features.features())
}

/**
 * The feature iterator in geotools isn't a Java/Kotlin iterator so we need to convert it to a normal iterator so that
 * we can create a stream from it. However we also need to be able to close it so that the underlying resources are
 * closed so we implement closable as well.
 *
 * We need to store a reference to the datastore otherwise the finaliser on ShpFile starts clearing stuff down even though
 * we've still go a reference to the iterator.
 */
class KotlinFeatureIterator(private val dataStore: DataStore, private val geotoolsFeatureIterator: SimpleFeatureIterator) : Iterator<SimpleFeature>, Closeable {
    override fun close() {
        geotoolsFeatureIterator.close()
    }

    override fun hasNext(): Boolean {
        return geotoolsFeatureIterator.hasNext()
    }

    override fun next(): SimpleFeature {
        return geotoolsFeatureIterator.next()
    }
}
