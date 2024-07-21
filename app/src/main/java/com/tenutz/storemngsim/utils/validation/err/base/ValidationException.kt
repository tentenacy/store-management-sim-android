package com.tenutz.storemngsim.utils.validation.err.base

open class ValidationException(val errorCode: ValidationErrorCode): RuntimeException()