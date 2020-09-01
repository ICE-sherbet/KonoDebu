package white.ices.konoa77kg;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class Logger
{	private static Minecraft minecraft;

    public Logger()
    {
        minecraft = Minecraft.getMinecraft();
    }

    public static Minecraft getMinecraft()
    {
        return minecraft;
    }

    public static void addChatMessage(String text)
    {
        minecraft.ingameGUI.addChatMessage(ChatType.SYSTEM, new TextComponentString(text)) ;

    }
}