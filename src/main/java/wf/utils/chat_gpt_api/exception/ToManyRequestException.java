package wf.utils.chat_gpt_api.exception;




public class ToManyRequestException extends BadRequestException {


    private final static int STATUS_CODE = 429;
    private final static String MESSAGE = "Too many requests. Please try again later.";

    public ToManyRequestException() {
        super(MESSAGE);
    }

    public ToManyRequestException(String message) {
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
