package ai.rever.noccaventilator.view.home.patient_information

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.view.common.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_patient_information.*

class PatientInformationFragment: BaseFragment() {

    override val title: String
        get() = "Patient Information"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_patient_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btAddNewPatient.setOnClickListener {
            toast("Add new patient")
        }

        btLastFourDays.setOnClickListener {
            toast("Last 4 days")
        }

        btClearData.setOnClickListener {
            toast("Clear Data")
        }
    }
}