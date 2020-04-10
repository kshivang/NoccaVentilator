package ai.rever.noccaventilator.view.home.patient_information.add_patient

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.view.common.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AddPatientFragment: BaseFragment() {
    override val title: String
        get() = "Patient Information"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_add_patient, container, false)
    }
}