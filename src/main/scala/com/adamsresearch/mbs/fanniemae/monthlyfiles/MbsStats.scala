package com.adamsresearch.mbs.fanniemae.monthlyfiles

import scala.io.Source

import java.io.InputStream
import java.util.Date

import com.adamsresearch.mbs.common.Utils._

/**
 * Created by wma on 1/31/15.
 */
class MbsStats(val cusipNumber: String,
               val poolPrefix: String,
               val poolNumber: Int,
               val poolType: String,
               val originalBalance: Float,
               val currentDate: Date,
               val currentBalance: Float,
               val currentFactor:  String,    // can be a float >or< a String -- too messy to track, so it's a String!
               val passThroughRate: String,   // same as above
               val issueDate:  Date,
               val maturityDate: Date,
               val originalWeightedAverageMaturity: Int,
               val sellerName: String,
               val sellerAddressStreet: String,
               val sellerAddressCity: String,
               val sellerAddressState: String,
               val sellerAddressZip: String,    // this is numeric in file but can be null; no point in being numeric anyway
               val originalWaCoupon: Float,
               val securityType: String,
               val securityInterestRate: String, // another that can be a string description or a float
               val securityPoolPrefix: String,
               val securityPoolNumber: String,
               val currentWaCoupon: Int,
               val currentWaMaturity: Int,
               val filler: Int) {

  override def equals(other: Any): Boolean = {
    other match {
      case y: MbsStats =>
        this.cusipNumber == y.cusipNumber &&
          this.poolPrefix == y.poolPrefix &&
          this.poolNumber == y.poolNumber &&
          this.poolType == y.poolType &&
          this.originalBalance.compareTo(y.originalBalance) == 0 &&
          this.currentDate.compareTo(y.currentDate) == 0 &&
          this.currentBalance.compareTo(y.currentBalance) == 0 &&
          this.currentFactor == y.currentFactor &&
          this.passThroughRate == y.passThroughRate &&
          this.issueDate.compareTo(y.issueDate) == 0 &&
          this.maturityDate.compareTo(y.maturityDate) == 0 &&
          this.originalWeightedAverageMaturity == y.originalWeightedAverageMaturity &&
          this.sellerName == y.sellerName &&
          this.sellerAddressStreet == y.sellerAddressStreet &&
          this.sellerAddressCity == y.sellerAddressCity &&
          this.sellerAddressState == y.sellerAddressState &&
          this.sellerAddressZip == y.sellerAddressZip &&
          this.originalWaCoupon.compareTo(y.originalWaCoupon) == 0 &&
          this.securityType == y.securityType &&
          this.securityInterestRate == y.securityInterestRate &&
          this.securityPoolPrefix == y.securityPoolPrefix &&
          this.securityPoolNumber == y.securityPoolNumber &&
          this.currentWaCoupon == y.currentWaCoupon &&
          this.currentWaMaturity == y.currentWaMaturity &&
          this.filler == y.filler
      case _ => false
    }
  }

  // next time someone asks you "Why did you use a case class?", you
  // can point out that case classes come with attribute-level equality
  // for free.  if you have to override equals(), then you need to override
  // hashCode(), too.
  // note I have overridden the field, rather than the method, for hashCode.
  // this calculation could become expensive quickly, so at the expense of
  // a little more memory, I only have to calculate it once.  See Odersky
  // section 30.4 "Recipes for equals and hashCode".
  override val hashCode: Int = {
    41 * (
      41 * (
        41 * (
          41 * (
            41 * (
              41 * (
                41 * (
                  41 * (
                    41 * (
                      41 * (
                        41 * (
                          41 * (
                            41 * (
                              41 * (
                                41 * (
                                  41 * (
                                    41 * (
                                      41 * (
                                        41 * (
                                          41 * (
                                            41 * (
                                              41 * (
                                                41 * (
                                                  41 * (
                                                    41 * (
                                                      41 + cusipNumber.hashCode
                                                      ) + poolPrefix.hashCode
                                                    ) + poolNumber  // hashCode for Int is just the Int itself
                                                  ) + poolType.hashCode
                                                ) + originalBalance.hashCode
                                              ) + currentDate.hashCode
                                            ) + currentBalance.hashCode
                                          ) + currentFactor.hashCode
                                        ) + passThroughRate.hashCode
                                      ) + issueDate.hashCode
                                    ) + maturityDate.hashCode
                                  ) + originalWeightedAverageMaturity
                                ) + sellerName.hashCode
                              ) + sellerAddressStreet.hashCode
                            ) + sellerAddressCity.hashCode
                          ) + sellerAddressState.hashCode
                        ) + sellerAddressZip.hashCode
                      ) + originalWaCoupon.hashCode
                    ) + securityType.hashCode
                  ) + securityInterestRate.hashCode
                ) + securityPoolPrefix.hashCode
              ) + securityPoolNumber.hashCode
            ) + currentWaCoupon
          ) + currentWaMaturity
        ) + filler
      )
  }
}

object MbsStats {

  val recordLength = 220

  def parseMbsStatsRecord(record: String): Option[MbsStats] = record.length match {
    case `recordLength` =>
      try {
        // lots of parsing, substring ops, etc.; if there's an issue, return None:
        Some(new MbsStats(record.substring(0, 9).trim,
          record.substring(9, 12).trim,
          record.substring(12, 18).trim.toInt,
          record.substring(18, 20).trim,
          picToFloat(record.substring(20, 34), 12, 2), // PIC 9(12)V99 === 14 digits, 2 right of decimal
          monthAndTwoDigitYearToDate(record.substring(34, 37), record.substring(37, 39)),
          picToFloat(record.substring(39, 53), 12, 2), // also PIC 9(12)V99
          record.substring(53, 63).trim,
          record.substring(63, 69).trim,
          monthDayAndTwoDigitYearToDate(record.substring(69, 71),
            record.substring(71, 73),
            record.substring(73, 75)),
          monthDayAndTwoDigitYearToDate(record.substring(75, 77),
            record.substring(77, 79),
            record.substring(79, 81)),
          record.substring(81, 84).toInt,
          record.substring(84, 124).trim,
          record.substring(124, 154).trim,
          record.substring(154, 169).trim,
          record.substring(169, 171).trim,
          record.substring(171, 176),
          picToFloat(record.substring(176, 182), 2, 4), // PIC 9(02)V9(04)
          record.substring(182, 187).trim,
          record.substring(187, 193).trim,
          record.substring(193, 196).trim,
          record.substring(196, 202).trim,
          record.substring(202, 208).trim.toInt,
          record.substring(208, 211).trim.toInt,
          record.substring(211, 220).trim.toInt
        ))
      } catch {
        case ex: Exception =>
          println(ex.getMessage)
          None
      }
    case _ => None
  }

  def parseMbsStatsRecordsFromFile(filename: String): List[MbsStats] = {
    // there isn't so much as a single newline in these files...  chop up into 220-byte segments,
    // create a list of Seq[Char], then map over the sequence calling mkString, to get List[String]
    val lines = Source.fromFile(filename).grouped(recordLength).toList.map(seqChar => seqChar.mkString)
    val iter = for {
      line <- lines
      mbsStat = parseMbsStatsRecord(line.toString)
      if mbsStat.isInstanceOf[Some[MbsStats]]
    } yield mbsStat.get
    iter
  }

  // reads and discards recordLength bytes at a time until it reaches the first desired
  // record, then reads and keeps the specified number of records and returns a List[String].
  // typical usage is to get a FileInputStream on an MBS stats file, pass that to this function.
  def retrieveRangeOfRecordsFromStream(iStream: InputStream, start: Int, total: Int): List[String] = {
    try {
      def loop(stream: InputStream, currentTotal: Int, currentList: List[String]): List[String] = {
        (total - currentTotal) match {
          case 0 => currentList
          case _ => {
            // get the next record:
            val buffer = new Array[Byte](recordLength)
            stream.read(buffer) match {
              case `recordLength` => {
                loop(stream, currentTotal + 1, currentList :+ new String(buffer))
              }
              case _ => Nil
            }
          }
        }
      }

      // figure out how many records to skip, first.  keep in mind that the start parameter is zero-based.
      val bytesToSkip: Long = (recordLength*start).toLong
      iStream.skip(bytesToSkip)
      // loop over file recordLength bytes at a time:
      loop(iStream, 0, Nil)
    } catch {
      // any exception, you get an empty List
      case ex: Exception => Nil
    }
  }

  def main(args: Array[String]): Unit = {
    // your app here
  }
}
