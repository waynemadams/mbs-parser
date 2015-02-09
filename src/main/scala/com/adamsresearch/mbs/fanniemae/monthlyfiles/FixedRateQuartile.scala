package com.adamsresearch.mbs.fanniemae.monthlyfiles

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by wma on 2/2/15.
 *
 * TODO:  parse these COBOL PICTURE elements...
 */
class FixedRateQuartile

case class FixedQuartilesHeader(quartileRecordType: String,
                                poolNumber: String,
                                prefix: String,
                                reportingPeriod: Date,  // yyyyMMdd
                                cusipNumber: String,
                                issueDate: Date)        // yyyyMMdd
  extends FixedRateQuartile

case class FixedQuartilesDetails(quartileRecordType: String)
  extends FixedRateQuartile

object FixedRateQuartile {

  val recordLength = 199

  // parses a record, returning either a Header or Detail-type record
  // depending on the quartileRecordType field, or None if we have supplied
  // something that doesn't work.
  def parseFixedRateQuartile(record: String): Option[FixedRateQuartile] = {
    try {
      record.length match {
        case `recordLength` =>
          // look at first char, which tells us if this is a header or details record:
          record.substring(0, 1) match {
            case "1" =>
              Some(new FixedQuartilesHeader(record.substring(0, 1),
                                            record.substring(1, 7).trim,
                                            record.substring(7, 10).trim,
                                            new SimpleDateFormat("yyyyMMdd").parse(record.substring(10, 16)),
                                            record.substring(16, 25).trim,
                                            new SimpleDateFormat("yyyyMMdd").parse(record.substring(25, 34))

              ))
            case "2" =>
              Some(new FixedQuartilesDetails(record.substring(0, 1)))
            case _ => None
          }
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }
}
