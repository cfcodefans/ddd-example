package com.metamagic.ddd.service

import com.metamagic.ddd.dto.PaymentDTO
import com.metamagic.ddd.dto.ShippingAddressDTO
import com.metamagic.ddd.entity.Order
import com.metamagic.ddd.exception.InvalidDataException

interface IOrderService {
    /**
     * creates the order
     * @param order
     */
    fun createOrder(order: Order?)

    /**
     * adds shipping address to order
     * @param dto
     * @throws InvalidDataException
     * @throws Exception
     */
    @Throws(InvalidDataException::class, Exception::class)
    fun addShippingAddressDetails(dto: ShippingAddressDTO?)

    /**
     * adds payment details to order
     * @param dto
     * @throws InvalidDataException
     * @throws Exception
     */
    @Throws(InvalidDataException::class, Exception::class)
    fun addPaymentDetails(dto: PaymentDTO?)

    /**
     * Prints all order based on Specification given
     * @throws Exception
     */
    @Throws(Exception::class)
    fun applyDiscount()
}