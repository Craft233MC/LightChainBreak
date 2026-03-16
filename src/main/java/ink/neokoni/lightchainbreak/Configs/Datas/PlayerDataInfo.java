package ink.neokoni.lightchainbreak.Configs.Datas;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class PlayerDataInfo {
    @Getter @Setter private UUID uuid;
    @Getter @Setter private boolean enabled;
    @Getter @Setter private boolean displayCount;
    @Getter @Setter private boolean sneakToEnable;
    @Getter @Setter private boolean itemProtective;
    public PlayerDataInfo (UUID uuid, boolean enabled, boolean displayCount, boolean sneakToEnable, boolean itemProtective) {
        this.uuid = uuid;
        this.enabled = enabled;
        this.displayCount = displayCount;
        this.sneakToEnable = sneakToEnable;
        this.itemProtective = itemProtective;
    }
}