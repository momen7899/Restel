package com.momen.restel.main.view

class CustomerDate {

    companion object {
        fun compareDate(
            start: String?, finish: String?, startDate: String?, finishDate: String?
        ): Boolean {
            val startLong = transformDateToLong(start)
            val finishLong = transformDateToLong(finish)
            val startDateLong = transformDateToLong(startDate)
            val finishDateLong = transformDateToLong(finishDate)
            if (startLong in startDateLong..finishDateLong || finishLong in startDateLong..finishDateLong) {
                return true
            }
            return false
        }

        private fun transformDateToLong(date: String?): Long {
            val reDate = date?.split("،")?.get(1)
            val date2 = reDate?.split(" ")
            var year: Long? = date2?.get(3)?.toLong()
            val month: Long = transformMonthToInt(date2?.get(2))
            var day: Long? = date2?.get(1)?.toLong()
            if (year == null || day == null) {
                year = 1400
                day = 1
            }
            return (year.times(365) + month) + day
        }

        private fun transformMonthToInt(month: String?): Long {
            when (month) {
                "فروردین" -> return 0
                "اردیبهشت" -> return 31
                "خرداد" -> return 62
                "تیر" -> return 93
                "مرداد" -> return 124
                "شهریور" -> return 155
                "مهر" -> return 186
                "آبان" -> return 216
                "آذر" -> return 246
                "دی" -> return 276
                "بهمن" -> return 306
                "اسفند" -> return 336
            }
            return 0
        }
    }
}
