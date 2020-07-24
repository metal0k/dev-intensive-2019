package ru.skillbranch.devintensive.extensions

import java.util.function.Predicate

fun <T> MutableList<T>.addIf(item:T, index: Int = 0, predicate: Collection<T>.() -> Boolean): MutableList<T> {
    if (this.predicate()) this.add(index, item)
    return this
}