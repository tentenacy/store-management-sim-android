package com.tenutz.storemngsim.utils.validation.err

import com.tenutz.storemngsim.utils.validation.err.base.ValidationErrorCode
import com.tenutz.storemngsim.utils.validation.err.base.ValidationException

class OptionGroupNameValidationException: ValidationException(ValidationErrorCode.INVALID_OPTION_GROUP_NAME)