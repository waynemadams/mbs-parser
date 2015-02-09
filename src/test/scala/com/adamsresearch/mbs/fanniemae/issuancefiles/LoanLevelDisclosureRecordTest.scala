package com.adamsresearch.mbs.fanniemae.issuancefiles

import java.text.SimpleDateFormat

import org.scalatest.FunSuite

/**
 * Created by wma on 1/20/15.
 */
class LoanLevelDisclosureRecordTest extends FunSuite {
  val poolLevelExample = "1|AY3001| CI|3138YGKP6|01/01/2015|NO |                                                                                                                                                                                                                                                                                                                                                                             "
  val fixedRateLoanLevelExample = "2|AU5599|N|8265260357|CORRESPONDENT|MYCUMORTGAGE, LLC                       |MYCUMORTGAGE, LLC                       | 04.1250| 04.1250| 03.5000| 000074602.00| 000074602.00|360|02/01/2015| 000|360|01/2045|059|080|01|032|632|NO |REFINANCE|SF   |1|PRINCIPAL|KY|   |FRM|NONE |NO |          |   |   |        |        |   |   |        |        |   |          |   |   |        |        |        |        |    "
  val adjustableRateLoanLevelExample = "2|AY2846|N|8265252131|RETAIL       |FIRST NATIONAL BANK OF OMAHA            |FIRST NATIONAL BANK OF OMAHA            | 03.1250| 03.1250| 02.5600| 000140000.00| 000140000.00|360|02/01/2015| 000|360|01/2045|071|071|02|044|804|NO |REFINANCE|SF   |1|PRINCIPAL|NE|   |ARM|NONE |NO |          |   |NO | 02.2500| 01.6850|075|045| 08.1250| 07.5600|084|01/01/2022|012|007| 05.0000| 05.0000| 02.0000| 02.0000|    "

  test("Pool Level Disclosure Record (record type 1) parses correctly") {
    val plrOption = LoanLevelDisclosureRecord.parsePoolLevelRecord(poolLevelExample)
    plrOption match {
      case Some(plr) =>
        assert(plr.recordType == "1" &&
               plr.poolNumber == "AY3001" &&
               plr.poolPrefix == "CI" &&
               plr.cusip == "3138YGKP6" &&
               plr.issueDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2015")) == 0 &&
               !plr.poolCorrectionIndicator)
      case None => fail("Did not successfully parse as a Pool Level Disclosure record")
    }
  }

  test("Fixed-Rate Loan Level Disclosure Record (record type 2) parses correctly") {
    val frllrOption = LoanLevelDisclosureRecord.parseLoanLevelRecord(fixedRateLoanLevelExample)
    frllrOption match {
      case Some(frllr) =>
        assert(frllr.recordType == "2" &&
               frllr.poolNumber == "AU5599" &&
               !frllr.loanCorrectionIndicator &&
               frllr.loanIdentifier == "8265260357" &&
               frllr.channel == "CORRESPONDENT" &&
               frllr.sellerName == "MYCUMORTGAGE, LLC" &&
               frllr.servicerName == "MYCUMORTGAGE, LLC" &&
               frllr.originalInterestRate.compareTo(4.125f) == 0 &&
               frllr.currentInterestRate.compare(4.125f) == 0 &&
               frllr.currentNetInterestRate.compareTo(3.5f) == 0 &&
               frllr.originalUPB.compareTo(74602.00f) == 0 &&
               frllr.currentScheduledUPB.compareTo(74602.00f) == 0 &&
               frllr.originalLoanTerm == 360 &&
               frllr.firstPaymentDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("02/01/2015")) == 0 &&
               frllr.loanAge == 0 &&
               frllr.remainingMonthsToMaturity == 360 &&
               frllr.maturityDate.compareTo(new SimpleDateFormat("MM/yyyy").parse("01/2045")) == 0 &&
               frllr.originalLTV == 59 &&
               frllr.originalCombinedLTV == 80 &&
               frllr.numberOfBorrowers == 1 &&
               frllr.debtToIncomeRatio.get == 32 &&
               frllr.creditScore.get == 632 &&
               !frllr.firstTimeHomeBuyerIndicator &&
               frllr.loanPurpose == "REFINANCE" &&
               frllr.propertyType == "SF" &&
               frllr.numberOfUnits == 1 &&
               frllr.occupancyStatus == "PRINCIPAL" &&
               frllr.state == "KY" &&
               frllr.mortgageInsurancePercentage == None &&
               frllr.productType == "FRM" &&
               frllr.prepaymentPremiumTerm == "NONE" &&
               !frllr.interestOnlyIndicator &&
               frllr.firstPrincipalAndInterestPaymentDate == None &&
               frllr.monthsToFirstScheduledAmortization == None)
      case None => fail("Did not successfully parse as a Loan Level Disclosure record")
    }
  }

  test("Adjustable-Rate Loan Level Disclosure Record (record type 2) parses correctly") {
    val arllrOption = LoanLevelDisclosureRecord.parseLoanLevelRecord(adjustableRateLoanLevelExample)
    arllrOption match {
      case Some(arllr) =>
        assert(arllr.recordType == "2" &&
          arllr.poolNumber == "AY2846" &&
          !arllr.loanCorrectionIndicator &&
          arllr.loanIdentifier == "8265252131" &&
          arllr.channel == "RETAIL" &&
          arllr.sellerName == "FIRST NATIONAL BANK OF OMAHA" &&
          arllr.servicerName == "FIRST NATIONAL BANK OF OMAHA" &&
          arllr.originalInterestRate.compareTo(3.125f) == 0 &&
          arllr.currentInterestRate.compare(3.125f) == 0 &&
          arllr.currentNetInterestRate.compareTo(2.56f) == 0 &&
          arllr.originalUPB.compareTo(140000.00f) == 0 &&
          arllr.currentScheduledUPB.compareTo(140000.00f) == 0 &&
          arllr.originalLoanTerm == 360 &&
          arllr.firstPaymentDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("02/01/2015")) == 0 &&
          arllr.loanAge == 0 &&
          arllr.remainingMonthsToMaturity == 360 &&
          arllr.maturityDate.compareTo(new SimpleDateFormat("MM/yyyy").parse("01/2045")) == 0 &&
          arllr.originalLTV == 71 &&
          arllr.originalCombinedLTV == 71 &&
          arllr.numberOfBorrowers == 2 &&
          arllr.debtToIncomeRatio.get == 44 &&
          arllr.creditScore.get == 804 &&
          !arllr.firstTimeHomeBuyerIndicator &&
          arllr.loanPurpose == "REFINANCE" &&
          arllr.propertyType == "SF" &&
          arllr.numberOfUnits == 1 &&
          arllr.occupancyStatus == "PRINCIPAL" &&
          arllr.state == "NE" &&
          arllr.mortgageInsurancePercentage == None &&
          arllr.productType == "ARM" &&
          arllr.prepaymentPremiumTerm == "NONE" &&
          !arllr.interestOnlyIndicator &&
          arllr.firstPrincipalAndInterestPaymentDate == None &&
          arllr.monthsToFirstScheduledAmortization == None &&
          !arllr.convertibilityIndicator.get &&
          arllr.mortgageMargin.get.compareTo(2.25f) == 0 &&
          arllr.netMortgageMargin.get.compareTo(1.685f) == 0 &&
          arllr.index.get == 75 &&
          arllr.interestRateLookBack.get == 45 &&
          arllr.maximumInterestRate.get.compareTo(8.125f) == 0 &&
          arllr.netMaximumInterestRate.get.compareTo(7.56f) == 0 &&
          arllr.monthsToNextRateChange.get == 84 &&
          arllr.nextRateChangeDate.get.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2022")) == 0 &&
          arllr.rateAdjustmentFrequency.get == 12 &&
          arllr.initialFixedRatePeriod.get == 7 &&
          arllr.initialRateCapUpPercent.get.compareTo(5.0f) == 0 &&
          arllr.initialRateCapDownPercent.get.compareTo(5.0f) == 0 &&
          arllr.periodicCapUpPercent.get.compareTo(2.0f) == 0 &&
          arllr.periodicCapDownPercent.get.compare(2.0f) == 0)
      case None => fail("Did not successfully parse as a Loan Level Disclosure record")
    }
  }
}
