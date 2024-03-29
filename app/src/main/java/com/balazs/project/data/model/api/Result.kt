package com.balazs.project.data.model.api

data class Result(
    val advertisers: List<Advertiser>,
    val branding: Any,
    val community: Any,
    val description: Description,
    val flags: Flags,
    val href: String,
    val last_price_change_amount: Any,
    val last_price_change_date: Any,
    val last_sold_date: Any,
    val last_sold_price: Any,
    val last_update_date: String,
    val lead_attributes: LeadAttributes,
    val list_date: String,
    val list_price: Any,
    val list_price_max: Int,
    val list_price_min: Int,
    val listing_id: String,
    val location: Location,
    val matterport: Boolean,
    val open_houses: Any,
    val other_listings: OtherListings,
    val permalink: String,
    val pet_policy: PetPolicy,
    val photo_count: Int,
    val photos: List<Photo>,
    val price_reduced_amount: Any,
    val price_reduced_date: Any,
    val primary_photo: PrimaryPhoto,
    val products: Products,
    val property_history: Any,
    val property_id: String,
    val seller_promotion: Any,
    val source: Source,
    val status: String,
    val suppression_flags: SuppressionFlags,
    val tags: List<String>,
    val virtual_tours: Any,
    val when_indexed: String
)