/**
 * This file is part of Guthix Jagex-Store-5.
 *
 * Guthix Jagex-Store-5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Guthix Jagex-Store-5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */
package io.guthix.cache.js5.container.disk

import io.guthix.cache.js5.iterationFill
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.netty.buffer.Unpooled
import java.nio.file.Files

class Js5DiskStoreTest : StringSpec() {
    init {
        val fsFolder = Files.createTempDirectory("js5")
        val diskStore = autoClose(Js5DiskStore.open(fsFolder))
        val index0 = diskStore.createArchiveIdxFile()
        val containerId1 = 0
        val data1 = Unpooled.buffer(34720).iterationFill()
        "After writing and reading the data should be the same as the original" {
            diskStore.write(index0, containerId1, data1.copy())
            diskStore.read(index0, containerId1) shouldBe data1
        }

        val containerId2 = 1
        val data2 = Unpooled.buffer(3865).iterationFill()
        "After writing and reading a second time the data should be the same as the original" {
            diskStore.write(index0, containerId2, data2)
            diskStore.read(index0, containerId2) shouldBe data2
        }

        "After overwriting and reading the data should be the same as the overwritten data" {
            diskStore.write(index0, containerId1, data2)
            diskStore.read(index0, containerId1) shouldBe data2
        }
    }
}