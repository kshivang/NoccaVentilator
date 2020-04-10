package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import ai.rever.noccaventilator.model.VentilatorAlarm

private fun onCommand(command: String) {
    UsbServiceManager.onCommand(command)
}

private fun onCommand(command: String, vararg confirmationCommand: String) = run {
    onCommand(command)
    signalFlowable(*confirmationCommand)
        .firstOrErrorStage()
}

val requestPreCheck get() = onCommand("c")

val requestPC_CMV get() = onCommand("m", "mk")

val requestPC_AC get() = onCommand("a", "ak")

val requestCPAP get() = onCommand("p", "pk")

val requestSetAlarm get() = onCommand("j", "jk")

fun requestStart(alarmData: VentilatorAlarm) =
    alarmData.run {
        onCommand(
            "h${pHigh}n${pLow}q${vTelHigh}r${vTelLow}s${rrHigh}u${rrLow}",
            "hk"
        )
    }

