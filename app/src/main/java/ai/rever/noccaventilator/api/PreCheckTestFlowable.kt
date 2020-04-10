package ai.rever.noccaventilator.api

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable

val inletPumpTestObservable: @NonNull Flowable<Boolean>
    get() = signalFlowable("ik", "in")
        .map { it == "ik" }

val outletPumpTestObservable: @NonNull Flowable<Boolean>
    get() = signalFlowable("ok", "on")
        .map { it == "ok" }

val onLeadTubeTestObservable: @NonNull Flowable<Boolean>
    get() = signalFlowable("lk", "ln")
        .map { it == "lk" }

val onBuzzerTestObservable: @NonNull Flowable<Boolean>
    get() = signalFlowable("bk", "bn")
        .map { it == "bk" }

val onPressureTestObservable: @NonNull Flowable<Boolean>
    get() = signalFlowable("tk", "tn")
        .map { it == "tk" }
