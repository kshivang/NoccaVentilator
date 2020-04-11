package ai.rever.noccaventilator.room

import ai.rever.noccaventilator.model.PatientData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface PatientDataDao {
    @Query("SELECT * FROM patient_data ORDER BY millisValue DESC")
    fun getAllData(): Flowable<List<PatientData>>

    @Query("SELECT * FROM patient_data WHERE millisValue > :last ORDER BY millisValue DESC")
    fun getLastDataFor(last: Long): Flowable<List<PatientData>>

    @Query("SELECT * FROM patient_data ORDER BY millisValue DESC LIMIT 1")
    fun lastData(): Flowable<List<PatientData>>

    @Insert
    fun add(patientData: PatientData): Completable

    @Query("DELETE FROM patient_data")
    fun removeAll(): Completable

}