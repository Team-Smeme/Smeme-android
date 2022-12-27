package com.sopt.smeme.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(view: View, msg: String) {
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
}

fun showSnackBar(view: View, msg: String, action: Snackbar.() -> Unit) {
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).apply { action() }.show()
}