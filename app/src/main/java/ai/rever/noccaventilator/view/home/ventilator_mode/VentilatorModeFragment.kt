package ai.rever.noccaventilator.view.home.ventilator_mode

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.view.common.BaseFragment
import ai.rever.noccaventilator.view.home.ventilator_mode.ventilator_mode_details.VentilatorDetailsFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            setFragment(VentilatorDetailsFragment("PC-CVM"))
        }

        btPC_AC.setOnClickListener {
            setFragment(VentilatorDetailsFragment("PC-AC"))
        }

        btCPAP.setOnClickListener {
            setFragment(VentilatorDetailsFragment("CPAP"))
        }
    }
}