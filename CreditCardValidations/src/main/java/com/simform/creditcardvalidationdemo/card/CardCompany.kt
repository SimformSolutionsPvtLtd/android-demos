package com.scutt.android.util.card

/**
 * Created by Sanat Shukla on 13/07/18.
 */
enum class CardCompany private constructor(regex:String, issuerName:String) {
    VISA("^4[0-9]{12}(?:[0-9]{3})?$", "VISA"),
    MASTERCARD("^5[1-5][0-9]{14}$", "MASTER"),
    AMEX("^3[47][0-9]{13}$", "AMEX"),
    DINERS("^3(?:0[0-5]|[68][0-9])[0-9]{11}$", "Diners"),
    DISCOVER("^6(?:011|5[0-9]{2})[0-9]{12}$", "DISCOVER"),
    JCB("^(?:2131|1800|35\\d{3})\\d{11}$", "JCB");
    private val regex:String
    val issuerName:String
    init{
        this.regex = regex
        this.issuerName = issuerName
    }

    /**
     * matches
     */
    fun matches(card:String):Boolean {
        return card.matches((this.regex).toRegex())
    }
    companion object {
        /**
         * get an enum from a card number
         * @param card
         * @return
         */
        fun gleanCompany(card:String): CardCompany? {
            for (cc in CardCompany.values())
            {
                if (cc.matches(card))
                {
                    return cc
                }
            }
            return null
        }
        /**
         * get an enum from an issuerName
         * @param issuerName
         * @return
         */
        fun gleanCompanyByIssuerName(issuerName:String): CardCompany? {
            for (cc in CardCompany.values())
            {
                if (cc.issuerName == issuerName)
                {
                    return cc
                }
            }
            return null
        }
    }
}