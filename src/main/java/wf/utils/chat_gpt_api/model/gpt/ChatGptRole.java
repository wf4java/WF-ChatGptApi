package wf.utils.chat_gpt_api.model.gpt;

import lombok.Getter;

@Getter
public enum ChatGptRole {

    USER("user"),
    ASSISTANT("assistant"),
    FUNCTION("function"),
    SYSTEM("system");


    private final String name;

    ChatGptRole(String name) {
        this.name = name;
    }



}
