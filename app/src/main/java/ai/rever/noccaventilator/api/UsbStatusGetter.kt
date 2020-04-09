package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

val usbStatusGetter get() = UsbServiceManager.usbStatusFlowable
    .observeOn(AndroidSchedulers.mainThread())


