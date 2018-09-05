package com.onos.weather.weatherapp.base

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.onos.weather.weatherapp.extentions.isNetworkAvailable


abstract class BaseActivity : AppCompatActivity(), BaseView {

    private val loadingDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }


    override fun isNetworkAvailable(): Boolean {
        return applicationContext.isNetworkAvailable()
    }

    override fun showLoading() {
        prepareAndShowDialog()
    }

    override fun showLoading(message: String) {
        prepareAndShowDialog {
            loadingDialog.setMessage(message)
        }
    }

    override fun showLoading(message: Int) {
        prepareAndShowDialog {
            loadingDialog.setMessage(getString(message))
        }
    }

    private fun prepareAndShowDialog(function: (() -> Unit)? = null) {
        function?.let { it() }
        loadingDialog.isIndeterminate = true
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    override fun dismissLoading() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    override fun getAppContext(): Context {
        return applicationContext
    }

    override fun showMessage(message: String) {
        runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
    }

    override fun showMessage(messageResId: Int) {
        runOnUiThread { Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show() }
    }


}