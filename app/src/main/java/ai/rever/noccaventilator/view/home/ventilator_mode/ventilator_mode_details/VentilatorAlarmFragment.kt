package ai.rever.noccaventilator.view.home.ventilator_mode.ventilator_mode_details

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.api.requestStart
import ai.rever.noccaventilator.model.VentilatorAlarm
import ai.rever.noccaventilator.view.common.BaseFragment
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    var ventilatorAlarmData = VentilatorAlarm(32,  32, 32,  32, 32,  32)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLabel()
        setAlarmData()
        setClickListener()
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

    private fun setAlarmData() {
        tvPHigh.text = "${ventilatorAlarmData.pHigh}"
        tvPLow.text = "${ventilatorAlarmData.pLow}"
        tvVTeHigh.text = "${ventilatorAlarmData.vTelHigh}"
        tvVTeLow.text = "${ventilatorAlarmData.vTelLow}"
        tvRRHigh.text = "${ventilatorAlarmData.rrHigh}"
        tvRRLow.text = "${ventilatorAlarmData.rrLow}"
    }

    private fun setClickListener() {
        ibPHighLeft.setOnClickListener {
            ventilatorAlarmData.pHigh -= 1
            setAlarmData()
        }

        ibPHighRight.setOnClickListener {
            ventilatorAlarmData.pHigh += 1
            setAlarmData()
        }

        ibPLowLeft.setOnClickListener {
            ventilatorAlarmData.pLow -= 1
            setAlarmData()
        }

        ibPLowRight.setOnClickListener {
            ventilatorAlarmData.pLow += 1
            setAlarmData()
        }

        ibVTEHighLeft.setOnClickListener {
            ventilatorAlarmData.vTelHigh -= 1
            setAlarmData()
        }

        ibVTEHighRight.setOnClickListener {
            ventilatorAlarmData.vTelHigh += 1
            setAlarmData()
        }

        ibVTELowLeft.setOnClickListener {
            ventilatorAlarmData.vTelLow -= 1
            setAlarmData()
        }

        ibVTELowRight.setOnClickListener {
            ventilatorAlarmData.vTelLow += 1
            setAlarmData()
        }

        ibRRHighLeft.setOnClickListener {
            ventilatorAlarmData.rrHigh -= 1
            setAlarmData()
        }

        ibRRHighRight.setOnClickListener {
            ventilatorAlarmData.rrHigh += 1
            setAlarmData()
        }

        ibRRLowLeft.setOnClickListener {
            ventilatorAlarmData.rrLow -= 1
            setAlarmData()
        }

        ibRRLowRight.setOnClickListener {
            ventilatorAlarmData.rrLow += 1
            setAlarmData()
        }

        holderActivity?.setBottomNavButton(getString(R.string.start), View.OnClickListener {
            requestStart(ventilatorAlarmData).thenAccept { runOnActive {
                holderFragment?.setChildFragment(VentilatorGraphFragment(title))
            } }
        })
    }
}