package com.sample.demo.dialog

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AlertDialog


class ErrorDialog(private val activity: Activity, private val title: String,
                  private val msg: String, private var runnable: Runnable?) {

    private var dialog: AlertDialog? = null

    init {
        configureDialog()
    }

    @SuppressLint("InflateParams")
    private fun configureDialog(): AlertDialog? {

        if (activity.isFinishing) return null

        val builder = AlertDialog.Builder(activity)
        dialog = builder.create()
        (dialog as AlertDialog).setCancelable(false)
        (dialog as AlertDialog).setTitle(title)
        (dialog as AlertDialog).setMessage(msg)
        (dialog as AlertDialog).setCanceledOnTouchOutside(false)
        (dialog as AlertDialog).setButton(AlertDialog.BUTTON_NEUTRAL, "Continue.."
        ) { dialog, _ ->
            dialog.dismiss()
            runnable?.run()
        }

        return dialog as AlertDialog
    }

    fun show() = dialog?.show()
}