package com.zenjob.android.browsr.data.movie_details

import com.google.gson.annotations.SerializedName


data class Belongs_to_collection (

  val id : Int,
  val name : String,
  val poster_path : String,
  val backdrop_path : String
)