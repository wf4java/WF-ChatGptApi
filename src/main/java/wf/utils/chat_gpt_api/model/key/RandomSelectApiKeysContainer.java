package wf.utils.chat_gpt_api.model.key;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RandomSelectApiKeysContainer implements ApiKeysContainer {

    private List<String> apiKeys = new ArrayList<>();
    private Random random;

    public RandomSelectApiKeysContainer(List<String> apiKeys) {
        this.apiKeys = apiKeys;
    }

    @Override
    public String getNextKey() {
        return apiKeys.get(random.nextInt(apiKeys.size()));
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
