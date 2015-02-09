package com.adamsresearch.mbs.fanniemae.issuancefiles

import java.io.File
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import com.adamsresearch.mbs.common.Utils

import scala.io.Source

/**
 * Created by wma on 1/10/15.
 */
class LoanLevelDisclosureRecord

// PoolLevel record-type note:
//   There is a 7th field called "filler" in the FM docs; it is ignored here and
//   discarded on parse since it contains no usable data.  This record is record-
//   type 1 in the loan-level disclosure file.
case class PoolLevelRecord(recordType: String,                // 1
                           poolNumber: String,                // 2
                           poolPrefix: String,                // 3
                           cusip: String,                     // 4
                           issueDate: Date,                   // 5
                           poolCorrectionIndicator: Boolean)  // 6
  extends LoanLevelDisclosureRecord

// LoanLevelRecord record-type note:
//     This record type is somewhat overloaded; if the last 15 fields are blank,
//     it corresponds to a fixed-rate loan; otherwise it's an adjustable-rate
//     loan.  Note this state can also be inferred from field 30, Product Type.
//     This record is record-type 2 in the loan-level disclosure file.
//     Also note for loan-level-disclsoure types, there are actually 50
//     fields; the 50th field is not mentioned in the FM docs, but appears to be a "filler"
//     field much like the 7th field in the pool-level disclosure record.  It is ignored
//     in this data model, but when parsing the record, the pattern matcher needs to know that
//     the record contains 50 fields.
case class LoanLevelRecord(recordType: String,                                   //  1
                           poolNumber: String,                                   //  2
                           loanCorrectionIndicator: Boolean,                     //  3
                           loanIdentifier: String,                               //  4
                           channel: String,                                      //  5
                           sellerName: String,                                   //  6
                           servicerName: String,                                 //  7
                           originalInterestRate: Float,                          //  8
                           currentInterestRate: Float,                           //  9
                           currentNetInterestRate: Float,                        // 10
                           originalUPB: Float,                                   // 11 UPB == unpaid principal balance
                           currentScheduledUPB: Float,                           // 12
                           originalLoanTerm: Int,                                // 13
                           firstPaymentDate: Date,                               // 14
                           loanAge: Int,                                         // 15
                           remainingMonthsToMaturity: Int,                       // 16
                           maturityDate: Date,                                   // 17
                           originalLTV: Int,                                     // 18 LTV == loan to value
                           originalCombinedLTV: Int,                             // 19
                           numberOfBorrowers: Int,                               // 20
                           debtToIncomeRatio: Option[Int],                       // 21
                           creditScore: Option[Int],                             // 22
                           firstTimeHomeBuyerIndicator: Boolean,                 // 23
                           loanPurpose: String,                                  // 24
                           propertyType: String,                                 // 25
                           numberOfUnits: Int,                                   // 26
                           occupancyStatus: String,                              // 27
                           state: String,                                        // 28
                           mortgageInsurancePercentage: Option[Int],             // 29
                           productType: String,                                  // 30
                           prepaymentPremiumTerm: String,                        // 31
                           interestOnlyIndicator: Boolean,                       // 32
                           firstPrincipalAndInterestPaymentDate: Option[Date],   // 33
                           monthsToFirstScheduledAmortization: Option[Int],      // 34
                           // remaining fields only populated for Adjustable-Rate loans:
                           convertibilityIndicator: Option[Boolean],             // 35
                           mortgageMargin: Option[Float],                        // 36
                           netMortgageMargin: Option[Float],                     // 37
                           index: Option[Int],                                   // 38
                           interestRateLookBack: Option[Int],                    // 39
                           maximumInterestRate: Option[Float],                   // 40
                           netMaximumInterestRate: Option[Float],                // 41
                           monthsToNextRateChange: Option[Int],                  // 42
                           nextRateChangeDate: Option[Date],                     // 43
                           rateAdjustmentFrequency: Option[Int],                 // 44
                           initialFixedRatePeriod: Option[Int],                  // 45
                           initialRateCapUpPercent: Option[Float],               // 46
                           initialRateCapDownPercent: Option[Float],             // 47
                           periodicCapUpPercent: Option[Float],                  // 48
                           periodicCapDownPercent: Option[Float])                // 49
  extends LoanLevelDisclosureRecord

object LoanLevelDisclosureRecord {

  def main(args: Array[String]): Unit = {
    val fileName = "/home/wma/data/fannie-mae-mbs/LLD01092015.txt"

    println("Loan-level disclosure file name: " + fileName)
    val lldContents = Source.fromFile(fileName)
    val lldLines = lldContents.getLines().toList

    val poolLevelRecords = extractPoolLevelRecords(lldLines)
    println("  Found " + poolLevelRecords.length + " pool-level disclosure records")
    val loanLevelRecords = extractLoanLevelRecords(lldLines)
    println("  Found " + loanLevelRecords.length + " loan-level disclosure records")

    println("\n\n" + Calendar.getInstance().getTime() + ":")
    println("Now, attempting to get a ton of records from 3 months of data:")
    val ninetyDaysRecords = this.getAllLoanLevelDisclosureRecordsInDirectory("/home/wma/data/fannie-mae-mbs/loanLevelDisclosure")
    println("  There are " + ninetyDaysRecords.length + " records in the directory")
    val ninetyDaysPoolLevel = extractPoolLevelRecords(ninetyDaysRecords)
    println("  Found " + ninetyDaysPoolLevel.length + " pool-level disclosure records")
    val ninetyDaysLoanLevel = extractLoanLevelRecords(ninetyDaysRecords)
    println("  Found " + ninetyDaysLoanLevel.length + " total loan-level disclosure records")
    println("  Found " + ninetyDaysLoanLevel.filter((record) => record.productType == "FRM").length + " fixed-rate loan-level disclosure records")
    println("  Found " + ninetyDaysLoanLevel.filter((record) => record.productType == "ARM").length + " adjustable-rate loan-level disclosure records")
    println(Calendar.getInstance().getTime())

  }

  def getAllLoanLevelDisclosureRecordsInDirectory(dir: String): List[String] = {
    def loop(fileList: List[File], pldList: List[String]): List[String] = fileList match {
      case Nil => pldList
      case x :: xs => loop(xs, pldList ::: Source.fromFile(x).getLines().toList)
    }
    loop(new File(dir).listFiles().toList, Nil)
  }

  // note:  single-level directory parse; does not recurse through directory structure
  def extractPoolLevelRecordsInDir(dir: String): List[PoolLevelRecord] = {
    val records = getAllLoanLevelDisclosureRecordsInDirectory(dir)
    extractPoolLevelRecords(records)
  }

  // given an Iterator over the lines in a source file, retrieve a
  // List of all the contained pool-level disclosure records:
  def extractPoolLevelRecords(lines: List[String]): List[PoolLevelRecord] = {
    val iter = for {
      line <- lines
      pld = parsePoolLevelRecord(line)
      if pld.isInstanceOf[Some[PoolLevelRecord]]
    } yield pld.get
    iter.toList
  }

  def parsePoolLevelRecord(record: String): Option[PoolLevelRecord] = {
    try {
      val fields = record.split("\\|")
      fields.length match {
        case 7 =>
          fields(0) match {
            case "1" => Some(PoolLevelRecord(fields(0).trim,
                                                  fields(1).trim,
                                                  fields(2).trim,
                                                  fields(3).trim,
                                                  new SimpleDateFormat("MM/dd/yyyy").parse(fields(4)),
                                                  Utils.yesNoBoolean(fields(5))))
            case _ => None
          }
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  // given a List of lines in a source file, retrieve a List of
  // all the contained loan-level disclosure records:
  def extractLoanLevelRecords(lines: List[String]): List[LoanLevelRecord] = {
    val iter = for {
      line <- lines
      llrOption = parseLoanLevelRecord(line)
      if llrOption.isInstanceOf[Some[LoanLevelRecord]]
    } yield llrOption.get
    iter.toList
  }

  def parseLoanLevelRecord(record: String): Option[LoanLevelRecord] = {
    try {
      val fields = record.split("\\|", 50)
      fields.length match {
        case 50 =>
          fields(0) match {
            case "2" =>
              Some(LoanLevelRecord(fields(0).trim,
                                   fields(1).trim,
                                   Utils.yesNoBoolean(fields(2)),
                                   fields(3).trim,
                                   fields(4).trim,
                                   fields(5).trim,
                                   fields(6).trim,
                                   fields(7).trim.toFloat,
                                   fields(8).trim.toFloat,
                                   fields(9).trim.toFloat,
                                   fields(10).trim.toFloat,
                                   fields(11).trim.toFloat,
                                   fields(12).trim.toInt,
                                   new SimpleDateFormat("MM/dd/yyyy").parse(fields(13)),
                                   fields(14).trim.toInt,
                                   fields(15).trim.toInt,
                                   new SimpleDateFormat("MM/yyyy").parse(fields(16)),
                                   fields(17).trim.toInt,
                                   fields(18).trim.toInt,
                                   fields(19).trim.toInt,
                                   Utils.optionInt(fields(20)),
                                   Utils.optionInt(fields(21)),
                                   Utils.yesNoBoolean(fields(22)),
                                   fields(23).trim,
                                   fields(24).trim,
                                   fields(25).trim.toInt,
                                   fields(26).trim,
                                   fields(27).trim,
                                   Utils.optionInt(fields(28)),
                                   fields(29).trim,
                                   fields(30).trim,
                                   Utils.yesNoBoolean(fields(31)),
                                   Utils.optionMonthDayYear(fields(32)),
                                   Utils.optionInt(fields(33)),
                                   // following fields populated only for adjustable-rate loans:
                                   Utils.optionYesNoBoolean(fields(34)),
                                   Utils.optionFloat(fields(35)),
                                   Utils.optionFloat(fields(36)),
                                   Utils.optionInt(fields(37)),
                                   Utils.optionInt(fields(38)),
                                   Utils.optionFloat(fields(39)),
                                   Utils.optionFloat(fields(40)),
                                   Utils.optionInt(fields(41)),
                                   Utils.optionMonthDayYear(fields(42)),
                                   Utils.optionInt(fields(43)),
                                   Utils.optionInt(fields(44)),
                                   Utils.optionFloat(fields(45)),
                                   Utils.optionFloat(fields(46)),
                                   Utils.optionFloat(fields(47)),
                                   Utils.optionFloat(fields(48))))
            case _ => None
          }
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

}
