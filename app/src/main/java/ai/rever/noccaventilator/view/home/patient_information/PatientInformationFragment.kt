package ai.rever.noccaventilator.view.home.patient_information

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.room.clearAll
import ai.rever.noccaventilator.view.common.BaseFragment
import ai.rever.noccaventilator.view.home.patient_information.add_patient.AddPatientFragment
import ai.rever.noccaventilator.view.home.patient_information.last_day.Last4DaysFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.alert_require_password.view.*
import kotlinx.android.synthetic.main.fragment_patient_information.*
import org.jetbrains.anko.alert

class PatientInformationFragment: BaseFragment() {

    companion object {
        const val ADMIN_PASSWORD = "nocca4321"
    }

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
            setFragment(AddPatientFragment())
        }

        btLastFourDays.setOnClickListener {
            setFragment(Last4DaysFragment())
        }

        btClearData.setOnClickListener {
            requestClearData()
        }
    }

    private fun requestClearData() {
        context?.apply {
            val alert = alert {}
            val view = View.inflate(this, R.layout.alert_require_password, null)
            alert.customView = view
            val dialog = alert.show()
            view.btConfirm.setOnClickListener {
                if (view.etPassword.text.toString() == ADMIN_PASSWORD) {
                    clearAll?.subscribe {
                        dialog.dismiss()
                        toast("All data deleted!")
                    }
                } else {
                    if (view.etPassword.text.isNotEmpty()) {
                        toast("Wrong password!")
                    } else {
                        toast("Password empty!")
                    }
                }
            }
        }
    }
}