package wf.utils.chat_gpt_api.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatGptResponseDTO {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "object")
    private String object;

    @JsonProperty(value = "created")
    private Long created;

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "choices")
    private List<Choice> choices;

    @JsonProperty(value = "usage")
    private Usage usage;



    @Data
    public static class Choice {
        @JsonProperty(value = "index")
        private Integer index;
        @JsonProperty(value = "message")
        private ChatGptMessage message;
        @JsonProperty(value = "finish_reason")
        private String finishReason;
    }


    @Data
    public static class Usage {
        @JsonProperty(value = "prompt_tokens")
        private Integer promptTokens;
        @JsonProperty(value = "completion_tokens")
        private Integer completionTokens;
        @JsonProperty(value = "total_tokens")
        private Integer totalTokens;
    }
}
