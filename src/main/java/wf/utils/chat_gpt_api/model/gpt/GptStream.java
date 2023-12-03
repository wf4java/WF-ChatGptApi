package wf.utils.chat_gpt_api.model.gpt;

public interface GptStream {

    public void next(int i, String s);

    public default void end(int i, String s) { };

    public default void start() { };

    public default void error(RuntimeException e) { };
}
