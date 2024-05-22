package carbon.compose.toggle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme
import carbon.compose.toggle.domain.ToggleState

/**
 * Colors to be used by a [Toggle] based on its state.
 */
@Immutable
internal data class ToggleColors(val theme: Theme) {

    val backgroundColor = theme.toggleOff
    val toggledBackgroundColor = theme.supportSuccess
    val disabledBackgroundColor = theme.buttonDisabled
    val readOnlyBackgroundColor = Color.Transparent

    val readOnlyBorderColor = theme.borderSubtle01

    val handleColor = theme.iconOnColor
    val disabledHandleColor = theme.iconOnColorDisabled
    val readOnlyHandleColor = theme.iconPrimary

    val handleCheckmarkColor = theme.supportSuccess
    val disabledHandleCheckmarkColor = theme.buttonDisabled

    val textColor = theme.textPrimary
    val disabledTextColor = theme.textDisabled

    fun backgroundColor(state: ToggleState): Color = when {
        !state.isEnabled -> disabledBackgroundColor
        state.isReadOnly -> readOnlyBackgroundColor
        state.isToggled -> toggledBackgroundColor
        else -> backgroundColor
    }

    fun borderColor(state: ToggleState): Color =
        if (state.isReadOnly && state.isEnabled) readOnlyBorderColor
        else Color.Transparent

    fun handleColor(state: ToggleState): Color = when {
        !state.isEnabled -> disabledHandleColor
        state.isReadOnly -> readOnlyHandleColor
        else -> handleColor
    }

    fun handleCheckmarkColor(state: ToggleState): Color = when {
        !state.isToggled || state.isReadOnly -> Color.Transparent
        !state.isEnabled -> disabledHandleCheckmarkColor
        else -> handleCheckmarkColor
    }

    fun textColor(state: ToggleState): Color =
        if (state.isEnabled) textColor
        else disabledTextColor

    companion object {

        @Composable
        fun colors() = ToggleColors(Carbon.theme)
    }
}
