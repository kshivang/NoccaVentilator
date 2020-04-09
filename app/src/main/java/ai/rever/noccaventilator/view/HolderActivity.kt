package ai.rever.noccaventilator.view

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.api.patientDetailsGetter
import ai.rever.noccaventilator.model.BottomStatus
import ai.rever.noccaventilator.model.Patient
import ai.rever.noccaventilator.view.common.BaseActivity
import ai.rever.noccaventilator.view.home.DebugFragment
import ai.rever.noccaventilator.view.home.HomeFragment
import android.os.Bundle
import android.view.View
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.activity_holder.*
import kotlinx.android.synthetic.main.layout_bottom_status.*
import java.text.SimpleDateFormat
import java.util.*

class HolderActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holder)

        btHome.setOnClickListener {
            moveToHome()
        }

        ivLogo.setOnClickListener {
            setFragment(DebugFragment())
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
        btHome.visibility = if (show) View.VISIBLE else View.INVISIBLE
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

    fun showBottomStatus(show: Boolean) {
        lBottomStatus.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    fun setBottomNavButton(startText: String, click: View.OnClickListener) {
        btStart.text = startText
        btStart.setOnClickListener(click)
    }

    fun setBottomStatus(data: BottomStatus) {
        tvPIP.text = "${data.pip}"
        tvPEEP.text = "${data.peep}"
        tvRR.text = "${data.rr}"
        tvIE.text = "${data.ie}"
    }
}