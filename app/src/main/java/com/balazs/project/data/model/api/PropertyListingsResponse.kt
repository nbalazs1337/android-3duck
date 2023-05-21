data class PropertyListingsResponse(
    val status: Int,
    val data: PropertyListingsData
)

data class PropertyListingsData(
    val home_search: HomeSearch
)

data class HomeSearch(
    val total: Int,
    val count: Int,
    val results: List<PropertyListingsResult>
)

data class PropertyListingsResult(
    val photos: List<Photo>,
    val other_listings: OtherListings,
    val list_price_min: Int,
    val href: String,
    val when_indexed: String,
    val last_sold_price: Double?,
    val property_id: String,
    val advertisers: List<Advertiser>,
    // Add other properties you need from the JSON response
)

data class Photo(
    val title: String?,
    val description: String?,
    val tags: List<String>?,
    val href: String,
    val type: String?
)


data class OtherListings(
    val rdc: List<Listing>
)

data class Listing(
    val listing_id: String,
    val status: String,
    val primary: Boolean?
)

data class Advertiser(
    val office: Office,
    val name: String?,
    val mls_set: String?,
    val email: String?,
    // Add other properties you need from the JSON response
)

data class Office(
    val phones: List<Phone>?,
    val lead_email: Email?,
    val photo: String?,
    val name: String?,
    val fulfillment_id: String?,
    val href: String?,
    val mls_set: String?,
    // Add other properties you need from the JSON response
)


data class Phone(
    val trackable: Boolean?,
    val number: String?,
    val ext: String?,
    val primary: Boolean?,
    val type: String?
)

data class Email(
    val to: String?,
    val cc: String?
)

// Add other necessary data classes based on the remaining properties in the JSON response

