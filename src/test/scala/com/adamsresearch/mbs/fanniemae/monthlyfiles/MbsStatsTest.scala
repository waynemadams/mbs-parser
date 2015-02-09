package com.adamsresearch.mbs.fanniemae.monthlyfiles

import java.io.InputStream
import java.text.SimpleDateFormat

import org.scalatest.FunSuite


/**
 * Created by wma on 1/31/15.
 */
class MbsStatsTest extends FunSuite
{
    val mbsStatsExampleRecord = "31365EJ46 CI125483  00002003473100OCT03000003103340610.1548980406.50003019704011218" +
      "0WASHINGTON MUTUAL BANK, FA              19850 PLUMMER STREET          CHATSWORTH     CA91311070147" +
      "FNMS 06.500 CI125483070170096000000000"

    val record7FromSampleFile = "31403BTR4 CL744060  00000770371700OCT03000007694084860.9987496805.000090103080133356MULTIPLE POOL                                                                          00000053920FNMS 05.000 CL744060000000000000000000"
    val record8FromSampleFile = "31403GND0 LB748388  00000952312900OCT03000009512089400.9988407604.525090103080133358DLJ MORTGAGE CAPITAL INC.               ELEVEN MADISON AVENUE         NEW YORK       NY10010058430FNAR XX.XXX LB748388000000000000000000"

    test("MbsStats record parses correctly") {
      val msOption = MbsStats.parseMbsStatsRecord(mbsStatsExampleRecord)
      msOption match {
        case Some(ms) =>
          assert(ms.cusipNumber == "31365EJ46" &&
                 ms.poolPrefix == "CI" &&
                 ms.poolNumber == 125483 &&
                 ms.poolType == "" &&
                 ms.originalBalance == 20034731.00f &&
                 ms.currentDate.compareTo(new SimpleDateFormat("MMM/yyyy").parse("OCT/2003")) == 0 &&
                 ms.currentBalance == 3103340.61f &&
                 ms.currentFactor == "0.15489804" &&
                 ms.passThroughRate == "06.500" &&
                 ms.issueDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/1997")) == 0 &&
                 ms.maturityDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("04/01/2012")) == 0 &&
                 ms.originalWeightedAverageMaturity == 180 &&
                 ms.sellerName == "WASHINGTON MUTUAL BANK, FA" &&
                 ms.sellerAddressStreet == "19850 PLUMMER STREET" &&
                 ms.sellerAddressCity == "CHATSWORTH" &&
                 ms.sellerAddressState == "CA" &&
                 ms.sellerAddressZip == "91311" &&
                 ms.originalWaCoupon.compareTo(7.0147f) == 0 &&
                 ms.securityType == "FNMS" &&
                 ms.securityInterestRate == "06.500" &&
                 ms.securityPoolPrefix == "CI" &&
                 ms.securityPoolNumber == "125483" &&
                 ms.currentWaCoupon == 70170 &&
                 ms.currentWaMaturity == 96 &&
                 ms.filler == 0)
        case None => fail("Parse returned a None")
      }
    }

    test("Sample file contains 10 records") {
      val iStream = this.getClass().getClassLoader().getResourceAsStream("mbsfact.txt")  // the mbs stats sample file
      assert(MbsStats.retrieveRangeOfRecordsFromStream(iStream, 0, 10).length == 10)
    }

    test("Retrieval of records 7 and 8 of sample file yields correct records") {
      val iStream = this.getClass().getClassLoader().getResourceAsStream("mbsfact.txt")  // the mbs stats sample file
      val records7And8 = MbsStats.retrieveRangeOfRecordsFromStream(iStream, 7, 2)

      val hasTwoRecords = records7And8.length == 2

      val record7_1 = MbsStats.parseMbsStatsRecord(records7And8(0))
      val record7_2 = MbsStats.parseMbsStatsRecord(record7FromSampleFile)
      val record7Matches = record7_1 match {
        case Some(x) => {
          record7_2 match {
              case Some(y) => x equals y
            case None => false
          }
        }
        case None => false
      }

      val record8_1 = MbsStats.parseMbsStatsRecord(records7And8(1))
      val record8_2 = MbsStats.parseMbsStatsRecord(record8FromSampleFile)
      val record8Matches = record8_1 match {
        case Some(x) => {
          record8_2 match {
            case Some(y) => x equals y
            case None => false
          }
        }
        case None => false
      }

      assert(hasTwoRecords && record7Matches && record8Matches)
    }
}
