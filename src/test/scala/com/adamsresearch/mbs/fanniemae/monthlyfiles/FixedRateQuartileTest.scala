package com.adamsresearch.mbs.fanniemae.monthlyfiles

import org.scalatest.FunSuite

/**
 * Created by wma on 2/2/15.
 */
class FixedRateQuartileTest extends FunSuite {

  // Note:  code below is not yet working...  need to do some cobol pic-parsing work.
  val headerExample  = "1176552 CL20031031367RBV319920901                                                                                                                                                                      "
  val detailsExample = "2" + // quartile record type
                       "176552" + // pool number
                       "08875{" + // S9(2)V9(4) numeric edited weighted average coupon -- this doesn't look right
                       "08875{" + // coupon pool minimum, also looks wrong
                       "08875{" + // coupon quartile 1
                       "08875{" + // coupon quartile 2
                       "08875{" + // coupon quartile 3
                       "08875{" + // coupon quartile 4
                       "22D" +    // wa maturity
                       "22D" +    // remaining months to maturity pool minimum
                       "22D" +    // remaining months to maturity quartile 1
                       "22D" +    // remaining months to maturity quartile 2
                       "22D" +    // remaining months to maturity quartile 3
                       "22D" +    // remaining months to maturity quartile 4
                       "13C" +    // loan age
                       "13C" +    // loan age pool minimum
                       "13C" +    // loan age quartile 1
                       "13C" +    // loan age quartile 2
                       "13C" +    // loan age quartile 3
                       "13C" +    // loan age quartile 4
                       "0000604000{" + // average loan size
                       "0000604000{" + // loan size pool minimum
                       "0000604000{" + // loan size quartile 1
                       "0000604000{" + // loan size quartile 2
                       "0000604000{" + // loan size quartile 3
                       "0000604000{" + // loan size quartile 4
                       "36{" + // wa original loan term
                       "36{" + // original loan term minimum
                       "36{" + // original loan term quartile 1
                       "36{" + // original loan term quartile 2
                       "36{" + // original loan term quartile 3
                       "36{" + // original loan term quartile 4
                       "08500{" + // wa pass-through rate
                       "08500{" + // pass-through rate pool minimum
                       "08500{" + // pass-through rate quartile 1
                       "08500{" + // pass-through rate quartile 2
                       "08500{" + // pass-through rate quartile 3
                       "08500{"   // pass-through rate quartile 4

  test("Fixed Rate Quartile Header record parses correctly") {
    assert(true)
  }

  test("Fixed Rate Quartile Details record parses correctly") {
    assert(true)
  }
}
