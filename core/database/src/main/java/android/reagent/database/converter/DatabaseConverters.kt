package android.reagent.database.converter

import android.reagent.domain.ErrorPhase
import android.reagent.domain.ErrorSource
import android.reagent.domain.NetworkTransport
import android.reagent.domain.RequestErrorType
import android.reagent.domain.RequestOutcome
import androidx.room.TypeConverter

class DatabaseConverters {

    @TypeConverter
    fun requestOutcomeToString(value: RequestOutcome?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToRequestOutcome(value: String?): RequestOutcome? {
        return value.toEnumOrNull()
    }

    @TypeConverter
    fun requestErrorTypeToString(value: RequestErrorType?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToRequestErrorType(value: String?): RequestErrorType? {
        return value.toEnumOrNull()
    }

    @TypeConverter
    fun errorSourceToString(value: ErrorSource?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToErrorSource(value: String?): ErrorSource? {
        return value.toEnumOrNull()
    }

    @TypeConverter
    fun errorPhaseToString(value: ErrorPhase?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToErrorPhase(value: String?): ErrorPhase? {
        return value.toEnumOrNull()
    }

    @TypeConverter
    fun networkTransportToString(value: NetworkTransport?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToNetworkTransport(value: String?): NetworkTransport? {
        return value.toEnumOrNull()
    }
}

private inline fun <reified T : Enum<T>> String?.toEnumOrNull(): T? {
    return this?.let { storedValue ->
        enumValues<T>().firstOrNull { enumValue ->
            enumValue.name == storedValue
        }
    }
}