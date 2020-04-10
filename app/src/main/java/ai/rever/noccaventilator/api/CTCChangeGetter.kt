package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable

val ctcChangeGetter: @NonNull Flowable<Boolean>
    get() = UsbServiceManager.ctcChangeFlowable
        .observeOn(AndroidSchedulers.mainThread())