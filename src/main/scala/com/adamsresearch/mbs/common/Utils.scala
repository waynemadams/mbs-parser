package com.adamsresearch.mbs.common

import java.text.{ParseException, SimpleDateFormat}
import java.util.{Calendar, Date}

/**
 * Created by wma on 1/23/15.
 */
object Utils {

  // some FM years are 2-digit years
  def parseTwoDigitYear(x: String): Date = {
    // overkill, but I was around in 1999...
    val fullDate = x.trim.substring(0, 6) + (Calendar.getInstance().get(Calendar.YEAR)/100).toString + x.trim.substring(6, 8)
    new SimpleDateFormat("MM/dd/yyyy").parse(fullDate)
  }

  def optionInt(x: String): Option[Int] = {
    try {
      Some(x.trim.toInt)
    } catch {
      case ex: NumberFormatException => None
    }
  }

  def optionFloat(x: String): Option[Float] = {
    x.trim match {
      case "" => None
      case "N/A" => None
      case _ => {
        try {
          Some(x.trim.toFloat)
        } catch {
          case ex: NumberFormatException => None
        }
      }
    }
  }

  // Note: because of exception handling, this also returns None when
  // some record Date fields contain the String "N/A".
  def optionMonthDayYear(x: String): Option[Date] = {
    try {
      Some(new SimpleDateFormat("MM/dd/yyyy").parse(x.trim))
    } catch {
      case ex: ParseException => None
    }
  }

  def yesNoBoolean(x: String): Boolean = x.trim match {
    case "Y" => true
    case "Yes" => true
    case "N" => false
    case "No" => false
    case _ => false
  }

  def optionYesNoBoolean(x: String): Option[Boolean] = x.trim match {
    case "" => None
    case "Y" => Option(true)
    case "Yes" => Option(true)
    case "N" => Option(false)
    case "No" => Option(false)
    case _ => Option(false)
  }

  // strips out dollar signs and en_us commas
  def dollarToFloat(x: String): Float = {
    x.trim.replace("$", "").replace(",", "").toFloat
  }

  // additionally handles the curious case where "N/A"
  // is used instead of a blank:
  def optionDollarToFloat(x: String): Option[Float] = {
    x.trim match {
      case "" => None
      case "N/A" => None
      case _ => Some(x.trim.replace("$", "").replace(",", "").toFloat)
    }
  }

  // for converting cobol-style formats:
  def picToFloat(x: String, leftDigits: Int, rightDigits: Int): Float = {
    val totalLength = leftDigits + rightDigits
    if (x.length == totalLength) {
      (x.substring(0, leftDigits) + "." + x.substring(leftDigits, leftDigits + rightDigits)).toFloat
    } else {
      throw new NumberFormatException("Expected " + totalLength + " characters; got " + x.length + " ('" + x + "'")
    }
  }

  // MBS Stats has some MMM/yy fields, with month in English abbreviation.
  // note some dates in MBS Stats data are from the 19xx's, necessitating an ugly, pre-Y2K-style hack
  // to figure out if it's 20th or 21st century.  mortgage dates can go quite far in the future, too.
  def monthAndTwoDigitYearToDate(month: String, year: String): Date = {
    if (month.length == 3 && year.length == 2) {
      val century = if (year.toInt > 70) "19" else "20"
      try {
        new SimpleDateFormat("MMM/yyyy").parse(month + "/" + century + year)
      } catch {
        case ex: Exception => throw new NumberFormatException("Could not parse '" + month + "' and '" + year + "' to a date")
      }
    }
    else throw new NumberFormatException("Expected 3-character month and 2-digit year; got '" + month + "' and '" + year + "'")
  }

  // MBS Stats also has some MM/dd/yy fields, with month as two-digit integer.
  // see comments in monthAndTwoDigitYearToDate above about the 2-digit-year problem.
  def monthDayAndTwoDigitYearToDate(month: String, day: String, year: String): Date = {
    if (month.length == 2 && day.length == 2 && year.length == 2) {
      try {
        val century = if (year.toInt > 70) "19" else "20"
        new SimpleDateFormat("MM/dd/yyyy").parse(month + "/" + day + "/" + century + year)
      } catch {
        case ex: Exception => throw new NumberFormatException("Could not parse '" + month + "', '" + day + "', and '" + year + "' to a date")
      }
    }
    else throw new NumberFormatException("Expected 2-digit month, 2-digit day and 2-digit year; got '" + month + "', '" + day + "' and '" + year + "'")
  }

}
