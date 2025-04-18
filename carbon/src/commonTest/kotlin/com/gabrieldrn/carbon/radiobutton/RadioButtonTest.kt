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

package com.gabrieldrn.carbon.radiobutton

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.common.selectable.SelectableInteractiveState
import com.gabrieldrn.carbon.test.semantics.assertIsReadOnly
import kotlin.test.Test
import kotlin.test.assertTrue

class RadioButtonTest {

    private var selected by mutableStateOf(false)
    private var interactiveState by mutableStateOf<SelectableInteractiveState>(
        SelectableInteractiveState.Default
    )

    private val interactiveStates = listOf(
        SelectableInteractiveState.Default,
        SelectableInteractiveState.Disabled,
        SelectableInteractiveState.ReadOnly,
        SelectableInteractiveState.Error("Error message"),
        SelectableInteractiveState.Warning("Warning message")
    )

    fun ComposeUiTest.setup() {
        setContent {
            RadioButton(
                selected = selected,
                label = "Radio button",
                onClick = { selected = !selected },
                interactiveState = interactiveState,
                modifier = Modifier.testTag("root")
            )
        }
    }

    private fun ComposeUiTest.assertWarningContentIsDisplayed(displayed: Boolean) =
        onNodeWithTag(RadioButtonTestTags.WARNING_CONTENT, useUnmergedTree = true)
            .run {
                if (displayed) assertIsDisplayed() else assertDoesNotExist()
            }

    private fun ComposeUiTest.assertErrorContentIsDisplayed(displayed: Boolean) =
        onNodeWithTag(RadioButtonTestTags.ERROR_CONTENT, useUnmergedTree = true)
            .run {
                if (displayed) assertIsDisplayed() else assertDoesNotExist()
            }

    @Test
    fun radioButton_validateLayout() = runComposeUiTest {
        setup()
        interactiveStates.forEach { state ->
            interactiveState = state

            onNodeWithTag(RadioButtonTestTags.BUTTON, useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag(RadioButtonTestTags.LABEL, useUnmergedTree = true)
                .assertIsDisplayed()

            when (state) {
                is SelectableInteractiveState.Default -> {
                    onNodeWithTag("root").assertHasClickAction()
                    assertErrorContentIsDisplayed(false)
                    assertWarningContentIsDisplayed(false)
                }

                is SelectableInteractiveState.Disabled -> {
                    onNodeWithTag("root").assertIsNotEnabled()
                    assertErrorContentIsDisplayed(false)
                    assertWarningContentIsDisplayed(false)
                }

                is SelectableInteractiveState.ReadOnly -> {
                    onNodeWithTag("root").assertIsReadOnly()
                    assertErrorContentIsDisplayed(false)
                    assertWarningContentIsDisplayed(false)
                }

                is SelectableInteractiveState.Error -> {
                    onNodeWithTag("root").assertHasClickAction()
                    assertWarningContentIsDisplayed(false)
                    assertErrorContentIsDisplayed(true)
                }

                is SelectableInteractiveState.Warning -> {
                    onNodeWithTag("root").assertHasClickAction()
                    assertErrorContentIsDisplayed(false)
                    assertWarningContentIsDisplayed(true)
                }
            }
        }
    }

    @Test
    fun radioButton_onClick_stateGetsUpdated() = runComposeUiTest {
        setup()

        selected = false
        onNodeWithTag("root").performClick()
        assertTrue(selected)
    }
}
