package com.lumiform.formtree.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.SystemClock
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import com.lumiform.formtree.R
import dmax.dialog.SpotsDialog


/**
 * @created 19/07/2025 - 5:23 AM
 * @project FormTree
 * @author adell
 */

/**
 * Shows a custom Dialog with spots progress.
 *
 * @param message The message to display in the dialog. Default is "Processing".
 */
fun Activity.getSpotsDialogProgress(message: String = getString(R.string.processing)): android.app.AlertDialog {
    return SpotsDialog.Builder()
        .setContext(this) // set the context of the progress dialog
        .setTheme(R.style.ProgressBarStyle) // set the theme of the progress dialog
        .setMessage(message) // set the message of the progress dialog (default is "Processing")
        .setCancelable(false) // set the progress dialog to not cancelable
        .build() // initialize the spotsDialogProgress
}

/**
 * Shows a custom alert dialog with a title, message, and optional buttons.
 *
 * @param title The title of the dialog.
 * @param message The message to display in the dialog.
 * @param showCancelButton Whether to show the Cancel button. Default is false.
 * @param okButtonText Text for the OK button. Default is "OK".
 * @param cancelButtonText Text for the Cancel button. Default is "Cancel".
 * @param onOkClick Callback for when the OK button is clicked. Default is an empty lambda.
 * @param onCancelClick Callback for when the Cancel button is clicked. Default is an empty lambda.
 * @param onDismiss Callback for when the dialog is dismissed. Default is an empty lambda.
 */
fun Activity.showCustomAlertDialog(
    title: String,
    message: String,
    showCancelButton: Boolean = false,
    okButtonText: String = getString(R.string.ok),
    cancelButtonText: String = getString(R.string.cancel),
    onOkClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val alertDialogView = LayoutInflater.from(this).inflate(
        R.layout.dialog_error,
        findViewById<ConstraintLayout>(R.id.constraint_layout_error_dialog),
        false
    )

    val alertDialogTitle = alertDialogView.findViewById<TextView>(R.id.tv_error_dialog_title)
    val alertDialogMessage = alertDialogView.findViewById<TextView>(R.id.tv_error_dialog_message)
    val alertDialogOkButton = alertDialogView.findViewById<Button>(R.id.dialog_ok_button)
    val alertDialogCancelButton = alertDialogView.findViewById<Button>(R.id.dialog_cancel_button)

    alertDialogMessage.movementMethod = android.text.method.ScrollingMovementMethod()
    alertDialogTitle.text = title
    alertDialogMessage.text = message
    alertDialogOkButton.text = okButtonText
    alertDialogCancelButton.text = cancelButtonText

    val alertDialog = AlertDialog.Builder(this)
        .setView(alertDialogView)
        .setCancelable(false)
        .create()

    alertDialog.window?.apply {
        setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        setDimAmount(0.7f)
    }

    alertDialogCancelButton.isVisible = showCancelButton

    var result = false

    alertDialogOkButton.setDebouncedClickListener {
        result = true
        alertDialog.dismiss()
    }

    alertDialogCancelButton.setDebouncedClickListener {
        result = false
        alertDialog.dismiss()
    }

    alertDialog.setOnDismissListener {
        if (result) onOkClick() else onCancelClick()
        onDismiss()
    }

    alertDialog.show()
}

/**
 * Sets a debounced click listener on the view to prevent multiple rapid clicks.
 *
 * @param interval The minimum time interval between clicks in milliseconds. Default is 600ms.
 * @param action The action to perform when the view is clicked.
 */
fun View.setDebouncedClickListener(interval: Long = 600L, action: (View) -> Unit) {
    var lastClickTime = 0L

    setOnClickListener {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime >= interval) {
            lastClickTime = currentTime
            action(it)
        }
    }
}

/**
 * Converts density-independent pixels (dp) to pixels (px).
 *
 * @param dp The value in density-independent pixels to convert.
 * @return The converted value in pixels.
 */
fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
    )
}

/**
 * Converts scale-independent pixels (sp) to pixels (px).
 *
 * @param sp The value in scale-independent pixels to convert.
 * @return The converted value in pixels.
 */
fun Context.spToPx(sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics
    )
}

