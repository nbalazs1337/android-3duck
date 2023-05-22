package com.balazs.project.data.model.api

data class Office(
    val email: String,
    val fulfillment_id: Any,
    val href: Any,
    val lead_email: LeadEmail,
    val mls_set: Any,
    val name: String,
    val phones: List<Phone>,
    val photo: Any
)