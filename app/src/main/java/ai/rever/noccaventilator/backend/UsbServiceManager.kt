package ai.rever.noccaventilator.backend

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import org.jetbrains.anko.intentFor
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList

object UsbServiceManager {

    /**
     * call from onCreate/onResume
     */
    fun Activity.usbServiceRegister() {
        if (!UsbService.SERVICE_CONNECTED) {
            startService(intentFor<UsbService>())
        }
        bindService(intentFor<UsbService>(), usbConnection, Context.BIND_AUTO_CREATE)
    }

    /**
     * call from onDestroy/onPause
     */
    fun Activity.usbServiceUnregister() {
        unbindService(usbConnection)
    }

    /**
     * API start
     */
    val messageFlowable: Flowable<String>
        get() = UsbServiceInternalManager
            .messageFromSerialPortSubject
            .toFlowable(BackpressureStrategy.LATEST)

    val usbStatusFlowable: Flowable<UsbStatus>
        get() = UsbServiceInternalManager
            .usbStatusBehaviorSubject
            .toFlowable(BackpressureStrategy.LATEST)

    val ctcChangeFlowable: Flowable<Boolean>
        get() = UsbServiceInternalManager
            .ctcChangeSubject
            .toFlowable(BackpressureStrategy.LATEST)

    val dsrChangeFlowable: Flowable<Boolean>
        get() = UsbServiceInternalManager
            .dsrChangeSubject
            .toFlowable(BackpressureStrategy.LATEST)

    fun write(string: String) = run {
        write(string.toByteArray())
    }
    /**
     * Api end
     */


    /**
     * No need to understand below code
     */
    private var holderUsbServiceInterface:
            WeakReference<UsbServiceInterface?>? = null

    var usbServiceInterface
        get() = holderUsbServiceInterface?.get()
        set(value) {
            holderUsbServiceInterface = WeakReference(value)
            runOnSet()
        }

    private val usbConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(arg0: ComponentName, arg1: IBinder) {
            (arg1 as? UsbService.UsbBinder)?.usbServiceInterface?.let {
                usbServiceInterface = it
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            usbServiceInterface = null
        }
    }

    private var runs = ArrayList<UsbServiceInterface.() -> Unit>()

    private fun runOnSet() {
        val currentRuns = ArrayList(runs)
        runs.clear()
        run {
            currentRuns.forEach { it() }
        }
    }

    private fun run(run: UsbServiceInterface.() -> Unit) {
        if (usbServiceInterface != null) {
            usbServiceInterface?.apply { run() }
        } else {
            runs.add(run)
        }
    }
}