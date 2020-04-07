package ai.rever.noccaventilator.view.home

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.view.common.BaseFragment
import ai.rever.noccaventilator.view.home.patient_information.PatientInformationFragment
import ai.rever.noccaventilator.view.home.precheck.PreCheckFragment
import ai.rever.noccaventilator.view.home.ventilator_mode.VentilatorModeFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frament_home.*

class HomeFragment: BaseFragment() {

    override val title: String
        get() = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.frament_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btPatientInfo.setOnClickListener {
            setFragment(PatientInformationFragment())
        }

        btPreCheck.setOnClickListener {
            setFragment(PreCheckFragment())
        }

        btVentilatorMode.setOnClickListener {
            setFragment(VentilatorModeFragment())
        }

        btStandby.setOnClickListener {
            toast("Standby clicked")
        }
    }

    override fun onResume() {
        super.onResume()
        holderActivity?.showHomeButton(false)
    }

    override fun onPause() {
        super.onPause()
        holderActivity?.showHomeButton()
    }
}
