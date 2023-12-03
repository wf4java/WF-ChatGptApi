package wf.utils.chat_gpt_api.exception;


import org.jetbrains.annotations.Nullable;

public class CustomHttpException extends HttpException{

    private final int statusCode;
    private final String defaultMessage;

    public CustomHttpException(int statusCode, String defaultMessage) {
        this.statusCode = statusCode;
        this.defaultMessage = defaultMessage;
    }

    public CustomHttpException(@Nullable String message, int statusCode, String defaultMessage) {
        super(message != null ? message : defaultMessage);
        this.statusCode = statusCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }
}
