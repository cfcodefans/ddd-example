package com.metamagic.ddd.repo

import com.metamagic.ddd.entity.Order
import org.jetbrains.kotlin.utils.addToStdlib.cast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.jdo.PersistenceManager
import javax.jdo.PersistenceManagerFactory
import javax.jdo.Query

@Repository
open class OrderRepositoryImpl {
    @Autowired
    lateinit var pmf: PersistenceManagerFactory

    private fun pm(): PersistenceManager = pmf.persistenceManager

    /**
     * Saves order
     */
    open fun saveOrder(order: Order) {
        val pm = pm()
        pm.detachAllOnCommit = true
        pm.makePersistent(order)
        pm.close()
    }

    /**
     * Fetch order based on order id
     */
    @Throws(Exception::class)
    open fun findByOrderId(orderId: String?): Order? {
        val pm = pm()
        val query: Query = pm.newQuery(Order::class.java)
        query.setFilter("this.orderId==:orderId")
        query.setUnique(true)
        return query.execute(orderId, true)?.cast() ?: throw Exception("Unable to retrive data")
    }

    /**
     * Fetch all orders
     * @return [<]
     * @throws Exception
     */
    @Throws(java.lang.Exception::class)
    open fun findAllOrders(): Collection<Order>? {
        val pm = pm()
        val query: Query = pm.newQuery(Order::class.java)
        return query.execute(true).cast()
    }
}