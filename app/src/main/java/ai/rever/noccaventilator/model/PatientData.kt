package ai.rever.noccaventilator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient_data")
data class PatientData(@PrimaryKey val id: String = "Not set",
                       val name: String =  "",
                       val dataType: String = changedPatient,
                       val pressureValue: Float? = null,
                       val millisValue: Long = System.currentTimeMillis())

val changedPatient = "changedPatient"
val ptData = "ptData"

