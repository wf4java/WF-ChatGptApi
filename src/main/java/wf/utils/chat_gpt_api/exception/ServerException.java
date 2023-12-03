package wf.utils.chat_gpt_api.exception;

public class ServerException extends HttpException {

    private final static int STATUS_CODE = 500;
    private final static String MESSAGE = "Server internal error.";


    public ServerException() {
        super(MESSAGE);
    }

    public ServerException(String message) {
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
