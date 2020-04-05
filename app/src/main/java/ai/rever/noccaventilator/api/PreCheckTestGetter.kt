package ai.rever.noccaventilator.api

import android.os.Handler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
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

val inletPumpTestObservable get() = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
    it.setCancellable {  }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}


val outletPumpTestObservable get() = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}

val onLeadTubeTestObservable get() = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}

val onBuzzerTestObservable get() = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}

val onPressureTestObservable get() = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}
