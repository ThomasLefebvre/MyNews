package fr.thomas.lefebvre.mynews

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import fr.thomas.lefebvre.mynews.controller.SearchActivity
import fr.thomas.lefebvre.mynews.controller.SearchViewActivity
import org.junit.*

class SearchActivityTest {
    @Rule
    @JvmField

    val mActivityTestRules= ActivityTestRule <SearchActivity>(SearchActivity::class.java)

    private lateinit var mActivity : SearchActivity

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

    @Test
    fun launchWebSearchViewActivity() {
        // register next activity that need to be monitored.
        val activityMonitor = InstrumentationRegistry.getInstrumentation()
            .addMonitor(SearchViewActivity::class.java.name, null, false)

        // click and write "camembert" on the query search
        Espresso.onView(ViewMatchers.withId(R.id.editText)).perform(ViewActions.click(),typeText("Camembert"))

        //check the food checkbox
        Espresso.onView(ViewMatchers.withId(R.id.cb_food)).perform(ViewActions.click())

        // click and search button
        Espresso.onView(ViewMatchers.withId(R.id.btn_search)).perform(ViewActions.click())

        //Watch for the timeout
        val nextActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000)

        // if nextActivity isn't null, a SearchActivity has opened
        Assert.assertNotNull(nextActivity)
        nextActivity.finish()
    }
}