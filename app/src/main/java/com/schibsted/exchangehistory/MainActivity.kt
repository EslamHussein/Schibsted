package com.schibsted.exchangehistory

import android.os.Bundle
import android.util.Log
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.schibsted.R
import com.schibsted.core.view.MvpActivity
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.presentation.ExchangeHistoryContract
import com.schibsted.exchangehistory.presentation.FiltrationType
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class MainActivity : MvpActivity<ExchangeHistoryContract.Presenter>(), ExchangeHistoryContract.View {
    override val presenter: ExchangeHistoryContract.Presenter
            by inject { parametersOf(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        presenter.getExchangeHistory(FiltrationType.TwoYear, Currencies.USD, Currencies.EUR)
    }


    override fun showExchange(result: List<CustomDataEntry>) {
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
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        any_chart_view.setChart(cartesian)
    }

    override fun showError(error: String) {
        Log.d(javaClass.name, error)

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

}


class CustomDataEntry internal constructor(x: String, value: Double) :
    ValueDataEntry(x, value)