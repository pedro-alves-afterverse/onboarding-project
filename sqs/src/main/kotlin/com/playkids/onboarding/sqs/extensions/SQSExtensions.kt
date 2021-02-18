package com.playkids.onboarding.sqs.extensions

import software.amazon.awssdk.services.sqs.model.MessageAttributeValue


fun String.toMessageAttributeValue(): MessageAttributeValue =
    this.let { MessageAttributeValue.builder().dataType("String").stringValue(it).build() }
