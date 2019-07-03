package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String = "HH:mm:ss dd:MM:yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, unit: TimeUnits): Date {
    //var time = this.time

    time += when (unit) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    return this
}

fun Date.humanizeDiff(): String =
    when (val diff = Date().time - time) {
        in (0..SECOND) -> "только что"
        in (SECOND..45 * SECOND) -> "несколько секунд назад"
        in (45 * SECOND..75 * SECOND) -> "минуту назад"
        in (75 * SECOND..45 * MINUTE) -> "${diff.toStringTimeFormat(TimeUnits.MINUTE)} назад"
        in (45 * MINUTE..75 * MINUTE) -> "час назад"
        in (75 * MINUTE..22 * HOUR) -> "${diff.toStringTimeFormat(TimeUnits.HOUR)} назад"
        in (22 * HOUR..26 * HOUR) -> "день назад"
        in (26 * HOUR..360 * DAY) -> "${diff.toStringTimeFormat(TimeUnits.DAY)} назад"
        in (360 * DAY..Long.MAX_VALUE) -> "более года назад"
        else -> "Ошибка!"
    }

private fun Long.toStringTimeFormat(timeUnit: TimeUnits): String {
    val divider = when (timeUnit) {
        TimeUnits.SECOND -> SECOND
        TimeUnits.MINUTE -> MINUTE
        TimeUnits.HOUR -> HOUR
        TimeUnits.DAY -> DAY
    }
    val units: Long = this / divider

    val rangeType = when {
        units in (5..20) || units % 10 in (5..10) -> 3
        units % 10 == 1L -> 1
        units % 10 in (2..4) -> 2
        else -> 0
    }

    return when (timeUnit) {
        TimeUnits.SECOND -> when (rangeType) {
            1 -> "$units секунду"
            2 -> "$units секунды"
            3 -> "$units секунд"
            else -> "ошибка"
        }
        TimeUnits.MINUTE -> when (rangeType) {
            1 -> "$units минуту"
            2 -> "$units минуты"
            3 -> "$units минут"
            else -> "ошибка"
        }
        TimeUnits.HOUR -> when (rangeType) {
            1 -> "$units час"
            2 -> "$units часа"
            3 -> "$units часов"
            else -> "ошибка"
        }
        TimeUnits.DAY -> when (rangeType) {
            1 -> "$units день"
            2 -> "$units дня"
            3 -> "$units дней"
            else -> "ошибка"
        }
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value:Int):String {
        val rangeType = when {
            value in (5..20) || value % 10 in (5..10) -> 3
            value % 10 == 1 -> 1
            value % 10 in (2..4) -> 2
            else -> 0
        }

        return when (this) {
            SECOND -> when (rangeType) {
                1 -> "$value секунду"
                2 -> "$value секунды"
                3 -> "$value секунд"
                else -> "ошибка"
            }
            MINUTE -> when (rangeType) {
                1 -> "$value минуту"
                2 -> "$value минуты"
                3 -> "$value минут"
                else -> "ошибка"
            }
            HOUR -> when (rangeType) {
                1 -> "$value час"
                2 -> "$value часа"
                3 -> "$value часов"
                else -> "ошибка"
            }
            DAY -> when (rangeType) {
                1 -> "$value день"
                2 -> "$value дня"
                3 -> "$value дней"
                else -> "ошибка"
            }
        }
    }
}