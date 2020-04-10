package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception

private fun parsedSignalFlowable(startDelimiter: String, endDelimiter: String)
     = UsbServiceManager.messageObservable
        .lift(DetectSignalOperator(startDelimiter, endDelimiter))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .toFlowable(BackpressureStrategy.LATEST)


val signalFlowable get() = parsedSignalFlowable("*", "#")

fun signalFlowable(vararg signals: String) = run {
    signalFlowable.filter {
        signals.any { signal -> it == signal }
    }
}

fun intSignalFlowable(signal: String) = run {
    signalFlowable
        .filter { it.contains(signal) }
        .map {
            it.substring(signal.length).toIntOrNull() ?: IDLE_SIGNAL
        }
}

const val IDLE_SIGNAL = -1

fun floatSignalFlowable(signal: String) = run {
    signalFlowable
        .filter { it.contains(signal) }
        .map {
            it.substring(signal.length).toFloatOrNull() ?: IDLE_SIGNAL.toFloat()
        }
}

