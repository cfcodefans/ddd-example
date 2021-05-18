package com.metamagic.ddd.specification

import com.metamagic.ddd.entity.Order
import com.metamagic.ddd.specification.core.ISpecification

class OrderAmountSpecification(private val amount: Double) : ISpecification<Order> {
    override fun isValid(od: Order): Boolean = od.moneytoryValue!!.total!! > this.amount
}