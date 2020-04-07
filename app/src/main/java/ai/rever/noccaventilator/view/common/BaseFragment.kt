package ai.rever.noccaventilator.view.common

import ai.rever.noccaventilator.api.bottomStatusObservable
import ai.rever.noccaventilator.view.HolderActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import org.jetbrains.anko.toast

abstract class BaseFragment: Fragment() {

    abstract val title: String
    open val showBottomStatus: Boolean = false

    val holderActivity get() = activity as? HolderActivity

    fun onBackPressed() {
        holderActivity?.onBackPressed()
    }

    private var _isFragmentActive = false
    var isFragmentActive get() = _isFragmentActive
        private set(value) {
            _isFragmentActive = value
            runOnActive()
        }

    private val runs = ArrayList<() -> Unit>()

    fun setFragment(fragment: Fragment) {
        holderActivity?.setFragment(fragment)
    }


    fun toast(message: String) {
        holderActivity?.toast(message)
    }

    /**
     * @return Will show whether this function handled onBack pressed or not
     * e.g. if true then activity will not handle back press
     */
    open fun handleOnBackPressed() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (showBottomStatus) {
            bottomStatusObservable.subscribe {
                runOnActive {
                    holderActivity?.setBottomStatus(it)
                }
            }.addTo(compositeDisposable)
        }
    }

    override fun onResume() {
        super.onResume()
        isFragmentActive = true
        holderActivity?.setTitleText(title)
        holderActivity?.showBottomStatus(showBottomStatus)
    }

    var compositeDisposable =  CompositeDisposable()

    override fun onPause() {
        super.onPause()
        isFragmentActive = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    private fun runOnActive() {
        if (isFragmentActive) {
            runs.forEach { it() }
            runs.clear()
        }
    }

    fun runOnActive(run: () -> Unit) {
        if (isFragmentActive) {
            run()
        } else {
            runs.add(run)
        }
    }
}