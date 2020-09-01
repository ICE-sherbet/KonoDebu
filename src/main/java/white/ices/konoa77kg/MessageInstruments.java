package white.ices.konoa77kg;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageInstruments
        implements IMessage, IMessageHandler< MessageInstruments, IMessage >
{
    private float x;
    private float y;
    private float z;
    private String entityUUID;
    private int entityid;
    private int fatlevel;

    public MessageInstruments() {}

    public MessageInstruments(
            float x,
            float y,
            float z,
            String entityUUID,
            int entityid,
            int fatlevel )
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityUUID = entityUUID;
        this.entityid = entityid;
        this.fatlevel = fatlevel;
    }

    @Override
    public void fromBytes( ByteBuf buf )
    {
        this.x = buf.readFloat();
        this.y = buf.readFloat();
        this.z = buf.readFloat();
        this.entityUUID = ByteBufUtils.readUTF8String(buf);
        this.entityid = buf.readInt();
        this.fatlevel = buf.readInt();
    }

    @Override
    public void toBytes( ByteBuf buf )
    {
        buf.writeFloat( this.x );
        buf.writeFloat( this.y );
        buf.writeFloat( this.z );
        ByteBufUtils.writeUTF8String(buf, this.entityUUID);
        buf.writeInt( this.entityid );
        buf.writeInt( this.fatlevel );
    }

    @Override
    public IMessage onMessage(MessageInstruments message, MessageContext ctx )
    {
        switch ( FMLCommonHandler.instance().getEffectiveSide() ) {
            case CLIENT:
                Konoa77kg.instace.fatCO(message.fatlevel);
                Konoa77kg.instace.fatUUID(message.entityUUID);


            case SERVER:
                break;
        }

        return null;
    }
}