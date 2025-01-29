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

package com.gabrieldrn.carbon.catalog.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.ic_add
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.toggle.Toggle
import org.jetbrains.compose.resources.painterResource

private enum class ButtonVariant { Default, Icon }

private val buttonVariants = ButtonVariant.entries.toDropdownOptions()
private val buttonTypes = ButtonType.entries.toDropdownOptions()
private val buttonSizes = ButtonSize.entries.toDropdownOptions()

@Composable
fun ButtonDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var buttonVariant by rememberSaveable { mutableStateOf(buttonVariants.keys.first()) }
        var buttonType by rememberSaveable { mutableStateOf(ButtonType.Primary) }
        var buttonSize by rememberSaveable { mutableStateOf(ButtonSize.LargeProductive) }
        var isEnabled by rememberSaveable { mutableStateOf(true) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(SpacingScale.spacing05),
            contentAlignment = Alignment.Center
        ) {
            val icon = painterResource(Res.drawable.ic_add)

            when (buttonVariant) {
                ButtonVariant.Default -> Button(
                    label = buttonType.name,
                    onClick = {},
                    modifier = Modifier.width(400.dp),
                    iconPainter = icon,
                    isEnabled = isEnabled,
                    buttonType = buttonType,
                    buttonSize = buttonSize
                )
                ButtonVariant.Icon -> IconButton(
                    onClick = {},
                    iconPainter = icon,
                    isEnabled = isEnabled,
                    buttonType = buttonType,
                )
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing04)
            ) {
                BasicText(
                    text = "Configuration",
                    style = Carbon.typography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                Dropdown(
                    label = "Button variant",
                    placeholder = "Choose option",
                    options = buttonVariants,
                    selectedOption = buttonVariant,
                    onOptionSelected = { buttonVariant = it },
                )

                Dropdown(
                    label = "Button type",
                    placeholder = "Choose option",
                    options = buttonTypes,
                    selectedOption = buttonType,
                    onOptionSelected = { buttonType = it },
                )

                Dropdown(
                    label = "Button size",
                    placeholder = "Choose option",
                    options = buttonSizes,
                    selectedOption = buttonSize,
                    state = when {
                        buttonVariant == ButtonVariant.Icon ->
                            DropdownInteractiveState.Disabled
                        buttonSize == ButtonSize.Small ||
                            buttonSize == ButtonSize.Medium ->
                            DropdownInteractiveState.Warning("Discouraged size usage")
                        else ->
                            DropdownInteractiveState.Enabled
                    },
                    onOptionSelected = { buttonSize = it },
                )

                Toggle(
                    label = "Enable button",
                    isToggled = isEnabled,
                    onToggleChange = { isEnabled = it },
                )
            }
        }
    }
}
