package com.adamsresearch.mbs.fanniemae.issuancefiles

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import com.adamsresearch.mbs.common.Utils

import scala.io.Source

/**
 * Created by wma on 1/21/15.
 *
 * Fannie Mae (FM) New Issue Pool Statistics file processing.
 *
 * note:  Column numbers in comments are 1-based, rather than 0-based,
 *        to match the column numbers in the FM documentation.
 *
 * note:  New Issue Pool Statistics FM documentation does not
 *        specify the data types of the columns!  Types here
 *        have been inferred from inspecting data files.  The
 *        use of Option has also been inferred from data inspection.
 */
class NewIssuePoolStatisticsRecord

// record type "01"
case class PoolStatistics(poolNumber: String,                           //  1
                          recordType: String,                           //  2
                          cusip: String,                                //  3
                          poolIssueDate: Date,                          //  4   MM/dd/YYYY
                          poolSecurityDescription: String,              //  5
                          poolIssueAmount: Float,                       //  6
                          percentPassThroughRate: Float,                //  7
                          initialPoolAccrualRate: Option[Float],        //  8
                          pIInitialPaymentDate: Option[Date],           //  9   pI = principal and interest; MM/dd/YYYY
                          seller: String,                               // 10
                          servicer: String,                             // 11
                          numberOfMortgageLoans: Option[Int],           // 12
                          averageLoanSize: Option[Float],               // 13
                          maturityDate: Date,                           // 14   MM/dd/YYYY
                          initialInterestRateChangeDate: Option[Date],  // 15   MM/dd/YYYY
                          wAMonthsToRoll: Option[Int],                  // 16   wA = weighted average
                          subtype: String,                              // 17
                          convertible: Boolean,                         // 18
                          transferType: String,                         // 19
                          passThroughMethod: String,                    // 20
                          wACoupon: Float,                              // 21
                          wAMaximumPoolAccrualRate: Option[Float],      // 22
                          waMinimumPoolAccrualRate: Option[Float],      // 23
                          waLoanAge: Option[Int],                       // 24
                          waLoanTermOrAmortizationTerm: Option[Int],    // 25   for non-balloon or balloon, respectively
                          waRemainingMaturityAtIssuance: Int,           // 26
                          waLoanToValue: Option[Int],                   // 27
                          waCreditScore: Option[Int],                   // 28
                          percentUPBWithoutCreditScore: Option[Int],    // 29   UPB = unpaid balance
                          percentUPBWithInterestOnly:  Option[Int],     // 30
                          percentUPBWithFullyAmortizing: Option[Int],   // 31
                          prefix: String,                               // 32
                          firstPaymentChangeDate: Option[Date],         // 33
                          percentUPBWithTPO: Option[Float],             // 34   TPO = third-party origination
                          waCombinedLTVRatio: Option[Int])              // 35   LTV = loan to value
    extends NewIssuePoolStatisticsRecord

// record type "02"
case class Quartile(poolNumber: String,       //  1
                    recordType: String,       //  2
                    quartileLevel: String,    //  3
                    loanSize: Float,          //  4
                    coupon: Float,            //  5
                    loanToValue: Float,       //  6
                    creditScore: Int,         //  7
                    loanTerm: Int,            //  8
                    loanAge: Int,             //  9
                    remainingMaturity: Int)   // 10
    extends NewIssuePoolStatisticsRecord

// record type "03"
case class LoanPurpose(poolNumber: String,        //  1
                       recordType: String,        //  2
                       loanPurposeType: String,   //  3
                       numberOfLoans: Int,        //  4
                       percentageUPB: Float,      //  5  UPB = unpaid balance
                       aggregateUPB: Float)       //  6
    extends NewIssuePoolStatisticsRecord

// record type "04"
case class PropertyType(poolNumber: String,     //  1
                        recordType: String,     //  2
                        numberOfUnits: Int,     //  3
                        numberOfLoans: Int,     //  4
                        percentageUPB: Float,   //  5 UPB = unpaid balance
                        aggregateUPB: Float)    //  6
    extends NewIssuePoolStatisticsRecord

// record type "05"
case class OccupancyType(poolNumber: String,      //  1
                         recordType: String,      //  2
                         occupancyType: String,   //  3
                         numberOfLoans: Int,      //  4
                         percentageUPB: Float,    //  5 UPB = unpaid balance
                         aggregateUPB: Float)     //  6
    extends NewIssuePoolStatisticsRecord

// record type "06"
case class NonStandardLoans(poolNumber: String,            //  1
                            recordType: String,            //  2
                            nonStandardLoanType: String,   //  3
                            numberOfLoans: Int,            //  4
                            percentageUPB: Float,          //  5 UPB = unpaid balance
                            aggregateUPB: Float)           //  6
    extends NewIssuePoolStatisticsRecord

// record type "07"
case class FirstScheduledAmortization(poolNumber: String,      //  1
                                      recordType: String,      //  2
                                      date: Date,              //  3
                                      numberOfLoans: Int,      //  4
                                      percentageUPB: Float,    //  5 UPB = unpaid balance
                                      aggregateUPB: Float)     //  6
    extends NewIssuePoolStatisticsRecord

// record type "08"
case class OriginationYear(poolNumber: String,      //  1
                           recordType: String,      //  2
                           year: Int,               //  3
                           numberOfLoans: Int,      //  4
                           percentageUPB: Float,    //  5 UPB = unpaid balance
                           aggregateUPB: Float)     //  6
    extends NewIssuePoolStatisticsRecord

// record type "09"
case class GeographicDistribution(poolNumber: String,      //  1
                                  recordType: String,      //  2
                                  state: String,           //  3
                                  numberOfLoans: Int,      //  4
                                  percentageUPB: Float,    //  5 UPB = unpaid balance
                                  aggregateUPB: Float)     //  6
    extends NewIssuePoolStatisticsRecord

// record type "10"
case class Servicer(poolNumber: String,      //  1
                    recordType: String,      //  2
                    servicerName: String,    //  3
                    numberOfLoans: Int,      //  4
                    percentageUPB: Float,    //  5 UPB = unpaid balance
                    aggregateUPB: Float)     //  6
    extends NewIssuePoolStatisticsRecord

// record type "11"
case class DistributionOfLoansByFirstPaymentDate(poolNumber: String,             //  1
                                                 recordType: String,             //  2
                                                 date: Date,                     //  3
                                                 originalInterestRate: String,   //  4  more like a rate type
                                                 percentageOfLoans: Float,       //  5
                                                 aggregateUPB: Float)            //  6 UPB = unpaid balance
    extends NewIssuePoolStatisticsRecord

// record type "12"
case class CurrentInterestRate(poolNumber: String,                    // 1
                               recordType: String,                    // 2
                               currentMortgageInterestRate: String,   // 3
                               numberOfLoans: Int,                    // 4
                               aggregateUPB: Float)                   // 5 UPB = unpaid balance
    extends NewIssuePoolStatisticsRecord

// record type "13"
case class GrossMargin(poolNumber: String,     // 1
                       recordType: String,     // 2
                       grossMargins: Float,    // 3
                       numberOfLoans: Int,     // 4
                       aggregateUPB: Float)    // 5 UPB = unpaid balance
    extends NewIssuePoolStatisticsRecord

// record type "14"
case class NextRateChangeDate(poolNumber: String,           //  1
                              recordType: String,           //  2
                              date: Date,                   //  3
                              percentageOfBalance: Float,   //  4
                              mbsMarginHigh: Float,         //  5
                              mbsMarginLow: Float,          //  6
                              mbsMargin: Float,             //  7
                              netCouponHigh: Float,         //  8
                              netCouponLow: Float,          //  9
                              waNetCoupon: Float,           // 10
                              netLifeCapsHigh: Float,       // 11
                              netLifeCapsLow: Float,        // 12
                              netLifeFloorHigh: Float,      // 13
                              netLifeFloorLow: Float)       // 14
    extends NewIssuePoolStatisticsRecord

// record type "15"
case class WeightedAverageForNextRateChangeDate(poolNumber: String,      // 1
                                                recordType: String,      // 2
                                                waMbsMargin: Float,      // 3
                                                waNetCoupon: Float,      // 4
                                                waNetLifeCaps: Float,    // 5
                                                waNetLifeFloor: Float)   // 6
    extends NewIssuePoolStatisticsRecord

// record type "16"
case class AggregatePrepaymentPremium(poolNumber: String,                     // 1
                                      recordType: String,                     // 2
                                      prepaymentPremiumOption: String,        // 3
                                      latestPrepaymentPremiumEndDate: Date,   // 4 MM/dd/YYYY
                                      waPrepaymentPremiumTerm: Float)         // 5 wa = weighted average
    extends NewIssuePoolStatisticsRecord

// record type "17"
case class OriginationType(poolNumber: String,        //  1
                           recordType: String,        //  2
                           originationType: String,   //  3
                           numberOfLoans: Int,        //  4
                           percentageUPB: Float,      //  5 UPB = unpaid balance
                           aggregateUPB: Float)       //  6
    extends NewIssuePoolStatisticsRecord

object NewIssuePoolStatisticsRecord {

  def main(args: Array[String]): Unit = {
    println("Hello, New Issue Pool Statistics!")
  }

  def getAllRecordsFromFile(fileName: String): List[String] = {
    Source.fromFile(fileName).getLines().toList
  }

  def getAllRecordsFromDirectory(dirName: String): List[String] = {
    def loop(fileList: List[File], currentRecords: List[String]): List[String] = fileList match {
      case Nil => currentRecords
      case x :: xs => Source.fromFile(x).getLines().toList ::: currentRecords
    }
    loop((new File(dirName)).listFiles().toList, Nil)
  }

  def parsePoolStatisticsRecord(record: String): Option[PoolStatistics] = {
    try {
      val fields = record.split("\\|", 35)  // kind of overkill, but can have multiple trailing empty fields
      fields.length match {
        case 35 => {
          Some(
            PoolStatistics(fields(0).trim,
              fields(1).trim,
              fields(2).trim,
              new SimpleDateFormat("MM/dd/yyyy").parse(fields(3).trim),
              fields(4).trim,
              Utils.dollarToFloat(fields(5)),
              fields(6).trim.toFloat,
              Utils.optionFloat(fields(7)),
              Utils.optionMonthDayYear(fields(8)),
              fields(9).trim,
              fields(10).trim,
              Utils.optionInt(fields(11)),
              Utils.optionFloat(fields(12)),
              new SimpleDateFormat("MM/dd/yyyy").parse(fields(13).trim),
              Utils.optionMonthDayYear(fields(14)),
              Utils.optionInt(fields(15)),
              fields(16).trim,
              Utils.yesNoBoolean(fields(17)),
              fields(18).trim,
              fields(19).trim,
              fields(20).trim.toFloat,
              Utils.optionFloat(fields(21)),
              Utils.optionFloat(fields(22)),
              Utils.optionInt(fields(23)),
              Utils.optionInt(fields(24)),
              fields(25).trim.toInt,
              Utils.optionInt(fields(26)),
              Utils.optionInt(fields(27)),
              Utils.optionInt(fields(28)),
              Utils.optionInt(fields(29)),
              Utils.optionInt(fields(30)),
              fields(31).trim,
              Utils.optionMonthDayYear(fields(32)),
              Utils.optionFloat(fields(33)),
              Utils.optionInt(fields(34)))
          )
        }
        case _ => None
      }

    } catch {
      case ex: Exception => {
        ex.printStackTrace()
        None
      }
    }
  }

  def parseQuartileRecord(record: String): Option[Quartile] = {
    try {
      val fields = record.split("\\|", 10)
      fields.length match {
        case 10 => Some(
            Quartile(fields(0).trim,
                     fields(1).trim,
                     fields(2).trim,
                     fields(3).trim.toFloat,
                     fields(4).trim.toFloat,
                     fields(5).trim.toFloat,
                     fields(6).trim.toInt,
                     fields(7).trim.toInt,
                     fields(8).trim.toInt,
                     fields(9).trim.toInt))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseLoanPurposeRecord(record: String): Option[LoanPurpose] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
              LoanPurpose(fields(0).trim,
                          fields(1).trim,
                          fields(2).trim,
                          fields(3).trim.toInt,
                          fields(4).trim.toFloat,
                          Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parsePropertyTypeRecord(record: String): Option[PropertyType] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
            PropertyType(fields(0).trim,
              fields(1).trim,
              fields(2).trim.toInt,
              fields(3).trim.toInt,
              fields(4).trim.toFloat,
              Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseOccupancyTypeRecord(record: String): Option[OccupancyType] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
            OccupancyType(fields(0).trim,
              fields(1).trim,
              fields(2).trim,
              fields(3).trim.toInt,
              fields(4).trim.toFloat,
              Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseNonStandardLoansRecord(record: String): Option[NonStandardLoans] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
            NonStandardLoans(fields(0).trim,
              fields(1).trim,
              fields(2).trim,
              fields(3).trim.toInt,
              fields(4).trim.toFloat,
              Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseFirstScheduledAmortizationRecord(record: String): Option[FirstScheduledAmortization] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
            FirstScheduledAmortization(fields(0).trim,
              fields(1).trim,
              Utils.parseTwoDigitYear(fields(2)),
              fields(3).trim.toInt,
              fields(4).trim.toFloat,
              Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseOriginationYearRecord(record: String): Option[OriginationYear] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
            OriginationYear(fields(0).trim,
              fields(1).trim,
              fields(2).trim.toInt,
              fields(3).trim.toInt,
              fields(4).trim.toFloat,
              Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseGeographicDistributionRecord(record: String): Option[GeographicDistribution] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
            GeographicDistribution(fields(0).trim,
              fields(1).trim,
              fields(2).trim,
              fields(3).trim.toInt,
              fields(4).trim.toFloat,
              Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseServicerRecord(record: String): Option[Servicer] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
            Servicer(fields(0).trim,
              fields(1).trim,
              fields(2).trim,
              fields(3).trim.toInt,
              fields(4).trim.toFloat,
              Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseDistributionOfLoansByFirstPaymentDateRecord(record: String): Option[DistributionOfLoansByFirstPaymentDate] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
          DistributionOfLoansByFirstPaymentDate(fields(0).trim,
                                                fields(1).trim,
                                                new SimpleDateFormat("MM/dd/yyyy").parse(fields(2).trim),
                                                fields(3).trim,
                                                fields(4).trim.toFloat,
                                                Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseCurrentInterestRateRecord(record: String): Option[CurrentInterestRate] = {
    try {
      val fields = record.split("\\|", 5)
      fields.length match {
        case 5 =>
          Some(
            CurrentInterestRate(fields(0).trim,
              fields(1).trim,
              fields(2).trim,
              fields(3).trim.toInt,
              Utils.dollarToFloat(fields(4))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseGrossMarginRecord(record: String): Option[GrossMargin] = {
    try {
      val fields = record.split("\\|", 5)
      fields.length match {
        case 5 =>
          Some(
            GrossMargin(fields(0).trim,
              fields(1).trim,
              fields(2).trim.toFloat,
              fields(3).trim.toInt,
              Utils.dollarToFloat(fields(4))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseNextRateChangeDateRecord(record: String): Option[NextRateChangeDate] = {
    try {
      val fields = record.split("\\|", 14)
      fields.length match {
        case 14 =>
          Some(
            NextRateChangeDate(fields(0).trim,
                               fields(1).trim,
                               new SimpleDateFormat("MM/dd/yyyy").parse(fields(2).trim),
                               fields(3).trim.toFloat,
                               fields(4).trim.toFloat,
                               fields(5).trim.toFloat,
                               fields(6).trim.toFloat,
                               fields(7).trim.toFloat,
                               fields(8).trim.toFloat,
                               fields(9).trim.toFloat,
                               fields(10).trim.toFloat,
                               fields(11).trim.toFloat,
                               fields(12).trim.toFloat,
                               fields(13).trim.toFloat))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  case class WeightedAverageForNextRateChangeDate(poolNumber: String,      // 1
                                                  recordType: String,      // 2
                                                  waMbsMargin: Float,      // 3
                                                  waNetCoupon: Float,      // 4
                                                  waNetLifeCaps: Float,    // 5
                                                  waNetLifeFloor: Float)   // 6

  def parseWeightedAverageForNextRateChangeDateRecord(record: String): Option[WeightedAverageForNextRateChangeDate] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
            WeightedAverageForNextRateChangeDate(fields(0).trim,
                                                 fields(1).trim,
                                                 fields(2).trim.toFloat,
                                                 fields(3).trim.toFloat,
                                                 fields(4).trim.toFloat,
                                                 fields(5).trim.toFloat))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseAggregatePrepaymentPremiumRecord(record: String): Option[AggregatePrepaymentPremium] = {
    try {
      val fields = record.split("\\|", 5)
      fields.length match {
        case 5 =>
          Some(
            AggregatePrepaymentPremium(fields(0).trim,
                                       fields(1).trim,
                                       fields(2).trim,
                                       new SimpleDateFormat("MM/dd/yyyy").parse(fields(3).trim),
                                       fields(4).trim.toFloat))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseOriginationTypeRecord(record: String): Option[OriginationType] = {
    try {
      val fields = record.split("\\|", 6)
      fields.length match {
        case 6 =>
          Some(
            OriginationType(fields(0).trim,
              fields(1).trim,
              fields(2).trim,
              fields(3).trim.toInt,
              fields(4).trim.toFloat,
              Utils.dollarToFloat(fields(5))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

}
