package ai.rever.noccaventilator.view.common

import ai.rever.noccaventilator.R
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.toast

open class BaseActivity: AppCompatActivity() {

    companion object {
        private const val NON_HOME_BACK_STACK = "non_home_back_stack"
    }

    open fun superOnBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            /**
             *  If you just want to move out of app use
             *  moveTaskToBack(false)
             *  other wise nothing will happen
             **/
            toast("You can't leave ventilator app!")
        } else {
            super.onBackPressed()
        }
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
                as? BaseFragment
        // null or false
        if (currentFragment?.handleOnBackPressed() != true) {
            superOnBackPressed()
        }
    }

    fun moveToHome() {
        supportFragmentManager.popBackStack(NON_HOME_BACK_STACK,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun setFragment(fragment: Fragment, isHomeFragment: Boolean = false) {
        supportFragmentManager
            .beginTransaction()
            .apply {
                replace(R.id.frame_layout, fragment)
                if (!isHomeFragment) addToBackStack(NON_HOME_BACK_STACK)
                commit()
            }

    }

    val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}