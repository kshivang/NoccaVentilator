package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.model.BottomStatus
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.Flowables

val bottomStatusFlowable get() = run {
    Flowables.combineLatest(pipFlowable, peepFlowable, rrFlowable, ieFlowable
    ) { pip, peep, rr, ie -> BottomStatus(pip, peep, rr, ie) }
}

private val pipFlowable get() = valueSignalFlowable("d")
    .startWithItem(IDLE_SIGNAL)

private val peepFlowable get() = valueSignalFlowable("e")
    .startWithItem(IDLE_SIGNAL)

private val rrFlowable get() = valueSignalFlowable("f")
    .startWithItem(IDLE_SIGNAL)

private val ieFlowable get() = valueSignalFlowable("g")
    .startWithItem(IDLE_SIGNAL)
