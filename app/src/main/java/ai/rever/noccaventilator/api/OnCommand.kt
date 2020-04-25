package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import ai.rever.noccaventilator.model.VentilatorAlarm
import io.reactivex.rxjava3.annotations.NonNull
import java.util.concurrent.CompletionStage

//const val KEEP_COMMANDING_EVERY_MS: Long = 500

private fun onCommand(command: String, repeatCount: Int = 1) {
    (0 until repeatCount)
        .map { "*$command#" }
        .reduce { acc, next -> acc + next }
        .let { UsbServiceManager.onCommand(it) }
}

private fun onCommand(command: String, repeatCount: Int, vararg confirmationCommand: String) = run {
    signalFlowable(*confirmationCommand)
        .firstOrErrorStage()
        .apply {
            onCommand(command, repeatCount)
        }
}

//private fun onCommand(command: String,  vararg confirmationCommand: String) = run {
//    Completable.create { completable ->
//        val compositeDisposable = CompositeDisposable()
//        Observable.interval(KEEP_COMMANDING_EVERY_MS, TimeUnit.MILLISECONDS)
//            .subscribe {
//                onCommand(command)
//            }.addTo(compositeDisposable)
//
//        signalFlowable(*confirmationCommand)
//            .firstElement().subscribe {
//                completable.onComplete()
//            }.addTo(compositeDisposable)
//
//        completable.setDisposable(compositeDisposable)
//    }
//}.apply {
//    subscribeOn(Schedulers.io())
//    observeOn(AndroidSchedulers.mainThread())
//}

val requestPreCheck: @NonNull CompletionStage<String>
    get() = onCommand("c", 1,
        "ik", "ok", "lk", "bk", "tk", "in", "on", "ln", "bn", "tn" )

val requestPC_CMV: @NonNull CompletionStage<String>
    get() = onCommand("m",1, "mk")

val requestPC_AC: @NonNull CompletionStage<String>
    get() = onCommand("a", 1, "ak")

val requestCPAP: @NonNull CompletionStage<String>
    get() = onCommand("p", 1, "pk")

val requestAlarmData: @NonNull CompletionStage<String>
    get() = onCommand("j", 1, "jk")

fun requestStopAlarmData() = onCommand("b")

fun requestHome() = onCommand("&", 10)

fun requestStart(alarmData: VentilatorAlarm): @NonNull CompletionStage<String> =
    alarmData.run {
        onCommand(
            "h${pHigh}#*n${pLow}#*q${vTelHigh}#*r${vTelLow}#*s${rrHigh}#*u${rrLow}",
            10, "hk", "P"
        )
    }

