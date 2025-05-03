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

package com.gabrieldrn.carbon.dropdown.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.interaction.FocusIndication
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text

private val checkmarkSize = 16.dp

/**
 * DropdownPopupContent is the composable used to display the popup content of the dropdown. It
 * displays a list of options that can be selected.
 *
 * @param K The type of option keys.
 * @param options The list of options to display. The keys are the values that will be returned when
 * an option is selected, and the values are the strings that will be displayed in the dropdown.
 * @param selectedOption The currently selected option. If null, no option is selected.
 * @param colors The colors to use for the dropdown.
 * @param componentHeight The height of each option in the dropdown.
 * @param onOptionClicked The callback to call when an option is selected.
 * @param modifier The modifier to apply to the composable.
 */
@Composable
internal fun <K : Any> DropdownPopupContent(
    options: Map<K, DropdownOption>,
    selectedOption: K?,
    colors: DropdownColors,
    componentHeight: Dp,
    onOptionClicked: (K) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    val optionEntries = remember(options) {
        options.entries.toList()
    }

    val selectedOptionIndex = remember(optionEntries, selectedOption) {
        optionEntries.indexOfFirst { it.key == selectedOption }
    }

    // Option to focus on when the composition ends.
    val compositionEndTargetOption = remember(selectedOption, options) {
        if (options.isEmpty()) {
            null
        } else {
            selectedOption ?: options.keys.first()
        }
    }

    val initialFirstVisibleItemIndex = remember(options, selectedOptionIndex) {
        options
            .keys
            .indexOf(compositionEndTargetOption)
            .let {
                if (it < 0) {
                    Logger.w("Selected option not found in options")
                    0
                } else {
                    it
                }
            }
    }

    LazyColumn(
        state = rememberLazyListState(
            initialFirstVisibleItemIndex = initialFirstVisibleItemIndex
        ),
        modifier = modifier
            .background(color = colors.menuOptionBackgroundColor)
            .testTag(DropdownTestTags.POPUP_CONTENT)
    ) {
        itemsIndexed(optionEntries) { index, optionEntry ->
            SideEffect {
                if (optionEntry.key == compositionEndTargetOption) {
                    focusRequester.requestFocus()
                }
            }
            DropdownMenuOption(
                option = optionEntry.value,
                isSelected = index == selectedOptionIndex,
                onOptionClicked = { onOptionClicked(optionEntry.key) },
                // Hide divider: first item + when previous item is selected.
                showDivider = index != 0 && index - 1 != selectedOptionIndex,
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(componentHeight)
                    .then(
                        if (optionEntry.key == compositionEndTargetOption) {
                            Modifier.focusRequester(focusRequester)
                        } else {
                            Modifier
                        }
                    )
            )
        }
    }
}

@Composable
private fun DropdownMenuOption(
    option: DropdownOption,
    isSelected: Boolean,
    colors: DropdownColors,
    showDivider: Boolean,
    onOptionClicked: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val menuOptionBackgroundSelectedColor by colors.menuOptionBackground(
        isSelected = isSelected,
        isHovered = isHovered,
        isActive = isPressed
    )

    val menuOptionTextColor by colors.menuOptionTextColor(
        isEnabled = option.enabled,
        isSelected = isSelected
    )

    Box(
        modifier = modifier
            .selectable(
                selected = isSelected,
                interactionSource = interactionSource,
                indication = FocusIndication(Carbon.theme),
                enabled = option.enabled,
                onClick = onOptionClicked
            )
            .background(color = menuOptionBackgroundSelectedColor)
            .padding(horizontal = SpacingScale.spacing05)
            .testTag(DropdownTestTags.MENU_OPTION)
    ) {
        if (showDivider) {
            DropdownMenuOptionDivider(
                colors.menuOptionBorderColor,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .testTag(DropdownTestTags.MENU_OPTION_DIVIDER)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = option.value,
                style = Carbon.typography.bodyCompact01,
                color = menuOptionTextColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            if (isSelected) {
                Image(
                    imageVector = dropdownCheckmarkIcon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colors.checkmarkIconColor),
                    modifier = Modifier
                        .size(checkmarkSize)
                        .testTag(DropdownTestTags.MENU_OPTION_CHECKMARK)
                )
            } else {
                Spacer(modifier = Modifier.size(checkmarkSize))
            }
        }
    }
}

@Composable
internal fun DropdownMenuOptionDivider(
    color: Color,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .background(color = color)
            .height(1.dp)
            .fillMaxWidth()
    )
}
