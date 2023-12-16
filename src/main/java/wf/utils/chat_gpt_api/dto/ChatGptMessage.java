package wf.utils.chat_gpt_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatGptMessage {

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "content")
    private String content;

}
