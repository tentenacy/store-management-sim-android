package com.tenutz.storemngsim.data.datasource.api.err

import javax.net.ssl.HttpsURLConnection

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {

    /**
     * COMMON
     */
    INVALID_INPUT_VALUE(HttpsURLConnection.HTTP_BAD_REQUEST, "CMM-001", "잘못된 입력입니다."),
    METHOD_NOT_ALLOWED(HttpsURLConnection.HTTP_BAD_METHOD, "CMM-002", "Method Not Allowed"),
    ENTITY_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "CMM-003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(HttpsURLConnection.HTTP_INTERNAL_ERROR, "CMM-004", "Server Error"),
    INVALID_TYPE_VALUE(HttpsURLConnection.HTTP_BAD_REQUEST, "CMM-005", "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(HttpsURLConnection.HTTP_FORBIDDEN, "CMM-006", "접근이 거부되었습니다."),
    JSON_WRITE_ERROR(HttpsURLConnection.HTTP_UNAUTHORIZED, "CMM-007", "JSON content that are not pure I/O problems"),

    /**
     * BUSINESS
     * MZS-1xxx
     */
    LOGIN_FAIL(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1000", "존재하지 않는 계정이거나, 잘못된 비밀번호입니다."),
    ALREADY_SIGNEDUP(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1001", "이미 가입한 사용자입니다."),
    USER_NOT_AUTHENTICATION(HttpsURLConnection.HTTP_UNAUTHORIZED, "MZS-1002", "인증된 사용자가 아닙니다."),
    USER_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1003", "사용자가 존재하지 않습니다."),
    MENU_REVIEW_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1004", "메뉴 평가 정보가 존재하지 않습니다."),
    STORE_REVIEW_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1005", "가맹점 평가 정보가 존재하지 않습니다."),
    CATEGORY_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1006", "카테고리가 존재하지 않습니다."),
    MAIN_MENU_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1007", "메인메뉴가 존재하지 않습니다."),
    MAIN_MENU_DETAILS_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1008", "메뉴 성분 정보가 존재하지 않습니다."),
    MENU_IMAGE_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1009", "이미지 정보가 존재하지 않습니다."),
    OPTION_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1010", "옵션 메뉴가 존재하지 않습니다."),
    OPTION_GROUP_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1011", "옵션 그룹이 존재하지 않습니다."),
    OPTION_GROUP_MAIN_MENU_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1012", "메인메뉴 옵션 그룹 매핑이 존재하지 않습니다."),
    OPTION_GROUP_OPTION_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1013", "옵션 그룹 옵션 매핑이 존재하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1014", "리프레시 토큰이 존재하지 않습니다."),
    SALES_DETAILS_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1015", "주문 상세가 존재하지 않습니다."),
    SALES_MASTER_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1016", "주문 마스터가 존재하지 않습니다."),
    SALES_PAYMENT_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1017", "주문 결제 정보가 존재하지 않습니다."),
    BRAND_MASTER_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1018", "브랜드 마스터가 존재하지 않습니다."),
    EQUIPMENT_MASTER_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1019", "장비 마스터가 존재하지 않습니다."),
    STORE_MASTER_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1020", "가맹점 마스터가 존재하지 않습니다."),
    HELP_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1021", "도움말이 존재하지 않습니다."),
    TERMS_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1022", "약관이 존재하지 않습니다."),
    TERMS_AGREEMENT_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1023", "약관 동의가 존재하지 않습니다."),
    ALREADY_CATEGORY_CREATED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1024", "이미 생성된 카테고리입니다."),
    ALREADY_MAIN_MENU_CREATED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1025", "이미 생성된 메인메뉴입니다."),
    ALREADY_MAIN_MENU_DETAILS_CREATED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1026", "이미 생성된 메뉴 성분 정보입니다."),
    ALREADY_MAIN_MENU_MAPPED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1027", "이미 옵션 그룹에 설정된 메뉴입니다."),
    NON_EXISTENT_OPTION_GROUP_MAIN_MENU_INCLUDED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1028", "요청에 존재하지 않는 메인메뉴 옵션 그룹 매핑이 있습니다."),
    NON_EXISTENT_MAIN_MENU_INCLUDED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1029", "요청에 존재하지 않는 메인메뉴가 있습니다."),
    NON_EXISTENT_CATEGORY_INCLUDED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1030", "요청에 존재하지 않는 카테고리가 있습니다."),
    ALREADY_OPTION_CREATED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1031", "이미 생성된 옵션입니다."),
    NON_EXISTENT_OPTION_INCLUDED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1032", "요청에 존재하지 않는 옵션이 있습니다."),
    ALREADY_OPTION_MAPPED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1033", "이미 옵션 그룹에 설정된 옵션입니다."),
    NON_EXISTENT_OPTION_GROUP_OPTION_INCLUDED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1034", "요청에 존재하지 않는 옵션 옵션 그룹 매핑이 있습니다."),
    ALREADY_OPTION_GROUP_CREATED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1035", "이미 생성된 옵션그룹입니다."),
    NON_EXISTENT_OPTION_GROUP_INCLUDED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1036", "요청에 존재하지 않는 옵션 그룹이 있습니다."),
    REPLY_MAXIMUM_LEVEL_EXCEEDED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1037", "대댓글 허용 단계를 초과했습니다."),
    NOT_A_REPLY_ERROR(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1038", "대댓글이 아닙니다."),
    NOT_USER_OWN_REPLY_ERROR(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1039", "사용자의 대댓글이 아닙니다."),
    REPLY_MAXIMUM_COUNT_EXCEEDED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1040", "대댓글 등록 허용 갯수를 초과했습니다."),
    MANAGER_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-1041", "매니저가 존재하지 않습니다.."),

    /**
     * SOCIAL
     * MZS-2xxx
     */
    SOCIAL_COMMUNICATION_ERROR(HttpsURLConnection.HTTP_INTERNAL_ERROR, "MZS-2000", "소셜 인증 과정 중 오류가 발생했습니다."),
    SOCIAL_AGREEMENT_ERROR(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-2001", "필수동의 항목에 대해 동의가 필요합니다."),
    INVALID_SOCIAL_TYPE(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-2002", "알 수 없는 소셜 타입입니다."),
    SOCIAL_TOKEN_VALID_FAILED(HttpsURLConnection.HTTP_UNAUTHORIZED, "MZS-2003", "소셜 액세스 토큰 검증에 실패했습니다."),

    /**
     * SECURITY
     * MZS-3xxx
     */
    ACCESS_TOKEN_ERROR(HttpsURLConnection.HTTP_UNAUTHORIZED, "MZS-3000", "액세스 토큰이 만료되거나 잘못된 값입니다."),
    REFRESH_TOKEN_ERROR(HttpsURLConnection.HTTP_UNAUTHORIZED, "MZS-3001", "리프레시 토큰이 만료되거나 잘못된 값입니다."),
    TOKEN_PARSE_ERROR(HttpsURLConnection.HTTP_UNAUTHORIZED, "MZS-3002", "해석할 수 없는 토큰입니다."),

    /**
     * SECURITY
     * MZS-4xxx
     */
    FILE_CONVERT_FAILED(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-4000", "파일을 변환할 수 없습니다."),
    INVALID_FILE_FORMAT(HttpsURLConnection.HTTP_BAD_REQUEST, "MZS-4001", "잘못된 형식의 파일입니다."),
    CLOUD_COMMUNICATION_ERROR(HttpsURLConnection.HTTP_INTERNAL_ERROR, "MZS-4002", "파일 업로드 중 오류가 발생했습니다."),

}