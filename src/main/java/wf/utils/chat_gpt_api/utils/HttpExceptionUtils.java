package wf.utils.chat_gpt_api.utils;


import lombok.SneakyThrows;
import okhttp3.Response;
import wf.utils.chat_gpt_api.exception.*;

import java.util.Optional;

public class HttpExceptionUtils {


    public static void throwByResponse(Response response) {
        Optional<RuntimeException> exceptionOptional = getExceptionByResponse(response);
        if(exceptionOptional.isPresent()) throw exceptionOptional.get();
    }


    @SneakyThrows
    public static Optional<RuntimeException> getExceptionByResponse(Response response) {
        if(response.code() == 200) return Optional.empty();

        String body = response.body() != null ? response.body().string() : null;

        if(response.code() == 400) return Optional.of(new BadRequestException(body));
        if(response.code() == 401) return Optional.of(new InvalidAuthenticationException(body));
        if(response.code() == 429) return Optional.of(new ToManyRequestException(body));

        if(response.code() == 500) return Optional.of(new ServerException(body));
        if(response.code() == 503) return Optional.of(new ServiceUnavailableException(body));

        if(response.code() >= 400) return Optional.of(new CustomHttpException(body, response.code(), "Error with status code: " + response.code()));

        return Optional.empty();
    }

    @SneakyThrows
    public static Optional<RuntimeException> getExceptionByStatusCode(int code) {
        if(code == 200) return Optional.empty();

        if(code == 400) return Optional.of(new BadRequestException());
        if(code == 401) return Optional.of(new InvalidAuthenticationException());
        if(code == 429) return Optional.of(new ToManyRequestException());

        if(code == 500) return Optional.of(new ServerException());
        if(code == 503) return Optional.of(new ServiceUnavailableException());

        if(code >= 400) return Optional.of(new CustomHttpException(code, "Error with status code: " + code));

        return Optional.empty();
    }

}
