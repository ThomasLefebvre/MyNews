package fr.thomas.lefebvre.mynews.controller

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import fr.thomas.lefebvre.mynews.adapter.DocsAdapter
import fr.thomas.lefebvre.mynews.service.SearchService
import fr.thomas.lefebvre.mynews.model.Docs
import fr.thomas.lefebvre.mynews.model.MainResponseSearch
import fr.thomas.lefebvre.mynews.R
import kotlinx.android.synthetic.main.activity_search_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SearchViewActivity : AppCompatActivity() {
    var pageNumber:Int=0
    lateinit var textSearch:String
    lateinit var sectionValues:String
    lateinit var dateBegin:String
    lateinit var dateEnd:String
    lateinit var valueUrl:String
    var orderArticle:String="newest"
    lateinit var mainResponseSearch: MainResponseSearch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view)

        this.initValuesSearch()
        this.setButtonNext()
        this.setButtonPrevious()
        this.apiService()


    }
    private fun articleClick(itemClick: Docs){//MANAGEMENT CLICK ARTICLE AND START WEB VIEW ACTIVITY
        val webViewActivityIntent= Intent(this, WebViewActivity::class.java)
        webViewActivityIntent.putExtra(Intent.EXTRA_TEXT, itemClick.web_url)
        startActivity(webViewActivityIntent)
    }

    private fun apiService(){
        val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(SearchService.url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        val serviceSearch=retrofit.create(SearchService::class.java)//INSTANCE OF SERVICE
        var requestSearch:Call<MainResponseSearch>
        requestSearch = if(dateEnd==""&&dateBegin==""){
            serviceSearch.articleByCategorySearch(orderArticle,pageNumber,sectionValues,textSearch,getString(R.string.api_key))//INSTANCE OF REQUEST
        }else if(dateBegin==""&&dateEnd!=""){
            serviceSearch.articleByCategorySearchDateEnd(orderArticle,dateEnd,pageNumber,sectionValues,textSearch,getString(R.string.api_key))
        }
        else if(dateBegin!=""&&dateEnd==""){
            serviceSearch.articleByCategorySearchDateBegin(orderArticle,dateBegin,pageNumber,sectionValues,textSearch,getString(R.string.api_key))
        }
        else{
            serviceSearch.articleByCategorySearchAndDates(orderArticle,dateBegin,dateEnd,pageNumber,sectionValues,textSearch,getString(R.string.api_key))//INSTANCE OF REQUEST
        }
        requestSearch.enqueue(object: Callback<MainResponseSearch> {
            override fun onFailure(call: Call<MainResponseSearch>, t: Throwable) {
                Log.i("RETRO",t.message)
            }
            override fun onResponse(call: Call<MainResponseSearch>, response: Response<MainResponseSearch>) {
                val mainResponse=response.body()
                val allArticle=mainResponse!!.response.docs
                mainResponseSearch=mainResponse
                    setRecyclerView(allArticle)
                if(mainResponseSearch.response.meta.hits==0){
                   alertDialog()
                }
            }
        })}

    private fun setButtonNext(){
        btn_previous.setOnClickListener(View.OnClickListener {//BUTTON PREVIOUS AND NEXT LIST ARTICLE
            if (pageNumber>0){
                pageNumber -= 1
                this.apiService()}
        })
    }

    private fun setButtonPrevious(){
        btn_next.setOnClickListener(View.OnClickListener {
            var maxPage:Int=((mainResponseSearch.response.meta.hits)/10)-1
            if (pageNumber<maxPage){
                pageNumber += 1
                this.apiService()
            }
        })
    }

    private fun initValuesSearch(){
        textSearch= intent.extras.getString("textSearch","")//RECOVER TEXT RESEARCH
        dateBegin=intent.extras.getString("dateBegin","")//RECOVER DATE BEGIN
        dateEnd=intent.extras.getString("dateEnd","")//RECOVER DATE END
        valueUrl=intent.extras.getString("valueUrl","")
        sectionValues="section_name:($valueUrl)"//BUILD SECTION NAME
    }

    private fun alertDialog(){
        val alertDialog=AlertDialog.Builder(this@SearchViewActivity)
        alertDialog.setTitle("No Results")
        alertDialog.setMessage("No results for this search, return to search screen!")
        alertDialog.setNeutralButton("Return"){ _: DialogInterface?, _: Int ->
            this.startActivitySearch()
        }
        alertDialog.show()
    }

    private fun startActivitySearch(){
        val searchActivityIntent = Intent(this@SearchViewActivity, SearchActivity::class.java)
        startActivity(searchActivityIntent)

    }

    private fun setRecyclerView(allArticle:List<Docs>){
        rv_search_view.apply {
            layoutManager= LinearLayoutManager(this@SearchViewActivity)
            adapter= DocsAdapter(allArticle) { itemClick: Docs ->articleClick(itemClick)}
        }
    }

}
