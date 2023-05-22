package com.balazs.project.data.model.api

data class SuppressionFlags(
    val has_suppress_management_company_logo: Boolean,
    val has_suppress_management_company_name: Boolean,
    val has_suppress_management_company_other_listings: Boolean,
    val has_suppress_management_company_url: Boolean
)