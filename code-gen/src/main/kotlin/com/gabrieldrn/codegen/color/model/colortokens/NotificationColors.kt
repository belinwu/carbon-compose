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

package com.gabrieldrn.codegen.color.model.colortokens

import kotlinx.serialization.Serializable

@Serializable
data class NotificationColors(
    val notificationActionHover: String,
    val notificationActionTertiaryInverse: String,
    val notificationActionTertiaryInverseActive: String,
    val notificationActionTertiaryInverseHover: String,
    val notificationActionTertiaryInverseText: String,
    val notificationActionTertiaryInverseTextOnColorDisabled: String,
    val notificationBackgroundError: String,
    val notificationBackgroundInfo: String,
    val notificationBackgroundSuccess: String,
    val notificationBackgroundWarning: String
)