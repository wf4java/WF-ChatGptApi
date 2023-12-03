package wf.utils.chat_gpt_api.model.key;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class RoundSelectApiKeyContainer implements ApiKeysContainer {

    private List<String> apiKeys = new ArrayList<>();
    private int currentKeyIndex = 0;

    public RoundSelectApiKeyContainer(List<String> apiKeys) {
        this.apiKeys = apiKeys;
    }

    @Override
    public String getNextKey() {
        if (apiKeys.isEmpty())
            throw new IllegalStateException("No API keys available");

        return apiKeys.get(currentKeyIndex++ % apiKeys.size());
    }

    @Override
    public List<String> getAllKeys() {
        return apiKeys;
    }

    public void addApiKey(String apiKey) {
        apiKeys.add(apiKey);
    }

    public boolean removeApiKey(String apiKey) {
        return apiKeys.remove(apiKey);
    }

}
