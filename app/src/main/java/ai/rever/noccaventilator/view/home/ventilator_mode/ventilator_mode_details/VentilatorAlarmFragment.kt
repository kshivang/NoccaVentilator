package ai.rever.noccaventilator.view.home.ventilator_mode.ventilator_mode_details

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.api.ventilatorAlarmGetter
import ai.rever.noccaventilator.model.VentilatorAlarm
import ai.rever.noccaventilator.view.common.BaseFragment
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.fragment_ventilator_alarm.*

class VentilatorAlarmFragment(override val title: String) : BaseFragment() {

    private val holderFragment get() = parentFragment as? VentilatorDetailsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_ventilator_alarm, container, false)
    }

    override val showBottomStatus: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        holderActivity?.setBottomNavButton(getString(R.string.start), View.OnClickListener {
            holderFragment?.setChildFragment(VentilatorGraphFragment(title))
        })

        setLabel()

        ventilatorAlarmGetter
            .subscribe(::setAlarmData)
            .addTo(compositeDisposable)
    }

    private fun setLabel() {
        SpannableString("Phigh cmH20").run {
            setSpan(RelativeSizeSpan(2f), 0, 1, 0)
            tvPHighLabel.text = this
        }

        SpannableString("Plow cmH20").run {
            setSpan(RelativeSizeSpan(2f), 0, 1, 0)
            tvPLowLabel.text = this
        }

        SpannableString("VTehigh cmH20").run {
            setSpan(RelativeSizeSpan(2f), 0, 3, 0)
            tvVTeHighLabel.text = this
        }

        SpannableString("VTelow cmH20").run {
            setSpan(RelativeSizeSpan(2f), 0, 3, 0)
            tvVTeLowLabel.text = this
        }

        SpannableString("RRhigh / min").run {
            setSpan(RelativeSizeSpan(2f), 0, 2, 0)
            tvRRHighLabel.text = this
        }

        SpannableString("RRlow / min").run {
            setSpan(RelativeSizeSpan(2f), 0, 2, 0)
            tvRRLowLabel.text = this
        }
    }

    private fun setAlarmData(alarmData: VentilatorAlarm) {
        tvPHigh.text = "${alarmData.pHigh}"
        tvPLow.text = "${alarmData.pLow}"
        tvVTeHigh.text = "${alarmData.vTelHigh}"
        tvVTeLow.text = "${alarmData.vTelLow}"
        tvRRHigh.text = "${alarmData.rrHigh}"
        tvRRLow.text = "${alarmData.rrLow}"
    }
}