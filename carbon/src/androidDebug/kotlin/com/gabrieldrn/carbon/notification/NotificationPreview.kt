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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.gabrieldrn.carbon.CarbonDesignSystem

private class NotificationStatusParameterProvider : PreviewParameterProvider<NotificationStatus> {
    override val values = sequenceOf(
        NotificationStatus.Informational,
        NotificationStatus.Success,
        NotificationStatus.Warning,
        NotificationStatus.Error
    )
}

@Preview(group = "Low contrast")
@Composable
private fun CalloutNotificationPreview(
    @PreviewParameter(NotificationStatusParameterProvider::class) status: NotificationStatus
) {
    CarbonDesignSystem {
        CalloutNotification(
            title = "Callout Notification",
            body = buildAnnotatedString {
                append("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
            },
            status = status
        )
    }
}

@Preview(group = "High contrast")
@Composable
private fun CalloutNotificationHighContrastPreview(
    @PreviewParameter(NotificationStatusParameterProvider::class) status: NotificationStatus
) {
    CarbonDesignSystem {
        CalloutNotification(
            title = "Callout Notification",
            body = buildAnnotatedString {
                append("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
            },
            status = status,
            highContrast = true
        )
    }
}
