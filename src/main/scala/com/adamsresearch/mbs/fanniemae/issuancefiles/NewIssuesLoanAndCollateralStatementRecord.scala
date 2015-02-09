package com.adamsresearch.mbs.fanniemae.issuancefiles

import java.text.SimpleDateFormat
import java.util.Date

import com.adamsresearch.mbs.common.Utils

/**
 * Created by wma on 1/23/15.
 *
 * In case classes, indices start with 1, not 0, to match the Fannie Mae
 * record documentation.
 *
 * Note that numerous fields that would appear to be numeric are all defined
 * as varchars in the FM documentation.  Only Date objects are treated differently.
 * In a future version, we may take the liberty of converting obviously-numeric
 * fields to Ints and Floats.
 */
class NewIssuesLoanAndCollateralStatementRecord

case class LoanAndPropertyRecordInformationRecord(
    poolNumber: String,                               //  1
    recordId: String,                                 //  2
    fannieMaeLoanNumber: String,                      //  3
    filler: String,                                   //  4
    lenderName: String,                               //  5
    propertyCityName: String,                         //  6
    propertyState: String,                            //  7
    propertyZipCode: String,                          //  8
    issuanceUPBAmount: Float,                         //  9
    issueDate:  Date,                                 // 10
    originalNoteRate: String,                         // 11
    issuanceNoteRate: Float,                          // 12
    interestAccrualMethod: String,                    // 13
    firstPaymentDate: Date,                           // 14
    maturityDate: Date,                               // 15
    lienPosition: String,                             // 16
    firstScheduledPaymentChangeDate: Option[Date],    // 17
    firstScheduledRateChangeDate: Option[Date],       // 18
    nextScheduledPaymentChangeDate: Option[Date],     // 19
    nextScheduledRateChangeDate: Option[Date],        // 20
    armIndexDescription: String,                      // 21
    balloonIndicator: Boolean,                        // 22
    interestOnlyPeriod: Int,                          // 23
    noteDate: Date,                                   // 24
    interestType: String,                             // 25
    variableRateChangeFrequency: String,              // 26
    variablePaymentChangeFrequency: String,           // 27
    otherDebtIndicator: Boolean,                      // 28
    mezzanineFinancingIndicator: String,              // 29
    prepaymentPenaltyIndicator: String,               // 30
    adjustableRateTerm: String,                       // 31
    fixedRateTerm: String,                            // 32
    fixedRateTermEndDate: Option[Date],               // 33
    propertyName: String,                             // 34
    propertyStreetAddressLine1: String,               // 35
    propertyStreetAddressLine2: String,               // 36
    greenFinancing: String,                           // 37
    loanPurpose: String,                              // 38
    originalUPB: Float,                               // 39
    bifurcatedStructureIndicator: Boolean)            // 40

    extends NewIssuesLoanAndCollateralStatementRecord

case class RateRecordInformationRecord(poolNumber: String,
                                       recordId: String,
                                       fannieMaeLoanNumber: String,
                                       percentOfInitialPoolBalance: Float,
                                       issuanceArmMargin: String,
                                       noteRateCeiling: String,
                                       noteRateFloor: String,
                                       standardLookback: String,
                                       negativeAmortizationIndicator: String,
                                       negativeAmortizationLimitPercent: Option[Float],
                                       convertibleIndicator: String,
                                       conversionEligibleStartDate: Option[Date],
                                       conversionEligibleEndDate: Option[Date],
                                       conversionEligibleTerm: String,
                                       originalAmortizationTerm: Int,
                                       interestOnlyIndicator: Boolean,
                                       interestOnlyEndDate: Date,
                                       armIndexDescription: String,
                                       ratioLtvToCombinedLtv: Float,
                                       noteRateRoundingMethod: String,
                                       perPaymentChangeIncreaseCap: String,
                                       perPaymentChangeDecreaseCap: String,
                                       perRateChangeIncreaseCap: String,
                                       perRateChangeDecreaseCap: String,
                                       fixedPrincipalPaymentAmount: String)
    extends NewIssuesLoanAndCollateralStatementRecord

case class PrepaymentPremiumRecordInformationRecord(poolNumber: String,
                                                    recordId: String,
                                                    fannieMaeLoanNumber: String,
                                                    prepaymentPremiumOption: String,
                                                    yieldMaintenanceSecurityRate: Option[Float],
                                                    yieldMaintenanceSecurityDueDate: Option[Date],
                                                    decliningPremiumFormula: String,
                                                    otherPrepaymentPremiumDescription: String,
                                                    prepaymentPremiumTerm: Int,
                                                    prepaymentPremiumEndDate: Date,
                                                    tier: Int,
                                                    tierDropEligible: Boolean,
                                                    prepaymentLockoutTerm: Int,
                                                    prepaymentLockoutEndDate:  Date)
    extends NewIssuePoolStatisticsRecord

case class DefeasanceRecordInformationRecord(poolNumber: String,
                                             recordId: String,
                                             fannieMaeLoanNumber: String,
                                             defeasanceEligibilityPeriod: Int,
                                             defeasanceEligibilityEndDate: Date,
                                             defeasanceLockoutTerm: Int,
                                             defeasanceLockoutEndDate: Date)
    extends NewIssuePoolStatisticsRecord

// note: the Property Information record rather recently expanded to 30 fields.
// if you attempt to parse earlier data, you'll need to modify code to handle
// the narrower records.
case class PropertyInformationRecord(poolNumber: String,
                                     recordId: String,
                                     fannieMaeLoanNumber: String,
                                     propertyType: String,
                                     numberOfUnits: Int,
                                     netOperatingIncome: Float,
                                     propertyValue: Float,
                                     economicOccupancy: Float,
                                     debtServiceCoverageRatio: Float,
                                     debtServiceCoverageRatioYear: Int,
                                     physicalOccupancy: Float,
                                     lowIncomeHousingTaxCredits: String,
                                     taxesCurrentlyEscrowedIndicator: Boolean,
                                     percentageOfUnitsAtOrBelowSixtyPercentOfMedianIncome: String,
                                     percentateOfUnitsAtOrBelowFixtyPercentOfMedianIncome: String,
                                     yearBuilt: Int,
                                     landOwnershipRights: String,
                                     terrorismInsuranceCoverageIndicator: Boolean,
                                     seismicZone: Boolean,
                                     initialDepositToReplacementReserves: Float,
                                     geographicMsaDescription: String,
                                     debtServiceCoverageRatioAtMaximumPayment: Float,
                                     phaseYear: Int,
                                     phaseYearNumberOfUnits: Int,
                                     greenBuildingCertification: String,
                                     affordableHousingType: String,
                                     percentageOfUnitsAtOrBelowEightyPercentOfMedianIncome: String,
                                     unitsWithIncomeOrRentRestriction: String,
                                     hapRemainingTerm: String,  // HAP = housing assistance payment
                                     ageRestrictedIndicator: Boolean)
    extends NewIssuesLoanAndCollateralStatementRecord

case class OtherDebtLienHolderInformationRecord(poolNumber: String,
                                                recordId: String,
                                                fannieMaeLoanNumber: String,
                                                otherDebtPosition: String,
                                                otherDebtHolder: String,
                                                otherDebtCurrentActualUpbAmount: Float,
                                                otherDebtMaturityDate: Date,
                                                otherDebtBalloonIndicator: Boolean,
                                                otherDebtMonthlyDebtServiceAmount: Float,
                                                otherDebtCurrentInterestRate: Float,
                                                otherDebtAmortizationTerm: Int,
                                                otherDebtMaximumPaymentAmount: Float,
                                                lineOfCreditFlag: Boolean,
                                                lineOfCreditFullAmount: Option[Float],
                                                otherDebtNoteRateFloor: Option[Float],
                                                otherDebtArmMargin: Option[Float],
                                                otherDebtInterestOnlyEndDate: Option[Date])
    extends NewIssuesLoanAndCollateralStatementRecord

case class FootnotesRecord(poolNumber: String,
                           recordId: String,
                           poolFootnote: String,
                           loanFootnote: String,
                           collateralFootnote: String)
    extends NewIssuesLoanAndCollateralStatementRecord

object NewIssuesLoanAndCollateralStatementRecord {
  def main(args: Array[String]) = {
    println("Hello, Collateral Statements!")
  }

  def parseLoanAndPropertyRecordInformationRecord(record: String): Option[LoanAndPropertyRecordInformationRecord] = {
    try {
      val fields = record.split("\\|", 40)
      fields.length match {
        case 40 =>
          Some(
            LoanAndPropertyRecordInformationRecord(fields(0).trim,
                                                   fields(1).trim,
                                                   fields(2).trim,
                                                   fields(3).trim,
                                                   fields(4).trim,
                                                   fields(5).trim,
                                                   fields(6).trim,
                                                   fields(7).trim,
                                                   Utils.dollarToFloat(fields(8)),
                                                   new SimpleDateFormat("MM/dd/yyyy").parse(fields(9).trim),
                                                   fields(10).trim,
                                                   fields(11).trim.toFloat,
                                                   fields(12).trim,
                                                   new SimpleDateFormat("MM/dd/yyyy").parse(fields(13).trim),
                                                   new SimpleDateFormat("MM/dd/yyyy").parse(fields(14).trim),
                                                   fields(15).trim,
                                                   Utils.optionMonthDayYear(fields(16)),
                                                   Utils.optionMonthDayYear(fields(17)),
                                                   Utils.optionMonthDayYear(fields(18)),
                                                   Utils.optionMonthDayYear(fields(19)),
                                                   fields(20).trim,
                                                   Utils.yesNoBoolean(fields(21)),
                                                   fields(22).trim.toInt,
                                                   new SimpleDateFormat("MM/dd/yyyy").parse(fields(23).trim),
                                                   fields(24).trim,
                                                   fields(25).trim,
                                                   fields(26).trim,
                                                   Utils.yesNoBoolean(fields(27)),
                                                   fields(28).trim,
                                                   fields(29).trim,
                                                   fields(30).trim,
                                                   fields(31).trim,
                                                   Utils.optionMonthDayYear(fields(32)),
                                                   fields(33).trim,
                                                   fields(34).trim,
                                                   fields(35).trim,
                                                   fields(36).trim,
                                                   fields(37).trim,
                                                   Utils.dollarToFloat(fields(38)),
                                                   Utils.yesNoBoolean(fields(39))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseRateRecordInformationRecord(record: String): Option[RateRecordInformationRecord] = {
    try {
      val fields = record.split("\\|", 25)
      fields.length match {
        case 25 =>
          Some(RateRecordInformationRecord(fields(0).trim,
            fields(1).trim,
            fields(2).trim,
            fields(3).trim.toFloat,
            fields(4).trim,
            fields(5).trim,
            fields(6).trim,
            fields(7).trim,
            fields(8).trim,
            Utils.optionFloat(fields(9)),
            fields(10).trim,
            Utils.optionMonthDayYear(fields(11)),
            Utils.optionMonthDayYear(fields(12)),
            fields(13).trim,
            fields(14).trim.toInt,
            Utils.yesNoBoolean(fields(15)),
            new SimpleDateFormat("MM/dd/yyyy").parse(fields(16).trim),
            fields(17).trim,
            fields(18).trim.toFloat,
            fields(19).trim,
            fields(20).trim,
            fields(21).trim,
            fields(22).trim,
            fields(23).trim,
            fields(24).trim))
        case _ => None
      }
    } catch {
      case ex: Exception => {
        ex.printStackTrace()
        None
      }
    }
  }

  def parsePrepaymentPremiumRecordInformationRecord(record: String): Option[PrepaymentPremiumRecordInformationRecord] = {
    try {
      val fields = record.split("\\|", 14)
      fields.length match {
        case 14 =>
          Some(PrepaymentPremiumRecordInformationRecord(fields(0).trim,
                                                        fields(1).trim,
                                                        fields(2).trim,
                                                        fields(3).trim,
                                                        Utils.optionFloat(fields(4)),
                                                        Utils.optionMonthDayYear(fields(5).trim),
                                                        fields(6).trim,
                                                        fields(7).trim,
                                                        fields(8).trim.toInt,
                                                        new SimpleDateFormat("MM/dd/yyyy").parse(fields(9).trim),
                                                        fields(10).trim.toInt,
                                                        Utils.yesNoBoolean(fields(11)),
                                                        fields(12).trim.toInt,
                                                        new SimpleDateFormat("MM/dd/yyyy").parse(fields(13).trim)))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseDefeasanceRecordInformationRecord(record: String): Option[DefeasanceRecordInformationRecord] = {
    try {
      val fields = record.split("\\|", 7)
      fields.length match {
        case 7 =>
          Some(DefeasanceRecordInformationRecord(fields(0).trim,
                                                 fields(1).trim,
                                                 fields(2).trim,
                                                 fields(3).trim.toInt,
                                                 new SimpleDateFormat("MM/dd/yyyy").parse(fields(4).trim),
                                                 fields(5).trim.toInt,
                                                 new SimpleDateFormat("MM/dd/yyyy").parse(fields(6).trim)))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parsePropertyInformationRecord(record: String): Option[PropertyInformationRecord] = {
    try {
      val fields = record.split("\\|", 30)
      fields.length match {
        case 30 =>
        Some(PropertyInformationRecord(fields(0).trim,
                                       fields(1).trim,
                                       fields(2).trim,
                                       fields(3).trim,
                                       fields(4).trim.toInt,
                                       Utils.dollarToFloat(fields(5)),
                                       Utils.dollarToFloat(fields(6)),
                                       fields(7).trim.toFloat,
                                       fields(8).trim.toFloat,
                                       fields(9).trim.toInt,
                                       fields(10).trim.toFloat,
                                       fields(11).trim,
                                       Utils.yesNoBoolean(fields(12)),
                                       fields(13).trim,
                                       fields(14).trim,
                                       fields(15).trim.toInt,
                                       fields(16).trim,
                                       Utils.yesNoBoolean(fields(17)),
                                       Utils.yesNoBoolean(fields(18)),
                                       Utils.dollarToFloat(fields(19)),
                                       fields(20).trim,
                                       fields(21).trim.toFloat,
                                       fields(22).trim.toInt,
                                       fields(23).trim.toInt,
                                       fields(24).trim,
                                       fields(25).trim,
                                       fields(26).trim,
                                       fields(27).trim,
                                       fields(28).trim,
                                       Utils.yesNoBoolean(fields(29))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseOtherDebtLienHolderInformationRecord(record: String): Option[OtherDebtLienHolderInformationRecord] = {
    try {
      val fields = record.split("\\|", 17)
      fields.length match {
        case 17 => Some(
            OtherDebtLienHolderInformationRecord(fields(0).trim,
                                                 fields(1).trim,
                                                 fields(2).trim,
                                                 fields(3).trim,
                                                 fields(4).trim,
                                                 Utils.dollarToFloat(fields(5)),
                                                 new SimpleDateFormat("MM/dd/yyyy").parse(fields(6).trim),
                                                 Utils.yesNoBoolean(fields(7)),
                                                 Utils.dollarToFloat(fields(8)),
                                                 fields(9).trim.toFloat,
                                                 fields(10).trim.toInt,
                                                 Utils.dollarToFloat(fields(11)),
                                                 Utils.yesNoBoolean(fields(12)),
                                                 Utils.optionDollarToFloat(fields(13)),
                                                 Utils.optionFloat(fields(14)),
                                                 Utils.optionFloat(fields(15)),
                                                 Utils.optionMonthDayYear(fields(16))))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }

  def parseFootnotesRecord(record: String): Option[FootnotesRecord] = {
    try {
      val fields = record.split("\\|", 5)
      fields.length match {
        case 5 =>
          Some(
              FootnotesRecord(fields(0).trim,
                              fields(1).trim,
                              fields(2).trim,
                              fields(3).trim,
                              fields(4).trim))
        case _ => None
      }
    } catch {
      case ex: Exception => None
    }
  }
}
