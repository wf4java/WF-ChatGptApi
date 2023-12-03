package wf.utils.chat_gpt_api.model.gpt;

import lombok.Getter;


@Getter
public enum ChatGptModel {

    GPT_4("gpt-4"),
    GPT_4_32K("gpt-4-32k"),
    GPT_4_1106_PREVIEW("gpt-4-1106-preview"),
    GPT_3_5_TURBO_1106("gpt-3.5-turbo-1106"),
    GPT_3_5_TURBO_0613("gpt-3.5-turbo-0613"),
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    TEXT_DAVINCI_003("text-davinci-003"),
    TEXT_DAVINCI_002("text-davinci-002"),
    TEXT_DAVINCI_001("text-davinci-001");

    private final String name;

    ChatGptModel(String name) {
        this.name = name;
    }
}
