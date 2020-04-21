package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.model.VentilatorAlarm
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.kotlin.Flowables
import java.util.concurrent.CompletionStage

val alarmDataFlowable: @NonNull CompletionStage<VentilatorAlarm>
    get() = Flowables.combineLatest(alarmPHighFlowable, alarmPLowFlowable,
    alarmVTHighFlowable, alarmVTLowFlowable, alarmRRHighFlowable, alarmRRLowFlowable
) { pHigh, pLow, vTHHigh, vTHLow, rrHigh, rrLow ->
        requestStopAlarmData()
        VentilatorAlarm(pHigh, pLow, vTHHigh, vTHLow, rrHigh, rrLow)
    }.firstOrErrorStage()

private val alarmPHighFlowable get() = intSignalFlowable("c")
    .startWithItem(IDLE_SIGNAL)

private val alarmPLowFlowable get() = intSignalFlowable("q")
    .startWithItem(IDLE_SIGNAL)

private val alarmVTHighFlowable get() = intSignalFlowable("r")
    .startWithItem(IDLE_SIGNAL)

private val alarmVTLowFlowable get() = intSignalFlowable("s")
    .startWithItem(IDLE_SIGNAL)

private val alarmRRHighFlowable get() = intSignalFlowable("u")
    .startWithItem(IDLE_SIGNAL)

private val alarmRRLowFlowable get() = intSignalFlowable("?")
    .startWithItem(IDLE_SIGNAL)
