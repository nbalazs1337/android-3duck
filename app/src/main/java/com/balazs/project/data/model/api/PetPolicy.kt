package com.balazs.project.data.model.api

data class PetPolicy(
    val cats: Boolean,
    val dogs: Boolean,
    val dogs_large: Boolean,
    val dogs_small: Boolean,
    val text: String
)