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

package com.gabrieldrn.carbon.catalog.notification

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import com.gabrieldrn.carbon.catalog.common.DemoScreenRoot
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.notification.CalloutNotification
import com.gabrieldrn.carbon.notification.NotificationStatus
import com.gabrieldrn.carbon.toggle.Toggle

@Composable
fun NotificationDemoScreen(modifier: Modifier = Modifier) {

    var notificationStatus by remember { mutableStateOf(NotificationStatus.Informational) }
    var highContrast by remember { mutableStateOf(false) }

    val demoScreen: @Composable ColumnScope.() -> Unit = {
        CalloutNotification(
            title = "Callout notification",
            body = buildAnnotatedString {
                append("This is a callout notification.")
            },
            status = notificationStatus,
            highContrast = highContrast,
            modifier = Modifier.width(IntrinsicSize.Max)
        )
    }

    val parametersContent: @Composable ColumnScope.() -> Unit = {
        Dropdown(
            placeholder = "Choose option",
            label = "Notification status",
            options = NotificationStatus.entries.toDropdownOptions(),
            selectedOption = notificationStatus,
            onOptionSelected = { notificationStatus = it }
        )

        Toggle(
            label = "High contrast",
            isToggled = highContrast,
            onToggleChange = { highContrast = it }
        )
    }

    DemoScreenRoot(
        modifier = modifier,
        demoContent = demoScreen,
        demoParametersContent = parametersContent
    )
}
