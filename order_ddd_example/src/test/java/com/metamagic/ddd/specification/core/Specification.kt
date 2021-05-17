package com.metamagic.ddd.specification.core

/**
 * @author cfcodefan as student
 */
interface ISpecification<T> {
    fun isValid(any: T): Boolean
    fun and(spec: ISpecification<T>): ISpecification<T> = AndSpecification(this, spec)
    fun or(spec: ISpecification<T>): ISpecification<T> = OrSpecification(this, spec)

    companion object {
        val DEFAUL_SPEC = object : ISpecification<Any?> {
            override fun isValid(any: Any?): Boolean = false
        }
    }
}

open class AndSpecification<T>(private val spec1: ISpecification<T>, private val spec2: ISpecification<T>) : ISpecification<T> {
    override fun isValid(any: T): Boolean = spec1.isValid(any) && spec2.isValid(any)
}


open class OrSpecification<T>(private val spec1: ISpecification<T>, private val spec2: ISpecification<T>) : ISpecification<T> {
    override fun isValid(any: T): Boolean = spec1.isValid(any) || spec2.isValid(any)
}