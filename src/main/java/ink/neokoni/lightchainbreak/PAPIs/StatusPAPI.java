package ink.neokoni.lightchainbreak.PAPIs;

import ink.neokoni.lightchainbreak.LightChainBreak;
import ink.neokoni.lightchainbreak.Configs.Datas.PlayerDataInfo;
import ink.neokoni.lightchainbreak.Configs.PlayerData;
import ink.neokoni.lightchainbreak.Utils.TextUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class StatusPAPI extends PlaceholderExpansion {
    private final Set<String> enableStatus  = Set.of("status_enable", "status_displayCount", "status_itemProtective", "status_sneakEnable");
    @Override
    public @NotNull String getIdentifier() {
        return "lightchainbreak";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.valueOf(LightChainBreak.getInstance().getPluginMeta().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return LightChainBreak.version;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (!enableStatus.contains(params)) return null;

        PlayerDataInfo playerData = PlayerData.getPlayerData(player, false);

        switch (params) {
            case "status_enable":
                if (playerData.isEnabled()) {
                    return ChatColor.GREEN+ TextUtils.getLangString("PAPIs.enabled");
                } else {
                    return ChatColor.RED+ TextUtils.getLangString("PAPIs.disabled");
                }
            case "status_displayCount":
                if (playerData.isDisplayCount()) {
                    return ChatColor.GREEN+ TextUtils.getLangString("PAPIs.enabled");
                } else {
                    return ChatColor.RED+ TextUtils.getLangString("PAPIs.disabled");
                }
            case "status_itemProtective":
                if (playerData.isItemProtective()) {
                    return ChatColor.GREEN+ TextUtils.getLangString("PAPIs.enabled");
                } else {
                    return ChatColor.RED+ TextUtils.getLangString("PAPIs.disabled");
                }
            case "status_sneakEnable":
                if (playerData.isSneakToEnable()) {
                    return ChatColor.GREEN+ TextUtils.getLangString("PAPIs.enabled");
                } else {
                    return ChatColor.RED+ TextUtils.getLangString("PAPIs.disabled");
                }
        }
        return null; // not found
    }
}
