package org.matthelliwell.terasology.osheightmap

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

internal class ZipperKtTest {
    @Test
    internal fun shouldUnzipFiles() {
        // given
        val expectedFiles40 = listOf("HP40_line.dbf",
                "HP40_line.prj",
                "HP40_line.shp",
                "HP40_line.shx",
                "HP40_point.dbf",
                "HP40_point.prj",
                "HP40_point.shp",
                "HP40_point.shx",
                "Metadata_HP40.xml")

        val expectedFiles50 = listOf(
                "HP50_line.dbf",
                "HP50_line.prj",
                "HP50_line.shp",
                "HP50_line.shx",
                "HP50_point.dbf",
                "HP50_point.prj",
                "HP50_point.shp",
                "HP50_point.shx",
                "Metadata_HP50.xml")

        // when
        val unzippedDirs = unzipFilesInDir("src/test/resources/ZipFiles")

        // then
        assertThat(unzippedDirs).isNotNull()

        assertThat(unzippedDirs).hasSize(2)

        verifyDirContents(unzippedDirs[0], expectedFiles40)
        verifyDirContents(unzippedDirs[1], expectedFiles50)

        // If the test fails before it gets here you will be left with a temp directory that you will need to remove
        // manually
        unzippedDirs.forEach { dir -> dir.deleteRecursively() }
    }

    private fun verifyDirContents(dir: File, expectedFiles: List<String>) {
        val dirList = dir.list()
        assertThat(dirList).hasSize(expectedFiles.size)
        assertThat(dirList).containsAll(expectedFiles)
    }
}

