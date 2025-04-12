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

package com.gabrieldrn.carbon.accordion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.Theme

@Immutable
internal class AccordionColors private constructor(
    theme: Theme,
    layer: Layer
) {

    val textColor = theme.textPrimary

    val dividerColor = when (layer) {
        Layer.Layer00 -> theme.borderSubtle00
        Layer.Layer01 -> theme.borderSubtle01
        Layer.Layer02 -> theme.borderSubtle02
        Layer.Layer03 -> theme.borderSubtle03
    }

    internal companion object {

        @Composable
        fun colors(
            theme: Theme = Carbon.theme,
            layer: Layer = Carbon.layer
        ): AccordionColors = remember(theme, layer) {
            AccordionColors(theme, layer)
        }
    }
}
