package ai.rever.noccaventilator.view.home.ventilator_mode.ventilator_mode_details

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.view.common.BaseFragment
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_ventilator_details.*

class VentilatorDetailsFragment(override val title: String) : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_ventilator_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChildFragment(VentilatorGraphFragment(title))
        setLabel()
    }

    private fun setLabel() {
        SpannableString("PIP cmH20").run {
            setSpan(RelativeSizeSpan(2f), 0,3, 0)
            tvPIPLabel.text = this
        }

        SpannableString("PEEP cmH20").run {
            setSpan(RelativeSizeSpan(2f), 0,4, 0)
            tvPEEPLabel.text = this
        }


        SpannableString("VTe ml").run {
            setSpan(RelativeSizeSpan(2f), 0,3, 0)
            tvVTeLabel.text = this
        }

        SpannableString("RR / min").run {
            setSpan(RelativeSizeSpan(2f), 0,2, 0)
            tvRRLabel.text = this
        }

    }

    fun setChildFragment(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    override val showBottomStatus: Boolean
        get() = true
}