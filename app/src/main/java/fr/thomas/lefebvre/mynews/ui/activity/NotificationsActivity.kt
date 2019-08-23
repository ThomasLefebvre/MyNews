package fr.thomas.lefebvre.mynews.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.AlarmManager
import android.app.AlarmManager.*
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import fr.thomas.lefebvre.mynews.utils.AlarmReceveir
import android.text.TextWatcher
import android.util.Log
import android.text.Editable
import android.widget.CompoundButton
import android.widget.Switch
import kotlinx.android.synthetic.main.search_layout.*



class NotificationsActivity : AppCompatActivity() {
    // INIT VARIABLES

    var SHARED_PREFS:String="sharedPrefs"
    var TEXT_SEARCH:String="textSearch"
    var SWITCH1:String="switch1"
    var CB_ARTS:String="cbarts"
    var CB_AUTO:String="cbauto"
    var CB_BOOKS:String="cbbooks"
    var CB_FOOD:String="cbfood"
    var CB_HEALTh:String="cbhealth"
    var CB_MOVIE:String="cbmovies"
    var CB_SCIENCES:String="cbsciences"
    var CB_SPORTS:String="cbsports"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fr.thomas.lefebvre.mynews.R.layout.activity_notifications)
        this.setToolbar()
        loadData()
        textSearchListener()
        switchListener()
        setCheckBox()
        goneElementLayout()

    }

    fun setToolbar(){//INIT TOOLBAR METHOD
        toolbar.setTitle("Notifications")
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            super.onBackPressed()
        })
    }

    fun setAlarm(){//INIT ALARM METHOD

        val manager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var notifIntent:Intent=Intent(this,AlarmReceveir::class.java)
        var pi:PendingIntent= PendingIntent.getBroadcast(this,0,notifIntent,0)



        manager.setRepeating(ELAPSED_REALTIME_WAKEUP, INTERVAL_FIFTEEN_MINUTES/8, +INTERVAL_FIFTEEN_MINUTES/8,pi)
        Log.i("ALARM","start alarm")

    }

    fun cancelAlarm(){//CANCEL ALARM METHOD

        val manager=this.getSystemService(Context.ALARM_SERVICE)as AlarmManager
        var notifIntent:Intent=Intent(this,AlarmReceveir::class.java)
        var pi:PendingIntent= PendingIntent.getBroadcast(this,0,notifIntent,0)

        manager.cancel(pi)
        Log.i("ALARM","Cancel alarm")
    }

    fun startOrCancelAlarm(switch:Switch){//TEST FOR INIT OR CANCEL ALARM

        if (switch.isChecked){
            Log.i("NOTIFS","Switch is checked, start alarm ")
            setAlarm()
        }
        else {
            Log.i("NOTIFS","Switch is not checked, cancel alarm")
            cancelAlarm()
        }
    }



    fun switchListener(){// SWITCH LISTENER METHOD
        switch_notifs.setOnClickListener(View.OnClickListener {
            startOrCancelAlarm(switch_notifs)
            Log.i("NOTIFS","Switch Listener for start or cancel alarm")
            saveData()
        })
    }

    fun textSearchListener(){//LISTENER FO QUERY SEARCH
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                saveData()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    fun saveData(){//SAVE DATA METHOD IN SHARED PREF
        val sharedPreferences=getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()

        editor.putString(TEXT_SEARCH,editText.text.toString())
        editor.putBoolean(SWITCH1,switch_notifs.isChecked)
        editor.putBoolean(CB_ARTS,cb_arts.isChecked)
        editor.putBoolean(CB_AUTO,cb_auto.isChecked)
        editor.putBoolean(CB_BOOKS,cb_books.isChecked)
        editor.putBoolean(CB_FOOD,cb_food.isChecked)
        editor.putBoolean(CB_HEALTh,cb_health.isChecked)
        editor.putBoolean(CB_MOVIE,cb_movie.isChecked)
        editor.putBoolean(CB_SCIENCES,cb_sciences.isChecked)
        editor.putBoolean(CB_SPORTS,cb_sports.isChecked)

        editor.apply()
    }

    fun loadData(){//LOAD DATA OF SHARED PREF AT LAUNCH
        val sharedPreferences=getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        editText.setText(sharedPreferences.getString(TEXT_SEARCH,""))
        switch_notifs.isChecked=sharedPreferences.getBoolean(SWITCH1,false)
        cb_arts.isChecked=sharedPreferences.getBoolean(CB_ARTS,false)
        cb_auto.isChecked=sharedPreferences.getBoolean(CB_AUTO,false)
        cb_books.isChecked=sharedPreferences.getBoolean(CB_BOOKS,false)
        cb_food.isChecked=sharedPreferences.getBoolean(CB_FOOD,false)
        cb_health.isChecked=sharedPreferences.getBoolean(CB_HEALTh,false)
        cb_movie.isChecked=sharedPreferences.getBoolean(CB_MOVIE,false)
        cb_sciences.isChecked=sharedPreferences.getBoolean(CB_SCIENCES,false)
        cb_sports.isChecked=sharedPreferences.getBoolean(CB_SPORTS,false)
    }
    fun setCheckBox(){//SAVE DATA AT CHANGE STATE CHECKBOX
        cb_arts.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, _ ->
            saveData()
        })
        cb_auto.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, _ ->
            saveData()
        })
        cb_books.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, _ ->
            saveData()
        })
        cb_food.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, _ ->
            saveData()
        })
        cb_health.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, _ ->
            saveData()
        })
        cb_movie.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, _ ->
            saveData()
        })
        cb_sciences.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, _ ->
            saveData()
        })
        cb_sports.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, _ ->
            saveData()
        })
    }

    fun goneElementLayout(){//SET VISIBILITY OF LAYOUT ELEMENTS
        btn_search.visibility=View.GONE
        bt_begin.visibility=View.GONE
        bt_end.visibility=View.GONE
        tv_begin.visibility=View.GONE
        tv_date_begin.visibility=View.GONE
        tv_end.visibility=View.GONE
        tv_date_end.visibility=View.GONE
        imageView.visibility=View.GONE
        imageView2.visibility=View.GONE

    }
}


