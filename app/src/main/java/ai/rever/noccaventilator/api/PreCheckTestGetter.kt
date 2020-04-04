package ai.rever.noccaventilator.api

import android.os.Handler
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

var inletPumpTestObservable = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}


var outletPumpTestObservable = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}

var onLeadTubeTestObservable = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}

var onBuzzerTestObservable = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}

var onPressureTestObservable = Observable.create<Boolean> {
    dummyTest { result ->
        it.onNext(result)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}
