package ai.rever.noccaventilator.view.common

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.receiver.AdminReceiver
import android.accounts.AccountManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log.e
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast


open class BaseActivity: AppCompatActivity() {

    companion object {
        private const val NON_HOME_BACK_STACK = "non_home_back_stack"
        private const val TAG = "holder_activity"
        private const val REQUEST_CODE = 1
    }

    private val dpm: DevicePolicyManager? get() {
        return applicationContext.getSystemService(Context.DEVICE_POLICY_SERVICE)
                as? DevicePolicyManager
    }
    private val adminName: ComponentName by lazy {
        ComponentName(this, AdminReceiver::class.java)
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

    private val flags: Int = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

    private fun fullScreen() {
        window.decorView.systemUiVisibility = flags
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLockPackage()
        fullScreen()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            fullScreen()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                e(TAG, "made admin!")
            } else {
                e(TAG, "not made admin")
            }

            dpm?.apply {
                if (isDeviceOwnerApp(packageName)) {
                    setLockTaskPackages(adminName, arrayOf(packageName));
                }
                requestLockTask(true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun DevicePolicyManager.requestLockTask(force: Boolean = false) {
        val isPermitted = isLockTaskPermitted(packageName)
        if (isPermitted || force) {
            startLockTask()
            if (!isPermitted && force) {
                e(TAG, "task lock not permitted therefore pinned")
            } else {
                toast("task locked!")
                e(TAG, "task locked!")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requestLockPackage()
    }

    private fun requestLockPackage() {
        dpm?.apply {
            if (isDeviceOwnerApp(packageName)) {
                setLockTaskPackages(adminName, arrayOf(packageName));
                requestLockTask()
            } else {
                val accounts = AccountManager.get(this@BaseActivity).accounts
                if (accounts.isEmpty()) {
                    // find out if we are a device administrator
                    if (isAdminActive(adminName)) {
                        // become the device owner
                        toast("Nocca Ventilator is device admin now!")
                        try {
                            e(TAG, "running command!")
                            Runtime.getRuntime()
                                .exec("dpm set-device-owner ${adminName.flattenToString()}")
                            if (isDeviceOwnerApp(packageName)) {
                                setLockTaskPackages(adminName, arrayOf(packageName));
                            }
                            requestLockTask(true)
                        } catch (exp: Exception) {
                            e(TAG, "exception: $exp")
                        }
                    } else {
                        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminName)
                        intent.putExtra(
                            DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            getString(R.string.grant_admin)
                        )
                        startActivity(intent)
                    }
                } else {
                    accounts.forEach {
                        e(TAG, it.name)
                    }
                    longToast("Nocca Ventilator cannot become device owner on this device, " +
                            "account: ${accounts.first().name} is admin!")
                    requestLockTask(true)
                }
            }
        }
    }
}