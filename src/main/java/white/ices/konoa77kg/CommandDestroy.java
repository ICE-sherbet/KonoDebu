package white.ices.konoa77kg;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandDestroy extends CommandBase {
    @Override
    public String getName() {
        return "konoa77";
    }
    @Override
    public String getUsage(ICommandSender sender) {
        return "konoa77kgmod";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length==0)return;
        EntityPlayer EPtarget = (!isNumeric(args[0])) ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
        if (EPtarget == null)return;
        if (!isNumeric(args[args.length-1]))return;
        IKonoFat fat = EPtarget.getCapability(KonoFatProvider.Fat_CAP, null);
        fat.setThickness(Integer.parseInt(args[args.length-1]));

        PacketHander.INSTANCE.sendToAll(
                new MessageInstruments(
                        (float)EPtarget.posX,
                        (float)EPtarget.posY,
                        (float)EPtarget.posZ,
                        EPtarget.getUniqueID().toString(),
                        EPtarget.getEntityId(),
                        Integer.parseInt(args[args.length-1]) ));
    }
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
