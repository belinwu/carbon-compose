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

import androidx.compose.ui.unit.Dp
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

/**
 * Visual variant of a [TabList].
 */
public enum class TabVariant(
    internal val height: Dp
) {

    /**
     * A standalone tab that can also be nested within components. It is commonly used within
     * components or for content using the entire page for layout, not connected to
     * any other components.
     */
    Line(height = SpacingScale.spacing08),

    /**
     * An emphasized tab that is commonly used for defined content areas.
     */
    Contained(height = SpacingScale.spacing09)
}