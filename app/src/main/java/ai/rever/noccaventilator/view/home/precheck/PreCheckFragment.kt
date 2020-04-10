package ai.rever.noccaventilator.view.home.precheck

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.api.*
import ai.rever.noccaventilator.view.common.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.fragment_pre_check.*

class PreCheckFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_pre_check, container, false)
    }

    override val title: String
        get() = "Pre-use Check"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPreCheck

        inletPumpTestObservable
            .subscribe(::onInletPumpTestComplete)
            .addTo(compositeDisposable)

        outletPumpTestObservable
            .subscribe(::onOutletPumpTestComplete)
            .addTo(compositeDisposable)

        onLeadTubeTestObservable
            .subscribe(::onIVLeadTubeTestComplete)
            .addTo(compositeDisposable)

        onBuzzerTestObservable
            .subscribe(::onBuzzerTestComplete)
            .addTo(compositeDisposable)

        onPressureTestObservable
            .subscribe(::onPressureTestComplete)
            .addTo(compositeDisposable)
    }

    private fun onInletPumpTestComplete(passed: Boolean) = runOnActive {
        pbInletPumpTest.visibility = View.INVISIBLE
        ivInletPumpTest.visibility = View.VISIBLE
        if (!passed) {
            ivInletPumpTest.setImageResource(R.drawable.ic_failed)
        }
    }

    private fun onOutletPumpTestComplete(passed: Boolean) = runOnActive {
        pbOutletPumpTest.visibility = View.INVISIBLE
        ivOutletPumpTest.visibility = View.VISIBLE
        if (!passed) {
            ivOutletPumpTest.setImageResource(R.drawable.ic_failed)
        }
    }

    private fun onIVLeadTubeTestComplete(passed: Boolean) = runOnActive {
        pbLeadTubeTest.visibility = View.INVISIBLE
        ivLeadTubeTest.visibility = View.VISIBLE
        if (!passed) {
            ivLeadTubeTest.setImageResource(R.drawable.ic_failed)
        }
    }

    private fun onBuzzerTestComplete(passed: Boolean) = runOnActive {
        pbBuzzerTest.visibility = View.INVISIBLE
        ivBuzzerTest.visibility = View.VISIBLE
        if (!passed) {
            ivBuzzerTest.setImageResource(R.drawable.ic_failed)
        }
    }

    private fun onPressureTestComplete(passed: Boolean) = runOnActive {
        pbPressureTest.visibility = View.INVISIBLE
        ivPressureTest.visibility = View.VISIBLE
        if (!passed) {
            ivPressureTest.setImageResource(R.drawable.ic_failed)
        }
    }
}