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

package com.gabrieldrn.carbon.foundation.color

import androidx.compose.ui.graphics.Color

/**
 * Returns the container color based on a provided [layer].
 *
 * @param layer Associated layer. Defaults to layer 00.
 */
public fun Theme.containerColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> background
    Layer.Layer01 -> layer01
    Layer.Layer02 -> layer02
    Layer.Layer03 -> layer03
}
