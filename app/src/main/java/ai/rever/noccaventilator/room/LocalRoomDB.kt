package ai.rever.noccaventilator.room

import ai.rever.noccaventilator.model.PatientData
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

var localRoomDb : LocalRoomDB? = null

@Database(entities = [PatientData::class], version = 1, exportSchema = false)
abstract class LocalRoomDB : RoomDatabase() {

    abstract fun patientDataDao(): PatientDataDao

    companion object {

        fun initialize(context: Context) {
            if (localRoomDb == null) {
                synchronized(LocalRoomDB::class) {
                    localRoomDb = Room.databaseBuilder(context.applicationContext,
                        LocalRoomDB::class.java, "track_database.db")
                        .build()
                }
            }
        }

        fun destroy() {
            localRoomDb = null
        }
    }
}