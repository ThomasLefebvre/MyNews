package fr.thomas.lefebvre.mynews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var titleFragmentSearch:String="Category"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter= MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentTopStories(),"Top Stories")
        adapter.addFragment(FragmentMostPopular(),"Most Popular")
        adapter.addFragment(FragmentResearch(),titleFragmentSearch)

        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        menuInflater.inflate(R.menu.menu_search,menu)

        return true
    }

    class MyViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {

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
