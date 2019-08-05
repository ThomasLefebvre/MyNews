package fr.thomas.lefebvre.mynews.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import fr.thomas.lefebvre.mynews.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setFragment()

        //SET TOOLBAR IN ACTIONBAR
        setSupportActionBar(toolbar)

        //SET DRAWER MENU
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout,toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navViewListener()




    }
    fun navViewListener(){//SET LISTENER CLICK MENU DRAWER
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_top_stories -> {
                    viewPager.currentItem = 0
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_most_popular ->{
                    viewPager.currentItem = 1
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_search ->{
                    viewPager.currentItem = 2
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }
    //VIEW PAGER FRAGMENT
    fun setFragment(){
        val adapter= MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentTopStories(),"Top Stories")
        adapter.addFragment(FragmentMostPopular(),"Most Popular")
        adapter.addFragment(FragmentSport(),"Sport")
        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //SET MENU IN TOOLBAR
        menuInflater.inflate(R.menu.mymenu,menu)
        return true
    }


    override fun onBackPressed() {//SET THE BUTTON BACK
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {//SET THE ACTION OF MENU CLICK
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menu_search ->{
                val searchActivityIntent = Intent(this, SearchActivity::class.java)
                startActivity(searchActivityIntent)
            }
            R.id.menu_notifications ->{
                val notificationsActivityIntent = Intent(this, NotificationsActivity::class.java)
                startActivity(notificationsActivityIntent)
            }
            R.id.menu_help ->{
                val  webViewIntent=Intent(this,WebViewActivity::class.java)
                webViewIntent.putExtra(Intent.EXTRA_TEXT,"https://help.nytimes.com/hc/en-us")
                startActivity(webViewIntent)
            }
            R.id.menu_about ->{
                val  webViewIntent=Intent(this,WebViewActivity::class.java)
                webViewIntent.putExtra(Intent.EXTRA_TEXT,"https://www.nytco.com/company/")
                startActivity(webViewIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }





        class MyViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {//SET THE PAGER ADAPTER

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(poisition: Int): Fragment {
            return fragmentList[poisition]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment,title:String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}
