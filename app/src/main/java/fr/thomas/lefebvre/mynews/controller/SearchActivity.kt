package fr.thomas.lefebvre.mynews.controller

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.*
import fr.thomas.lefebvre.mynews.R
import kotlinx.android.synthetic.main.search_layout.*
import java.text.SimpleDateFormat
import java.util.*


class SearchActivity : AppCompatActivity() {
    val c=Calendar.getInstance()
    var dateBegin:String=""
    var dateEnd:String=""
    var valueSports: String = ""
    var valueScience: String = ""
    var valueArts: String = ""
    var valueAutomobiles: String = ""
    var valueBooks: String = ""
    var valueFood: String = ""
    var valueHealth: String = ""
    var valueMovies: String = ""
    var valueUrl:String=""





    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        this.setBeginDate()
        this.setEndDate()
        this.setToolbar()
        this.setCheckBox()
        this.startSearchViewActivityWhithValues()
        this.goneSwitch()



    }

    fun setCheckBox(){
        cb_arts.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) valueArts = "\"Arts\""
            else valueArts = ""
        })

        cb_auto.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) valueAutomobiles = "\"Automobiles\""
            else valueAutomobiles = ""
        })

        cb_books.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) valueBooks = "\"Books\""
            else valueBooks = ""
        })

        cb_food.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) valueFood = "\"Food\""
            else valueFood = ""
        })

        cb_health.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) valueHealth = "\"Health\""
            else valueHealth = ""
        })

        cb_movie.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) valueMovies = "\"Movies\""
            else valueMovies = ""
        })

        cb_sciences.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) valueScience = "\"Science\""
            else valueScience = ""
        })

        cb_sports.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) valueSports = "\"Sports\""
            else valueSports = ""
        })
    }
    private fun setBeginDate(){
        bt_begin.setOnClickListener(View.OnClickListener {
            val date=object:DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    c.set(Calendar.YEAR,year)
                    c.set(Calendar.MONTH,month)
                    c.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                    tv_date_begin.text=SimpleDateFormat("dd/MM/yyy",Locale.US).format(c.time)
                    dateBegin=SimpleDateFormat("yyyMMdd",Locale.US).format(c.time)
                }
            }
            DatePickerDialog(this,date,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show()
        })
    }

    private fun setEndDate(){
        bt_end.setOnClickListener(View.OnClickListener {
            val date=object:DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    c.set(Calendar.YEAR,year)
                    c.set(Calendar.MONTH,month)
                    c.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                    tv_date_end.text=SimpleDateFormat("dd/MM/yyy",Locale.US).format(c.time)
                    dateEnd=SimpleDateFormat("yyyMMdd",Locale.US).format(c.time)
                }
            }
            DatePickerDialog(this,date,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show()
        })
    }

    private fun startSearchViewActivityWhithValues(){
        btn_search.setOnClickListener(View.OnClickListener {
           valueUrl= valueArts+valueAutomobiles+valueBooks+valueFood+valueHealth+valueMovies+valueScience+valueSports
           if((cb_arts.isChecked||cb_auto.isChecked||cb_books.isChecked||cb_food.isChecked||cb_health.isChecked||cb_movie.isChecked||cb_sciences.isChecked||cb_sports.isChecked)&&editText.length()!=0){
               val searchViewActivity = Intent(this, SearchViewActivity::class.java)
               searchViewActivity.putExtra("textSearch", editText.text.toString())
               searchViewActivity.putExtra("valueUrl",valueUrl)
               searchViewActivity.putExtra("dateBegin",dateBegin)
               searchViewActivity.putExtra("dateEnd",dateEnd)
               startActivity(searchViewActivity)
           }
            else
               Toast.makeText(this,getString(R.string.message_search),Toast.LENGTH_LONG).show()
        })
    }

    fun setToolbar(){
        toolbar.setTitle("Search")
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            super.onBackPressed()
        })

    }
    fun goneSwitch(){
        switch_notifs.visibility=View.GONE
        tv_switch.visibility=View.GONE
    }
}
