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

package com.gabrieldrn.carbon.catalog.radiobutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.CatalogLayoutType
import com.gabrieldrn.carbon.catalog.LocalCatalogLayoutType
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.loading.Loading
import com.gabrieldrn.carbon.loading.SmallLoading

@Composable
fun LoadingDemoScreen(modifier: Modifier = Modifier) {
    @Composable
    fun LargeLoadingDemo() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BasicText(
                text = "Large",
                style = Carbon.typography.bodyCompact01.copy(
                    color = LocalCarbonTheme.current.textPrimary
                )
            )
            Loading(modifier = Modifier.padding(top = SpacingScale.spacing04))
        }
    }

    @Composable
    fun SmallLoadingDemo() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BasicText(
                text = "Small",
                style = Carbon.typography.bodyCompact01.copy(
                    color = LocalCarbonTheme.current.textPrimary
                )
            )
            SmallLoading(modifier = Modifier.padding(top = SpacingScale.spacing04))
        }
    }

    val isVertical = LocalCatalogLayoutType.current == CatalogLayoutType.Vertical

    if (isVertical) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = SpacingScale.spacing10,
                alignment = Alignment.CenterVertically
            )
        ) {
            LargeLoadingDemo()
            SmallLoadingDemo()
        }
    } else {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = SpacingScale.spacing11,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            LargeLoadingDemo()
            SmallLoadingDemo()
        }
    }
}
