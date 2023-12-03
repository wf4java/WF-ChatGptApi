import wf.utils.chat_gpt_api.ChatGpt;



public class Main {


    public static void main(String[] args) {
        ChatGpt gpt = new ChatGpt("api-key");

        System.out.println(gpt.ask("Hello, who are you?"));
    }


}
