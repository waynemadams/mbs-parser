package com.adamsresearch.mbs.fanniemae.issuancefiles

import java.text.SimpleDateFormat

import org.scalatest.FunSuite

/**
 * Created by wma on 1/22/15.
 */
class NewIssuePoolStatisticsRecordTest extends FunSuite {

  val recordType1Example = "AL6280|01|3138EN6S3|12/01/2014||$63,676,686.00|4.0|||||||10/01/2044|||||||4.69|||||355||||||CL  |||"
  val recordType2Example = "AV2422|02|75%|225040.0|4.875|97.0|736|360|2|359"
  val recordType3Example = "AV2421|03|PURCHASE|59|100.0|$11,322,173.96"
  val recordType4Example = "AV2421|04|1|59|100.0|$11,322,173.96"
  val recordType5Example = "AV2422|05|PRINCIPAL RESIDENCE|6|100.0|$1,173,375.26"
  val recordType6Example = "AX4930|06|RELOCATION|177|100.0|$50,890,104.07"
  val recordType7Example = "AM7706|07|02/01/17|1|0.0|$17,880,000.00"
  val recordType8Example = "AM7706|08|2014|1|100.0|$17,880,000.00"
  val recordType9Example = "AV2421|09|COLORADO|59|100.0|$11,322,173.96"
  val recordType10Example = "AY1918|10|UMPQUA BANK|6|100.0|$1,704,507.31"
  val recordType11Example = "AY1918|11|11/01/2014|BELOW -  5.00|18.21|$310,457.31"
  val recordType12Example = "AY1918|12|BELOW -  5.00|6|$1,704,507.31"
  val recordType13Example = "AM7651|13|2.05|1|$11,700,000.00"
  val recordType14Example = "AM7651|14|02/01/2015|100.0|0.37|0.37|0.37|0.532|0.532|0.532|99.999|99.999|0.37|0.37"
  val recordType15Example = "AM7689|15|0.95|1.111|6.0|0.95"
  val recordType16Example = "AM7655|16|Declining Premium|09/30/2021|81.000"
  val recordType17Example = "AV2421|17|CORRESPONDENT|59|100.0|$11,322,173.96"

  test("Pool Statistic (record type 1) parses correctly") {
    val psOption = NewIssuePoolStatisticsRecord.parsePoolStatisticsRecord(recordType1Example)
    psOption match {
      case Some(ps) =>
        assert(ps.poolNumber == "AL6280" &&
          ps.recordType == "01" &&
          ps.cusip == "3138EN6S3" &&
          ps.poolIssueDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("12/01/2014")) == 0 &&
          ps.poolIssueAmount.compareTo(63676686.00f) == 0 &&
          ps.percentPassThroughRate.compareTo(4.0f) == 0 &&
          ps.maturityDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("10/01/2044")) == 0 &&
          ps.wACoupon.compareTo(4.69f) == 0 &&
          ps.waRemainingMaturityAtIssuance == 355 &&
          ps.prefix == "CL")
      case None => fail("Did not successfully parse as a Pool Statistic record")
    }
  }

  test("Quartile (record type 2) parses correctly") {
    val qOption = NewIssuePoolStatisticsRecord.parseQuartileRecord(recordType2Example)
    qOption match {
      case Some(x) =>
        assert(x.poolNumber == "AV2422" &&
          x.recordType == "02" &&
          x.quartileLevel == "75%" &&
          x.loanSize.compareTo(225040.0f) == 0 &&
          x.coupon.compareTo(4.875f) == 0 &&
          x.loanToValue.compareTo(97.0f) == 0 &&
          x.creditScore == 736 &&
          x.loanTerm == 360 &&
          x.loanAge == 2 &&
          x.remainingMaturity == 359)
      case None => fail("Did not successfully parse as a Quartile record")
    }
  }

  test("Loan Purpose (record type 3) parses correctly") {
    val lpOption = NewIssuePoolStatisticsRecord.parseLoanPurposeRecord(recordType3Example)
    lpOption match {
      case Some(x) =>
        assert(x.poolNumber == "AV2421" &&
          x.recordType == "03" &&
          x.loanPurposeType == "PURCHASE" &&
          x.numberOfLoans == 59 &&
          x.percentageUPB.compareTo(100.0f) == 0 &&
          x.aggregateUPB.compareTo(11322173.96f) == 0)
      case None => fail("Did not successfully parse as a Loan Purpose record")
    }
  }

  test("Property Type (record type 4) parses correctly") {
    val ptOption = NewIssuePoolStatisticsRecord.parsePropertyTypeRecord(recordType4Example)
    ptOption match {
      case Some(x) =>
        assert(x.poolNumber == "AV2421" &&
          x.recordType == "04" &&
          x.numberOfUnits == 1 &&
          x.numberOfLoans == 59 &&
          x.percentageUPB.compareTo(100.0f) == 0 &&
          x.aggregateUPB.compareTo(11322173.96f) == 0)
      case None => fail("Did not successfully parse as a Property Type record")
    }
  }

  test("Occupancy Type (record type 5) parses correctly") {
    val otOption = NewIssuePoolStatisticsRecord.parseOccupancyTypeRecord(recordType5Example)
    otOption match {
      case Some(x) =>
        assert(x.poolNumber == "AV2422" &&
          x.recordType == "05" &&
          x.occupancyType == "PRINCIPAL RESIDENCE" &&
          x.numberOfLoans == 6 &&
          x.percentageUPB.compareTo(100.0f) == 0 &&
          x.aggregateUPB.compareTo(1173375.26f) == 0)
      case None => fail("Did not successfully parse as an Occupancy Type record")
    }
  }

  test("Non Standard Loans (record type 6) parses correctly") {
    val nslOption = NewIssuePoolStatisticsRecord.parseNonStandardLoansRecord(recordType6Example)
    nslOption match {
      case Some(x) =>
        assert(x.poolNumber == "AX4930" &&
          x.recordType == "06" &&
          x.nonStandardLoanType == "RELOCATION" &&
          x.numberOfLoans == 177 &&
          x.percentageUPB.compareTo(100.0f) == 0 &&
          x.aggregateUPB.compareTo(50890104.07f) == 0)
      case None => fail("Did not successfully parse as a Non Standard Loans record")
    }
  }

  test("First Scheduled Amortization (record type 7) parses correctly") {
    val fsaOption = NewIssuePoolStatisticsRecord.parseFirstScheduledAmortizationRecord(recordType7Example)
    fsaOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7706" &&
          x.recordType == "07" &&
          x.date.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("02/01/2017")) == 0 &&
          x.numberOfLoans == 1 &&
          x.percentageUPB.compareTo(0.0f) == 0 &&
          x.aggregateUPB.compareTo(17880000.00f) == 0)
      case None => fail("Did not successfully parse as a First Scheduled Amortization record")
    }
  }

  test("Origination Year (record type 8) parses correctly") {
    val oyOption = NewIssuePoolStatisticsRecord.parseOriginationYearRecord(recordType8Example)
    oyOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7706" &&
          x.recordType == "08" &&
          x.year == 2014 &&
          x.numberOfLoans == 1 &&
          x.percentageUPB.compareTo(100.0f) == 0 &&
          x.aggregateUPB.compareTo(17880000.00f) == 0)
      case None => fail("Did not successfully parse as an Origination Year record")
    }
  }

  test("Geographic Distribution (record type 9) parses correctly") {
    val gdOption = NewIssuePoolStatisticsRecord.parseGeographicDistributionRecord(recordType9Example)
    gdOption match {
      case Some(x) =>
        assert(x.poolNumber == "AV2421" &&
          x.recordType == "09" &&
          x.state == "COLORADO" &&
          x.numberOfLoans == 59 &&
          x.percentageUPB.compareTo(100.0f) == 0 &&
          x.aggregateUPB.compareTo(11322173.96f) == 0)
      case None => fail("Did not successfully parse as a Geographic Distribution record")
    }
  }

  test("Servicer (record type 10) parses correctly") {
    val sOption = NewIssuePoolStatisticsRecord.parseServicerRecord(recordType10Example)
    sOption match {
      case Some(x) =>
        assert(x.poolNumber == "AY1918" &&
          x.recordType == "10" &&
          x.servicerName == "UMPQUA BANK" &&
          x.numberOfLoans == 6 &&
          x.percentageUPB.compareTo(100.0f) == 0 &&
          x.aggregateUPB.compareTo(1704507.31f) == 0)
      case None => fail("Did not successfully parse as a Servicer record")
    }
  }

  test("Distribution of Loans by First Payment Date (record type 11) parses correctly") {
    val dlfpdOption = NewIssuePoolStatisticsRecord.parseDistributionOfLoansByFirstPaymentDateRecord(recordType11Example)
    dlfpdOption match {
      case Some(x) =>
        assert(x.poolNumber == "AY1918" &&
          x.recordType == "11" &&
          x.date.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2014")) == 0 &&
          x.originalInterestRate == "BELOW -  5.00" &&
          x.percentageOfLoans.compareTo(18.21f) == 0 &&
          x.aggregateUPB.compareTo(310457.31f) == 0)
      case None => fail("Did not successfully parse as a Distribution of Loans by First Payment Date record")
    }
  }

  test("Current Interest Rate (record type 12) parses correctly") {
    val cirOption = NewIssuePoolStatisticsRecord.parseCurrentInterestRateRecord(recordType12Example)
    cirOption match {
      case Some(x) =>
        assert(x.poolNumber == "AY1918" &&
          x.recordType == "12" &&
          x.currentMortgageInterestRate == "BELOW -  5.00" &&
          x.numberOfLoans == 6 &&
          x.aggregateUPB.compareTo(1704507.31f) == 0)
      case None => fail("Did not successfully parse as a Current Interest Rate record")
    }
  }

  test("Gross Margin (record type 13) parses correctly") {
    val gmOption = NewIssuePoolStatisticsRecord.parseGrossMarginRecord(recordType13Example)
    gmOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7651" &&
          x.recordType == "13" &&
          x.grossMargins.compareTo(2.05f) == 0 &&
          x.numberOfLoans == 1 &&
          x.aggregateUPB.compareTo(11700000.00f) == 0)
      case None => fail("Did not successfully parse as a Gross Margin record")
    }
  }

  test("Next Rate Change Date (record type 14) parses correctly") {
    val nrcdOption = NewIssuePoolStatisticsRecord.parseNextRateChangeDateRecord(recordType14Example)
    nrcdOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7651" &&
               x.recordType == "14" &&
               x.date.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("02/01/2015")) == 0 &&
               x.percentageOfBalance.compareTo(100.0f) == 0 &&
               x.mbsMarginHigh.compareTo(0.37f) == 0 &&
               x.mbsMarginLow.compareTo(0.37f) == 0 &&
               x.mbsMargin.compareTo(0.37f) == 0 &&
               x.netCouponHigh.compareTo(0.532f) == 0 &&
               x.netCouponLow.compareTo(0.532f) == 0 &&
               x.waNetCoupon.compareTo(0.532f) == 0 &&
               x.netLifeCapsHigh.compareTo(99.999f) == 0 &&
               x.netLifeCapsLow.compareTo(99.999f) == 0 &&
               x.netLifeFloorHigh.compareTo(0.37f) == 0 &&
               x.netLifeFloorLow.compareTo(0.37f) == 0)
      case None => fail("Did not successfully parse as a Next Rate Change Date record")

    }
  }

  test ("Weighted Average for Next Rate Change Date (record type 15) parses correctly") {
    val wanrcdOption = NewIssuePoolStatisticsRecord.parseWeightedAverageForNextRateChangeDateRecord(recordType15Example)
    wanrcdOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7689" &&
               x.recordType == "15" &&
               x.waMbsMargin.compareTo(0.95f) == 0 &&
               x.waNetCoupon.compareTo(1.111f) == 0 &&
               x.waNetLifeCaps.compareTo(6.0f) == 0 &&
               x.waNetLifeFloor.compareTo(0.95f) == 0)
      case None => fail("Did not successfully parse as a Weighted Average for Next Rate Change Date record")
    }
  }

  test ("Aggregate Prepayment Premium (record type 16) parses correctly") {
    val appOption = NewIssuePoolStatisticsRecord.parseAggregatePrepaymentPremiumRecord(recordType16Example)
    appOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7655" &&
               x.recordType == "16" &&
               x.prepaymentPremiumOption == "Declining Premium" &&
               x.latestPrepaymentPremiumEndDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("09/30/2021")) == 0 &&
               x.waPrepaymentPremiumTerm.compareTo(81.000f) == 0)
      case None => fail("Did not successfully parse as a Aggregate Prepayment Premium record")
    }
  }

  test ("Origination Type (record type 17) parses correctly") {
    val otOption = NewIssuePoolStatisticsRecord.parseOriginationTypeRecord(recordType17Example)
    otOption match {
      case Some(x) =>
        assert(x.poolNumber == "AV2421" &&
               x.recordType == "17" &&
               x.originationType == "CORRESPONDENT" &&
               x.numberOfLoans == 59 &&
               x.percentageUPB.compareTo(100.0f) == 0 &&
               x.aggregateUPB.compareTo(11322173.96f) == 0)
      case None => fail("Did not successfully parse as an Origination Type record")
    }
  }

}


