package org.matthelliwell.terasology.osheightmap

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

internal class KotlinFeatureIteratorKtTest {
    @Test
    fun shouldProvideFeatures() {
        // given
        val results = ArrayList<String>()

        // when
        iterator(File("src/test/resources/UnzippedFiles/HP40_line.shp")).use { iter -> iter
                    .asSequence()
                    .map { f -> f.id }
                    .toCollection(results)
        }

        // then
        assertThat(results).hasSize(154)
    }
}
