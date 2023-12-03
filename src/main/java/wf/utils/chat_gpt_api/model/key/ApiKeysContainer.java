package wf.utils.chat_gpt_api.model.key;

import java.util.List;

public interface ApiKeysContainer {

    public String getNextKey();

    public List<String> getAllKeys();


    public static ApiKeysContainer of(String key) {
        return new ApiKeysContainer() {
            public String getNextKey() { return key; }
            public List<String> getAllKeys() { return List.of(key); }
        };
    }

}
