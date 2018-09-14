package com.onos.weather.weatherapp.screen_add_city

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BaseActivity
import com.onos.weather.weatherapp.base.WeatherApp
import kotlinx.android.synthetic.main.activity_add_city.*

fun Context.newAddCityActivity(): Intent {
    return Intent(this, AddCityActivity::class.java)
}

class AddCityActivity : BaseActivity(), AddCityView {

    private val presenter: AddCityPresenter by lazy {
        AddCityPresenter(WeatherApp.apiService, WeatherApp.forecastStorage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        presenter.attachView(this)

        initUI()

    }

    private fun initUI() {
        new_city_name.addTextChangedListener(cityNameTextWatcher)
        new_city_name.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.getForecastForCity(new_city_name.text.toString())
                true
            } else {
                false
            }
        }
        clear_icon.setOnClickListener { new_city_name.setText("") }
    }

    override fun onDestroy() {
        new_city_name.removeTextChangedListener(cityNameTextWatcher)
        presenter.detachView()
        super.onDestroy()
    }

    override fun goBackToMainScreen(success: Boolean) {
        when (success) {
            true -> setResult(Activity.RESULT_OK)
            false -> setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

    private val cityNameTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.length?.let {
                if (it > 0) {
                    clear_icon.visibility = View.VISIBLE
                } else {
                    clear_icon.visibility = View.INVISIBLE
                }
            }
        }
    }
}