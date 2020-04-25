package ai.rever.noccaventilator.backend

import ai.rever.noccaventilator.api.IDLE_SIGNAL
import ai.rever.noccaventilator.model.VentilatorAlarm
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

object Simulator {
    val isActive = false

    private val signalSubject get() = UsbServiceInternalManager.messageFromSerialPortSubject
    private val ventilatorAlarmData = VentilatorAlarm()

    private fun sendSignal(signal: String) {
        signalSubject.onNext("*$signal#")
    }

    private val randomPressureData: Float get() {
        return ((40000..61000).random() / 100.0).toFloat()
    }

    private val randomMS: Long get() {
        return (500L..1500L).random()
    }

    private var emitDisposable: Disposable? = null

    private fun emitPressureData() {
        emitDisposable?.dispose()
        emitDisposable = Observable.interval(randomMS, TimeUnit.MILLISECONDS)
            .map {
                sendSignal("P$randomPressureData")
            }.subscribe()
    }

    private fun stopDataEmission() {
        emitDisposable?.dispose()
        emitDisposable = null
    }

    private fun emitAlarmData() {
        emitDisposable?.dispose()
        emitDisposable = Observable.interval(randomMS, TimeUnit.MILLISECONDS)
            .map {
                sendSignal("c${ventilatorAlarmData.pHigh}#" +
                        "*q${ventilatorAlarmData.pLow}#" +
                        "*r${ventilatorAlarmData.vTelHigh}#" +
                        "*s${ventilatorAlarmData.vTelLow}#" +
                        "*u${ventilatorAlarmData.rrHigh}#" +
                        "*?${ventilatorAlarmData.rrLow}")
            }.subscribe()
    }

    fun onCommand(str: String) {
        var parsing = false
        var parsedString = ""

        str.split("")
            .forEach { currChar ->
                if (currChar == "*") {
                    parsing = true
                } else if (currChar == "#") {
                    parsing = false
                    if (parsedString.isNotEmpty()) {
                        onParsedCommand(parsedString)
                        parsedString = ""
                    }
                } else {
                    if (parsing) {
                        parsedString += currChar
                    }
                }
            }
    }

    private fun onParsedCommand(command: String) {
        when (command.firstOrNull()?.toString()) {
            "c" -> {
                sendSignal("ik")
                sendSignal("ok")
                sendSignal("lk")
                sendSignal("bk")
                sendSignal("tk")
            }
            "m" -> {
                emitPressureData()
                sendSignal("mk")
            }
            "a" -> {
                emitPressureData()
                sendSignal("ak")
            }
            "p" -> {
                emitPressureData()
                sendSignal("pk")
            }
            "j" -> {
                emitAlarmData()
                sendSignal("jk")
            }
            "b" -> {
                stopDataEmission()
            }
            "&" -> {
                stopDataEmission()
            }
            "h" -> {
                if (command.length > 1) {
                    val pHigh = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                    ventilatorAlarmData.pHigh = pHigh
                }
                emitPressureData()
                sendSignal("hk")
            }
            "n" -> {
                if (command.length > 1) {
                    val pLow = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                    ventilatorAlarmData.pLow = pLow
                }
            }
            "q" -> {
                if (command.length > 1) {
                    val vTelHigh = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                    ventilatorAlarmData.vTelHigh = vTelHigh
                }
            }
            "r" -> {
                if (command.length > 1) {
                    val vTelLow = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                    ventilatorAlarmData.vTelLow = vTelLow
                }
            }
            "s" -> {
                if (command.length > 1) {
                    val rrHigh = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                    ventilatorAlarmData.rrHigh = rrHigh
                }
            }
            "u" -> {
                if (command.length > 1) {
                    val rrLow = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                    ventilatorAlarmData.rrLow = rrLow
                }
            }
        }
    }
}

