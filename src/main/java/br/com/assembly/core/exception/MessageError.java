package br.com.assembly.core.exception;

public final class MessageError {

    public static final String VALIDATION_FIELDS = "VALIDATION FIELDS";
    public static final String INVALID_FIELD_TYPE = "INFORMATION INVALID FIELD TYPE";
    public static final String INVALID_FIELDS_TYPE = "INFORMATIONS INVALID FIELDS TYPES";
    public static final String INVALID_REQUEST_TYPE = "INVALID REQUEST TYPE";
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR_BAD_REQUEST = "Invalid request";
    public static final String NOT_FOUND = "not found";
    public static final String UNEXPECTED_ERRO = "Unexpected error";
    public static final String REQUIRED_REQUEST_BODY_IS_MISSING = "Required request body is missing";
    public static final String ALREADY_HAVE_AN_OPEN_SESSION = "Already have an open session";
    public static final String VOTE_FORBIDDEN = "Vote forbidden has record for this subject";
    public static final String ANSWER_INVALID = "Answer invalid does not match subject answers";
    public static final String SESSION_EXPIRED = "Session expired";
    public static final String INVALID_CPF = "Invalid cpf";
    public static final String NOT_AVAILABLE = "Not available";
    public static final String CPF_VERIFICATION_UNAVAILABLE = "CPF verification unavailable";

    public static String NO_EXISTS(final String context){ return String.format("%s no exist", context);}
    public static String GETTING_ALL_CONTEXT_NOT_EXISTS(final String context){ return String.format("%ss no exists", context);};

    private MessageError() {
        throw new IllegalStateException("Utility class");
    }
}
