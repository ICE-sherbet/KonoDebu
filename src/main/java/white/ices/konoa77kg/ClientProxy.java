package white.ices.konoa77kg;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
    @Override
    public EntityPlayer getEntityPlayerInstance() {
        return Minecraft.getMinecraft().player;
    }
}