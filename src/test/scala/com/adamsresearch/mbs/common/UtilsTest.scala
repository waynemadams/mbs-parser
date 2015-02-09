package com.adamsresearch.mbs.common

import java.text.SimpleDateFormat

import org.scalatest.FunSuite

/**
 * Created by wma on 1/31/15.
 */
class UtilsTest extends FunSuite {

  test("picToFloat successfully parses '00002003473100' to 2.0034731E7") {
    assert(Utils.picToFloat("00002003473100", 12, 2) == 2.0034731E7f)
  }

  test("string month and 2-digit year 'OCT' and '03' parses to October 2003") {
    assert(Utils.monthAndTwoDigitYearToDate("OCT", "03").compareTo(new SimpleDateFormat("MM/yyyy").parse("10/2003")) == 0)
  }

  test("030197 parses to 1 March 1997") {
    assert(Utils.monthDayAndTwoDigitYearToDate("03", "01", "97").compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/1997")) == 0)
  }

  test("071452 parses to 14 July 2052") {
    assert(Utils.monthDayAndTwoDigitYearToDate("07", "14", "52").compareTo(new SimpleDateFormat("MM/dd/yyyy").parse("07/14/2052")) == 0)
  }
}
