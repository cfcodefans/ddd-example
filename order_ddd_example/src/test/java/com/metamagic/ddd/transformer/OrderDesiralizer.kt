package com.metamagic.ddd.transformer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.ObjectCodec
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.metamagic.ddd.entity.Order
import com.metamagic.ddd.exception.InvalidDataException
import java.io.IOException

open class OrderDesiralizer : JsonDeserializer<Order>() {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Order? {
        val oc: ObjectCodec = p!!.codec
        val ordernode: JsonNode = oc.readTree(p)

        try {
            val order = Order(ordernode.get("userId").asText())
            val shippingAddressNode: JsonNode? = ordernode.get("shippingAddress")
            if (shippingAddressNode != null) {
                order.addShippingAddress(shippingAddressNode["shippinglabel"].asText(),
                    shippingAddressNode["address"].asText(),
                    shippingAddressNode["country"].asText(),
                    shippingAddressNode["province"].asText(),
                    shippingAddressNode["postalcode"].asText(),
                    shippingAddressNode["city"].asText())
            }
            val lineItemsNodes: JsonNode = ordernode.withArray("lineitems")
            for (lineItemsNode in lineItemsNodes) {
                order.addLineItem(lineItemsNode["itemid"].asText(),
                    lineItemsNode["itemname"].asText(),
                    lineItemsNode["price"].asDouble(),
                    lineItemsNode["quantity"].asInt())
            }
            val paymentNode: JsonNode? = ordernode.get("payment")
            if (paymentNode != null) {
                order.addPaymentDetails(paymentNode["paymentmode"].asText())
            }
            return order
        } catch (e: InvalidDataException) {
            e.printStackTrace()
        }

        return null
    }
}