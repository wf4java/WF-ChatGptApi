package wf.utils.chat_gpt_api.exception;

public class ServiceUnavailableException extends ServerException {

    private final static int STATUS_CODE = 503;
    private final static String MESSAGE = "Server internal error.";

    public ServiceUnavailableException() {
        super(MESSAGE);
    }


    public ServiceUnavailableException(String message) {
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
