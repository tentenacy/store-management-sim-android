package com.tenutz.storemngsim.utils.validation.err.base

enum class ValidationErrorCode(
    val code: String,
    val message: String,
) {

    INVALID_TEXT_DATA("VALID-001", "입력에 유효하지 않은 문자가 포함되어 있습니다."),
    INVALID_NUMBER_DATA("VALID-002", "숫자 입력에 문자가 포함되어 있습니다."),
    INVALID_DATE_DATA("VALID-003", "숫자 입력에 문자가 포함되어 있습니다."),
    INPUT_REQUIRED("VALID-004", "필수 항목을 입력해야 합니다."),
    INVALID_CATEGORY_CODE("VALID-005", "카테고리 코드는 10자 이내여야 합니다."),
    INVALID_CATEGORY_NAME("VALID-006", "카테고리명은 한글만 입력 가능하며, 50자 이내여야 합니다."),
    INVALID_BUSINESS_NUMBER("VALID-007", "유효하지 않은 사업자 번호입니다."),
    INVALID_REPRESENTATIVE("VALID-008", "대표자명은 10자 이내여야 합니다."),
    INVALID_PHONE_NUMBER("VALID-009", "유효하지 않은 전화번호입니다."),
    INVALID_ADDRESS("VALID-010", "주소는 500자 이내여야 합니다."),
    INVALID_TID("VALID-011", "TID는 20자 이내여야 합니다."),
    INVALID_MENU_CODE("VALID-012", "메뉴 코드는 20자 이내여야 합니다."),
    INVALID_MENU_NAME("VALID-013", "메뉴명은 한글만 입력 가능하며, 100자 이내여야 합니다."),
    INVALID_PRICE("VALID-014", "가격의 입력 범위를 초과했습니다."),
    INVALID_DISCOUNTING_PRICE("VALID-015", "할인가격의 입력 범위를 초과했습니다."),
    INVALID_ADDITIONAL_PACKAGING_PRICE("VALID-016", "포장추가 비용의 입력 범위를 초과했습니다."),
    INVALID_INGREDIENT_DETAILS("VALID-017", "재료/성분정보는 500자 이내여야 합니다."),
    INVALID_SHOW_DATE("VALID-018", "유효하지 않은 메뉴 표시 기간입니다."),
    INVALID_EVENT_DATE("VALID-019", "유효하지 않은 이벤트 기간입니다."),
    INVALID_MEMO("VALID-020", "메모는 한글만 입력 가능하며, 200자 이내여야 합니다."),
    INVALID_OPTION_GROUP_CODE("VALID-021", "옵션그룹 코드는 5자 이내여야 합니다."),
    INVALID_OPTION_GROUP_NAME("VALID-022", "옵션그룹명은 한글만 입력 가능하며, 200자 이내여야 합니다."),
    INVALID_REVIEW_REPLY_CONTENT("VALID-023", "평가답변은 4000자 이내여야 합니다."),
}