package fr.thomas.lefebvre.mynews.service


import fr.thomas.lefebvre.mynews.model.MainResponseMostPopular
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MostPopularService {
    companion object{
        var url:String="https://api.nytimes.com/svc/mostpopular/v2/viewed/"
    }

    @GET("{period}.json")
    fun articleByPeriod(@Path("period")period:Int, @Query("api-key")key:String): Call<MainResponseMostPopular>
}
