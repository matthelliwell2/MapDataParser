package org.matthelliwell.terasology.osheightmap

import java.io.File

private const val DATA_DIR = "src/main/data/terr50_cesh_gb/data/"

fun main(args: Array<String>) {

    unzipFilesInDir(DATA_DIR + "hp")
            .asSequence()
            .forEach { shapeFileDir ->
                processShapeFiles(shapeFileDir)
            }

    // TODO delete temp dir
}

/**
 * Processes the contour and spot height shape files in a directory. It assumes there is exactly one of each in the
 * directory.
 */
private fun processShapeFiles(shapeFileDir: File) {
    // Each directory covers a 10x10km square so process them independently to avoid using too much memory
    val heightGenerator = HeightGenerator()

    val contourFile = shapeFileDir.listFiles({ file -> file.name.endsWith("_line.shp") })[0]
    val featureIterator = iterator(contourFile)
    processContours(heightGenerator, featureIterator)

    // TODO load spot heights
    val spotHeightFile = shapeFileDir.listFiles({ file -> file.name.endsWith("_point.shp") })[0]
}

/**
 * Iterates through all the feature, generating a contour for each feature and adding it to the height
 * generator.
 */
private fun processContours(heightGenerator: HeightGenerator, featureIterator: KotlinFeatureIterator) {
    println("Processing contour")
    featureIterator.use { iter ->
        iter.asSequence()
                .map { feature -> Contour(feature) }
                .forEach { contour -> heightGenerator.add(contour) }
    }
}
