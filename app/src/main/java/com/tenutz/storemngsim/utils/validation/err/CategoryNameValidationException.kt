package com.tenutz.storemngsim.utils.validation.err

import com.tenutz.storemngsim.utils.validation.err.base.ValidationErrorCode
import com.tenutz.storemngsim.utils.validation.err.base.ValidationException

class CategoryNameValidationException: ValidationException(ValidationErrorCode.INVALID_CATEGORY_NAME)