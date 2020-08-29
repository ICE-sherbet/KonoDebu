package white.ices.konoa77kg;

import com.google.common.base.Predicate;
import java.util.Iterator;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFatPlayer extends RenderPlayer {
    private static boolean isSmallArms;

    public static int thickness = 0;

    private RenderManager man;

    public RenderFatPlayer(RenderManager renderManager) {
        super(renderManager);
        this.man = renderManager;
    }

    public void render(RenderManager renderManager) {
        new RenderFatPlayer(renderManager);
    }

    public ModelFat1 getMainModel() {
        for (EntityPlayer player : this.man.world.getPlayers(EntityPlayer.class, new Predicate<EntityPlayer>() {
            public boolean apply(EntityPlayer input) {
                return true;
            }
        })) {
            if (DefaultPlayerSkin.getSkinType(player.getUniqueID()).equals("slim")) {
                isSmallArms = true;
                continue;
            }
            isSmallArms = false;
        }
        return getModelNoPlayer(getT(), isSmallArms);
    }

    public static ModelFat1 getModel(World world, int thickness2, boolean isSmallArms2) {
        Iterator<EntityPlayer> iterator = world.getPlayers(EntityPlayer.class, new Predicate<EntityPlayer>() {
            public boolean apply(EntityPlayer input) {
                return true;
            }
        }).iterator();
        if (iterator.hasNext()) {
            EntityPlayer player = iterator.next();
            if (player.hasCapability(KonoFatProvider.Fat_CAP, null))
                return new ModelFat1(0.0F, isSmallArms2, thickness2);
            return new ModelFat1(0.0F, isSmallArms2, 0);
        }
        return new ModelFat1(0.0F, isSmallArms2, thickness2);
    }

    public static ModelFat1 getModelNoPlayer(int thickness2, boolean isSmallArms2) {
        return new ModelFat1(0.0F, isSmallArms2, thickness2);
    }

    public ResourceLocation getEntityTexture(EntityPlayer entity) {
        return (entity instanceof AbstractClientPlayer) ? ((AbstractClientPlayer)entity).getLocationSkin() : new ResourceLocation((isSmallArms != true) ? "textures/entity/steve.png" : "textures/entity/alex.png");
    }

    public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        getMainModel().render((Entity)entity, 1.0F, 1.0F, 1.0F, entity.cameraYaw, entity.cameraPitch, partialTicks);
        GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
    }

    public static int getT() {
        return thickness;
    }

    public void setT(int t) {
        thickness = t;
    }

    public void doRender(EntityPlayer entityPlayer) {
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        getMainModel().render((Entity)entityPlayer, 1.0F, 1.0F, 1.0F, entityPlayer.cameraYaw, entityPlayer.cameraPitch, 0.1F);
        GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
    }
}
