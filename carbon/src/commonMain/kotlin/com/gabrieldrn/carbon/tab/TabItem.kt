/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.tab

import androidx.compose.runtime.Immutable

/**
 * Represents a single tab in a [TabList] component.
 * @param label The label of the tab to be displayed.
 * @param enabled Whether the tab is enabled or not.
 */
@Immutable
public data class TabItem(
    val label: String,
    val enabled: Boolean = true
)

/**
 * Returns a map of strings to [TabItem]s.
 */
public fun tabItemsOf(vararg values: String): List<TabItem> = values.map { TabItem(it) }
