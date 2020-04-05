package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.model.Patient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

val patientDetailsGetter: Observable<Patient>
    get() = Observable.create<Patient> {
    it.onNext(Patient())
}.apply {
    observeOn(AndroidSchedulers.mainThread())
}
