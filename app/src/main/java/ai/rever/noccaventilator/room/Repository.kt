package ai.rever.noccaventilator.room

import ai.rever.noccaventilator.model.PatientData
import android.util.Log.e
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

fun addPatient(patientName: String, patientID: String) = run {
    e("*****", "patient added")
    localRoomDb?.patientDataDao()
        ?.add(PatientData(patientID, patientName))
        ?.`as`(RxJavaBridge.toV3Completable())
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
}

val lastPatientFlowable
    get() = localRoomDb?.patientDataDao()
        ?.lastData()
        ?.map { if (it.isEmpty()) PatientData() else it[0] }
        ?.`as`(RxJavaBridge.toV3Flowable())
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())

val galAllDataFlowable
    get() = localRoomDb?.patientDataDao()
        ?.getAllData()
        ?.`as`(RxJavaBridge.toV3Flowable())
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())

val getLastData4DaysFlowable
    get() = localRoomDb?.patientDataDao()?.run {
        Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, -4)
        }.time.time.let {
            getLastDataFor(it)
        }
    }?.`as`(RxJavaBridge.toV3Flowable())
    ?.subscribeOn(Schedulers.io())
    ?.observeOn(AndroidSchedulers.mainThread())

val clearAll get() = localRoomDb?.patientDataDao()
    ?.removeAll()
    ?.`as`(RxJavaBridge.toV3Completable())
    ?.subscribeOn(Schedulers.io())
    ?.observeOn(AndroidSchedulers.mainThread())

