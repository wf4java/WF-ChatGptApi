# WF-ChatGptApi:
## Maven:
`Java(min): 17`
```xml
  <dependency>
    <groupId>io.github.wf4java</groupId>
    <artifactId>WF-ChatGptApi</artifactId>
    <version>1.0</version>
  </dependency>
```

## Examples:

### Create and generate:
```java
ChatGpt gpt = new ChatGpt("api-key");

String result1 = gpt.ask("Say hello in 10 languages"); // 2048 tokens, model: GPT_3_5_TURBO_1106
String result2 = gpt.ask("Say hello in 10 languages", 512); // 512 tokens, model: GPT_3_5_TURBO_1106
```
ㅤ
ㅤ 
ㅤ
### Using a proxy:
```java
Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("ip", port));
ChatGpt gpt = new ChatGpt("api-key", proxy);
```
ㅤ
ㅤ
ㅤ
### Using a ChatGPT stream (The answer comes in chunks):
```java
ChatGptStream gptStream = new ChatGptStream("api-key"); // You can specify a proxy


gptStream.ask("Hello", new GptStream() {
    @Override
    public void start() {
        System.out.println("Starting");
    }

    @Override
    public void next(int i, String s) { // 'i' is not a chunk index, it is a message index (If you generate several of them at a time)
        System.out.println("Next letters: " + s);
    }

    @Override
    public void end(int i, String s) {
        System.out.println("Result: " + s);
    }

    @Override
    public void error(RuntimeException e) {
        e.printStackTrace();
     }
});
```
ㅤ
ㅤ
ㅤ
### Request with different parameters (Not everything is shown here):
```java
ChatGpt gpt = new ChatGpt("api-key");

ChatGptRequestDTO request = ChatGptRequestDTO.builder()
        .maxTokens(1024) // Max tokens
        .model(ChatGptModel.GPT_3_5_TURBO.getName()) // Model
        .user("User") // User name (Optional)
        .n(3) // Generation count (Optional)
        .messages(
                List.of(
                        ChatGptMessage.builder()
                                .role(ChatGptRole.USER.getName())
                                .content("Hello, my name is Nick")
                                .build(),
                        ChatGptMessage.builder()
                                .role(ChatGptRole.USER.getName())
                                .content("What is my name?")
                                .build()
        )) // Messages
        .build();

List<String> result = gpt.ask(request);
```
ㅤ
ㅤ
ㅤ
### Possible exception:
```java
BadRequestException.class // Bad request
InvalidAuthenticationException.class // Failed to authenticate (Perhaps the key is incorrect)
ToManyRequestException.class // Most likely you have exceeded the message limit for a certain time
ServerException.class // Server exception
ServiceUnavailableException.class // Most likely the server is overloaded, try again
CustomHttpException.class  // Ohter exceptions

They all extend from:
        HttpException.class
```
ㅤ
ㅤ
ㅤ
### If you have several keys, you can use:
ㅤ
Single key container:
```java
ApiKeysContainer apiKeysContainer = ApiKeysContainer.of("api-key");
        
ChatGpt gpt = new ChatGpt(apiKeysContainer);
```
ㅤ
Random select container:
```java
RandomSelectApiKeysContainer apiKeysContainer = new RandomSelectApiKeysContainer(List.of("api-key-1", "api-key-2"));

ChatGpt gpt = new ChatGpt(apiKeysContainer);
```
ㅤ
Rounded select container:
```java
RoundSelectApiKeyContainer apiKeysContainer = new RoundSelectApiKeyContainer(List.of("api-key-1", "api-key-2"));

ChatGpt gpt = new ChatGpt(apiKeysContainer);
```