package org.example.swp391.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.swp391.constant.AccountConst;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_EXISTED_ERROR(1001, "User existed"),
    EMAIL_EXISTED_ERROR(1002, AccountConst.EMAIL_EXISTED),
    USERNAME_EXISTED_ERROR(1003, AccountConst.USERNAME_EXISTED),
    USERNAME_NULL_ERROR(1004, AccountConst.USERNAME_NULL),
    EMAIL_NULL_ERROR(1005, AccountConst.EMAIL_NULL),
    PASSWORD_NULL_ERROR(1006, AccountConst.PASSWORD_NULL),
    ACCOUNT_NOT_EXIST_ERROR(1007, AccountConst.ACCOUNT_NOT_EXIST),
    CANNOT_DELETE_ADMIN_ERROR(1008, AccountConst.CANNOT_DELETE_ADMIN),
    USERNAME_EMPTY_ERROR(1009, AccountConst.USERNAME_EMPTY),
    USERNAME_BLANK_ERROR(1010, AccountConst.USERNAME_BLANK),
    EMAIL_EMPTY_ERROR(1011, AccountConst.EMAIL_EMPTY),
    EMAIL_BLANK_ERROR(1012, AccountConst.EMAIL_BLANK),
    USER_ID_NULL_ERROR(1013, AccountConst.USER_ID_NULL),
    USER_ID_POSITIVE_ERROR(1014, AccountConst.USER_ID_POSITIVE),
    ;

    private int code;
    private String message;

}
