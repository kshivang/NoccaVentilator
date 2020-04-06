package ai.rever.noccaventilator.view.home.ventilator_mode.ventilator_mode_details

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.api.frGraphDataGetter
import ai.rever.noccaventilator.api.ptGraphDataGetter
import ai.rever.noccaventilator.api.vtGraphDataGetter
import ai.rever.noccaventilator.view.common.BaseFragment
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.fragment_ventilator_graph.*
import org.jetbrains.anko.colorAttr
import org.jetbrains.anko.dip

class VentilatorGraphFragment(override val title: String) : BaseFragment() {

    private val holderFragment get() = parentFragment as? VentilatorDetailsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_ventilator_graph, container, false)
    }

    override val showBottomStatus: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        holderActivity?.setBottomNavButton(getString(R.string.set_alarm), View.OnClickListener {
            holderFragment?.setChildFragment(VentilatorAlarmFragment(title))
        })

        setChartStyle(lcPT)
        setChartStyle(lcVT)
        setChartStyle(lcFR)

        ptGraphDataGetter(title)
            .subscribe {
                LineDataSet(it, "").run {
                    setDrawCircleHole(false)
                    setDrawCircles(false)
                    setDrawValues(false)
                    mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                    LineData(this)
                }.apply {
                    lcPT.data = this
                    lcPT.invalidate()
                }
            }.addTo(compositeDisposable)

        vtGraphDataGetter(title)
            .subscribe {
                LineDataSet(it, "").run {
                    setDrawCircleHole(false)
                    setDrawCircles(false)
                    setDrawValues(false)
                    mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                    LineData(this)
                }.apply {
                    lcVT.data = this
                    lcVT.invalidate()
                }
            }.addTo(compositeDisposable)

        frGraphDataGetter(title)
            .subscribe {
                LineDataSet(it, "").run {
                    setDrawCircleHole(false)
                    setDrawCircles(false)
                    setDrawValues(false)
                    mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                    LineData(this)
                }.apply {
                    lcFR.data = this
                    lcFR.invalidate()
                }
            }.addTo(compositeDisposable)
    }

    private fun setChartStyle(chart: LineChart) {
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
        chart.setDrawMarkers(false)
        chart.setDrawBorders(true)
        chart.setBorderColor(Color.WHITE)
        chart.xAxis.apply {
            setDrawLabels(false)
            setDrawGridLines(true)
            gridColor = Color.WHITE
        }
        chart.axisRight.isEnabled = false
        chart.axisLeft.run {
            gridColor = Color.WHITE
            zeroLineColor = Color.WHITE
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            textSize = 13f
        }

    }
}