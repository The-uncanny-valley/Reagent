package android.reagent.database.dao

import android.reagent.database.entity.EndpointTestResultEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EndpointTestResultDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(result: EndpointTestResultEntity): Long

    @Query(
        """
        SELECT *
        FROM endpoint_test_results
        ORDER BY startedAt DESC
        """
    )
    fun observeAll(): Flow<List<EndpointTestResultEntity>>

    @Query(
        """
        SELECT *
        FROM endpoint_test_results
        WHERE id = :id
        LIMIT 1
        """
    )
    suspend fun getById(
        id: Long,
    ): EndpointTestResultEntity?

    @Query(
        """
        SELECT *
        FROM endpoint_test_results
        WHERE url = :url
        ORDER BY startedAt DESC
        """
    )
    fun observeForUrl(
        url: String,
    ): Flow<List<EndpointTestResultEntity>>

    @Query(
        """
        DELETE FROM endpoint_test_results
        WHERE id = :id
        """
    )
    suspend fun deleteById(
        id: Long,
    )

    @Query(
        """
        DELETE FROM endpoint_test_results
        """
    )
    suspend fun deleteAll()
}