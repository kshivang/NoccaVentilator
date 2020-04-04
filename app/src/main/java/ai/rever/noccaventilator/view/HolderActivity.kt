package ai.rever.noccaventilator.view

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.api.patientDetailsGetter
import ai.rever.noccaventilator.model.Patient
import ai.rever.noccaventilator.view.common.BaseActivity
import ai.rever.noccaventilator.view.home.HomeFragment
import android.os.Bundle
import android.view.View
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_holder.*
import java.text.SimpleDateFormat
import java.util.*

class HolderActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holder)

        tvHome.setOnClickListener {
            moveToHome()
        }

        setFragment(HomeFragment(), true)

        patientDetailsGetter
            .subscribe(::setPatientDetails)
            .addTo(compositeDisposable)
    }

    override fun onResume() {
        super.onResume()
        setDate()
    }

    fun showHomeButton(show: Boolean = true) {
        tvHome.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    fun setTitleText(title: String) {
        tvTitle.text = title
    }

    private fun setPatientDetails(patient: Patient) {
        tvPatientName.text = patient.name
        tvPatientID.text = getString(R.string.patient_id__, patient.id)
    }

    private fun setDate() {
        SimpleDateFormat("EEEE dd-MM-yyyy", Locale.ENGLISH).apply {
            tvDate.text = format(Date())
        }
    }
}