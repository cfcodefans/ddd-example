package com.metamagic.ddd.entity

import javax.jdo.annotations.PersistenceCapable
import javax.jdo.annotations.Persistent
import javax.jdo.annotations.PrimaryKey

/**
 * Line items for the order.
 * @author cfcodefans as student
 *
 */
@PersistenceCapable(table = "lineitems", detachable = "true")
open class LineItem(
        @PrimaryKey
        @Persistent(column = "lineitemid", customValueStrategy = "uuid")
        var lineitemid: String? = null,

        @javax.jdo.annotations.Persistent(column = "orderId")
        var order: Order? = null,

        @Persistent(column = "itemid")
        var itemId: String? = null,

        @Persistent(column = "itemname")
        var itemName: String? = null,

        @Persistent(column = "price")
        var price: Double? = null,

        @Persistent(column = "quantity")
        var quantity: Int? = null,

        @Persistent(column = "subtotal")
        var subTotal: Double? = null,
) {
    override fun equals(other: Any?): Boolean {
        return other is LineItem && other.itemId == this.itemId
    }

    override fun hashCode(): Int = itemId!!.hashCode()


}