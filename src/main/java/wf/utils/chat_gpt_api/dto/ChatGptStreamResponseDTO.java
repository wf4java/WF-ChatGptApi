package wf.utils.chat_gpt_api.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatGptStreamResponseDTO {


    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "object")
    private String object;

    @JsonProperty(value = "created")
    private Long created;

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "system_fingerprint")
    private String systemFingerprint;

    @JsonProperty(value = "choices")
    private List<Choice> choices;



    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        @JsonProperty(value = "index")
        private Integer index;

        @JsonProperty(value = "delta")
        private Delta delta;

        @JsonProperty(value = "finish_reason")
        private String finishReason;


    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Delta {
        @JsonProperty(value = "role")
        private String role;

        @JsonProperty(value = "content")
        private String content;
    }


    public static String choicesToContent(List<Choice> choices) {
        return choices.stream()
                .filter(choice -> choice.getDelta() != null && choice.getDelta().getContent() != null)
                .map(choice -> choice.getDelta().getContent())
                .collect(Collectors.joining());
    }


}
