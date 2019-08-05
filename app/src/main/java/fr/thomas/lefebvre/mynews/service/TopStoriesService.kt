package fr.thomas.lefebvre.mynews.service


import fr.thomas.lefebvre.mynews.model.MainResponseTopStories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TopStoriesService {

    companion object{
        var url:String="https://api.nytimes.com/svc/topstories/v2/"
    }

    @GET("{section}.json")
    fun articleByCategory(@Path("section")section:String,@Query("api-key")key:String): Call<MainResponseTopStories>
}