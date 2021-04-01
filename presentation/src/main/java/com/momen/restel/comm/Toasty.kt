package com.momen.restel.comm

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty

class Toasty {
    companion object {
        fun showErrorToasty(context: Context, msg: String) {
            Toasty.error(context, msg, Toast.LENGTH_SHORT, false).show()
        }

        fun showSuccessToasty(context: Context, msg: String) {
            Toasty.success(context, msg, Toast.LENGTH_SHORT, false).show()
        }

        fun showWarningToasty(context: Context, msg: String) {
            Toasty.warning(context, msg, Toast.LENGTH_SHORT, false).show()
        }

        fun showInfoToasty(context: Context, msg: String) {
            Toasty.info(context, msg, Toast.LENGTH_SHORT, false).show()
        }

    }
}