package white.ices.konoa77kg;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHander {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Konoa77kg.MOD_ID);


    public static void init() {

        INSTANCE.registerMessage(MessageInstruments.class, MessageInstruments.class, 0, Side.CLIENT);
        //INSTANCE.registerMessage(PacketDamageEntity.Handler.class, PacketDamageEntity.class, 2, Side.SERVER);
        INSTANCE.registerMessage(MessagePlayerJoinInAnoucementHandler.class, MessagePlayerJoinInAnnouncement.class, 1, Side.SERVER);
    }
}