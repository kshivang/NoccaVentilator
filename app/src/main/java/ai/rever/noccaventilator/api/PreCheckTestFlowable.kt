package ai.rever.noccaventilator.api

import android.os.Handler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

val randomDelay: Long get() {
    return (2000..10000).random().toLong()
}

val randomTestResult: Boolean get() {
    return randomDelay.toInt() % 4 != 0
}

fun dummyTest(completion: (Boolean) -> Unit) {
    Handler().postDelayed({
        completion(randomTestResult)
    }, randomDelay)

}

val inletPumpTestObservable get() = signalFlowable
    .filter { it == "ik" && it == "in" }
    .map { it == "ik" }


val outletPumpTestObservable get() = signalFlowable
    .filter { it == "ok" && it == "on" }
    .map { it == "ok" }

val onLeadTubeTestObservable get() = signalFlowable
    .filter { it == "lk" && it == "ln" }
    .map { it == "lk" }

val onBuzzerTestObservable get() = signalFlowable
    .filter { it == "bk" && it == "bn" }
    .map { it == "bk" }

val onPressureTestObservable get() = signalFlowable
    .filter { it == "tk" && it == "tn" }
    .map { it == "tk" }
