package ink.neokoni.lightchainbreak.utils;

import ink.neokoni.lightchainbreak.LightChainBreak;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class text {

    private static String replace(String str){
        return str.replace("%version%", LightChainBreak.version)
              .replace("%count%", String.valueOf(blocks.getCountBreakBlocks())) ;
    }

    private static String replace(String str, String pl){
        return str.replace("%version%", LightChainBreak.version)
                .replace("%count%", String.valueOf(blocks.getCountBreakBlocks()))
                .replace("%plugin%", pl);
    }

    public static Component colored(String str){
        return MiniMessage.miniMessage().deserialize(replace(str));
    }

    public static Component colored(String str, String pl){
        return MiniMessage.miniMessage().deserialize(replace(str, pl));
    }


    public static Component getLang(String str){
        return colored(file.getConfig("lang").getString(str));
    }

    public static Component getLang(String str, String pl){
        return colored(file.getConfig("lang").getString(str), pl);
    }

    public static String getLangString(String str){
        return replace(file.getConfig("lang").getString(str));
    }

    public static String getLangString(String str, String pl){
        return replace(file.getConfig("lang").getString(str), pl);

    }
}
