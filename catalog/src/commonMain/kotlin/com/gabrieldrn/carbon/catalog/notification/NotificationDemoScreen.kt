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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.notification.CalloutNotification
import com.gabrieldrn.carbon.notification.NotificationStatus
import com.gabrieldrn.carbon.textinput.TextInput
import com.gabrieldrn.carbon.toggle.Toggle

@Composable
fun NotificationDemoScreen(modifier: Modifier = Modifier) {

    var notificationStatus by remember { mutableStateOf(NotificationStatus.Success) }
    var highContrast by remember { mutableStateOf(false) }

    val urlDemoNotification: @Composable ColumnScope.() -> Unit = {
        val url = remember {
            "https://carbondesignsystem.com/components/notification/usage/#callout"
        }
        CalloutNotification(
            title = "Callout notification",
            body = buildAnnotatedString {
                val clickableText = "callout notification"
                val string = "This is a $clickableText."
                append(string)
                addLink(
                    url = LinkAnnotation.Url(
                        url = url,
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                color = Carbon.theme.linkPrimary,
                                textDecoration = TextDecoration.Underline
                            )
                        ),
                    ),
                    start = string.indexOf(clickableText),
                    end = string.indexOf(clickableText) + clickableText.length
                )
            },
            status = notificationStatus,
            highContrast = highContrast,
            modifier = Modifier.width(IntrinsicSize.Max)
        )
    }

    var title by remember {
        mutableStateOf("Lorem ipsum")
    }
    var body by remember {
        mutableStateOf("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
    }

    val editableDemoNotification: @Composable ColumnScope.() -> Unit = {
        CalloutNotification(
            title = title,
            body = body,
            status = notificationStatus,
            highContrast = highContrast,
            modifier = Modifier.width(IntrinsicSize.Max)
        )
    }

    val parametersContent: @Composable ColumnScope.() -> Unit = {
        CalloutNotification(
            title = "",
            body = buildAnnotatedString {
                append("Other notification variants are a work in progress.")
            },
            status = NotificationStatus.Informational,
        )

        TextInput(
            label = "Title",
            value = title,
            onValueChange = { title = it },
            placeholderText = "Enter a title"
        )

        TextInput(
            label = "Body",
            value = body,
            onValueChange = { body = it },
            placeholderText = "Enter a body"
        )

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

    DemoScreen(
        modifier = modifier,
        demoContent = {
            urlDemoNotification()

            Spacer(modifier = Modifier.height(SpacingScale.spacing05))

            editableDemoNotification()
        },
        demoParametersContent = parametersContent
    )
}
