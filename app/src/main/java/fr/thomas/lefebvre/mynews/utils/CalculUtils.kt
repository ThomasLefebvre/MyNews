package fr.thomas.lefebvre.mynews.utils

class CalculUtils {

    fun buttonNextIsPossible(pageNumber:Int):Boolean{
        if (pageNumber>0){
            return true
    }
        return false
}
    fun buttonPreviousIsPossible(pageNumber:Int,pageMax:Int):Boolean{
        if (pageNumber<pageMax){
            return true
        }
        return false
    }

}