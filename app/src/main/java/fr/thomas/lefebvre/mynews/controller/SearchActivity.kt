package fr.thomas.lefebvre.mynews.controller

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
    var valueUrl:String=""
    val listValeuCheckBox:List<String> =listOf("\"Arts\"","\"Automobiles\"","\"Books\"","\"Food\"","\"Health\"","\"Movies\"","\"Science\"","\"Sports\"")
    var listValeurObtenuCheckBox: MutableList<String> = mutableListOf("","","","","","","","")






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        this.setBeginDate()
        this.setEndDate()
        this.setToolbar()
        this.setValueCheckBox(listValeuCheckBox,listValeurObtenuCheckBox)
        this.startSearchViewActivityWhithValues()
        this.goneSwitch()

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
                valueUrl= listValeurObtenuCheckBox[0]+listValeurObtenuCheckBox[1]+listValeurObtenuCheckBox[2]+listValeurObtenuCheckBox[3]+
                        listValeurObtenuCheckBox[4]+listValeurObtenuCheckBox[5]+listValeurObtenuCheckBox[6]+listValeurObtenuCheckBox[7]


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

    fun setToolbar(){//SET TOOLBAR
        toolbar.setTitle("Search")
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            super.onBackPressed()
        })

    }
    fun goneSwitch(){//SET VISIBILTY OF VIEW
        switch_notifs.visibility=View.GONE
        tv_switch.visibility=View.GONE
    }

    fun setValueCheckBox(listValeur:List<String>,listValeurObtenu:MutableList<String>){//SET VALUE IN FUNCTION AT CHECKBOX

        val listCheckBox=listOf(cb_arts,cb_auto,cb_books,cb_food,cb_health,cb_movie,cb_sciences,cb_sports)
        for (i in listCheckBox.indices){
            listCheckBox.get(i).setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
                if (isChecked) listValeurObtenu[i] = listValeur[i]
              else listValeurObtenu[i]=""
            })
        }

    }

}
