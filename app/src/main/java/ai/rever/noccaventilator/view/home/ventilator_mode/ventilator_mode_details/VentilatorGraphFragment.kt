package ai.rever.noccaventilator.view.home.ventilator_mode.ventilator_mode_details

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.api.*
import ai.rever.noccaventilator.view.common.BaseFragment
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.fragment_ventilator_graph.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton

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
            requestStopGraphData.thenAccept { runOnActive {
                holderFragment?.setChildFragment(VentilatorAlarmFragment(title))
            } }
        })

        setChartStyle(lcPT)
        setChartStyle(lcVT)
        setChartStyle(lcFR)

        subscribeFlowable()
    }

    override fun onPause() {
        super.onPause()
        holderActivity?.setHomeClick()
    }

    override fun onResume() {
        super.onResume()
        holderActivity?.setHomeClick(View.OnClickListener {
            requestStopGraphData.thenAccept {
                holderActivity?.moveToHome()
            }
        })
    }

    private val LineDataSet.lineData: LineData get() {
        setDrawCircleHole(false)
        setDrawCircles(false)
        setDrawValues(false)
        mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        return LineData(this)
    }

    private fun subscribeFlowable() {
        ptGraphEntryFlowable
            .subscribe {
                lcPT.data = LineDataSet(it, "").lineData
                lcPT.invalidate()
            }.addTo(compositeDisposable)

        vtGraphDataFlowable
            .subscribe {
                if (lcVT.visibility != View.VISIBLE) {
                    lcVT.visibility = View.VISIBLE
                    tvVTLabel.visibility = View.VISIBLE
                }
                lcVT.data = LineDataSet(it, "").lineData
                lcVT.invalidate()
            }.addTo(compositeDisposable)

        frGraphDataFlowable
            .subscribe {
                if (lcFR.visibility != View.VISIBLE) {
                    lcFR.visibility = View.VISIBLE
                    tvFRLabel.visibility = View.VISIBLE
                }
                lcFR.data = LineDataSet(it, "").lineData
                lcFR.invalidate()
            }.addTo(compositeDisposable)

        pHighAlarmFlowable
            .subscribe {
                showAlert("P high alarm")
            }

        pLowAlarmFlowable
            .subscribe {
                showAlert("P low alarm")
            }

        vtHighAlarmFlowable
            .subscribe {
                showAlert("vt high alarm")
            }

        vtLowAlarmFlowable
            .subscribe {
                showAlert("vt low alarm")
            }

        rrHighAlarmFlowable
            .subscribe {
                showAlert("rr high alarm")
            }

        rrLowAlarmFlowable
            .subscribe {
                showAlert("rr low alarm")
            }
    }

    private fun showAlert(message: String) {
        requestStopGraphData.thenAccept {
            context?.alert(message) {
                cancelButton {
                    holderActivity?.moveToHome()
                }
            }?.onCancelled {
                holderActivity?.moveToHome()
            }
        }
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
            isGranularityEnabled = true
            granularity = 1.toFloat()
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