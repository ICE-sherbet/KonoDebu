package white.ices.konoa77kg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayerJoinInAnoucementHandler implements IMessageHandler<MessagePlayerJoinInAnnouncement, MessageInstruments> {
    @Override
    public MessageInstruments onMessage(MessagePlayerJoinInAnnouncement message, MessageContext ctx) {
        //
        String uuidString = message.getUuid();
        EntityPlayer player = ctx.getServerHandler().player;
        //
        if (player.getGameProfile().getId().toString().equals(uuidString)) {
            //
            return new MessageInstruments(
                    (float)player.posX,
                    (float)player.posY,
                    (float)player.posZ,
                    player.getUniqueID().toString(),
                    player.getEntityId(),
                    player.getCapability(KonoFatProvider.Fat_CAP, null).getThickness());
        }
        //
        return null;
    }
}