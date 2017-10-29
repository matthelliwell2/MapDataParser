package org.matthelliwell.terasology.osheightmap

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Paths
import java.util.zip.ZipFile


/**
 * Creates a temp directory under the given rootDir and then unzips all the files in the rootDir subdirectories beneath
 * the temp directory. The created directories will have the same prefix as the zip file, eg sy07
 *
 * @param rootDir Directory where all the zip files are located
 *
 * @return The list of directories into which the zip files were unzipped
 */
fun unzipFilesInDir(rootDir: String): Array<out File> {
    val zipDir = File(rootDir)
    val unzippedParentDir = createTempDir("tmp", null, zipDir)
    zipDir.listFiles({ _, name -> name.endsWith(".zip") })
            .map({ zipFileName -> ZipFile(zipFileName, ZipFile.OPEN_READ) })
            .forEach { zipFile ->
                val name = Paths.get(zipFile.name).fileName.toString().substring(0, 4)
                val destinationDir = unzippedParentDir.resolve(name)
                destinationDir.mkdir()
                unzip(zipFile, destinationDir)
            }

    return unzippedParentDir.listFiles()
}

private fun unzip(zipFile: ZipFile, destinationDir: File) {
    for (entry in zipFile.entries()) {
        val destination = destinationDir.resolve(entry.name)
        if (entry.isDirectory) {
            destination.mkdir()
        } else {
            zipFile.getInputStream(entry).use { input ->
                BufferedOutputStream(FileOutputStream(destination)).use { out -> input.copyTo(out) }
            }
        }
    }
}
