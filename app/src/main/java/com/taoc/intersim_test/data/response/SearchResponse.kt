package com.taoc.intersim_test.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("jobs")
	val jobs: List<SearchResponseItem>
)

data class SearchResponseItem(

	@field:SerializedName("urlJob")
	val urlJob: String? = null,

	@field:SerializedName("min_edu")
	val minEdu: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("urlImg")
	val urlImg: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("min_exp")
	val minExp: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
