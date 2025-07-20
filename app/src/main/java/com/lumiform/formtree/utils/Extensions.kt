package com.lumiform.formtree.utils

import android.app.Activity
import android.graphics.Color
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

    alertDialogOkButton.setOnClickListenerWithDebounce {
        result = true
        alertDialog.dismiss()
    }

    alertDialogCancelButton.setOnClickListenerWithDebounce {
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
 * Sets a click listener on a view with a debounce mechanism to prevent multiple rapid clicks.
 *
 * This method applies a debounce time to the click listener to avoid multiple clicks within a short
 * period. The click listener will be triggered only after the specified debounce time has elapsed
 * since the last click.
 *
 * @param debounceTime The time in milliseconds to wait before allowing another click. Default is 500 milliseconds.
 * @param onClickListener The [View.OnClickListener] to be invoked when the view is clicked.
 */
fun View.setOnClickListenerWithDebounce(
    debounceTime: Long = 500L,
    onClickListener: View.OnClickListener
) = ClickUtils.applySingleDebouncing(arrayOf(this), debounceTime, onClickListener)

/**
 * Sets a click listener on a view with a default debounce mechanism.
 *
 * This method applies a default debounce time to the click listener to avoid multiple clicks
 * within a short period. The click listener will be triggered only after the default debounce time
 * has elapsed since the last click.
 *
 * @param onClickListener The [View.OnClickListener] to be invoked when the view is clicked.
 */
fun View.setOnClickListenerWithDebounce(onClickListener: View.OnClickListener) =
    ClickUtils.applySingleDebouncing(this, onClickListener)
