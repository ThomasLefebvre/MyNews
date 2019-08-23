package fr.thomas.lefebvre.mynews

import fr.thomas.lefebvre.mynews.utils.CalculUtils
import fr.thomas.lefebvre.mynews.utils.DateUtils
import org.junit.Assert
import org.junit.Test

class CalculUtilsTest {
    @Test
    fun buttonNextIsPossibleTest(){//TEST A BUTTON NEXT IS POSSIBLE
        var calculUtils=CalculUtils()
        var pageNumber=5
        var test:Boolean= calculUtils.buttonNextIsPossible(pageNumber)

        Assert.assertEquals(true,test )
    }

    @Test
    fun buttonPreviousIsPossibleTest(){//TEST A BUTTON PREVIOUS IS POSSIBLE
        var calculUtils=CalculUtils()
        var pageNumber=5
        var pageMax=10
        var test:Boolean=calculUtils.buttonPreviousIsPossible(pageNumber,pageMax)
        Assert.assertEquals(true, test)
    }
}