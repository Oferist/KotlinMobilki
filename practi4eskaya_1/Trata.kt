package com.example.practi4eskaya_1


import kotlin.text.StringBuilder

class Trata(val amount:Double, val category:String, val time:String) {

    override fun toString(): String {
        return "Информация о расходе: сумма - $amount, категория - $category, время - $time"   }

}



object Expense {
    var listOfExpense:Map<String,List<Trata>> = mutableMapOf()



    fun addTrata(amount:Double, category:String, time:String) {
        val trata:Trata = Trata(amount,category,time)

        // Создаю пустой List для пары Category to List, чтобы избежать NullPointerException при добавлении нового расхода
        if (listOfExpense.get(category) == null) {listOfExpense += category to listOf()}

        // В Map по ключу-категории добавляю новую трату в List трат по этой категории
        listOfExpense += category to listOfExpense.get(category)!!.plus(trata)

    }


    fun print() {
        listOfExpense.forEach { println(it) }
    }

    fun summa():String {
        var summaStr = StringBuilder()
        summaStr.append("Сумма всех расходов - ")
        for (i in listOfExpense) {
            var summa:Double = 0.0
            i.value.forEach { summa+= it.amount }
            summaStr.append("${i.key}: $summa рублей; ")
        }
        return summaStr.toString()
    }


}