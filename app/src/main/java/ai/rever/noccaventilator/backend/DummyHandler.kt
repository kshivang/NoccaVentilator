package ai.rever.noccaventilator.backend

import ai.rever.noccaventilator.api.IDLE_SIGNAL
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

object  DummyHandler {
    val isActive = false

    private val signalSubject get() = UsbServiceInternalManager.messageFromSerialPortSubject

    private fun sendSignal(signal: String) {
        signalSubject.onNext("*$signal#")
    }

    private val randomPressureData: Float get() {
        return ((40000..61000).random() / 100.0).toFloat()
    }

    private var pressureDisposable: Disposable? = null

    private fun emitPressureData() {
        pressureDisposable = Observable.interval(500, TimeUnit.MILLISECONDS)
            .map {
                sendSignal("P$randomPressureData")
                it
            }.subscribe()
    }

    private fun stopPressureData() {
        pressureDisposable?.dispose()
        pressureDisposable = null
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
                sendSignal("jk")
            }
            "b" -> {
                stopPressureData()
            }
            "&" -> {
                stopPressureData()
            }
            "h" -> {
                if (command.length > 1) {
                    val pHigh = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                }
                emitPressureData()
                sendSignal("hk")
            }
            "n" -> {
                if (command.length > 1) {
                    val pLow = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                }
            }
            "q" -> {
                if (command.length > 1) {
                    val vTelHigh = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                }
            }
            "r" -> {
                if (command.length > 1) {
                    val vTelLow = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                }
            }
            "s" -> {
                if (command.length > 1) {
                    val rrHigh = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                }
            }
            "u" -> {
                if (command.length > 1) {
                    val rrLow = command.substring(1).toIntOrNull() ?: IDLE_SIGNAL
                }
            }
        }
    }
}

