package org.example.swp391.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.swp391.constant.AccountConst;
import org.example.swp391.constant.CategoryConst;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorCode {
    // General errors
    UNCATEGORIZED_ERROR(9999, "Uncategorized error"),
    INVALID_KEY(1001, "Invalid message key"),

    // Account existence errors (1000-1009)
    EMAIL_EXISTED_ERROR(1002, AccountConst.EMAIL_EXISTED),
    USERNAME_EXISTED_ERROR(1003, AccountConst.USERNAME_EXISTED),
    ACCOUNT_NOT_EXIST_ERROR(1004, AccountConst.ACCOUNT_NOT_EXIST),
    
    // Null checks (1010-1019)
    USERNAME_NULL_ERROR(1010, AccountConst.USERNAME_NULL),
    EMAIL_NULL_ERROR(1011, AccountConst.EMAIL_NULL),
    PASSWORD_NULL_ERROR(1012, AccountConst.PASSWORD_NULL),
    USER_ID_NULL_ERROR(1013, AccountConst.USER_ID_NULL),
    
    // Empty/Blank checks (1020-1029)
    USERNAME_EMPTY_ERROR(1020, AccountConst.USERNAME_EMPTY),
    USERNAME_BLANK_ERROR(1021, AccountConst.USERNAME_BLANK),
    EMAIL_EMPTY_ERROR(1022, AccountConst.EMAIL_EMPTY),
    EMAIL_BLANK_ERROR(1023, AccountConst.EMAIL_BLANK),
    
    // Validation errors (1030-1039)
    USERNAME_INVALID(1030, AccountConst.USERNAME_INVALID),
    EMAIL_INVALID(1031, AccountConst.EMAIL_INVALID),
    PASSWORD_INVALID(1032, AccountConst.PASSWORD_INVALID),
    FIRSTNAME_INVALID(1033, AccountConst.FIRSTNAME_INVALID),
    LASTNAME_INVALID(1034, AccountConst.LASTNAME_INVALID),
    PHONE_INVALID(1035, AccountConst.PHONE_INVALID),
    ADDRESS_INVALID(1036, AccountConst.ADDRESS_INVALID),
    
    // Other business rules (1040-1049)
    CANNOT_DELETE_ADMIN_ERROR(1040, AccountConst.CANNOT_DELETE_ADMIN),
    USER_ID_POSITIVE_ERROR(1041, AccountConst.USER_ID_POSITIVE),







    // Category existence errors (4000-4009)
    CATEGORY_NOT_EXIST_ERROR(4000, CategoryConst.CATEGORY_NOT_EXIST),
    NAME_EXISTED_ERROR(4001, CategoryConst.NAME_EXISTED),

    // Category null checks (4010-4019)
    CATEGORY_ID_NULL_ERROR(4010, CategoryConst.CATEGORY_ID_NULL),
    NAME_NULL_ERROR(4011, CategoryConst.NAME_NULL),
    CATEGORY_STATUS_NULL_ERROR(4012, CategoryConst.STATUS_NULL),

    // Category empty/blank checks (4020-4029)
    NAME_EMPTY_ERROR(4020, CategoryConst.NAME_EMPTY),
    NAME_BLANK_ERROR(4021, CategoryConst.NAME_BLANK),
    NAME_REQUIRED_ERROR(4022, CategoryConst.NAME_REQUIRED),
    CATEGORY_STATUS_REQUIRED_ERROR(4023, CategoryConst.STATUS_REQUIRED),

    // Category validation errors (4030-4039)
    NAME_INVALID(4030, CategoryConst.NAME_INVALID),
    DESCRIPTION_INVALID(4031, CategoryConst.DESCRIPTION_INVALID),

    // Category other business rules (4040-4049)
    CATEGORY_ID_POSITIVE_ERROR(4040, CategoryConst.CATEGORY_ID_POSITIVE),




    ;

    private int code;
    private String message;
}
