package com.zenjob.android.browsr.helpers

import android.app.Activity
import android.view.View
import android.view.WindowManager

object GeneralHelpers {
    fun displayProgressBar(isDisplayed: Boolean, progess: View, context: Activity) {

        if (isDisplayed) {

            context.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            );
        } else {
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        }

        progess.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}