package com.metamagic.ddd.entity

import com.metamagic.ddd.exception.InvalidDataException
import javax.jdo.annotations.Persistent
import javax.jdo.annotations.PrimaryKey

open class ShippingAddress(
        @PrimaryKey
        @Persistent(column = "shippingaddressid", customValueStrategy = "uuid")
        var shippingAddressId: String? = null,
        label: String? = null,
        address: String? = null,
        country: String? = null,
        province: String? = null,
        city: String? = null,
        postalcode: String? = null,
        order: Order? = null) {

    @Persistent(column = "label")
    var label: String? = label
        @Throws(InvalidDataException::class) set(value) {
            if (value.isNullOrBlank()) throw InvalidDataException("Invalid Shippinglabel")
            field = value
        }

    @Persistent(column = "address")
    var address: String? = address
        @Throws(InvalidDataException::class) set(value) {
            if (value.isNullOrBlank()) throw InvalidDataException("Invalid address")
            field = value
        }

    @Persistent(column = "country")
    var country: String? = country
        @Throws(InvalidDataException::class) set(value) {
            if (value.isNullOrBlank()) throw InvalidDataException("Invalid country")
            field = value
        }

    @Persistent(column = "province")
    var province: String? = province
        @Throws(InvalidDataException::class) set(value) {
            if (value.isNullOrBlank()) throw InvalidDataException("Invalid province")
            field = value
        }

    @Persistent(column = "province")
    var postalcode: String? = postalcode
        @Throws(InvalidDataException::class) set(value) {
            if (value.isNullOrBlank()) throw InvalidDataException("Invalid postalcode")
            field = value
        }

    @Persistent(column = "orderId")
    var order: Order? = null
        @Throws(InvalidDataException::class) set(value) {
            if (value == null) throw InvalidDataException("Invalid order")
            field = value
        }

    @Persistent(column = "city")
    var city: String? = city
        @Throws(InvalidDataException::class) set(value) {
            if (value == null) throw InvalidDataException("Invalid city")
            field = value
        }

    override fun equals(other: Any?): Boolean {
        return other is ShippingAddress && hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return (label + address + country + province + city + postalcode).hashCode()
    }
}