package ink.neokoni.lightchainbreak.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class text {
    private static String replace(String str, String key, String value) {
        return str.replace(key, value);
    }

    public static Component colored(String str){
        return MiniMessage.miniMessage().deserialize(str);
    }

    public static Component colored(String str, String key, String value) {
        return MiniMessage.miniMessage().deserialize(replace(str, key, value));
    }

    public static Component getLang(String str){
        return colored(file.getConfig("lang").getString(str));
    }

    public static Component getLang(String str, String key, String value) {
        return colored(file.getConfig("lang").getString(str), key, value);
    }

    public static String getLangString(String str){
        return file.getConfig("lang").getString(str);
    }

    public static String getLangString(String str, String key, String value) {
        return replace(file.getConfig("lang").getString(str), key, value);
    }
}
