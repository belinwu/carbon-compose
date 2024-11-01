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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.common.selectable.ErrorContent
import com.gabrieldrn.carbon.common.selectable.SelectableInteractiveState
import com.gabrieldrn.carbon.common.selectable.WarningContent
import com.gabrieldrn.carbon.foundation.interaction.ToggleableFocusIndication
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text
import com.gabrieldrn.carbon.semantics.readOnly

private val RadioButtonSize = 20.dp
private val RadioButtonStrokeWidth = 1.dp

/**
 * # Carbon Radio button
 * Radio buttons are used for mutually exclusive choices, not for multiple choices. Only one radio
 * button can be selected at a time. When a user chooses a new item, the previous choice is
 * automatically deselected.
 *
 * ## Anatomy
 * The radio button component is comprised of a set of clickable circles (the inputs) with text
 * labels positioned to the right. If there is a group of radio buttons, a group label can be added.
 *
 * (From [Radio button documentation](https://carbondesignsystem.com/components/radio-button/usage))
 *
 * @param selected Whether the radio button is selected.
 * @param label The text to be displayed next to the radio button.
 * @param onClick Callback invoked when the radio button is clicked.
 * @param modifier The modifier to be applied to the radio button.
 * @param interactiveState The [SelectableInteractiveState] of the radio button.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the radio button
 * state.
 */
@Composable
public fun RadioButton(
    selected: Boolean,
    label: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    interactiveState: SelectableInteractiveState = SelectableInteractiveState.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val colors = RadioButtonColors.colors()
    val labelColor by colors.labelColor(interactiveState = interactiveState)

    val radioButtonModifier = when {
        interactiveState == SelectableInteractiveState.ReadOnly -> Modifier.readOnly(
            role = Role.RadioButton,
            interactionSource = interactionSource,
            selected = selected,
            mergeDescendants = true
        )
        onClick != null -> Modifier.selectable(
            selected = selected,
            interactionSource = interactionSource,
            enabled = interactiveState != SelectableInteractiveState.Disabled,
            onClick = onClick,
            indication = null,
            role = Role.RadioButton
        )
        else -> Modifier
    }

    Column(modifier = modifier.then(radioButtonModifier)) {
        Row {
            RadioButtonComponent(
                colors = colors,
                interactiveState = interactiveState,
                selected = selected,
                modifier = Modifier.indication(
                    interactionSource = interactionSource,
                    indication = ToggleableFocusIndication(
                        theme = Carbon.theme,
                        indicationCornerRadius = RadioButtonSize
                    )
                )
            )
            Text(
                text = label,
                color = labelColor,
                modifier = Modifier
                    .padding(start = SpacingScale.spacing03)
                    .testTag(RadioButtonTestTags.LABEL),
                style = Carbon.typography.bodyCompact01
            )
        }
        if (interactiveState is SelectableInteractiveState.Error) {
            ErrorContent(
                colors = colors,
                errorMessage = interactiveState.errorMessage,
                modifier = Modifier
                    .padding(top = SpacingScale.spacing03)
                    .testTag(RadioButtonTestTags.ERROR_CONTENT)
            )
        }
        if (interactiveState is SelectableInteractiveState.Warning) {
            WarningContent(
                colors = colors,
                warningMessage = interactiveState.warningMessage,
                modifier = Modifier
                    .padding(top = SpacingScale.spacing03)
                    .testTag(RadioButtonTestTags.WARNING_CONTENT)
            )
        }
    }
}

@Composable
private fun RadioButtonComponent(
    colors: RadioButtonColors,
    interactiveState: SelectableInteractiveState,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor by colors.borderColor(interactiveState, selected)
    val dotColor by colors.dotColor(interactiveState, selected)

    Canvas(
        modifier = modifier
            .requiredSize(RadioButtonSize)
            .testTag(RadioButtonTestTags.BUTTON)
    ) {
        drawCircle(
            color = borderColor,
            radius = (size.width - RadioButtonStrokeWidth.toPx()) * .5f,
            style = Stroke(width = RadioButtonStrokeWidth.toPx())
        )
        drawCircle(
            color = dotColor,
            radius = size.width * .25f,
        )
    }
}
