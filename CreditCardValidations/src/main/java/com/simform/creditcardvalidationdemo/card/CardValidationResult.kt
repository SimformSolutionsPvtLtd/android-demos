package com.scutt.android.util.card
/**
* Container for validation result
*/
class CardValidationResult {
  var isValid:Boolean = false
  var cardType:CardCompany? = null
  var error:String = ""
  private val cardNo:String
  val message:String
  get() {
    return cardNo + " >> " + (if ((isValid)) ("card: " + this.cardType) else error)
  }
  constructor(cardNo:String, error:String) {
    this.cardNo = cardNo
    this.error = error
  }
  constructor(cardNo:String, cardType:CardCompany) {
    this.cardNo = cardNo
    this.isValid = true
    this.cardType = cardType
  }

  fun cardNo():String {
    return this.cardNo
  }
}