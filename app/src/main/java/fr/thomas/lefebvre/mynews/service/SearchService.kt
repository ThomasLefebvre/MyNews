package fr.thomas.lefebvre.mynews.service


import retrofit2.Call
import fr.thomas.lefebvre.mynews.model.MainResponseSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    companion object{
        var url:String="https://api.nytimes.com/svc/search/v2/"
    }

    @GET("articlesearch.json")
    fun articleByCategorySearchAndDates(
        @Query("sort")order:String,
        @Query("begin_date")beginDate:String,
        @Query("end_date")endDate:String,
        @Query("page")page:Int,
        @Query("fq")section:String,
        @Query("q")textSearch:String,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>

    @GET("articlesearch.json")
    fun articleByCategorySearch(
        @Query("sort")order:String,
        @Query("page")page:Int,
        @Query("fq")section:String,
        @Query("q")textSearch:String,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>

    @GET("articlesearch.json")
    fun articleByCategorySearchDateBegin(
        @Query("sort")order:String,
        @Query("begin_date")beginDate:String,
        @Query("page")page:Int,
        @Query("fq")section:String,
        @Query("q")textSearch:String,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>

    @GET("articlesearch.json")
    fun articleByCategorySearchDateEnd(
        @Query("sort")order:String,
        @Query("end_date")endDate:String,
        @Query("page")page:Int,
        @Query("fq")section:String,
        @Query("q")textSearch:String,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>

    @GET("articlesearch.json")
    fun articleNotification(
        @Query("begin_date")beginDate:String,
        @Query("end_date")endDate:String,
        @Query("fq")section:String,
        @Query("q")textSearch:String,
        @Query("api-key")key:String
    ):Call<MainResponseSearch>
}
