package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

val ctcChangeGetter get() = UsbServiceManager.ctcChangeFlowable
    .observeOn(AndroidSchedulers.mainThread())