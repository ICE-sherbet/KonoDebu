package white.ices.konoa77kg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class GuiWeightMeter extends Gui {
    String text;

    String text2;

    public GuiWeightMeter(Minecraft mc,int tic) {

        this.text = "Weight Level: " + String.valueOf(tic) + " / 40";
        this.text2 = "Saturation Level: " + Konoa77kg.fatClient + "  Jumping jacks: " + Konoa77kg.jumpCount;

        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
        drawCenteredString(mc.fontRenderer, this.text, width / 2, height * 3 / 4 - 10, 11488017);
        drawCenteredString(mc.fontRenderer, this.text2, width / 2, height * 3 / 4 - 25, 11488017);
    }
}