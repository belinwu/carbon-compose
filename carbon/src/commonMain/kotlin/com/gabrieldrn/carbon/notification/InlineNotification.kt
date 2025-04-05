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

package com.gabrieldrn.carbon.notification

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString

/**
 * # Inline notification
 *
 * Inline notifications show up in task flows, to notify users of the status of an action or system.
 * They usually appear at the top of the primary content area or close to the item needing
 * attention.
 *
 * (From [Notification documentation](https://carbondesignsystem.com/components/notification/usage/#inline))
 *
 * @param title The title of the notification.
 * @param body The body of the notification.
 * @param status The status of the notification, which determines its color and icon
 * used.
 * @param onClose The callback to be called when the notification is closed.
 * @param modifier The modifier to apply to the component.
 * @param highContrast Whether to use high contrast colors.
 */
@Composable
public fun InlineNotification(
    title: String,
    body: String,
    status: NotificationStatus,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    highContrast: Boolean = false
) {
    NotificationContainer(
        body = AnnotatedString(body),
        title = title,
        status = status,
        displayCloseButton = true,
        highContrast = highContrast,
        modifier = modifier,
        onClose = onClose
    )
}
