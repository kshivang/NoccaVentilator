package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.model.Patient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

var patientDetailsGetter: Observable<Patient> = Observable.create<Patient> {
    it.onNext(Patient())
}.apply {
    observeOn(AndroidSchedulers.mainThread())
}