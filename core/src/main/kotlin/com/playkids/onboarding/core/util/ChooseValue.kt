package com.playkids.onboarding.core.util

import com.movile.kotlin.commons.dynamodb.toAttributeValue

class ChooseValue(private val intValue: Int?, private val floatValue: Float?) {
    fun chooseToAttributeValue() = intValue?.toAttributeValue() ?: floatValue.toAttributeValue()
}