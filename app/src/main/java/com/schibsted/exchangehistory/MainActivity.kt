package com.schibsted.exchangehistory

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anychart.AnyChart
import com.anychart.data.Set
import com.anychart.enums.MarkerType
import com.schibsted.R
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.presentation.ExchangeHistoryViewModel
import com.schibsted.exchangehistory.presentation.FiltrationType
import com.schibsted.exchangehistory.presentation.dto.ExchangeDataEntry
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: ExchangeHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.isShowLoading().observe(this, Observer {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })
        viewModel.getError().observe(this, Observer {
            showError(it)
        })

        viewModel.getExchangeData(FiltrationType.TwoYear, Currencies.USD, Currencies.EUR).observe(this, Observer {
            showExchange(it)
        })
    }


    private fun showExchange(result: List<ExchangeDataEntry>) {
        Log.d(javaClass.name, result.toString())
        any_chart_view.setProgressBar(progress_bar)

        val cartesian = AnyChart.line()

        cartesian.animation(true)
        cartesian.yAxis(0).title(Currencies.USD.name)
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)


        val set = Set.instantiate()
        set.data(result)
        val series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")


        val series1 = cartesian.line(series1Mapping)
        series1.name("exchange")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)

        any_chart_view.setChart(cartesian)
    }

    private fun showError(error: String) {
        Log.d(javaClass.name, error)
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

}


