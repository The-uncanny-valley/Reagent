package android.reagent.database

import android.reagent.database.converter.DatabaseConverters
import android.reagent.database.dao.EndpointTestResultDao
import android.reagent.database.entity.EndpointTestResultEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        EndpointTestResultEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DatabaseConverters::class)
abstract class ReagentDatabase : RoomDatabase() {

    abstract fun endpointTestResultDao(): EndpointTestResultDao
}