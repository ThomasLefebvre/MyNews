package fr.thomas.lefebvre.mynews

import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import fr.thomas.lefebvre.mynews.controller.MainActivity
import fr.thomas.lefebvre.mynews.controller.NotificationsActivity
import fr.thomas.lefebvre.mynews.controller.SearchActivity
import fr.thomas.lefebvre.mynews.controller.WebViewActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest

class MainActivityTest {


    @Rule @JvmField

    val mActivityTestRules= ActivityTestRule <MainActivity>(MainActivity::class.java)

    private lateinit var mActivity : MainActivity

    // Prepares the activity
    @Before
    fun setUp()
    {
        mActivity = mActivityTestRules.activity
    }

    // Finishes the activity
    @After
    @Throws(Exception::class)
    fun tearDown() {
        mActivity.finish()
    }

    // Tests that the tab lists are not empty, hence that both api call and gson conversion are ok */
    @Test
    fun articles() {
        // register next activity that need to be monitored.
        val activityMonitor = getInstrumentation().addMonitor(WebViewActivity::class.java.name, null, false)

        // Waiting max time for the articles to appear
        Thread.sleep(2000L)

        // Click on the first item of the list with corresponding id
        onView(withId(R.id.rv_top_stories))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))


        // Waiting max time for the articles to appear
        Thread.sleep(2000L)
        //Watch for the timeout
        val nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 500)

        // if nextActivity isn't null, a WebViewActivity has opened
        assertNotNull(nextActivity)
        nextActivity.finish()

    }


    @Test
    fun launchSearchActivity() {
        // register next activity that need to be monitored.
        val activityMonitor = getInstrumentation().addMonitor(SearchActivity::class.java.name, null, false)

        // click on the search button
        onView(withId(R.id.menu_search)).perform(click())


        //Watch for the timeout
        val nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000)

        // if nextActivity isn't null, a SearchActivity has opened
        assertNotNull(nextActivity)
        nextActivity.finish()
    }

    @Test
    fun launchNotificationActivity() {
        // click on the search button
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())

        // register next activity that need to be monitored.
        val activityMonitor = getInstrumentation().addMonitor(NotificationsActivity::class.java.name, null, false)

        // click on the notifications button
        onView(withText("Notifications")).perform(click())

        //Watch for the timeout
        val nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000)

        // if nextActivity isn't null, a NotificationActivity has opened
        assertNotNull(nextActivity)
        nextActivity.finish()
    }

//    @Test
//    fun openMenuDrawer(){
//        Thread.sleep(500L)
//        //Open the navigation drawer
//        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
//        Thread.sleep(500L)
//        //Click on the sport menu
//        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_most_popular))
//
//        //Assert if the position tab is the position most popular tab
//        assertEquals(mActivity.tabLayout.selectedTabPosition , 1)
//
//
//    }

    @Test
    fun openTabSport(){
        Thread.sleep(500L)
        //Click on the sport tab
        onView(withText("SPORT")).perform(click())
        Thread.sleep(2000L)
        //Assert if the position tab is the position sport tab
        assertEquals(mActivity.tabLayout.selectedTabPosition , 2)


    }

}