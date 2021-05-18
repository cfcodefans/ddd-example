package com.metamagic.ddd.service

import com.metamagic.ddd.dto.PaymentDTO
import com.metamagic.ddd.dto.ShippingAddressDTO
import com.metamagic.ddd.entity.Order
import com.metamagic.ddd.exception.InvalidDataException
import com.metamagic.ddd.repo.OrderRepository
import com.metamagic.ddd.specification.OrderAmountSpecification
import com.metamagic.ddd.specification.OrderStatusSpecification
import com.metamagic.ddd.specification.core.ISpecification
import com.metamagic.ddd.specification.core.Specification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class OrderServiceImpl {
    @Autowired
    lateinit var orderRepository: OrderRepository


    /**
     * creates the order
     * @param order
     */
    open fun createOrder(order: Order?) {
        orderRepository.saveOrder(order)
    }

    /**
     * adds shipping address to order
     * @param dto
     * @throws InvalidDataException
     * @throws Exception
     */
    @Throws(Exception::class)
    open fun addShippingAddressDetails(dto: ShippingAddressDTO) {
        val order = orderRepository.findByOrderId(dto.orderId)
        order.addShippingAddress(dto.label, dto.address, dto.country, dto.province, dto.postalcode, dto.city)
        orderRepository.saveOrder(order)
    }

    /**
     * adds payment details to order
     * @param dto
     * @throws InvalidDataException
     * @throws Exception
     */
    @Throws(InvalidDataException::class, Exception::class)
    open fun addPaymentDetails(dto: PaymentDTO) {
        val order = orderRepository.findByOrderId(dto.orderId)
        order.addPaymentDetails(dto.paymentmode)
        orderRepository.saveOrder(order)
    }

    /**
     * Apply the discount based on specification specified
     */
    @Throws(Exception::class)
    open fun applyDiscount() {
        val orders = orderRepository.findAllOrders()
        val spe = discountSpecification()
        orders.filter { order: Order? ->
            spe.isValid(order!!)
        }.forEach { order: Order ->
            println(" Apply discount to order - ${order.orderId} ${order.status} ${order.moneytoryValue()?.total}")
        }
    }

    /**
     * Return discount specification
     * @return [Specification]
     */
    private fun discountSpecification(): ISpecification<Order> {
        return OrderStatusSpecification(Order.Companion.Status.PREPARING).or(OrderStatusSpecification(Order.Companion.Status.PAYMENT_EXPECTED))
            .and(OrderAmountSpecification(5000.00))
    }
}