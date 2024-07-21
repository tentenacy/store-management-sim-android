package com.tenutz.storemngsim.utils.validation.err

import com.tenutz.storemngsim.utils.validation.err.base.ValidationErrorCode
import com.tenutz.storemngsim.utils.validation.err.base.ValidationException

class NumberDataValidationException: ValidationException(ValidationErrorCode.INVALID_NUMBER_DATA)