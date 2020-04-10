package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable

val dsrChangeGetter: @NonNull Flowable<Boolean>
    get() = UsbServiceManager.dsrChangeFlowable
        .observeOn(AndroidSchedulers.mainThread())