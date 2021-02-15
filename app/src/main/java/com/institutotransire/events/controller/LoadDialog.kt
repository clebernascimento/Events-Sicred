package com.institutotransire.events.controller

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import android.widget.*

class LoadDialog(private val context: Context) {
    private var dialog: AlertDialog
    private var llParam: LinearLayout.LayoutParams? = null
    private var ySize = 300
    private var xSize = 500
    private var textSize = 30
    private var color = Color.CYAN
    private var cancelable = true
    private var textColor = Color.BLACK
    private val WRAP_CONTENT = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
    private var FORMAT_LAYOUT_VERTICAL = true
    fun setColor(color: Int): LoadDialog {
        this.color = color
        return this
    }

    fun setCancelable(cancelable: Boolean): LoadDialog {
        this.cancelable = cancelable
        return this
    }

    fun setTextColor(textColor: Int): LoadDialog {
        this.textColor = textColor
        return this
    }

    fun setTextSize(size: Int): LoadDialog {
        textSize = size
        return this
    }

    fun setSize(x: Int, y: Int): LoadDialog {
        xSize = x
        ySize = y
        return this
    }

    fun setHorizontalLayout(): LoadDialog {
        FORMAT_LAYOUT_VERTICAL = false
        setSize(80, 80)
        return this
    }

    fun justLoad() {
        dismissIt()
        val layoutBackground = RelativeLayout(context)
        layoutBackground.gravity = Gravity.CENTER
        layoutBackground.layoutParams = WRAP_CONTENT
        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, 20, 0)
        progressBar.indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        llParam = LinearLayout.LayoutParams(xSize, ySize)
        progressBar.layoutParams = llParam
        layoutBackground.addView(progressBar)
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(cancelable)
        builder.setView(layoutBackground)
        dialog = builder.create()
        dialog.show()
        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }

    fun startWithMessage(textToShow: String?) {
        dismissIt()
        val ll = LinearLayout(context)
        val progressBar = ProgressBar(context)
        if (FORMAT_LAYOUT_VERTICAL) {
            ll.orientation = LinearLayout.VERTICAL
            progressBar.setPadding(0, 0, 0, 50)
        } else {
            ll.orientation = LinearLayout.HORIZONTAL
            progressBar.setPadding(0, 0, 30, 0)
        }
        ll.setPadding(10, 30, 0, 30)
        ll.gravity = Gravity.CENTER
        var llParam = WRAP_CONTENT
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        llParam = LinearLayout.LayoutParams(xSize, ySize)
        progressBar.isIndeterminate = true
        progressBar.layoutParams = llParam
        progressBar.indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        llParam = WRAP_CONTENT
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = textToShow
        tvText.setTextColor(textColor)
        tvText.textSize = textSize.toFloat()
        tvText.layoutParams = llParam
        ll.addView(progressBar)
        ll.addView(tvText)
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(cancelable)
        builder.setView(ll)
        dialog = builder.create()
        dialog.show()
        val window = dialog.window
        if (FORMAT_LAYOUT_VERTICAL) window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }

    fun loadInAView(view: ScrollView?) {
        dismissIt()
        val layoutBackground = RelativeLayout(context)
        layoutBackground.gravity = Gravity.CENTER
        layoutBackground.layoutParams = WRAP_CONTENT
        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, 20, 0)
        progressBar.indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        llParam = LinearLayout.LayoutParams(xSize, ySize)
        progressBar.layoutParams = llParam
        layoutBackground.addView(progressBar)
        dialog.show()
        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }

    fun dismissIt() {
        if (dialog.isShowing) dialog.dismiss()
    }

    companion object {
        fun showStandardLoading(loadDialog: LoadDialog, message: String?) {
            loadDialog
                    .setCancelable(false)
                    .setColor(Color.rgb(2, 101, 137))
                    .setTextSize(16)
                    .setSize(150, 150)
                    .setTextColor(Color.WHITE)
                    .startWithMessage(message)
        }
    }

    init {
        val builder = AlertDialog.Builder(context)
        dialog = builder.create()
    }
}