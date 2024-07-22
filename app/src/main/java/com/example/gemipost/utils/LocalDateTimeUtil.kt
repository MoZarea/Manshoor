package com.example.gemipost.utils

import korlibs.time.DateTimeTz
import korlibs.time.YearMonth
import korlibs.time.days
import korlibs.time.minus
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle

object LocalDateTimeUtil {
    fun LocalDateTime.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    fun LocalDateTime.toYYYYMMDD() = "${this.year} / ${this.monthNumber} / ${this.dayOfMonth}"
    fun LocalDateTime.toDDMMYYYY() = "${this.dayOfMonth} / ${this.monthNumber} / ${this.year}"
    fun LocalDateTime.toMillis() = this.toInstant(TimeZone.UTC).toEpochMilliseconds()
    fun Long.toHHMMTimestamp(): String {
        val localDateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
        return "${localDateTime.hour}:${localDateTime.minute}"
    }

    fun Long.toLocalDateTime() = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    fun Long.getDateHeader(): String {
        val localDateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
        val today = LocalDateTime.now().date
        return when {
            localDateTime.date == today -> "Today"
            localDateTime.date == today.minus(1, DateTimeUnit.DAY) -> "Yesterday"
            localDateTime.date.daysUntil(today) < 7 -> localDateTime.date.dayOfWeek.name
            else -> localDateTime.toDDMMYYYY()
        }
    }
    fun Long.getPostDate(): String{
        val then = DateTimeTz.fromUnix(this)
        val now = DateTimeTz.nowLocal()
        val hoursDifference = now.hours - then.hours
        val minutesDifference = now.minutes - then.minutes
        return when {
            then.dayOfYear == now.dayOfYear && then.year == now.year -> {
                when {
                    hoursDifference > 0 -> "${hoursDifference}h"
                    minutesDifference > 0 -> "${minutesDifference}m"
                    else -> "just now"
                }
            }
            then.dayOfYear == (now - 1.days).dayOfYear && then.year == (now - 1.days).year-> "Yesterday"
            then.dayOfYear == (now - 7.days).dayOfYear && then.year == (now - 7.days).year -> "Last "+then.dayOfWeek.name
            else -> then.month.localShortName+" "+then.dayOfMonth+", "+then.year.year
        }
    }
    fun LocalDateTime.getDateHeader(): String {
        val today = LocalDateTime.now().date
        return when {
            this.date == today -> "Today"
            this.date == today.minus(1, DateTimeUnit.DAY) -> "Yesterday"
            this.date.daysUntil(today) < 7 -> "Last "+this.date.dayOfWeek.getDisplayName(TextStyle.FULL, java.util.Locale.getDefault())
            else -> this.toDDMMYYYY()
        }
    }
    fun DateTimeTz.getDateHeader(): String {
        val today = DateTimeTz.nowLocal()
        return when {
            this.dayOfYear == today.dayOfYear && this.year == today.year -> "Today"
            this.dayOfYear == (today - 1.days).dayOfYear && this.year == (today - 1.days).year-> "Yesterday"
            this.dayOfYear == (today - 7.days).dayOfYear && this.year == (today - 7.days).year -> this.dayOfWeek.name
            else -> this.month.name+" "+this.dayOfMonth+", "+this.year.year
        }
    }

    fun Long.getSubmissionFormattedDate(): String {
        val localDateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
        val today = LocalDateTime.now().date
        return this.getDateHeader() + ", " + this.toHHMMTimestamp()
    }
    val YearMonth.lengthOfMonth: Int
        get() = when (this.month.index1) {
            1 -> 31
            2 -> if (year.year % 4 == 0) 29 else 28
            3 -> 31
            4 -> 30
            5 -> 31
            6 -> 30
            7 -> 31
            8 -> 31
            9 -> 30
            10 -> 31
            11 -> 30
            12 -> 31
            else -> throw IllegalArgumentException("Invalid month number")
        }
    fun LocalDate.with(predicate: (LocalDate) -> Boolean): LocalDate {
        var date = this
        while (!predicate(date)) {
            date = LocalDate.fromEpochDays(date.toEpochDays().minus(1))
        }
        return date
    }
    fun DateTimeTz.getRecentRoomTimestamp(): String {
        val today = DateTimeTz.nowLocal()
        return when {
            this.dayOfYear == today.dayOfYear && this.year == today.year -> this.format("HH:mm a")
            this.dayOfYear == (today - 1.days).dayOfYear && this.year == (today - 1.days).year-> "Yesterday"
            this.dayOfYear == (today - 7.days).dayOfYear && this.year == (today - 7.days).year -> this.dayOfWeek.name
            else -> this.format("dd/MM/yy")
        }
    }

    fun convertEpochToTime(epoch: Long): String {
        val localDateTime = Instant.fromEpochMilliseconds(epoch).toLocalDateTime(TimeZone.currentSystemDefault())
        val hour = (localDateTime.hour)
        val minute = localDateTime.minute
        val amOrPm = if (hour < 12) "AM" else "PM"
        val hourIn12HrFormat = if (hour > 12) hour - 12 else hour
        return "$hourIn12HrFormat:$minute $amOrPm"
    }
    private fun LocalDateTime.toMMMDD(): String {
        val month = this.month.name.take(3)
        val day = this.dayOfMonth
        return "$month $day"
    }
}
