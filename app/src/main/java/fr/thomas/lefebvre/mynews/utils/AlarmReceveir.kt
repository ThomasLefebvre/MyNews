package fr.thomas.lefebvre.mynews.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import fr.thomas.lefebvre.mynews.R
import java.util.*
import fr.thomas.lefebvre.mynews.service.SearchService
import fr.thomas.lefebvre.mynews.model.MainResponseSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat



@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AlarmReceveir : BroadcastReceiver() {
    var CHANNEL_ID = "fr.thomas.lefebvre.mynews"
    var NOTIFICATION_ID = 0
    var resultNumber:Int=0
    lateinit var listCheck:List<Boolean>
    val listSection:List<String> = listOf("\"Arts\"","\"Automobiles\"","\"Books\"","\"Food\"","\"Health\"","\"Movies\"","\"Science\"","\"Sports\"")

    override fun onReceive(context: Context, intent: Intent?) {

        apiService(context)


    }

    private fun createNotificationChannel(context: Context) {
        // Create  the channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification channel name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = "Notification channel description"
            Log.i("ALARM RECEVEIR","Create the channel in the system")
            // SAVE THE CHANNEL IN THE SYSTEM
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
            Log.i("ALARM RECEVEIR","Save the channel in the system")
        }
    }

    fun showNotification(context: Context,numberResult: Int) {
        if (numberResult>0){
            //SET CHANNEL
            createNotificationChannel(context)
            //SET NOTIFICATION
            val notificationManager = NotificationManagerCompat.from(context)
            val notifBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Your research...")
                .setContentText("$numberResult articles correspondent à votre recherche")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            //SET SHOW NOTIFICATION
            notificationManager.notify(NOTIFICATION_ID, notifBuilder.build())
        }
        else Log.i("ALARM RECEVEIR","No result for notification")

    }

    private fun apiService(context: Context) {
        var sectionsWithTitle: String="section_name:("+loadDataCheckBox(context)+")"
        var textSearchApi=loadDataTextSearch(context)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(SearchService.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val serviceSearch = retrofit.create(SearchService::class.java)//INSTANCE OF SERVICE
        val requestSearch = serviceSearch.articleNotification(setDateYesterday(),setDateYesterday(),sectionsWithTitle,textSearchApi, context.getString(R.string.api_key))
        Log.i("ALARM","request with text search: "+textSearchApi)
        requestSearch.enqueue(object: Callback<MainResponseSearch> {
            override fun onFailure(call: Call<MainResponseSearch>, t: Throwable) {
                Log.i("ALARM RECEVEIR",t.message)
            }
            override fun onResponse(call: Call<MainResponseSearch>, response: Response<MainResponseSearch>) {
                val mainResponse=response.body()
               Log.i("ALARM RECEVEIR",textSearchApi)
                if(mainResponse!=null){
                    resultNumber=mainResponse.response.meta.hits
                    showNotification(context,resultNumber)
                }

        }}
        )
    }
    private fun setDateYesterday(): String {//SET DATE FOR REQUEST
        var calendar=GregorianCalendar()
        calendar.add(Calendar.DATE,-1)
        var date:String= SimpleDateFormat("yyyMMdd",Locale.US).format(calendar.time)
        Log.i("ALARM RECEIVER","Set date")
        return date

    }

    private fun loadDataCheckBox(context: Context):String{
        val result=context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        listCheck= mutableListOf(result.getBoolean("cbarts",false),
            result.getBoolean("cbauto",false),
            result.getBoolean("cbbooks",false),
            result.getBoolean("cbfood",false),
            result.getBoolean("cbhealth",false),
            result.getBoolean("cbmovies",false),
            result.getBoolean("cbsciences",false),
            result.getBoolean("cbsports",false))
        var sectionsString:String=""
        for (i in 0..7)
            if (listCheck[i]==true){
                sectionsString += listSection[i]
            }
        return sectionsString
    }

    private fun loadDataTextSearch(context: Context):String{
        val result=context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return result.getString("textSearch","")
    }


}
