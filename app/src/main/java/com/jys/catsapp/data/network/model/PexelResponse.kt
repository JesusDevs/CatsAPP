package com.jys.catsapp.data.network.model


import com.google.gson.annotations.SerializedName

data class PexelResponse(
    @SerializedName("next_page")
    val nextPage: String?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("photos")
    val photos: List<Photo?>?,
    @SerializedName("prev_page")
    val prevPage: String?,
    @SerializedName("total_results")
    val totalResults: Int?
)