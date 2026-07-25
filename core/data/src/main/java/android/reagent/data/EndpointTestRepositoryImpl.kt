package android.reagent.data

import android.reagent.database.dao.EndpointTestResultDao
import android.reagent.database.mapper.toDomain
import android.reagent.database.mapper.toEntity
import android.reagent.domain.model.EndpointTestResult
import android.reagent.domain.repository.EndpointTestRepository
import android.reagent.network.EndpointRequestExecutor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EndpointTestRepositoryImpl @Inject constructor(
    private val executor: EndpointRequestExecutor,
    private val dao: EndpointTestResultDao
) : EndpointTestRepository {

    override fun observeResults(): Flow<List<EndpointTestResult>> {

        return dao.observeAll()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun testEndpoint(
        url: String,
        method: String
    ): EndpointTestResult {

        val result = executor.execute(url, method)

        dao.insert(result.toEntity())

        return result
    }
}