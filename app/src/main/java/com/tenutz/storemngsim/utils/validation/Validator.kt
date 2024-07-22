package com.tenutz.storemngsim.utils.validation

import com.tenutz.storemngsim.utils.ext.localDateFrom
import com.tenutz.storemngsim.utils.validation.err.*
import com.tenutz.storemngsim.utils.validation.err.base.ValidationException

object Validator {

    private fun validateRequiredInput(text: String) {
        if(text.isBlank()) {
            throw RequiredInputValidationException()
        }
    }

    private fun validateTextData(text: String) {
        if(text.contains("'") || text.contains("\"") || text.contains("`")) {
            throw TextDataValidationException()
        }
    }

    fun validateNumberData(number: String) {
        if(number.isNotBlank() && !"""[0-9]+""".toRegex().matches(number)) {
            throw NumberDataValidationException()
        }
    }

    private fun validateDateData(date: String) {
        if(!"""\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])""".toRegex().matches(date)) {
            throw DateDataValidationException()
        }
    }

    fun validateCategoryCode(categoryCode: String, required: Boolean = false) {
        if(required) validateRequiredInput(categoryCode)
        if(categoryCode.isBlank()) return
        validateTextData(categoryCode)
        if(categoryCode.length > 10) {
            throw CategoryCodeValidationException()
        }
    }

    fun validateCategoryName(categoryName: String, required: Boolean = false) {
        if(required) validateRequiredInput(categoryName)
        if(categoryName.isBlank()) return
        validateTextData(categoryName)
        //if(!"""[ㄱ-ㅎㅏ-ㅣ가-힣0-9\{\}\[\]\/?.,;:|\)*~!^\-_+<>@\#${'$'}%&\\\=\(]+""".toRegex().matches(categoryName) || categoryName.length > 50) {
        if(!"""[^"'`]+""".toRegex().matches(categoryName) || categoryName.length > 50) {
            throw CategoryNameValidationException()
        }
    }

    fun validateBusinessNumber(businessNumber: String, required: Boolean = false) {
        if(required) validateRequiredInput(businessNumber)
        if(businessNumber.isBlank()) return
        validateTextData(businessNumber)
        if(!"""[0-9]+""".toRegex().matches(businessNumber) || businessNumber.length > 10) {
            throw BusinessNumberValidationException()
        }
    }

    fun validateRepresentative(representative: String, required: Boolean = false) {
        if(required) validateRequiredInput(representative)
        if(representative.isBlank()) return
        validateTextData(representative)
        if(representative.length > 10) {
            throw RepresentativeValidationException()
        }
    }

    fun validatePhoneNumber(phoneNumber: String, required: Boolean = false) {
        if(required) validateRequiredInput(phoneNumber)
        if(phoneNumber.isBlank()) return
        validateTextData(phoneNumber)
        if(!"""^\d{2,3}-?\d{3,4}-?\d{4}""".toRegex().matches(phoneNumber) || phoneNumber.length > 20) {
            throw PhoneNumberValidationException()
        }
    }

    fun validateAddress(address: String, required: Boolean = false) {
        if(required) validateRequiredInput(address)
        if(address.isBlank()) return
        validateTextData(address)
        if(address.length > 500) {
            throw AddressValidationException()
        }
    }

    fun validateTid(tid: String, required: Boolean = false) {
        if(required) validateRequiredInput(tid)
        if(tid.isBlank()) return
        validateTextData(tid)
        if(tid.length > 20) {
            throw TidValidationException()
        }
    }

    fun validateMenuCode(menuCode: String, required: Boolean = false) {
        if(required) validateRequiredInput(menuCode)
        if(menuCode.isBlank()) return
        validateTextData(menuCode)
        if(menuCode.length > 20) {
            throw MenuCodeValidationException()
        }
    }

    fun validateMenuName(menuName: String, required: Boolean = false) {
        if(required) validateRequiredInput(menuName)
        if(menuName.isBlank()) return
        validateTextData(menuName)
        //if(!"""[ㄱ-ㅎㅏ-ㅣ가-힣0-9\{\}\[\]\/?.,;:|\)*~!^\-_+<>@\#${'$'}%&\\\=\(]+""".toRegex().matches(menuName) || menuName.length > 100) {
        if(!"""[^"'`]+""".toRegex().matches(menuName) || menuName.length > 100) {
            throw MenuNameValidationException()
        }
    }

    fun validatePrice(price: String, required: Boolean = false) {
        if(required) validateRequiredInput(price)
        if(price.isBlank()) return
        validateNumberData(price)
        price.toIntOrNull() ?: throw PriceValidationException()
    }

    fun validateDiscountingPrice(discountingPrice: String, required: Boolean = false) {
        if(required) validateRequiredInput(discountingPrice)
        if(discountingPrice.isBlank()) return
        validateNumberData(discountingPrice)
        discountingPrice.toIntOrNull() ?: throw DiscountingPriceValidationException()
    }

    fun validateAdditionalPackagingPrice(additionalPackagingPrice: String, required: Boolean = false) {
        if(required) validateRequiredInput(additionalPackagingPrice)
        if(additionalPackagingPrice.isBlank()) return
        validateNumberData(additionalPackagingPrice)
        additionalPackagingPrice.toIntOrNull() ?: throw AdditionalPackagingPriceValidationException()
    }

    fun validateIngredientDetails(ingredientDetails: String, required: Boolean = false) {
        if(required) validateRequiredInput(ingredientDetails)
        if(ingredientDetails.isBlank()) return
        validateTextData(ingredientDetails)
        if(ingredientDetails.length > 500) {
            throw IngredientDetailsValidationException()
        }
    }

    fun validateShowDate(showDateFrom: String, showDateTo: String, required: Boolean = false) {
        if(required) {
            validateRequiredInput(showDateFrom)
            validateRequiredInput(showDateTo)
        }
        if(showDateFrom.isBlank() && showDateTo.isBlank()) return
//        validateDateData(showDateFrom)
//        validateDateData(showDateTo)
        if(localDateFrom(showDateFrom)?.let { localDateFrom(showDateTo)?.isBefore(it) != false } != false) {
            throw ShowDateValidationException()
        }
    }

    fun validateEventDate(eventDateFrom: String, eventDateTo: String, required: Boolean = false) {
        if(required) {
            validateRequiredInput(eventDateFrom)
            validateRequiredInput(eventDateFrom)
        }
        if(eventDateFrom.isBlank() && eventDateTo.isBlank()) return
//        validateDateData(eventDateFrom)
//        validateDateData(eventDateTo)
        if(localDateFrom(eventDateFrom)?.let { localDateFrom(eventDateTo)?.isBefore(it) != false } != false) {
            throw EventDateValidationException()
        }
    }

    fun validateMemo(memo: String, required: Boolean = false) {
        if(required) validateRequiredInput(memo)
        if(memo.isBlank()) return
        validateTextData(memo)
        //if(!"""[ㄱ-ㅎㅏ-ㅣ가-힣0-9\{\}\[\]\/?.,;:|\)*~!^\-_+<>@\#${'$'}%&\\\=\(]+""".toRegex().matches(memo) || memo.length > 200) {
        if(!"""[^"'`]+""".toRegex().matches(memo) || memo.length > 200) {
            throw MemoValidationException()
        }
    }

    fun validateOptionGroupCode(optionGroupCode: String, required: Boolean = false) {
        if(required) validateRequiredInput(optionGroupCode)
        if(optionGroupCode.isBlank()) return
        validateTextData(optionGroupCode)
        if(optionGroupCode.length > 5) {
            throw OptionGroupCodeValidationException()
        }
    }

    fun validateOptionGroupName(optionGroupName: String, required: Boolean = false) {
        if(required) validateRequiredInput(optionGroupName)
        if(optionGroupName.isBlank()) return
        validateTextData(optionGroupName)
        //if(!"""[ㄱ-ㅎㅏ-ㅣ가-힣0-9\{\}\[\]\/?.,;:|\)*~!^\-_+<>@\#${'$'}%&\\\=\(]+""".toRegex().matches(optionGroupName) || optionGroupName.length > 200) {
        if(!"""[^"'`]+""".toRegex().matches(optionGroupName) || optionGroupName.length > 200) {
            throw OptionGroupNameValidationException()
        }
    }

    fun validateReviewReplyContent(content: String, required: Boolean = false) {
        if(required) validateRequiredInput(content)
        if(content.isBlank()) return
        validateTextData(content)
        if(content.length > 4000) {
            throw ReviewReplyContentValidationException()
        }
    }

    fun validate(onValidation: () -> Unit, onSuccess: () -> Unit, onFailure: (ValidationException) -> Unit) {
        try {
            onValidation()
            onSuccess()
        } catch (e: ValidationException) {
            onFailure(e)
        }
    }

}