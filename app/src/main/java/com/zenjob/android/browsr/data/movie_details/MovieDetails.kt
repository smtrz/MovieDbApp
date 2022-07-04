package com.zenjob.android.browsr.data.movie_details

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2022 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class MovieDetails (

    val adult : Boolean,
    val backdrop_path : String,
    val belongs_to_collection : Belongs_to_collection,
    val budget : Int,
    val genres : List<Genres>,
    val homepage : String,
    val id : Int,
    val imdb_id : String,
    val original_language : String,
    val original_title : String,
    val overview : String,
    val popularity : Double,
    val poster_path : String,
    val production_companies : List<Production_companies>,
    val production_countries : List<Production_countries>,
    val release_date : String,
    val revenue : Int,
    val runtime : Int,
    val spoken_languages : List<Spoken_languages>,
    val status : String,
    val tagline : String,
    val title : String,
    val video : Boolean,
    val vote_average : Double,
    val vote_count : Int
)