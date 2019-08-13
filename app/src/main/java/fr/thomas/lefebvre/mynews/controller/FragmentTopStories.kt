package fr.thomas.lefebvre.mynews.controller


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.thomas.lefebvre.mynews.adapter.ArticleAdapter
import fr.thomas.lefebvre.mynews.service.TopStoriesService
import fr.thomas.lefebvre.mynews.service.TopStoriesService.Companion.url
import fr.thomas.lefebvre.mynews.model.Article
import fr.thomas.lefebvre.mynews.model.MainResponseTopStories
import fr.thomas.lefebvre.mynews.R
import kotlinx.android.synthetic.main.fragment_fragment_most_popular.*
import kotlinx.android.synthetic.main.fragment_fragment_top_stories.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class FragmentTopStories : Fragment() {






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_fragment_top_stories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit:Retrofit=Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val serviceTopStories=retrofit.create(TopStoriesService::class.java)//INSTANCE OF SERVICE
        val requestTopStories=serviceTopStories.articleByCategory("home",getString(R.string.api_key))//INSTANCE OF REQUEST
        requestTopStories.enqueue(object: Callback<MainResponseTopStories> {
            override fun onFailure(call: Call<MainResponseTopStories>, t: Throwable) {
                Log.e("RETRO","$t")
            }

            override fun onResponse(call: Call<MainResponseTopStories>, response: Response<MainResponseTopStories>) {
                val mainResponse=response.body()
                val allArticle=mainResponse!!.results
                rv_top_stories.apply {
                    layoutManager=LinearLayoutManager(activity)
                    adapter=ArticleAdapter(allArticle) { itemClick:Article->articleClick(itemClick)}
                }
            }
        })


    }

    fun articleClick(itemClick: Article){//START WEBVIEW  ACTIVTY ON CLICK
        val webViewActivityIntent= Intent(activity, WebViewActivity::class.java)
        webViewActivityIntent.putExtra(Intent.EXTRA_TEXT, itemClick.url)
        startActivity(webViewActivityIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance=true

    }
}
