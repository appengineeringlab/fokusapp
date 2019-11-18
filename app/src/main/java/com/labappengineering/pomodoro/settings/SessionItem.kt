package com.labappengineering.pomodoro.settings
data class SessionItem(
    var key: String,
    var value: String,
    var name: String,
    var valueType: ValueType,
    var index: Int = -1
){
    enum class ValueType{
        Int,
        String
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SessionItem

        if (key != other.key) return false
        if (value != other.value) return false
        if (name != other.name) return false
        if (valueType != other.valueType) return false
        if (index != other.index) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + valueType.hashCode()
        result = 31 * result + index
        return result
    }

}