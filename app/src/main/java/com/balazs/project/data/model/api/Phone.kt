package com.balazs.project.data.model.api

data class Phone(
    val ext: Any,
    val number: String,
    val primary: Boolean,
    val trackable: Boolean,
    val type: String
)