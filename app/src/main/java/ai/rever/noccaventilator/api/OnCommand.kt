package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import ai.rever.noccaventilator.model.VentilatorAlarm
import io.reactivex.rxjava3.annotations.NonNull
import java.util.concurrent.CompletionStage

private fun onCommand(command: String) {
    UsbServiceManager.onCommand(command)
}

private fun onCommand(command: String, vararg confirmationCommand: String) = run {
    onCommand(command)
    signalFlowable(*confirmationCommand)
        .firstOrErrorStage()
}

val requestPreCheck get() = onCommand("c")

val requestPC_CMV: @NonNull CompletionStage<String> get() = onCommand("m", "mk")

val requestPC_AC: @NonNull CompletionStage<String> get() = onCommand("a", "ak")

val requestCPAP: @NonNull CompletionStage<String> get() = onCommand("p", "pk")

val requestSetAlarm: @NonNull CompletionStage<String> get() = onCommand("j", "jk")

fun requestStart(alarmData: VentilatorAlarm): @NonNull CompletionStage<String> =
    alarmData.run {
        onCommand(
            "h${pHigh}n${pLow}q${vTelHigh}r${vTelLow}s${rrHigh}u${rrLow}",
            "hk"
        )
    }

