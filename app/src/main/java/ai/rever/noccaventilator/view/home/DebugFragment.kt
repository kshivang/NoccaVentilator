package ai.rever.noccaventilator.view.home

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.api.signalFlowable
import ai.rever.noccaventilator.backend.UsbServiceManager
import ai.rever.noccaventilator.view.common.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.fragment_debug.*

class DebugFragment : BaseFragment() {
    override val title: String
        get() = "Debug View"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_debug, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UsbServiceManager.messageObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                runOnActive {
                    tvSignalValue.text = "SignalValue: $it"
                }
            }.addTo(compositeDisposable)

        signalFlowable.subscribe{
            runOnActive {
                tvParsedValue.text = "ParsedValue: $it"
            }
        }
    }
}