package fr.thomas.lefebvre.mynews.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.thomas.lefebvre.mynews.service.ApiService.Companion.url
import fr.thomas.lefebvre.mynews.ui.adapter.ViewedArticleAdapter
import fr.thomas.lefebvre.mynews.model.MainResponseMostPopular
import fr.thomas.lefebvre.mynews.model.ViewedArticle
import fr.thomas.lefebvre.mynews.R
import fr.thomas.lefebvre.mynews.service.ApiService
import fr.thomas.lefebvre.mynews.ui.activity.WebViewActivity
import kotlinx.android.synthetic.main.fragment_fragment_most_popular.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FragmentMostPopular : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_fragment_most_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val serviceMostPopular=retrofit.create(ApiService::class.java)//INSTANCE OF SERVICE
        val requestMostPopular=serviceMostPopular.articleByPeriod(1,getString(R.string.api_key))//INSTANCE OF REQUEST
        requestMostPopular.enqueue(object: Callback<MainResponseMostPopular> {
            override fun onFailure(call: Call<MainResponseMostPopular>, t: Throwable) {
                Log.e("RETRO","$t")
            }

            override fun onResponse(call: Call<MainResponseMostPopular>, response: Response<MainResponseMostPopular>) {
                val mainResponse=response.body()
                val allArticle=mainResponse!!.results
                rv_most_popular.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter=
                        ViewedArticleAdapter(allArticle) { itemClick: ViewedArticle ->
                            articleClick(itemClick)
                        }
                }
            }
        })
    }

    fun articleClick(itemClick: ViewedArticle){//START WEBVIEW  ACTIVTY ON CLICK
        val webViewActivityIntent= Intent(activity, WebViewActivity::class.java)
        webViewActivityIntent.putExtra(Intent.EXTRA_TEXT, itemClick.url)
        startActivity(webViewActivityIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance=true

    }
}
