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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import co.touchlab.kermit.Logger
import com.gabrieldrn.carbon.CarbonDesignSystem
import kotlin.test.Test

class CalloutNotificationTest {

    private var _body by mutableStateOf(buildAnnotatedString {})
    private var _status by mutableStateOf(NotificationStatus.Informational)
    private var _title by mutableStateOf("")
    private var _highContrast by mutableStateOf(false)

    @Test
    fun calloutNotification_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                CalloutNotification(
                    title = _title,
                    body = _body,
                    status = _status,
                    highContrast = _highContrast,
                )
            }
        }

        forEachParameter { body, status, title, highContrast ->
            _body = body
            _status = status
            _title = title
            _highContrast = highContrast

            onNodeWithTag(NotificationTestTags.CONTAINER)
                .assertIsDisplayed()

            onNodeWithTag(
                when (status) {
                    NotificationStatus.Informational -> NotificationTestTags.ICON_INFORMATIONAL
                    NotificationStatus.Success -> NotificationTestTags.ICON_SUCCESS
                    NotificationStatus.Warning -> NotificationTestTags.ICON_WARNING
                    NotificationStatus.Error -> NotificationTestTags.ICON_ERROR
                }
            ).assertIsDisplayed()

            onNodeWithTag(NotificationTestTags.TITLE).run {
                if (title.isBlank())
                    assertDoesNotExist()
                else
                    assertIsDisplayed()
            }

            onNodeWithTag(NotificationTestTags.BODY)
                .assertIsDisplayed()
        }
    }

    @Suppress("NestedBlockDepth")
    private fun forEachParameter(
        testBlock: (AnnotatedString, NotificationStatus, String, Boolean) -> Unit
    ) {
        arrayOf(
            buildAnnotatedString {},
            buildAnnotatedString { append("This is a callout notification.") }
        ).forEach { body ->
            NotificationStatus.entries.forEach { status ->
                arrayOf("", "Callout notification").forEach { title ->
                    arrayOf(false, true).forEach { highContrast ->
                        Logger.d(
                            "body: $body, " +
                                "status: $status, " +
                                "title: $title, " +
                                "highContrast: $highContrast"
                        )
                        testBlock(body, status, title, highContrast)
                    }
                }
            }
        }
    }
}
