package ink.neokoni.lightchainbreak.utils;

import ink.neokoni.lightchainbreak.LightChainBreak;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class text {

    private static String replace(String str){
        return str.replace("%version%", LightChainBreak.version);
    }

    public static Component colored(String str){
        return MiniMessage.miniMessage().deserialize(replace(str));
    }


    public static Component getLang(String str){
        return colored(file.getConfig("lang").getString(str));
    }
}
