package fr.thomas.lefebvre.mynews

import fr.thomas.lefebvre.mynews.utils.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilsTest {

    @Test
    fun dateFormatIsCorrectMostPopular(){//TEST A FORMAT DATE
        var dateUtils=DateUtils()
        var dateString:String="19-08-13"
            assertEquals("13/08/19",dateUtils.dateFormatMostPopular(dateString))
    }

    @Test
    fun dateFormatIsCorrectTopStories(){//TEST A FORMAT DATE
        var dateUtils=DateUtils()
        var dateString:String="2019-08-13T05:00:06-0400"
        assertEquals("13/08/19",dateUtils.dateFormat(dateString))
    }

}