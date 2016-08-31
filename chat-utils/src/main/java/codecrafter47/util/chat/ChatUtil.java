package codecrafter47.util.chat;

import com.google.common.base.Strings;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class providing a chat formatting syntax similar to bbcode.
 *
 * Details:
 * For example [b]this is bold[/b], [i]this is italic[/i], [u]this is underlined[/u] and [s]this is crossed out[/s].
 * The difference between the above and making something &amp;lbold&amp;r the vanilla way is, that the above makes all the
 * enclosed text bold, while &amp;b makes bold everything until reaching the next color code.
 * Same for [color=...]
 *
 * How links will work is easy to guess, e.g. it's just [url]spigotmc.org[/url] or [url=spigotmc.org]click here[/url].
 * Executing commands works similar [command=/tp CodeCrafter47]click here[/command].
 *
 * Suggesting commands works with [suggest=/tp ]...[/suggest]
 * To create tooltips do [hover=Text magically appears when moving the mouse over]this[/hover].
 *
 * It is possible to use [nocolor][/nocolor] to prevent the use of legacy color codes in a block;
 * [nobbcode][/nobbcode] will prevent the use of bbcode in a block;
 *
 * Vanilla color codes still work and can be mixed with the [color=..] and other formatting tags without problems.
 */
public class ChatUtil {

    private static final String NON_UNICODE_CHARS = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000";

    private static final int[] NON_UNICODE_CHAR_WIDTHS = new int[]{6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 4, 4, 6, 7, 6, 6, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1, 1, 4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6, 8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9, 8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7, 7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1};

    private static final byte[] UNICODE_CHAR_WIDTHS = new byte[65536];

    static {
        InputStream resourceAsStream = ChatUtil.class.getResourceAsStream("unicode.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        try {
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                UNICODE_CHAR_WIDTHS[i++] = Byte.valueOf(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final int DEFAULT_CHAT_LINE_WIDTH = 320;

    private static final Pattern pattern = Pattern.compile("(?is)(?=\\n)|(?:[&\u00A7](?<color>[0-9A-FK-OR]))|" +
            "(?:\\[(?<tag>/?(?:b|i|u|s|nocolor|nobbcode)|(?:url|command|hover|suggest|color)=(?<value>(?:(?:[^]\\[]*)\\[(?:[^]\\[]*)\\])*(?:[^]\\[]*))|/(?:url|command|hover|suggest|color))\\])|" +
            "(?:\\[(?<implicitTag>url|command|suggest)\\](?=(?<implicitValue>.*?)\\[/\\k<implicitTag>\\]))");

    private static final Pattern strip_bbcode_pattern = Pattern.compile("(?is)(?:\\[(?<tag>/?(?:b|i|u|s|nocolor|nobbcode)|(?:url|command|hover|suggest|color)=(?<value>(?:(?:[^]\\[]*)\\[(?:[^]\\[]*)\\])*(?:[^]\\[]*))|/(?:url|command|hover|suggest|color))\\])|" +
            "(?:\\[(?<implicitTag>url|command|suggest)\\](?=(?<implicitValue>.*?)\\[/\\k<implicitTag>\\]))");

    private static final Logger logger = Logger.getLogger("Minecraft");

    public static BaseComponent[] parseBBCode(String text) {
        Matcher matcher = pattern.matcher(text);
        TextComponent current = new TextComponent();
        List<BaseComponent> components = new LinkedList<>();
        int forceBold = 0;
        int forceItalic = 0;
        int forceUnderlined = 0;
        int forceStrikethrough = 0;
        int nocolorLevel = 0;
        int nobbcodeLevel = 0;
        Deque<ChatColor> colorDeque = new LinkedList<>();
        Deque<ClickEvent> clickEventDeque = new LinkedList<>();
        Deque<HoverEvent> hoverEventDeque = new LinkedList<>();
        while (matcher.find()) {
            boolean parsed = false;
            {
                StringBuffer stringBuffer = new StringBuffer();
                matcher.appendReplacement(stringBuffer, "");
                TextComponent component = new TextComponent(current);
                current.setText(stringBuffer.toString());
                components.add(current);
                current = component;
            }
            String group_color = matcher.group("color");
            String group_tag = matcher.group("tag");
            String group_value = matcher.group("value");
            String group_implicitTag = matcher.group("implicitTag");
            String group_implicitValue = matcher.group("implicitValue");
            if (group_color != null && nocolorLevel <= 0) {
                ChatColor color = ChatColor.getByChar(group_color.charAt(0));
                if (color != null) {
                    switch (color) {
                        case MAGIC:
                            current.setObfuscated(true);
                            break;
                        case BOLD:
                            current.setBold(true);
                            break;
                        case STRIKETHROUGH:
                            current.setStrikethrough(true);
                            break;
                        case UNDERLINE:
                            current.setUnderlined(true);
                            break;
                        case ITALIC:
                            current.setItalic(true);
                            break;
                        case RESET:
                            color = ChatColor.WHITE;
                        default:
                            current = new TextComponent();
                            current.setColor(color);
                            current.setBold(forceBold > 0);
                            current.setItalic(forceItalic > 0);
                            current.setUnderlined(forceUnderlined > 0);
                            current.setStrikethrough(forceStrikethrough > 0);
                            if (!colorDeque.isEmpty()) {
                                current.setColor(colorDeque.peek());
                            }
                            if (!clickEventDeque.isEmpty()) {
                                current.setClickEvent(clickEventDeque.peek());
                            }
                            if (!hoverEventDeque.isEmpty()) {
                                current.setHoverEvent(hoverEventDeque.peek());
                            }
                    }
                    parsed = true;
                }
            }
            if (group_tag != null && nobbcodeLevel <= 0) {
                // [b]this is bold[/b]
                if (group_tag.matches("(?is)^b$")) {
                    forceBold++;
                    if (forceBold > 0) {
                        current.setBold(true);
                    } else {
                        current.setBold(false);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/b$")) {
                    forceBold--;
                    if (forceBold <= 0) {
                        current.setBold(false);
                    } else {
                        current.setBold(true);
                    }
                    parsed = true;
                }
                // [i]this is italic[/i]
                if (group_tag.matches("(?is)^i$")) {
                    forceItalic++;
                    if (forceItalic > 0) {
                        current.setItalic(true);
                    } else {
                        current.setItalic(false);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/i$")) {
                    forceItalic--;
                    if (forceItalic <= 0) {
                        current.setItalic(false);
                    } else {
                        current.setItalic(true);
                    }
                    parsed = true;
                }
                // [u]this is underlined[/u]
                if (group_tag.matches("(?is)^u$")) {
                    forceUnderlined++;
                    if (forceUnderlined > 0) {
                        current.setUnderlined(true);
                    } else {
                        current.setUnderlined(false);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/u$")) {
                    forceUnderlined--;
                    if (forceUnderlined <= 0) {
                        current.setUnderlined(false);
                    } else {
                        current.setUnderlined(true);
                    }
                    parsed = true;
                }
                // [s]this is crossed out[/s]
                if (group_tag.matches("(?is)^s$")) {
                    forceStrikethrough++;
                    if (forceStrikethrough > 0) {
                        current.setStrikethrough(true);
                    } else {
                        current.setStrikethrough(false);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/s$")) {
                    forceStrikethrough--;
                    if (forceStrikethrough <= 0) {
                        current.setStrikethrough(false);
                    } else {
                        current.setStrikethrough(true);
                    }
                    parsed = true;
                }
                // [color=red]huh this is red...[/color]
                if (group_tag.matches("(?is)^color=.*$")) {
                    ChatColor color = null;
                    for (ChatColor color1 : ChatColor.values()) {
                        if (color1.getName().equalsIgnoreCase(group_value)) {
                            color = color1;
                        }
                    }
                    colorDeque.push(current.getColor());
                    if (color != null && color != ChatColor.BOLD && color != ChatColor.ITALIC && color != ChatColor.MAGIC
                            && color != ChatColor.RESET && color != ChatColor.STRIKETHROUGH && color != ChatColor.UNDERLINE) {
                        colorDeque.push(color);
                        current.setColor(color);
                    } else {
                        logger.warning("Invalid color tag: [" + group_tag + "] UNKNOWN COLOR '" + group_value + "'");
                        colorDeque.push(ChatColor.WHITE);
                        current.setColor(ChatColor.WHITE);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/color$")) {
                    if (!colorDeque.isEmpty()) {
                        colorDeque.pop();
                        current.setColor(colorDeque.pop());
                    }
                    parsed = true;
                }
                // [url=....]
                if (group_tag.matches("(?is)^url=.*$")) {
                    String url = group_value;
                    url = url.replaceAll("(?is)\\[/?nobbcode\\]", "");
                    if (!url.startsWith("http")) {
                        url = "http://" + url;
                    }
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [/url], [/command], [/suggest]
                if (group_tag.matches("(?is)^/(?:url|command|suggest)$")) {
                    if (!clickEventDeque.isEmpty()) clickEventDeque.pop();
                    if (clickEventDeque.isEmpty()) {
                        current.setClickEvent(null);
                    } else {
                        current.setClickEvent(clickEventDeque.peek());
                    }
                    parsed = true;
                }
                // [command=....]
                if (group_tag.matches("(?is)^command=.*")) {
                    group_value = group_value.replaceAll("(?is)\\[/?nobbcode\\]", "");
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, group_value);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [suggest=....]
                if (group_tag.matches("(?is)^suggest=.*")) {
                    group_value = group_value.replaceAll("(?is)\\[/?nobbcode\\]", "");
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, group_value);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [hover=....]...[/hover]
                if (group_tag.matches("(?is)^hover=.*$")) {
                    BaseComponent[] components1 = parseBBCode(group_value);
                    if (!hoverEventDeque.isEmpty()) {
                        // why is there no apache commons lib in bungee
                        BaseComponent[] components2 = hoverEventDeque.getLast().getValue();
                        BaseComponent[] components3 = new BaseComponent[components1.length + components2.length + 1];
                        int i = 0;
                        for (BaseComponent baseComponent : components2) components3[i++] = baseComponent;
                        components3[i++] = new TextComponent("\n");
                        for (BaseComponent baseComponent : components1) components3[i++] = baseComponent;
                        components1 = components3;
                    }
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, components1);
                    hoverEventDeque.push(hoverEvent);
                    current.setHoverEvent(hoverEvent);
                    parsed = true;
                } else if (group_tag.matches("(?is)^/hover$")) {
                    if (!hoverEventDeque.isEmpty()) hoverEventDeque.pop();
                    if (hoverEventDeque.isEmpty()) {
                        current.setHoverEvent(null);
                    } else {
                        current.setHoverEvent(hoverEventDeque.peek());
                    }
                    parsed = true;
                }
            }
            if (group_implicitTag != null && nobbcodeLevel <= 0) {
                // [url]spigotmc.org[/url]
                if (group_implicitTag.matches("(?is)^url$")) {
                    String url = group_implicitValue;
                    if (!url.startsWith("http")) {
                        url = "http://" + url;
                    }
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [command]/spawn[/command]
                if (group_implicitTag.matches("(?is)^command$")) {
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, group_implicitValue);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [suggest]/friend add [/suggest]
                if (group_implicitTag.matches("(?is)^suggest$")) {
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, group_implicitValue);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
            }
            if (group_tag != null) {
                if (group_tag.matches("(?is)^nocolor$")) {
                    nocolorLevel++;
                    parsed = true;
                }
                if (group_tag.matches("(?is)^/nocolor$")) {
                    nocolorLevel--;
                    parsed = true;
                }
                if (group_tag.matches("(?is)^nobbcode$")) {
                    nobbcodeLevel++;
                    parsed = true;
                }
                if (group_tag.matches("(?is)^/nobbcode$")) {
                    nobbcodeLevel--;
                    parsed = true;
                }
            }
            if (!parsed) {
                TextComponent component = new TextComponent(current);
                current.setText(matcher.group(0));
                components.add(current);
                current = component;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        matcher.appendTail(stringBuffer);
        current.setText(stringBuffer.toString());
        components.add(current);
        return components.toArray(new BaseComponent[components.size()]);
    }

    public static String stripBBCode(String string){
        return strip_bbcode_pattern.matcher(string).replaceAll("");
    }

    public static double getCharWidth(int codePoint, boolean isBold) {
        int nonUnicodeIdx = NON_UNICODE_CHARS.indexOf(codePoint);
        double width;
        if (nonUnicodeIdx != -1) {
            width = NON_UNICODE_CHAR_WIDTHS[nonUnicodeIdx];
            if (isBold) {
                width += 1;
            }
        } else {
            // MC unicode -- what does this even do? but it's client-only so we can't use it directly :/
            int j = UNICODE_CHAR_WIDTHS[codePoint] >>> 4;
            int k = UNICODE_CHAR_WIDTHS[codePoint] & 15;

            if (k > 7) {
                k = 15;
                j = 0;
            }
            width = ((k + 1) - j) / 2 + 1;
            if (isBold) {
                width += 0.5;
            }
        }
        return width;
    }

    public static int getLength(BaseComponent[] text) {
        double length = 0;
        for (BaseComponent child : text) {
            final String txt;
            if (child instanceof TextComponent) {
                txt = ((TextComponent) child).getText();
            } else { // TODO translatable components
                continue;
            }
            boolean isBold = child.isBold();
            for (int i = 0; i < txt.length(); ++i) {
                length += getCharWidth(txt.codePointAt(i), isBold);
            }
        }
        return (int) Math.ceil(length);
    }
}
