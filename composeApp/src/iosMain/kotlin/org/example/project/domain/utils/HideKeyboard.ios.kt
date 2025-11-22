package org.example.project.domain.utils

import androidx.compose.ui.focus.FocusManager
import platform.UIKit.UIApplication
import platform.UIKit.endEditing

actual fun hideKeyboard(focusManager: FocusManager) {
    // UiKit
    UIApplication.sharedApplication.keyWindow?.endEditing(true)
}