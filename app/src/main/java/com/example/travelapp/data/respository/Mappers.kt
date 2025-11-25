import com.example.travelapp.data.local.entities.DestinationEntity
import com.example.travelapp.data.remote.dto.CountryDto
import com.example.travelapp.domain.model.Destination

// CountryDTO -> Destination
 fun CountryDto.toDomain(): Destination {
    return Destination(
        countryCode = countryCode,
        name = name.common,
        capital = capital?.firstOrNull() ?: "N/A",
        region = region ?: "Unknown",
        flagUrl = flags?.png ?: "N/A",
        population = population ?: 0L,
        mapUrl = maps?.googleMaps ?: "N/A",
        isSaved = false
    )
}

// Destination -> DestinationEntity (saving to DB)
fun Destination.toEntity() : DestinationEntity {
    return DestinationEntity(
        id = this.countryCode,
        name = this.name,
        capital = this.capital,
        region = this.region,
        flagUrl = this.flagUrl,
        population = this.population,
        mapUrl = this.mapUrl,
        savedTimestamp = System.currentTimeMillis()

    )
}

// DestinationEntity -> Destination (Reading from DB)
fun DestinationEntity.toDomain() : Destination {
    return Destination(
        countryCode = this.id,
        name = this.name,
        capital = this.capital,
        region = this.region,
        flagUrl = this.flagUrl,
        population = this.population,
        mapUrl = this.mapUrl
    )
}

// List<DestinationEntity> -> List<Destination> (Reading from DB
fun List<DestinationEntity>.toDomainList() : List<Destination> {
    return this.map { destinationEntity ->
        destinationEntity.toDomain()
    }
}