package com.metamagic.ddd.entity

import com.google.common.base.Objects
import javax.jdo.annotations.PersistenceCapable
import javax.jdo.annotations.Persistent

/**
 * Embedded value object for {@link Order}
 * @author cfcodefans as student
 *
 */
@PersistenceCapable(detachable = "true", embeddedOnly = "true")
open class MoneytoryValue(
        @Persistent(column = "grandtotal")
        var total: Double? = null,

        @Persistent(column = "unit")
        var unit: String? = null) {

    override fun equals(other: Any?): Boolean {
        return other is MoneytoryValue && this.total == other.total && this.unit == other.unit
    }

    override fun hashCode(): Int {
        return Objects.hashCode(total, unit)
    }
}