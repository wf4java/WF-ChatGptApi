import wf.utils.chat_gpt_api.ChatGpt;
import wf.utils.chat_gpt_api.ChatGptStream;
import wf.utils.chat_gpt_api.dto.ChatGptMessage;
import wf.utils.chat_gpt_api.dto.ChatGptRequestDTO;
import wf.utils.chat_gpt_api.model.gpt.ChatGptModel;
import wf.utils.chat_gpt_api.model.gpt.ChatGptRole;
import wf.utils.chat_gpt_api.model.gpt.GptStream;
import wf.utils.chat_gpt_api.model.key.ApiKeysContainer;
import wf.utils.chat_gpt_api.model.key.RandomSelectApiKeysContainer;
import wf.utils.chat_gpt_api.model.key.RoundSelectApiKeyContainer;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;


public class Main {



    public static void main(String[] args) {




        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("64.189.106.6", 3129));

        ChatGpt gpt = new ChatGpt("api", proxy);

        System.out.println(gpt.ask("Скажи привет на 100 языках!",2000));
//        System.out.println(gpt.ask("Скажи привет на 10 языках!",20));
//        System.out.println(gpt.ask("Скажи привет на 10 языках!",20));
//
//
//
//        ChatGptStream gptStream = new ChatGptStream("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", proxy);
//
//
//        gptStream.ask("Hello",
//                new GptStream() {
//                    @Override
//                    public void next(int i, String s) {
//                        System.out.println("Next: " + s);
//                    }
//
//                    @Override
//                    public void end(int i, String s) {
//                        System.out.println();
//                        System.out.println("I: " + i);
//                        System.out.println("End: " + s);
//                    }
//
//                    @Override
//                    public void error(RuntimeException e) {
//                        e.printStackTrace();
//                    }
//                });

    }





}
