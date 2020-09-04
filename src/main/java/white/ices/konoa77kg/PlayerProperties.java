package white.ices.konoa77kg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class PlayerProperties {

    @CapabilityInject(PlayerFat.class)
    public static Capability<PlayerFat> PLAYER_FAT;

    public static PlayerFat getPlayerMana(EntityPlayer player) {
        return player.getCapability(PLAYER_FAT, null);
    }

}
