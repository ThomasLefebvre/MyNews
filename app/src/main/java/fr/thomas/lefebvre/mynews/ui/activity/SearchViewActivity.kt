package fr.thomas.lefebvre.mynews.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import fr.thomas.lefebvre.mynews.ui.adapter.DocsAdapter
import fr.thomas.lefebvre.mynews.service.ApiService.Companion.url
import fr.thomas.lefebvre.mynews.model.Docs
import fr.thomas.lefebvre.mynews.model.MainResponseSearch
import fr.thomas.lefebvre.mynews.R
import fr.thomas.lefebvre.mynews.service.ApiService
import fr.thomas.lefebvre.mynews.utils.CalculUtils
import kotlinx.android.synthetic.main.activity_search_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SearchViewActivity : AppCompatActivity() {
    //INIT VARIABLES
    var calculUtils=CalculUtils()
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

    private fun apiService(){//SET METHOD FOR API SERVICE
        val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        val serviceSearch=retrofit.create(ApiService::class.java)//INSTANCE OF SERVICE
        val requestSearch:Call<MainResponseSearch>
        //INSTANCE OF REQUEST
        requestSearch = if(dateEnd==""&&dateBegin==""){//if no date
            serviceSearch.articleByCategorySearch(orderArticle,pageNumber,sectionValues,textSearch,getString(R.string.api_key))
        }else if(dateBegin==""&&dateEnd!=""){//if date end
            serviceSearch.articleByCategorySearchDateEnd(orderArticle,dateEnd,pageNumber,sectionValues,textSearch,getString(R.string.api_key))
        }
        else if(dateBegin!=""&&dateEnd==""){//if date begin
            serviceSearch.articleByCategorySearchDateBegin(orderArticle,dateBegin,pageNumber,sectionValues,textSearch,getString(R.string.api_key))
        }
        else{//if date end and date begin
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
            if (calculUtils.buttonNextIsPossible(pageNumber)){
                pageNumber -= 1
                this.apiService()}
        })
    }

    private fun setButtonPrevious(){
        btn_next.setOnClickListener(View.OnClickListener {
            val maxPage:Int=((mainResponseSearch.response.meta.hits)/10)-1
            if (calculUtils.buttonPreviousIsPossible(pageNumber,maxPage)){
                pageNumber += 1
                this.apiService()
            }
        })
    }

    private fun initValuesSearch(){//INIT THE VALUE OF  SEARCH
        textSearch= intent.extras.getString("textSearch","")//RECOVER TEXT RESEARCH
        dateBegin=intent.extras.getString("dateBegin","")//RECOVER DATE BEGIN
        dateEnd=intent.extras.getString("dateEnd","")//RECOVER DATE END
        valueUrl=intent.extras.getString("valueUrl","")//RECOVER VALUE CATEGORY
        sectionValues="section_name:($valueUrl)"//BUILD SECTION NAME
    }

    private fun alertDialog(){//SET THE DIALOG BOX IF NO RESULTS
        val alertDialog=AlertDialog.Builder(this@SearchViewActivity)
        alertDialog.setTitle("No Results")
        alertDialog.setMessage("No results for this search, return to search screen!")
        alertDialog.setNeutralButton("Return"){ _: DialogInterface?, _: Int ->
            super.onBackPressed()
        }
        alertDialog.show()
    }



    private fun setRecyclerView(allArticle:List<Docs>){//SET RECYCLER VIEW
        rv_search_view.apply {
            layoutManager= LinearLayoutManager(this@SearchViewActivity)
            adapter=
                DocsAdapter(allArticle) { itemClick: Docs -> articleClick(itemClick) }
        }
    }

}
