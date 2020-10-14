package com.sample.demo.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.sample.demo.R

class LoadingOverlayDialogController(private val activity: Activity, private var runnable: Runnable?) {

    private var dialog: AlertDialog? = null

    init {
        configureDialog()
    }

    @SuppressLint("InflateParams")
    private fun configureDialog(): AlertDialog? {

        if (activity.isFinishing) return null

        val builder = AlertDialog.Builder(activity)
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.custom_loading_dialog, null)
        builder.setView(dialogView)
        dialog = builder.create()
        (dialog as AlertDialog).setCancelable(false)
        (dialog as AlertDialog).setCanceledOnTouchOutside(false)

        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                runnable?.run()
            }
            true
        }

        return dialog as AlertDialog
    }

    fun show() = dialog?.show()

    fun dismiss() {
        runnable = null
        dialog?.dismiss()
    }
}