package cat_std.broseidon.mtleco.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ColorCode {
    private static final Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");

    public static String translate(String input) {
        Matcher match = pattern.matcher(input);
        while (match.find()) {
            String color = input.substring(match.start() + 1, match.end());
            input = input.replace("&" + color, ChatColor.of(color) + "");
            match = pattern.matcher(input);
        }
        return input.replace("&", "\u00a7");
    }

    public static List<String> translate(List<String> input) {
        return (List<String>) input.stream().map(ColorCode::translate).collect(Collectors.toList());
    }
}
