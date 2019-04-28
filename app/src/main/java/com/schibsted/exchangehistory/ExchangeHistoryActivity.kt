package com.schibsted.exchangehistory

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.snackbar.Snackbar
import com.schibsted.R
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.presentation.DateValueFormatter
import com.schibsted.exchangehistory.presentation.ExchangeHistoryViewModel
import com.schibsted.exchangehistory.presentation.FiltrationType
import kotlinx.android.synthetic.main.activity_exchange_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExchangeHistoryActivity : AppCompatActivity() {
    private val viewModel: ExchangeHistoryViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_history)
        setSupportActionBar(toolbar)
        setupChartConfigration()
        if (savedInstanceState == null)
            viewModel.updateExchangeHistory(FiltrationType.OneMonth, Currencies.USD, Currencies.EUR)

        viewModel.getExchangeData()
            .observe(this, Observer {
                showExchange(it)
            })

        viewModel.getError().observe(this, Observer {
            showError(it)
        })
        viewModel.isShowLoading().observe(this, Observer {
            if (it)
                showLoading()
            else
                hideLoading()
        })
    }


    private fun showExchange(result: List<Entry>) {

        exchangeChartView.visibility = View.VISIBLE
        errorLoadingTextView.visibility = View.INVISIBLE

        setData(result)
        exchangeChartView.invalidate()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exchange_history, menu)
        return true
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


    private fun setupChartConfigration() {
        exchangeChartView.setViewPortOffsets(0f, 0f, 0f, 0f)
        exchangeChartView.setBackgroundColor(Color.rgb(104, 241, 175))

        // no description text
        exchangeChartView.description.isEnabled = false

        // enable touch gestures
        exchangeChartView.setTouchEnabled(true)

        // enable scaling and dragging
        exchangeChartView.isDragEnabled = true
        exchangeChartView.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        exchangeChartView.setPinchZoom(false)

        exchangeChartView.setDrawGridBackground(false)
//        exchangeChartView.maxHighlightDistance = 300f

        exchangeChartView.xAxis.apply {
            isEnabled = true
            position = XAxis.XAxisPosition.BOTTOM_INSIDE
            setDrawGridLines(true)
            granularity = 1f
            isGranularityEnabled = true
            textColor = Color.WHITE
            axisLineColor = Color.WHITE
            valueFormatter = DateValueFormatter()

        }


        exchangeChartView.axisLeft.apply {
            setLabelCount(6, false)
            textColor = Color.WHITE
            setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
            setDrawGridLines(false)
            axisLineColor = Color.WHITE
        }

        exchangeChartView.axisRight.isEnabled = false

        exchangeChartView.legend.isEnabled = false

        exchangeChartView.animateXY(2000, 2000)

        exchangeChartView.invalidate()


    }


    private fun setData(values: List<Entry>) {

        val set1: LineDataSet

        if (exchangeChartView.data != null && exchangeChartView.data.dataSetCount > 0) {
            set1 = exchangeChartView.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            exchangeChartView.data.notifyDataChanged()
            exchangeChartView.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")

            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(false)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.WHITE)
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.color = Color.WHITE
            set1.fillColor = Color.WHITE
            set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(false)
//            set1.fillFormatter =
//                IFillFormatter { dataSet, dataProvider -> exchangeChartView.axisLeft.axisMinimum }

            // create a data object with the data sets
            val data = LineData(set1)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // set data
            exchangeChartView.data = data
        }
    }
}


