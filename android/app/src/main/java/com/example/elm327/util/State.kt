package com.example.elm327.util

class State<Type>(private var value: Type) {
    private var onGet: MutableList<(value: Type) -> Unit> = mutableListOf<(value: Type) -> Unit>()
    private var onSet: MutableList<(value: Type) -> Unit> = mutableListOf<(value: Type) -> Unit>()

    public fun setValue(value: Type): Type {
        this.value = value
        for (func in onSet) {
            func(this.value)
        }
        return this.value
    }

    public fun getValue(): Type {
        for (func in onGet) {
            func(this.value)
        }
        return this.value
    }

    public fun bindOnGet(func: (value: Type) -> Unit): Boolean {
        return onGet.add(func)
    }

    public fun bindOnSet(func: (value: Type) -> Unit): Boolean {
        return onSet.add(func)
    }
}