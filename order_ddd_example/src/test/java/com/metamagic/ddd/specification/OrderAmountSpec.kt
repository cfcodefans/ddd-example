package com.metamagic.ddd.specification

import com.metamagic.ddd.entity.Order
import com.metamagic.ddd.specification.core.ISpecification

class OrderAmountSpec(private val amount:Double):ISpecification<Order> {
    override fun isValid(any: Order): Boolean {
        TODO("Not yet implemented")
    }
}