package wf.utils.chat_gpt_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatGptMessage {

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "content")
    private String content;

}
