package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import ai.rever.noccaventilator.backend.UsbStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable

val usbStatusGetter: @NonNull Flowable<UsbStatus>
    get() = UsbServiceManager.usbStatusFlowable
    .observeOn(AndroidSchedulers.mainThread())

