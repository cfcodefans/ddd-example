package com.metamagic.ddd.specification

import com.metamagic.ddd.entity.Order
import com.metamagic.ddd.specification.core.ISpecification

class OrderStatusSpecification(private val status: Order.Companion.Status) : ISpecification<Order> {
    override fun isValid(od: Order): Boolean = status == od.status
}