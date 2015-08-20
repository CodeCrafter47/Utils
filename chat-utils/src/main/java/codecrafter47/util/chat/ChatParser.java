package codecrafter47.util.chat;

import net.md_5.bungee.api.chat.BaseComponent;

/**
 * Used to convert text to BaseComponent[] while parsing embedded formatting codes
 */
public interface ChatParser {

    /**
     * Converts text to BaseComponent. How this is done depends on the underlying implementation.
     * @param text Input text to be converted
     * @return A BaseComponent array matching the input String
     */
    BaseComponent[] parse(String text);
}
