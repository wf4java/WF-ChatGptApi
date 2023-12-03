package wf.utils.chat_gpt_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import okhttp3.*;
import wf.utils.chat_gpt_api.dto.ChatGptMessage;
import wf.utils.chat_gpt_api.dto.ChatGptRequestDTO;
import wf.utils.chat_gpt_api.dto.ChatGptResponseDTO;
import wf.utils.chat_gpt_api.model.gpt.ChatGptConstant;
import wf.utils.chat_gpt_api.model.gpt.ChatGptRole;
import wf.utils.chat_gpt_api.model.gpt.ChatGptModel;
import wf.utils.chat_gpt_api.model.key.ApiKeysContainer;
import wf.utils.chat_gpt_api.utils.HttpExceptionUtils;

import java.net.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class ChatGpt {


    private final ApiKeysContainer apiKeysContainer;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public ChatGpt(String apiKey) {
        this(ApiKeysContainer.of(apiKey), Proxy.NO_PROXY);
    }

    public ChatGpt(ApiKeysContainer apiKeyContainer) {
        this(apiKeyContainer, Proxy.NO_PROXY);
    }


    public ChatGpt(String apiKey, OkHttpClient client) {
        this(ApiKeysContainer.of(apiKey), client);
    }

    public ChatGpt(ApiKeysContainer apiKeyContainer, OkHttpClient client) {
        this.apiKeysContainer = apiKeyContainer;
        this.client = client;
    }

    public ChatGpt(String apiKey, Proxy proxy) {
        this(ApiKeysContainer.of(apiKey), proxy);
    }

    public ChatGpt(ApiKeysContainer apiKeyContainer, Proxy proxy) {
        this.apiKeysContainer = apiKeyContainer;
        client = new OkHttpClient.Builder()
                .proxy(proxy)
                .connectTimeout(300, TimeUnit.SECONDS)
                .callTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();
    }


    public String ask(String message) {
        return ask(message, 2048);
    }

    public String ask(String message, int maxTokens) {
        return ask(ChatGptRequestDTO.builder()
                .maxTokens(maxTokens)
                .model(ChatGptModel.GPT_3_5_TURBO_1106.getName())
                .messages(Collections.singletonList(new ChatGptMessage(ChatGptRole.USER.getName(), message)))
                .build()).get(0);
    }


    @SneakyThrows
    public List<String> ask(ChatGptRequestDTO gptRequestDTO) {
        gptRequestDTO.setStream(false);
        RequestBody body = RequestBody.create(objectMapper.writeValueAsString(gptRequestDTO), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(ChatGptConstant.CHAT_GPT_API_URL)
                .header("Authorization", "Bearer " + apiKeysContainer.getNextKey())
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            HttpExceptionUtils.throwByResponse(response);
            return objectMapper.readValue(response.body().string(), ChatGptResponseDTO.class).getChoices().stream().map(c -> c.getMessage().getContent()).toList();
        }
    }

}
