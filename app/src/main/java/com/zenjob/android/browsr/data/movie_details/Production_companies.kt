package com.zenjob.android.browsr.data.movie_details

import com.google.gson.annotations.SerializedName

data class Production_companies (

  val id : Int,
  val logo_path : String,
  val name : String,
  val origin_country : String
)