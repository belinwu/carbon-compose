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

package com.gabrieldrn.carbon.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.gabrieldrn.carbon.catalog.buttons.ButtonDemoScreen
import com.gabrieldrn.carbon.catalog.checkbox.CheckboxDemoScreen
import com.gabrieldrn.carbon.catalog.dropdown.DropdownDemoScreen
import com.gabrieldrn.carbon.catalog.dropdown.DropdownVariant
import com.gabrieldrn.carbon.catalog.progressbar.ProgressBarDemoScreen
import com.gabrieldrn.carbon.catalog.radiobutton.LoadingDemoScreen
import com.gabrieldrn.carbon.catalog.radiobutton.RadioButtonDemoScreen
import com.gabrieldrn.carbon.catalog.settings.SettingsScreen
import com.gabrieldrn.carbon.catalog.tag.TagDemoScreen
import com.gabrieldrn.carbon.catalog.textinput.TextInputDemoScreen
import com.gabrieldrn.carbon.catalog.theme.CarbonTheme
import com.gabrieldrn.carbon.catalog.toggle.ToggleDemoScreen
import org.jetbrains.compose.resources.DrawableResource

@Suppress("UndocumentedPublicClass", "UndocumentedPublicProperty")
enum class Destination(
    override val title: String,
    val illustration: DrawableResource? = null,
    override val route: String = "",
    val content: @Composable () -> Unit = {}
) : BaseDestination {
    Home(
        title = "Carbon Design System",
        illustration = null,
        route = "home"
    ),
    Settings(
        title = "Settings",
        illustration = null,
        route = "settings",
        content = {
            var selectedLightTheme by remember {
                mutableStateOf<CarbonTheme.LightTheme>(CarbonTheme.LightTheme.W)
            }
            var selectedDarkTheme by remember {
                mutableStateOf<CarbonTheme.DarkTheme>(CarbonTheme.DarkTheme.G100)
            }

            SettingsScreen(
                selectedLightTheme = selectedLightTheme,
                selectedDarkTheme = selectedDarkTheme,
                onLightThemeSelected = { selectedLightTheme = it },
                onDarkThemeSelected = { selectedDarkTheme = it }
            )
        }
    ),
    Accordion("Accordion"),
    Breadcrumb("Breadcrumb"),
    Button(
        title = "Button",
        illustration = Res.drawable.tile_button,
        route = "button",
        content = { ButtonDemoScreen() }
    ),
    Checkbox(
        title = "Checkbox",
        illustration = Res.drawable.tile_checkbox,
        route = "checkbox",
        content = { CheckboxDemoScreen() }
    ),
    CodeSnippet("Code snippet"),
    ContentSwitcher("Content switcher"),
    DataTable("Data table"),
    DatePicker("Date picker"),
    Dropdown(
        title = "Dropdown",
        illustration = Res.drawable.tile_dropdown,
        route = "dropdown"
    ),
    FileUploader("File uploader"),
    Form("Form"),
    InlineLoading("Inline loading"),
    Link("Link"),
    List("List"),
    Loading(
        title = "Loading",
        illustration = Res.drawable.tile_loading,
        route = "loading",
        content = { LoadingDemoScreen() }
    ),
    Modal("Modal"),
    MultiSelect(
        title = "Multi-select",
        illustration = Res.drawable.tile_mutliselect,
        route = "dropdown/multiselect",
        content = { DropdownDemoScreen(DropdownVariant.Multiselect) }
    ),
    Notification("Notification"),
    NumberInput("Number input"),
    Pagination("Pagination"),
    ProgressBar(
        title = "Progress bar",
        illustration = Res.drawable.tile_progress_bar,
        route = "progressbar",
        content = { ProgressBarDemoScreen() }
    ),
    ProgressIndicator("Progress indicator"),
    OverflowMenu("Overflow menu"),
    RadioButton(
        title = "Radio button",
        illustration = Res.drawable.tile_radiobutton,
        route = "radiobutton",
        content = { RadioButtonDemoScreen() }
    ),
    Search("Search"),
    Select("Select"),
    Slider("Slider"),
    StructuredList("Structured list"),
    Tabs("Tabs"),
    Tag(
        title = "Tag",
        illustration = Res.drawable.tile_tag,
        route = "tag",
        content = { TagDemoScreen() }
    ),
    TextInput(
        title = "Text input",
        illustration = Res.drawable.tile_text_input,
        route = "textinput",
        content = { TextInputDemoScreen() }
    ),
    Tile("Tile"),
    Toggle(
        title = "Toggle",
        illustration = Res.drawable.tile_toggle,
        route = "toggle",
        content = { ToggleDemoScreen() }
    ),
    Tooltip("Tooltip"),
    UIShell("UI shell");

    companion object {
        private val nonComponentDestinations = setOf(Home, Settings)

        val homeTilesDestinations = entries
            .filterNot { it in nonComponentDestinations }
            // Show first the components that have a demo activity
            .sortedByDescending { it.route.isNotEmpty() }
    }
}
