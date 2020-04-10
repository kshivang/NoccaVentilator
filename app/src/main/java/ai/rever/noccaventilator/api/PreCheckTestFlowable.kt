package ai.rever.noccaventilator.api

val inletPumpTestObservable get() = signalFlowable("ik", "in")
    .map { it == "ik" }

val outletPumpTestObservable get() = signalFlowable("ok", "on")
    .map { it == "ok" }

val onLeadTubeTestObservable get() = signalFlowable("lk", "ln")
    .map { it == "lk" }

val onBuzzerTestObservable get() = signalFlowable("bk", "bn")
    .map { it == "bk" }

val onPressureTestObservable get() = signalFlowable("tk", "tn")
    .map { it == "tk" }
