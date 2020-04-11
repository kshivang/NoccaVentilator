package ai.rever.noccaventilator.view.home.patient_information.add_patient

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.room.addPatient
import ai.rever.noccaventilator.view.common.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.fragment_add_patient.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btSave.setOnClickListener {
            onSave()
        }
    }

    private fun onSave() {
        val patientName = etPatientName.text
        val patientID = etPatientID.text

        if (patientName.isEmpty()) {
            toast("Patient Name is empty!")
        }

        if (patientID.isEmpty()) {
            toast("PatientID is empty!")
        }

        if (patientName.isNotEmpty() && patientID.isNotEmpty()) {
            addPatient(patientName.toString(), patientID.toString())
                ?.subscribe {
                    holderActivity?.moveToHome()
                    toast("New patient added!")
                }?.addTo(compositeDisposable)
        }
    }
}