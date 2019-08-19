package fr.thomas.lefebvre.mynews.service

import fr.thomas.lefebvre.mynews.model.MainResponseMostPopular
import fr.thomas.lefebvre.mynews.model.MainResponseSearch
import fr.thomas.lefebvre.mynews.model.MainResponseTopStories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object{
        var url:String="https://api.nytimes.com/svc/"
    }

    @GET("mostpopular/v2/viewed/{period}.json")
    fun articleByPeriod(@Path("period")period:Int, @Query("api-key")key:String): Call<MainResponseMostPopular>

    @GET("topstories/v2/{section}.json")
    fun articleByCategory(@Path("section")section:String,@Query("api-key")key:String): Call<MainResponseTopStories>

    @GET("search/v2/articlesearch.json")
    fun articleByCategorySearchAndDates(
        @Query("sort")order:String,
        @Query("begin_date")beginDate:String,
        @Query("end_date")endDate:String,
        @Query("page")page:Int,
        @Query("fq")section:String,
        @Query("q")textSearch:String,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>

    @GET("search/v2/articlesearch.json")
    fun articleByCategorySearch(
        @Query("sort")order:String,
        @Query("page")page:Int,
        @Query("fq")section:String,
        @Query("q")textSearch:String,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>

    @GET("search/v2/articlesearch.json")
    fun articleByCategorySearchDateBegin(
        @Query("sort")order:String,
        @Query("begin_date")beginDate:String,
        @Query("page")page:Int,
        @Query("fq")section:String,
        @Query("q")textSearch:String,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>

    @GET("search/v2/articlesearch.json")
    fun articleByCategorySearchDateEnd(
        @Query("sort")order:String,
        @Query("end_date")endDate:String,
        @Query("page")page:Int,
        @Query("fq")section:String,
        @Query("q")textSearch:String,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>

    @GET("search/v2/articlesearch.json")
    fun articleNotification(
        @Query("begin_date")beginDate:String,
        @Query("end_date")endDate:String,
        @Query("fq")section:String,
        @Query("q")textSearch:String?,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>
}

