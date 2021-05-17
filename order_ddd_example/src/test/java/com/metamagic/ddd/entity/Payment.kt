package com.metamagic.ddd.entity

import com.metamagic.ddd.exception.InvalidDataException
import javax.jdo.annotations.Persistent
import javax.jdo.annotations.PrimaryKey

/**
 * Payment details for order
 */
open class Payment(
        @PrimaryKey
        @Persistent(column = "paymentid", customValueStrategy = "uuid")
        var paymentid: String? = null,

        @Persistent(column = "transactionid")
        var transactionId: String? = null,

        @Persistent(column = "orderId")
        var order: Order? = null,

        mode: String? = null, amount: Double? = null) {

    @Persistent(column = "mode")
    var mode: String? = mode
        @Throws(InvalidDataException::class) set(value) {
            if (value.isNullOrBlank()) throw InvalidDataException("Invalid payment mode")
            field = value
        }


    @Persistent(column = "amount")
    var amount: Double? = amount
        @Throws(InvalidDataException::class) set(value) {
            if (value == null) throw InvalidDataException("Invalid amount")
            field = value
        }

    override fun equals(other: Any?): Boolean {
        return other is Payment && hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return (mode + paymentid).hashCode()
    }
}