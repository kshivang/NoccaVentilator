package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.model.BottomStatus
import io.reactivex.rxjava3.core.Observable

val bottomStatusObservable: Observable<BottomStatus>
    get() = Observable.create {
    it.onNext(BottomStatus())
}