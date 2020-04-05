package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.model.VentilatorAlarm
import io.reactivex.rxjava3.core.Observable

val ventilatorAlarmGetter: Observable<VentilatorAlarm>
    get() = Observable.create {
    it.onNext(VentilatorAlarm())
}
