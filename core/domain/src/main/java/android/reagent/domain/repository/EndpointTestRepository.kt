package android.reagent.domain.repository

import android.reagent.domain.model.EndpointTestResult
import kotlinx.coroutines.flow.Flow

interface EndpointTestRepository {

    fun observeResults(): Flow<List<EndpointTestResult>>

    suspend fun testEndpoint(
        url: String,
        method: String
    ): EndpointTestResult

    suspend fun deleteAllResults()
}