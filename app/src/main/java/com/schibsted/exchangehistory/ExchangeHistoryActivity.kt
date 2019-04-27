package com.schibsted.exchangehistory

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anychart.AnyChart
import com.anychart.data.Set
import com.anychart.enums.MarkerType
import com.google.android.material.snackbar.Snackbar
import com.schibsted.R
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.presentation.ExchangeHistoryViewModel
import com.schibsted.exchangehistory.presentation.FiltrationType
import com.schibsted.exchangehistory.presentation.dto.ExchangeDataEntry
import kotlinx.android.synthetic.main.activity_exchange_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExchangeHistoryActivity : AppCompatActivity() {
    private val viewModel: ExchangeHistoryViewModel by viewModel()
    private lateinit var chartData: Set
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_history)
        setSupportActionBar(toolbar)

        val cartesian = AnyChart.line()

        cartesian.animation(true)
        cartesian.yAxis(0).title(Currencies.USD.name)
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        chartData = Set.instantiate()
        val chartDataMapping = chartData.mapAs("{ x: 'x', value: 'value' }")

        cartesian.line(chartDataMapping).apply {
            name(Currencies.EUR.name)
            hovered().markers().enabled(true)
            hovered().markers().type(MarkerType.CIRCLE).size(4.0)
        }

        cartesian.apply {
            legend().apply {
                enabled(true)
                fontSize(13.0)
            }
        }
        exchangeChartView.setChart(cartesian)

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

        viewModel.getExchangeData(FiltrationType.OneMonth, Currencies.USD, Currencies.EUR)
            .observe(this, Observer {
                Log.d(javaClass.name, it.joinToString { it.keySet().toString() })
                showExchange(it)
            })
    }


    private fun showExchange(result: List<ExchangeDataEntry>) {
        exchangeChartView.visibility = View.VISIBLE
        errorLoadingTextView.visibility = View.INVISIBLE
        chartData.data(result)
        exchangeChartView.invalidate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exchange_history, menu)
        return true;
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.one_month -> {
                viewModel.updateExchangeHistory(FiltrationType.OneMonth, Currencies.USD, Currencies.EUR)
                return true
            }
            R.id.two_months -> {
                viewModel.updateExchangeHistory(FiltrationType.TwoMonths, Currencies.USD, Currencies.EUR)
                return true
            }
            R.id.six_months -> {
                viewModel.updateExchangeHistory(FiltrationType.SixMonths, Currencies.USD, Currencies.EUR)
                return true
            }
            R.id.one_year -> {
                viewModel.updateExchangeHistory(FiltrationType.OneYear, Currencies.USD, Currencies.EUR)
                return true
            }
            R.id.two_years -> {
                viewModel.updateExchangeHistory(FiltrationType.TwoYears, Currencies.USD, Currencies.EUR)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }


    private fun showError(error: String) {
        if (exchangeChartView.visibility == View.VISIBLE) {
            Snackbar.make(parentView, error, Snackbar.LENGTH_LONG).show()
        } else {
            exchangeChartView.visibility = View.INVISIBLE
            errorLoadingTextView.text = error
            errorLoadingTextView.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        if (exchangeChartView.visibility != View.VISIBLE) {
            errorLoadingTextView.visibility = View.VISIBLE
            errorLoadingTextView.text = getString(R.string.loading)
        }
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

}


