package com.scutt.android.util.card
/**
* Validator for credit card numbers
* Checks validity and returns card type
*
*/
object RegexCardValidator {
  /**
 * Checks if the field is a valid credit card number.
 * @param cardIn The card number to validate.
 * @return Whether the card number is valid.
 */
  fun isValid(cardIn:String):CardValidationResult {
    val card = cardIn.replace(("[^0-9]+").toRegex(), "") // remove all non-numerics
    if ((card == null) || (card.length < 13) || (card.length > 19))
    {
      return CardValidationResult(card, "failed length check")
    }
    if (!luhnCheck(card))
    {
      return CardValidationResult(card, "failed luhn check")
    }
    val cc = CardCompany.gleanCompany(card)
    if (cc == null) return CardValidationResult(card, "failed card company check")
    return CardValidationResult(card, cc)
  }
  /**
 * Checks for a valid credit card number.
 * @param cardNumber Credit Card Number.
 * @return Whether the card number passes the luhnCheck.
 */
  internal fun luhnCheck(cardNumber:String):Boolean {
    // number must be validated as 0..9 numeric first!!
    val digits = cardNumber.length
    val oddOrEven = digits and 1
    var sum:Long = 0
    for (count in 0 until digits)
    {
      var digit = 0
      try
      {
        digit = Integer.parseInt(cardNumber.get(count) + "")
      }
      catch (e:NumberFormatException) {
        return false
      }
      if (((count and 1) xor oddOrEven) == 0)
      { // not
        digit *= 2
        if (digit > 9)
        {
          digit -= 9
        }
      }
      sum += digit.toLong()
    }
    return if ((sum == 0L)) false else (sum % 10 == 0L)
  }
}