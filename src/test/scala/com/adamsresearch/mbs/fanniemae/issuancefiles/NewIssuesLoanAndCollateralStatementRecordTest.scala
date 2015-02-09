package com.adamsresearch.mbs.fanniemae.issuancefiles

import java.text.SimpleDateFormat

import org.scalatest.FunSuite

/**
 * Created by wma on 1/23/15.
 */
class NewIssuesLoanAndCollateralStatementRecordTest extends FunSuite {

  val recordType1Example = "AM7322|01|1717463639||ACRE Capital LLC|Fort Worth|TX|76132|$11,520,000.00|1/1/2015||4.4100|Actual/360|2/1/2015|1/1/2027|1st||||||Yes|36|12/16/2014|Fixed|||No||||||Oaks at Hulen Bend Apartments|6401 Hulen Bend Boulevard|||Purchase|$11,520,000.00|No"
  val recordType2Example = "AM7322|02|1717463639|100.0000||||||N/A|||||360|Yes|1/1/2018||80.0||||||"
  val recordType3Example = "AM7562|03|1717463631|Declining Premium|N/A|N/A|5-1-1-1-1-1-1-1-1-1|N/A|117|9/30/2024|3|Yes|12|12/31/2015"
  val recordType4Example = "AM5691|04|1717462909|45|6/30/2021|36|9/30/2017"
  val recordType5Example = "AM6881|05|1717463494|Multifamily|122|$527,608.00|$9,600,000.00|94.0|1.35|2014|95.0000||No|||1984|Fee Simple|Yes|No|$0.00|Austin-Round Rock-San Marcos, TX Metropolitan Statistical Area|1.35|1984|122||N/A||||No"
  val recordType6ExampleFirst = "AM6881|06|1717463494|1st|Fannie Mae|$5,198,920.27|10/1/2022|Yes|$24,756.12|3.8200|360|$32,549.59|No|N/A|||"
  val recordType6ExampleSecond = "AM7783|06|1717463651|2nd|NCB, FSB|$0.00|1/1/2045|No|$0.00|4.0000|360|$1,766.67|Yes|$500,000.00|4.0000|3.7500|1/1/2018"
  val recordType7Example = "AM7783|07||On and after February 1, 2018, principal is payable each month in a minimum amount of $100.|"

  test("Loan and Property Record Information (record type 1) parses correctly") {
    val lpriOption = NewIssuesLoanAndCollateralStatementRecord.parseLoanAndPropertyRecordInformationRecord(recordType1Example)
    lpriOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7322" &&
               x.recordId == "01" &&
               x.fannieMaeLoanNumber == "1717463639" &&
               x.lenderName == "ACRE Capital LLC" &&
               x.propertyCityName == "Fort Worth" &&
               x.propertyState == "TX" &&
               x.propertyZipCode == "76132" &&
               x.issuanceUPBAmount.compareTo(11520000.00f) == 0 &&
               x.issueDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2015")) == 0 &&
               x.issuanceNoteRate.compareTo(4.4100f) == 0 &&
               x.interestAccrualMethod == "Actual/360" &&
               x.firstPaymentDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("02/01/2015")) == 0 &&
               x.maturityDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2027")) == 0 &&
               x.lienPosition == "1st" &&
               x.balloonIndicator &&
               x.interestOnlyPeriod == 36 &&
               x.noteDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("12/16/2014")) == 0 &&
               x.interestType == "Fixed" &&
               !x.otherDebtIndicator &&
               x.propertyName == "Oaks at Hulen Bend Apartments" &&
               x.propertyStreetAddressLine1 == "6401 Hulen Bend Boulevard" &&
               x.loanPurpose == "Purchase" &&
               x.originalUPB.compareTo(11520000.00f) == 0 &&
               !x.bifurcatedStructureIndicator)
      case None => fail("Did not successfully parse as a Loan and Property Record Information record")
    }
  }

  test("Rate Record Information (record type 2) parses correctly") {
    val rriOption = NewIssuesLoanAndCollateralStatementRecord.parseRateRecordInformationRecord(recordType2Example)
    rriOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7322" &&
               x.recordId == "02" &&
               x.fannieMaeLoanNumber == "1717463639" &&
               x.percentOfInitialPoolBalance.compareTo(100.0000f) == 0 &&
               x.negativeAmortizationLimitPercent == None &&
               x.originalAmortizationTerm == 360 &&
               x.interestOnlyIndicator &&
               x.interestOnlyEndDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2018")) == 0 &&
               x.ratioLtvToCombinedLtv.compareTo(80.0f) == 0)
      case None => fail("Did not successfully parse as a Rate Record Information record")
    }
  }

  test("Prepayment Premium Record Information (record type 3) parses correctly") {
    val ppriOption = NewIssuesLoanAndCollateralStatementRecord.parsePrepaymentPremiumRecordInformationRecord(recordType3Example)
    ppriOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7562" &&
               x.recordId == "03" &&
               x.fannieMaeLoanNumber == "1717463631" &&
               x.prepaymentPremiumOption == "Declining Premium" &&
               x.yieldMaintenanceSecurityRate == None &&
               x.yieldMaintenanceSecurityDueDate == None &&
               x.decliningPremiumFormula == "5-1-1-1-1-1-1-1-1-1" &&
               x.otherPrepaymentPremiumDescription == "N/A" &&
               x.prepaymentPremiumTerm == 117 &&
               x.prepaymentPremiumEndDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("9/30/2024")) == 0 &&
               x.tier == 3 &&
               x.tierDropEligible &&
               x.prepaymentLockoutTerm == 12 &&
               x.prepaymentLockoutEndDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("12/31/2015")) == 0)
      case _ => fail("Did not successfully parse as a Prepayment Premium Record Information record")
    }
  }

  test("Defeasance Record Information (record type 4) parses correctly") {
    val driOption = NewIssuesLoanAndCollateralStatementRecord.parseDefeasanceRecordInformationRecord(recordType4Example)
    driOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM5691" &&
               x.recordId == "04" &&
               x.fannieMaeLoanNumber == "1717462909" &&
               x.defeasanceEligibilityPeriod == 45 &&
               x.defeasanceEligibilityEndDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("6/30/2021")) == 0 &&
               x.defeasanceLockoutTerm == 36 &&
               x.defeasanceLockoutEndDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("9/30/2017")) == 0)
      case _ => fail("Did not successfully parse as a Defeasance Record Information record")
    }
  }

  test("Property Information Record (record type 5) parses correctly") {
    val piOption = NewIssuesLoanAndCollateralStatementRecord.parsePropertyInformationRecord(recordType5Example)
    piOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM6881" &&
               x.recordId == "05" &&
               x.fannieMaeLoanNumber == "1717463494" &&
               x.propertyType == "Multifamily" &&
               x.numberOfUnits == 122 &&
               x.netOperatingIncome.compareTo(527608.00f) == 0 &&
               x.propertyValue.compareTo(9600000.00f) == 0 &&
               x.economicOccupancy.compareTo(94.0f) == 0 &&
               x.debtServiceCoverageRatio.compareTo(1.35f) == 0 &&
               x.debtServiceCoverageRatioYear == 2014 &&
               x.physicalOccupancy.compareTo(95.0000f) == 0 &&
               !x.taxesCurrentlyEscrowedIndicator &&
               x.yearBuilt == 1984 &&
               x.landOwnershipRights == "Fee Simple" &&
               x.terrorismInsuranceCoverageIndicator &&
               !x.seismicZone &&
               x.initialDepositToReplacementReserves.compareTo(0.0f) == 0 &&
               x.geographicMsaDescription == "Austin-Round Rock-San Marcos, TX Metropolitan Statistical Area" &&
               x.debtServiceCoverageRatioAtMaximumPayment.compareTo(1.35f) == 0 &&
               x.phaseYear == 1984 &&
               x.phaseYearNumberOfUnits == 122 &&
               x.affordableHousingType == "N/A" &&
               !x.ageRestrictedIndicator)
      case None => fail("Did not successfully parse as a Property Information record")
    }
  }

  test("Other Debt / Lien-Holder Information (First Mortgage) (record type 6) parses correctly") {
    val lhifmOption = NewIssuesLoanAndCollateralStatementRecord.parseOtherDebtLienHolderInformationRecord(recordType6ExampleFirst)
    lhifmOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM6881" &&
          x.recordId == "06" &&
          x.fannieMaeLoanNumber == "1717463494" &&
          x.otherDebtPosition == "1st" &&
          x.otherDebtHolder == "Fannie Mae" &&
          x.otherDebtCurrentActualUpbAmount.compareTo(5198920.27f) == 0 &&
          x.otherDebtMaturityDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("10/1/2022")) == 0 &&
          x.otherDebtBalloonIndicator &&
          x.otherDebtMonthlyDebtServiceAmount.compareTo(24756.12f) == 0 &&
          x.otherDebtCurrentInterestRate.compareTo(3.8200f) == 0 &&
          x.otherDebtAmortizationTerm == 360 &&
          x.otherDebtMaximumPaymentAmount.compare(32549.59f) == 0 &&
          !x.lineOfCreditFlag &&
          x.lineOfCreditFullAmount == None)
      case None => fail("Did not successfully parse as an Other Debt / Lien-Holder Information (First Mortgage) record")
    }
  }

  test("Other Debt / Lien-Holder Information (Second Mortgage) (record type 6) parses correctly") {
    val lhismOption = NewIssuesLoanAndCollateralStatementRecord.parseOtherDebtLienHolderInformationRecord(recordType6ExampleSecond)
    lhismOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7783" &&
          x.recordId == "06" &&
          x.fannieMaeLoanNumber == "1717463651" &&
          x.otherDebtPosition == "2nd" &&
          x.otherDebtHolder == "NCB, FSB" &&
          x.otherDebtCurrentActualUpbAmount.compareTo(0.00f) == 0 &&
          x.otherDebtMaturityDate.compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2045")) == 0 &&
          !x.otherDebtBalloonIndicator &&
          x.otherDebtMonthlyDebtServiceAmount.compareTo(0.00f) == 0 &&
          x.otherDebtCurrentInterestRate.compareTo(4.0000f) == 0 &&
          x.otherDebtAmortizationTerm == 360 &&
          x.otherDebtMaximumPaymentAmount.compare(1766.67f) == 0 &&
          x.lineOfCreditFlag &&
          x.lineOfCreditFullAmount == Some(500000.00f) &&
          x.otherDebtNoteRateFloor == Some(4.0000f) &&
          x.otherDebtArmMargin == Some(3.7500f) &&
          x.otherDebtInterestOnlyEndDate == Some(new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2018")))
      case None => fail("Did not successfully parse as an Other Debt / Lien-Holder Information (Second Mortgage) record")
    }
  }

  test("Footnotes (record type 7) parses correctly") {
    val fOption = NewIssuesLoanAndCollateralStatementRecord.parseFootnotesRecord(recordType7Example)
    fOption match {
      case Some(x) =>
        assert(x.poolNumber == "AM7783" &&
               x.recordId == "07" &&
               x.loanFootnote == "On and after February 1, 2018, principal is payable each month in a minimum amount of $100.")
      case None => fail("Did not successfully parse as a Footnotes record")
    }
  }
}
