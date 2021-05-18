package com.metamagic.ddd.repo

import com.metamagic.ddd.entity.Order

interface IOrderRepository {
    /**
     * Saves order
     * @param order
     */
    fun saveOrder(order: Order?)

    /**
     * Fetch order based on order id
     * @param orderId
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun findByOrderId(orderId: String?): Order?

    /**
     * Fetch all orders
     * @return [<]
     * @throws Exception
     */
    @Throws(Exception::class)
    fun findAllOrders(): Collection<Order?>?
}