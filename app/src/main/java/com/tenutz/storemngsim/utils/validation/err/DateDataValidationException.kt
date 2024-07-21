package com.tenutz.storemngsim.utils.validation.err

import com.tenutz.storemngsim.utils.validation.err.base.ValidationErrorCode
import com.tenutz.storemngsim.utils.validation.err.base.ValidationException

class DateDataValidationException: ValidationException(ValidationErrorCode.INVALID_DATE_DATA)