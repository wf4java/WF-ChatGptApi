package wf.utils.chat_gpt_api.exception;

public class InvalidAuthenticationException extends BadRequestException {

    private static final int STATUS_CODE = 401;
    private static final String MESSAGE = "Invalid authentication. Please check your credentials.";;

    public InvalidAuthenticationException() {
        super(MESSAGE);
    }

    public InvalidAuthenticationException(String message) {
        super(message != null ? message : MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return STATUS_CODE;
    }

    @Override
    public String getDefaultMessage() {
        return MESSAGE;
    }
}
