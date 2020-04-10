package ai.rever.noccaventilator.api

val inletPumpTestObservable get() = signalFlowable
    .filter { it == "ik" && it == "in" }
    .map { it == "ik" }

val outletPumpTestObservable get() = signalFlowable
    .filter { it == "ok" && it == "on" }
    .map { it == "ok" }

val onLeadTubeTestObservable get() = signalFlowable
    .filter { it == "lk" && it == "ln" }
    .map { it == "lk" }

val onBuzzerTestObservable get() = signalFlowable
    .filter { it == "bk" && it == "bn" }
    .map { it == "bk" }

val onPressureTestObservable get() = signalFlowable
    .filter { it == "tk" && it == "tn" }
    .map { it == "tk" }
