package white.ices.konoa77kg;

import com.google.common.base.Predicate;
import java.util.Iterator;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerEntityOnShoulder;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SideOnly(Side.CLIENT)
public class RenderFatPlayer3 extends RenderLivingBase<EntityPlayer> {
    private static final Logger LOGGER = LogManager.getLogger();

    public EntityPlayer p;

    private static boolean isSmallArms;

    public static int thickness = 0;

    private RenderManager man;

    public RenderFatPlayer3(RenderManager renderManager, EntityPlayer p) {
        super(renderManager, (ModelBase)getModelNoPlayer(getT(), isSmallArms), 0.0F);
        this.p = p;
        this.man = renderManager;
        this.addLayer((LayerRenderer)new LayerBipedArmor(this));
        this.addLayer((LayerRenderer)new LayerHeldItem(this));
        this.addLayer((LayerRenderer)new LayerArrow(this));
        this.addLayer((LayerRenderer)new LayerCustomHead((getMainModel()).bipedHead));
        this.addLayer((LayerRenderer)new LayerElytra(this));
        this.addLayer((LayerRenderer)new LayerEntityOnShoulder(renderManager));
    }

    public ModelFat1 getMainModel() {
        for (EntityPlayer player : this.man.world.getPlayers(EntityPlayer.class, input -> true)) {
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

    protected boolean canRenderName(EntityPlayer entity) {
        return super.canRenderName((EntityPlayer) entity);
    }

    public void doRender(EntityPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!entity.isUser() || this.renderManager.renderViewEntity == entity) {
            float yaw = entityYaw;
            double d0 = y;
            if (entity.isSneaking())
                d0 = y - 0.125D;
            setModelVisibilities(entity);
            GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
            doRender2(entity, x, d0, z, entityYaw, partialTicks);
            GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        }
    }

    private void setModelVisibilities(EntityPlayer entity) {
        ModelFat1 modelplayer = getMainModel();
        if (entity.isSpectator()) {
            modelplayer.setVisible(false);
            modelplayer.bipedHead.showModel = true;
            modelplayer.bipedHeadwear.showModel = true;
        } else {
            ItemStack itemstack = entity.getHeldItemMainhand();
            ItemStack itemstack1 = entity.getHeldItemOffhand();
            modelplayer.setVisible(true);
            modelplayer.bipedHeadwear.showModel = entity.isWearing(EnumPlayerModelParts.HAT);
            modelplayer.bipedBodyWear.showModel = entity.isWearing(EnumPlayerModelParts.JACKET);
            modelplayer.bipedLeftLegwear.showModel = entity.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
            modelplayer.bipedRightLegwear.showModel = entity.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
            modelplayer.bipedLeftArmwear.showModel = entity.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
            modelplayer.bipedRightArmwear.showModel = entity.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
            modelplayer.isSneak = entity.isSneaking();
            ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
            ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;
            if (itemstack.isEmpty()) {
                modelbiped$armpose = ModelBiped.ArmPose.ITEM;
                if (entity.getItemInUseCount() > 0) {
                    EnumAction enumaction = itemstack.getItemUseAction();
                    if (enumaction == EnumAction.BLOCK) {
                        modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                    } else if (enumaction == EnumAction.BOW) {
                        modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                    }
                }
            }
            if (itemstack1.isEmpty()) {
                modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;
                if (entity.getItemInUseCount() > 0) {
                    EnumAction enumaction1 = itemstack1.getItemUseAction();
                    if (enumaction1 == EnumAction.BLOCK) {
                        modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
                    } else if (enumaction1 == EnumAction.BOW) {
                        modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
                    }
                }
            }
            if (entity.getPrimaryHand() == EnumHandSide.RIGHT) {
                modelplayer.rightArmPose = modelbiped$armpose;
                modelplayer.leftArmPose = modelbiped$armpose1;
            } else {
                modelplayer.rightArmPose = modelbiped$armpose1;
                modelplayer.leftArmPose = modelbiped$armpose;
            }
        }
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.5F, 0.0F);
    }

    protected void preRenderCallback(EntityPlayer entitylivingbaseIn, float partialTickTime) {
        float f = 0.9375F;
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }

    public void renderRightArm(EntityPlayer scale) {
        float f = 1.0F;
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        float f1 = 0.0625F;
        ModelFat1 modelplayer = getMainModel();
        if (this.p != null)
            setModelVisibilities(this.p);
        GlStateManager.enableBlend();
        modelplayer.swingProgress = 0.0F;
        modelplayer.isSneak = false;
        if (this.p != null)
            modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)this.p);
        modelplayer.rarm.rotateAngleX = 0.0F;
        modelplayer.rarm.render(0.0625F);
        modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedRightArmwear.render(0.0625F);
        GlStateManager.disableBlend();
    }

    public void renderLeftArm(EntityPlayer clientPlayer) {
        float f = 1.0F;
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        float f1 = 0.0625F;
        ModelFat1 modelplayer = getMainModel();
        setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        modelplayer.isSneak = false;
        modelplayer.swingProgress = 0.0F;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
        modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArm.render(0.0625F);
        modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArmwear.render(0.0625F);
        GlStateManager.disableBlend();
    }

    protected void renderLivingAt(EntityPlayer entityLivingBaseIn, double x, double y, double z) {
        if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping()) {
            super.renderLivingAt((EntityPlayer) entityLivingBaseIn, x + entityLivingBaseIn.renderOffsetX, y + entityLivingBaseIn.renderOffsetY, z + entityLivingBaseIn.renderOffsetZ);
        } else {
            super.renderLivingAt((EntityPlayer) entityLivingBaseIn, x, y, z);
        }
    }

    protected void applyRotations(EntityPlayer entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        if (entityLiving.isEntityAlive() && entityLiving.isPlayerSleeping()) {
            GlStateManager.rotate(entityLiving.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(getDeathMaxRotation((EntityPlayer) entityLiving), 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
        } else if (entityLiving.isElytraFlying()) {
            super.applyRotations((EntityPlayer) entityLiving, p_77043_2_, rotationYaw, partialTicks);
            float f = entityLiving.getTicksElytraFlying() + partialTicks;
            float f1 = MathHelper.clamp(f * f / 100.0F, 0.0F, 1.0F);
            GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
            Vec3d vec3d = entityLiving.getLook(partialTicks);
            double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
            double d1 = vec3d.x * vec3d.x + vec3d.y * vec3d.y;

            if (d0 > 0.0D && d1 > 0.0D) {
                double d2 = (entityLiving.motionX * vec3d.x + entityLiving.motionZ * vec3d.y) / Math.sqrt(d0) * Math.sqrt(d1);
                double d3 = entityLiving.motionX * vec3d.y - entityLiving.motionZ * vec3d.x;
                GlStateManager.rotate((float)(Math.signum(d3) * Math.acos(d2)) * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
            }
        } else {
            super.applyRotations((EntityPlayer) entityLiving, p_77043_2_, rotationYaw, partialTicks);
        }
    }

    protected void renderEntityName(EntityPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
        if (distanceSq < 100.0D) {
            Scoreboard scoreboard = entityIn.getWorldScoreboard();
            ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
            if (scoreobjective != null) {
                Score score = scoreboard.getOrCreateScore(entityIn.getName(), scoreobjective);
                renderLivingLabel((EntityPlayer) entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z, 64);
                y += ((getFontRendererFromRenderManager()).FONT_HEIGHT * 1.15F * 0.025F);
            }
        }
        super.renderEntityName((EntityPlayer) entityIn, x, y, z, name, distanceSq);
    }

    public void doRender2(EntityPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel = (ModelBase)getMainModel();
        this.mainModel.swingProgress = getSwingProgress((EntityPlayer) entity, partialTicks);
        boolean shouldSit = (entity.isRiding() && entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
        this.mainModel.isRiding = shouldSit;
        try {
            float f = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;
            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity.getRidingEntity();
                f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                f2 = f1 - f;
                float f3 = MathHelper.wrapDegrees(f2);
                if (f3 < -85.0F)
                    f3 = -85.0F;
                if (f3 >= 85.0F)
                    f3 = 85.0F;
                f = f1 - f3;
                if (f3 * f3 > 2500.0F)
                    f += f3 * 0.2F;
                f2 = f1 - f;
            }
            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            renderLivingAt(entity, x, y, z);
            float f8 = handleRotationFloat((EntityPlayer)entity, partialTicks);
            applyRotations(entity, f8, f, partialTicks);
            float f4 = prepareScale((EntityPlayer) entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;
            if (!entity.isRiding()) {
                f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
                if (entity.isChild())
                    f6 *= 3.0F;
                if (f5 > 1.0F)
                    f5 = 1.0F;
                f2 = f1 - f;
            }
            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
            this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, (Entity)entity);
            if (this.renderOutlines) {
                boolean flag1 = setScoreTeamColor((EntityPlayer) entity);
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(getTeamColor((EntityPlayer) entity));
                if (!this.renderMarker)
                    renderModel((EntityPlayer) entity, f6, f5, f8, f2, f7, f4);
                if (!(entity instanceof EntityPlayer) || !entity.isSpectator())
                    renderLayers((EntityPlayer) entity, f6, f5, partialTicks, f8, f2, f7, f4);
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
                if (flag1)
                    unsetScoreTeamColor();
            } else {
                boolean flag = setDoRenderBrightness((EntityPlayer)entity, partialTicks);
                renderModel((EntityPlayer) entity, f6, f5, f8, f2, f7, f4);
                if (flag)
                    unsetBrightness();
                GlStateManager.depthMask(true);
                if (!(entity instanceof EntityPlayer) || !entity.isSpectator())
                    renderLayers((EntityPlayer) entity, f6, f5, partialTicks, f8, f2, f7, f4);
            }
            GlStateManager.disableRescaleNormal();
        } catch (Exception exception) {
            LOGGER.error("Couldn't render entity", exception);
        }
        this.mainModel.isChild = false;
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

    public void update() {
        if (Konoa77kg.logged == true && !this.man.world.isRemote)
            for (EntityPlayer player : this.man.world.getPlayers(EntityPlayer.class, new Predicate<EntityPlayer>() {
                public boolean apply(EntityPlayer input) {
                    return true;
                }
            })) {
                if (player.hasCapability(KonoFatProvider.Fat_CAP, null)) {
                    IKonoFat fat = (IKonoFat)player.getCapability(KonoFatProvider.Fat_CAP, null);
                    int bellyFatModifier = fat.getThickness();
                    thickness = bellyFatModifier;
                }
            }
    }

    public static int getT() {
        return thickness;
    }

    public void setT(int t) {
        thickness = t;
    }
}
