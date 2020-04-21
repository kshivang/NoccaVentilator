package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import ai.rever.noccaventilator.model.VentilatorAlarm
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

const val KEEP_COMMANDING_EVERY_MS: Long = 500

private fun onCommand(command: String, repeatCount: Int = 1) {
    (0 until repeatCount)
        .map { "*$command#" }
        .reduce { acc, next -> acc + next }
        .let { UsbServiceManager.onCommand(it) }
}

private fun onCommand(command: String,  vararg confirmationCommand: String) = run {
    Completable.create { completable ->
        val compositeDisposable = CompositeDisposable()
        Observable.interval(KEEP_COMMANDING_EVERY_MS, TimeUnit.MILLISECONDS)
            .subscribe {
                onCommand(command)
            }.addTo(compositeDisposable)

        signalFlowable(*confirmationCommand)
            .firstElement().subscribe {
                completable.onComplete()
            }.addTo(compositeDisposable)

        completable.setDisposable(compositeDisposable)
    }
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}

val requestPreCheck: @NonNull Completable
    get() = onCommand("c",
    "ik", "ok", "lk", "bk", "tk", "in", "on", "ln", "bn", "tn" )

val requestPC_CMV: @NonNull Completable get() = onCommand("m", "mk")

val requestPC_AC: @NonNull Completable get() = onCommand("a", "ak")

val requestCPAP: @NonNull Completable get() = onCommand("p", "pk")

val requestStopGraphData: @NonNull Completable get() = onCommand("j", "jk")

fun requestStopAlarmData() = onCommand("b")

fun requestHome() = onCommand("&", 10)

fun requestStart(alarmData: VentilatorAlarm): @NonNull Completable =
    alarmData.run {
        onCommand(
            "h${pHigh}#*n${pLow}#*q${vTelHigh}#*r${vTelLow}#*s${rrHigh}#*u${rrLow}",
            "hk", "P"
        )
    }

