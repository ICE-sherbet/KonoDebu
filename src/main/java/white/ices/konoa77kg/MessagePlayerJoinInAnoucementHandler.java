package white.ices.konoa77kg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayerJoinInAnoucementHandler implements IMessageHandler<MessagePlayerJoinInAnnouncement, MessageInstruments> {
    @Override
    public MessageInstruments onMessage(MessagePlayerJoinInAnnouncement message, MessageContext ctx) {
        //
        String uuidString = message.getUuid();
        EntityPlayer player = ctx.getServerHandler().player;
        //
        NBTTagCompound nbt = new NBTTagCompound();
        if (nbt.getInteger("Fat"+player.getUniqueID().toString())>20){//player.getUniqueID().toString().equals(uuidString)) {
            //

            nbt.getInteger("Fat"+player.getUniqueID().toString());
            return new MessageInstruments(
                    (float)player.posX,
                    (float)player.posY,
                    (float)player.posZ,
                    player.getUniqueID().toString(),
                    player.getEntityId(),
                    player.getCapability(KonoFatProvider.Fat_CAP, null).getThickness());
        }

        return null;
    }
}