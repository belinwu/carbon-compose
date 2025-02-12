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

package com.gabrieldrn.carbon.catalog.toggle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
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
import com.gabrieldrn.carbon.catalog.common.LayerSelectionDropdown
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.dropdownOptionsOf
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.toggle.SmallToggle
import com.gabrieldrn.carbon.toggle.Toggle

private const val SmallToggleType = "Small"
private const val DefaultToggleType = "Default"
private val toggleTypesOptions = dropdownOptionsOf(DefaultToggleType, SmallToggleType)

@Composable
fun ToggleDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        var toggleType by rememberSaveable {
            mutableStateOf(DefaultToggleType)
        }
        var isEnabled by rememberSaveable { mutableStateOf(true) }
        var isReadOnly by rememberSaveable { mutableStateOf(false) }
        var isToggled by rememberSaveable { mutableStateOf(false) }
        var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }

        CarbonLayer(layer = layer) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .containerBackground()
                    .padding(SpacingScale.spacing05)
            ) {
                if (toggleType == DefaultToggleType) {
                    Toggle(
                        label = "Toggle",
                        isToggled = isToggled,
                        isEnabled = isEnabled,
                        isReadOnly = isReadOnly,
                        onToggleChange = { isToggled = it },
                        actionText = "Action text",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    SmallToggle(
                        isToggled = isToggled,
                        isEnabled = isEnabled,
                        isReadOnly = isReadOnly,
                        onToggleChange = { isToggled = it },
                        actionText = "Action text",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing03)
            ) {
                BasicText(
                    text = "Configuration",
                    style = Carbon.typography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                Dropdown(
                    label = "Toggle type",
                    placeholder = "Choose option",
                    options = toggleTypesOptions,
                    selectedOption = toggleType,
                    onOptionSelected = { toggleType = it },
                )

                Toggle(
                    label = "Enabled",
                    isToggled = isEnabled,
                    onToggleChange = { isEnabled = it },
                )

                Toggle(
                    label = "Read only",
                    isToggled = isReadOnly,
                    onToggleChange = { isReadOnly = it },
                )

                Spacer(
                    modifier = Modifier
                        .background(Carbon.theme.borderStrong01)
                        .fillMaxWidth()
                        .height(1.dp)
                )

                LayerSelectionDropdown(
                    selectedLayer = layer,
                    onLayerSelected = { layer = it },
                )
            }
        }
    }
}
