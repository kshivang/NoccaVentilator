package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.model.BottomStatus
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.Flowables

val bottomStatusFlowable = run {
    Flowables.combineLatest(pipFlowable, peepFlowable, rrFlowable, ieFlowable
    ) { pip, peep, rr, ie -> BottomStatus(pip, peep, rr, ie) }
}

private val pipFlowable = valueSignalFlowable("d")
    .startWithItem(IDLE_SIGNAL)

private val peepFlowable = valueSignalFlowable("e")
    .startWithItem(IDLE_SIGNAL)

private var rrFlowable = valueSignalFlowable("f")
    .startWithItem(IDLE_SIGNAL)

private val ieFlowable = valueSignalFlowable("g")
    .startWithItem(IDLE_SIGNAL)
