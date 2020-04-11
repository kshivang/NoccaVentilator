package ai.rever.noccaventilator.view.home.ventilator_mode

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.api.requestCPAP
import ai.rever.noccaventilator.api.requestPC_AC
import ai.rever.noccaventilator.api.requestPC_CMV
import ai.rever.noccaventilator.view.common.BaseFragment
import ai.rever.noccaventilator.view.home.ventilator_mode.ventilator_mode_details.VentilatorDetailsFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.fragment_ventilator_mode.*

class VentilatorModeFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_ventilator_mode, container, false)
    }

    override val title: String
        get() = "Ventilation Mode"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btPC_CVM.setOnClickListener {
            requestPC_CMV.subscribe { runOnActive {
                setFragment(VentilatorDetailsFragment("PC-CMC"))
            } }.addTo(compositeDisposable)
        }

        btPC_AC.setOnClickListener {
            requestPC_AC.subscribe { runOnActive {
                setFragment(VentilatorDetailsFragment("PC-AC"))
            } }.addTo(compositeDisposable)
        }

        btCPAP.setOnClickListener {
            requestCPAP.subscribe { runOnActive {
                setFragment(VentilatorDetailsFragment("CPAP"))
            } }.addTo(compositeDisposable)
        }
    }
}