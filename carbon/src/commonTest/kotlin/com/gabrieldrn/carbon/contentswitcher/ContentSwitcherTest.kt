/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gabrieldrn.carbon.contentswitcher

import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelectable
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.toList
import kotlin.test.Test

class ContentSwitcherTest {

    private val stringOptions = listOf("Option 1", "Optiooooon 2", "Option 3")
    private var selectedStringOption = stringOptions.first()

    @Test
    fun contentSwitcher_default_validateLayout() = runComposeUiTest {
        setContent {
            ContentSwitcher(
                options = stringOptions,
                selectedOption = selectedStringOption,
                onOptionSelected = { selectedStringOption = it }
            )
        }

        onNodeWithTag(ContentSwitcherTestTags.ROOT)
            .assertIsDisplayed()

        onAllNodesWithTag(ContentSwitcherTestTags.BUTTON_LAYOUT).run {
            assertCountEquals(stringOptions.size)
            toList().run {
                forEach {
                    it.assertIsDisplayed()
                    it.onChildren().run {
                        assertCountEquals(2)
                        assertAny(hasTestTag(ContentSwitcherTestTags.BUTTON_CONTENT_ROOT))
                        assertAny(hasTestTag(ContentSwitcherTestTags.BUTTON_DIVIDER))
                    }
                }

                // Check that all buttons have the same width
                val expectedWidthForEachButton = with(density) {
                    first().fetchSemanticsNode().size.width.toDp()
                }
                forEach { it.assertWidthIsEqualTo(expectedWidthForEachButton) }
            }
        }

        onAllNodesWithTag(
            ContentSwitcherTestTags.BUTTON_CONTENT_ROOT,
            useUnmergedTree = true
        ).run {
            assertCountEquals(stringOptions.size)
            toList().forEachIndexed { index, node ->
                with(node) {
                    assertIsDisplayed()
                    assertIsSelectable()
                    onChildren().run {
                        assertCountEquals(1)
                        assertAll(
                            hasTestTag(ContentSwitcherTestTags.BUTTON_TEXT) and
                                hasText(stringOptions[index])
                        )
                    }
                }
            }
        }
    }
}
