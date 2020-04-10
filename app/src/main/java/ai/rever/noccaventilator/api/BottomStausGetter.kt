package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.model.BottomStatus
import io.reactivex.rxjava3.kotlin.Flowables

val bottomStatusFlowable get() = run {
    Flowables.combineLatest(pipFlowable, peepFlowable, rrFlowable, ieFlowable
    ) { pip, peep, rr, ie -> BottomStatus(pip, peep, rr, ie) }
}

private val pipFlowable get() = intSignalFlowable("d")
    .startWithItem(IDLE_SIGNAL)

private val peepFlowable get() = intSignalFlowable("e")
    .startWithItem(IDLE_SIGNAL)

private val rrFlowable get() = intSignalFlowable("f")
    .startWithItem(IDLE_SIGNAL)

private val ieFlowable get() = intSignalFlowable("g")
    .startWithItem(IDLE_SIGNAL)
