package wf.utils.chat_gpt_api.exception;

public class BadRequestException extends HttpException {

    private final static int STATUS_CODE = 400;
    private final static String MESSAGE = "Bad request error.";


    public BadRequestException() {
        super(MESSAGE);
    }

    public BadRequestException(String message) {
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
