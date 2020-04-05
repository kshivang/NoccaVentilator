package ai.rever.noccaventilator.view.home.ventilator_mode.ventilator_mode_details

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.view.common.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class VentilatorGraphFragment(override val title: String) : BaseFragment() {

    private val holderFragment get() = parentFragment as? VentilatorDetailsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_ventilator_graph, container, false)
    }

    override val showBottomStatus: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        holderActivity?.setBottomNavButton(getString(R.string.set_alarm), View.OnClickListener {
            holderFragment?.setChildFragment(VentilatorAlarmFragment(title))
        })
    }

}