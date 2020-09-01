package white.ices.konoa77kg;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageSample implements IMessage {

    public byte data;

    public MessageSample(){}

    public MessageSample(byte par1) {
        this.data= par1;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data= buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.data);
    }
}