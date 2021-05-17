package com.metamagic.ddd.entity

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.metamagic.ddd.exception.InvalidDataException
import com.metamagic.ddd.transformer.OrderDesiralizer
import java.sql.Date
import java.util.*
import javax.jdo.annotations.PersistenceCapable
import javax.jdo.annotations.Persistent
import javax.jdo.annotations.PrimaryKey

@JsonDeserialize(using = OrderDesiralizer::class)
@PersistenceCapable(table = "order", detachable = "true")
open class Order(
        @PrimaryKey
        @Persistent(column = "orderid", customValueStrategy = "uuid")
        var orderId: String? = null,

        @Persistent(column = "orderno")
        var orderNo: String? = null,

        @Persistent(column = "orderdate")
        var orderDate: Date? = null,

        @Persistent(column = "status")
        var status: Status? = null,

        @Persistent(mappedBy = "order", defaultFetchGroup = "true")
        var lineItems: MutableSet<LineItem>? = null,

        @Persistent(mappedBy = "order", defaultFetchGroup = "true")
        var shippingAddress: ShippingAddress? = null,

        @Persistent(mappedBy = "order", defaultFetchGroup = "true")
        var payment: Payment? = null,

        @Persistent(defaultFetchGroup = "true")
        var moneytoryValue: MoneytoryValue? = null,

        userId: String? = null) {
    @Persistent(column = "userid")
    var userId: String? = userId
        @Throws(InvalidDataException::class) set(value) {
            if (value.isNullOrBlank()) throw InvalidDataException("Invalid UserId")
            field = value
        }

    /**
     * Set order date an order no
     */
    private fun generateOrderNo(): Order = apply {
        orderDate = Date(Calendar.getInstance().timeInMillis)
        orderNo = "OD" + orderDate!!.time + ""
    }

    /**
     * Initialize the empty cart
     */
    private fun initCart(): Order = apply {
        lineItems = HashSet()
    }

    /**
     * Maps cart status as open
     */
    open fun markPaymentExepected(): Order = apply {
        status = Status.PAYMENT_EXPECTED
    }

    /**
     * Maps cart status as close
     */
    @Throws(InvalidDataException::class)
    open fun markPaid(): Order = apply {
        if (shippingAddress == null) {
            throw InvalidDataException("Invalid state exception")
        }
        status = Status.PAID
    }

    /**
     *
     * @return total [Double]
     */
    open fun getTotal(): Double? {
        var total: Double = 0.0
        for (lineItem: LineItem in lineItems!!) {
            println("---${arrayOf(lineItem.itemId, lineItem.itemName, lineItem.price, lineItem.quantity).joinToString("--")}")
            total += lineItem.subTotal ?: 0.0
        }
        return total
    }

    /**
     * @return [MoneytoryValue]
     */
    open fun moneytoryValue(): MoneytoryValue? {
        moneytoryValue = MoneytoryValue(getTotal(), "USD")
        return moneytoryValue
    }

    @Throws(InvalidDataException::class)
    open fun addLineItem(itemId: String, itemName: String, price: Double, quantity: Int): Order = apply {
        this.lineItems!!.add(LineItem(itemId = itemId, itemName = itemName, price = price, quantity = quantity, order = this))
        moneytoryValue()
    }

    @Throws(InvalidDataException::class)
    open fun addShippingAddress(shippingLabel: String,
                                address: String,
                                country: String,
                                province: String,
                                postalcode: String,
                                city: String): Order = apply {
        this.shippingAddress = (this.shippingAddress ?: ShippingAddress())
        this.shippingAddress!!.let {
            it.address = address
            it.city = city
            it.country = country
            it.province = province
            it.postalcode = postalcode
            it.label = shippingLabel
        }
    }

    @Throws(InvalidDataException::class)
    open fun addPaymentDetails(paymentMode: String): Order = apply {
        this.payment = Payment(mode = paymentMode, amount = getTotal(), order = this)
        this.markPaid()
    }

    override fun equals(other: Any?): Boolean {
        return other is Order && orderId == other.orderId
    }

    override fun hashCode(): Int = orderId!!.hashCode()

    companion object {
        enum class Status {
            /**
             * Placed, but not payed yet, still changable.
             */
            PAYMENT_EXPECTED,

            /**
             * {@link Order} was payed. No changes allowed to it anymore.
             */
            PAID,

            /**
             * The {@link Order} is currently processed.
             */
            PREPARING,

            /**
             * The {@link Order} is ready to be picked up by the customer.
             */
            READY,

            /**
             * The {@link Order} has been taken.
             */
            TAKEN;
        }
    }
}