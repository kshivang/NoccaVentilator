package ai.rever.noccaventilator.api


val pHighAlarmFlowable
    get() = signalFlowable("v")

val pLowAlarmFlowable
    get() = signalFlowable("w")

val vtHighAlarmFlowable
    get() = signalFlowable("x")

val vtLowAlarmFlowable
    get() = signalFlowable("y")

val rrHighAlarmFlowable
    get() = signalFlowable("z")

val rrLowAlarmFlowable
    get() = signalFlowable("@")

