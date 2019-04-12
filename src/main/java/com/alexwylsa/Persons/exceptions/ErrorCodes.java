package com.alexwylsa.Persons.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
    UNKNOWN_ERROR("000000", "unknown_error",HttpStatus.INTERNAL_SERVER_ERROR),
    MISSING_PARAMETER("000001", "missing_parameter", HttpStatus.BAD_REQUEST),
    /* GENERAL */
    NO_PERMISSIONS("001001", "no_permissions", HttpStatus.BAD_REQUEST),
    /* USER */
    USER_CANT_DELETE_HIMSELF("002001", "user_cant_delete_himself", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST("002002", "user_not_exist", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXIST("002003", "user_already_exist", HttpStatus.BAD_REQUEST),
    USER_WRONG_ID("002004", "user_wrong_id", HttpStatus.BAD_REQUEST),
    USER_EMPTY_DATA("002005", "user_empty_data", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("002006", "user_not_found", HttpStatus.BAD_REQUEST),
    /* Department */
    DEPARTMENT_NOT_EXIST("003001", "department_not_exist", HttpStatus.BAD_REQUEST),
    DEPARTMENT_WRONG_ID("003002", "department_wrong_id", HttpStatus.BAD_REQUEST),
    DEPARTMENT_NOT_FOUND("003003", "department_not_found", HttpStatus.BAD_REQUEST),
    DEPARTMENT_ALREADY_EXIST("003004", "department_already_exist", HttpStatus.BAD_REQUEST),
    /* Staff */
    STAFF_NOT_EXIST("004001", "staff_not_exist", HttpStatus.BAD_REQUEST),
    STAFF_WRONG_ID("004002", "staff_wrong_id", HttpStatus.BAD_REQUEST),
    STAFF_EMPTY_DATA("004003", "staff_empty_data", HttpStatus.BAD_REQUEST),
    STAFF_NOT_FOUND("004004", "staff_not_found", HttpStatus.BAD_REQUEST),
    /* File */
    FILE_NOT_FOUND("005001", "file_not_found", HttpStatus.BAD_REQUEST),
    FILE_SENDING_FAILED("005002", "file_wrong_id", HttpStatus.BAD_REQUEST),
    FILE_EMPTY_DATA("005003", "file_empty_data", HttpStatus.BAD_REQUEST),
    /* Email */
    EMAIL_SENDING_FAILED("006001", "email_sending_failed", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
