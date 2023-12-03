package wf.utils.chat_gpt_api;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import wf.utils.chat_gpt_api.dto.ChatGptRequestDTO;
import wf.utils.chat_gpt_api.dto.ChatGptStreamResponseDTO;
import wf.utils.chat_gpt_api.dto.ChatGptMessage;
import wf.utils.chat_gpt_api.model.gpt.*;
import wf.utils.chat_gpt_api.model.key.ApiKeysContainer;
import wf.utils.chat_gpt_api.utils.HttpExceptionUtils;


import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class ChatGptStream {


    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client;
    private final ApiKeysContainer apiKeysContainer;


    public ChatGptStream(String apiKey) {
        this(ApiKeysContainer.of(apiKey));
    }

    public ChatGptStream(ApiKeysContainer apiKeysContainer) {
        this.apiKeysContainer = apiKeysContainer;
        client = HttpClient.newBuilder().build();
    }

    public ChatGptStream(String apiKey, Proxy proxy) {
        this(ApiKeysContainer.of(apiKey), proxy);
    }

    public ChatGptStream(ApiKeysContainer apiKeysContainer, Proxy proxy) {
        this.apiKeysContainer = apiKeysContainer;

        ProxySelector proxySelector = new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) { return Collections.singletonList(proxy); }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) { }
        };

        client = HttpClient.newBuilder()
                .proxy(proxySelector)
                .build();
    }




    public void ask(String message, GptStream stream) {
        ask(message, 2048, stream);
    }


    public void ask(String message, int maxTokens, GptStream stream) {
        ask(ChatGptRequestDTO.builder()
                .maxTokens(maxTokens)
                .model(ChatGptModel.GPT_3_5_TURBO_1106.getName())
                .messages(Collections.singletonList(new ChatGptMessage(ChatGptRole.USER.name(), message)))
                .build(), stream);
    }


    @SneakyThrows
    public void ask(ChatGptRequestDTO requestDTO, GptStream stream) {
        requestDTO.setStream(true);

        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Authorization","Bearer " + apiKeysContainer.getNextKey())
                .setHeader("content-type","application/json; charset=utf-8")
                .uri(URI.create(ChatGptConstant.CHAT_GPT_API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestDTO), StandardCharsets.UTF_8))
                .build();


        ArrayList<ChatGptStreamResponseDTO> chunks = new ArrayList<>();

        StringSubscriber.ResponseStream responseStream = new StringSubscriber.ResponseStream() {
            @Override
            public void next(String next) {
                try {
                    ChatGptStreamResponseDTO response = objectMapper.readValue(next, ChatGptStreamResponseDTO.class);
                    chunks.add(response);

                    for(ChatGptStreamResponseDTO.Choice choice : response.getChoices())
                        stream.next(choice.getIndex(), choice.getDelta().getContent());

                } catch (Exception ignore) { }

            }

            @Override
            public void end(String end) {
                Map<Integer, List<ChatGptStreamResponseDTO.Choice>> choiceLists = chunks.stream()
                        .flatMap(response -> response.getChoices().stream())
                        .collect(Collectors.groupingBy(ChatGptStreamResponseDTO.Choice::getIndex));

                choiceLists.forEach((i, l) -> { stream.end(i, ChatGptStreamResponseDTO.choicesToContent(l)); });
            }

            @Override
            public void start() {
                stream.start();
            }

            @Override
            public void error(RuntimeException error) {
                stream.error(error);
            }
        };


        int code = client.sendAsync(request, responseInfo -> new StringSubscriber(responseStream))
                .thenApply(HttpResponse::statusCode)
                .get();

        HttpExceptionUtils.getExceptionByStatusCode(code).ifPresent(stream::error);
    }





    private static class StringSubscriber implements HttpResponse.BodySubscriber<String> {

        private final CompletableFuture<String> bodyCF = new CompletableFuture<>();
        private final List<ByteBuffer> responseData = new CopyOnWriteArrayList<>();
        private final ResponseStream stream;
        private Flow.Subscription subscription;


        public StringSubscriber(ResponseStream stream) {
            this.stream = stream;
        }

        @Override
        public CompletionStage<String> getBody() {
            return bodyCF;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
            stream.start();
        }

        @Override
        public void onNext(List<ByteBuffer> buffers) {
            String ret = asString(buffers);

            if(!ret.substring(6).equals("[DONE]")) {
                String[] data = ret.substring(6).split("\ndata: ");
                for(String d : data)
                    stream.next(d);
            }

            buffers.forEach(ByteBuffer::rewind); // Rewind after reading
            responseData.addAll(buffers);
            subscription.request(1); // Request next item
        }

        @Override
        public void onError(Throwable throwable) {
            stream.error(new RuntimeException(throwable));
            bodyCF.completeExceptionally(throwable);
        }

        @Override
        public void onComplete() {
            stream.end(asString(responseData));
            bodyCF.complete(asString(responseData));
        }

        private String asString(List<ByteBuffer> buffers) {
            return new String(toBytes(buffers), StandardCharsets.UTF_8);
        }

        private byte[] toBytes(List<ByteBuffer> buffers) {
            int size = buffers.stream()
                    .mapToInt(ByteBuffer::remaining)
                    .sum();
            byte[] bs = new byte[size];
            int offset = 0;
            for (ByteBuffer buffer : buffers) {
                int remaining = buffer.remaining();
                buffer.get(bs, offset, remaining);
                offset += remaining;
            }
            return bs;
        }

        private static interface ResponseStream {
            public void next(String next);
            public void end(String end);
            public void start();
            public void error(RuntimeException error);
        }

    }
}
