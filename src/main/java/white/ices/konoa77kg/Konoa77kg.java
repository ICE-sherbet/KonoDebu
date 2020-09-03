package white.ices.konoa77kg;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;


@Mod(
        modid = Konoa77kg.MOD_ID,
        name = Konoa77kg.MOD_NAME,
        version = Konoa77kg.VERSION
)
@Mod.EventBusSubscriber(modid = "konoa77kg")
public class Konoa77kg {
    public static final String MOD_ID = "konoa77kg";
    public static final String MOD_NAME = "Konoa77kg";
    public static final String VERSION = "1.0-SNAPSHOT";
    @Mod.Instance(MOD_ID)
    public static Konoa77kg instace;

    @SideOnly(Side.CLIENT)
    public static RenderFatPlayer3 render;

    static HashMap<String, Integer> fatlist = new HashMap<String,Integer>();

    public static ArrayList<BreakEv> arr =  new ArrayList<BreakEv>();
    public static ArrayList<BlockPos> destroyblocks =  new ArrayList<BlockPos>();
    static int fatClient = 0;
    static String fatEntity;
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        PacketHander.init();
        CapabilityManager.INSTANCE.register(IKonoFat.class, new IKonoFatStorage(), CFat::new);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public static void serverInit(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandDestroy());
    }


    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new Client());
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */


    static int testCo = 0;

    public static final ResourceLocation Fat_CAP = new ResourceLocation("konoa77kg", "fat");
    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof EntityPlayer)) return;

        event.addCapability(Fat_CAP, new KonoFatProvider());
    }

    @SubscribeEvent
    public void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.world.isRemote) {
            PacketHander.INSTANCE.sendTo( new MessageInstruments(
                    (float)event.player.posX,
                    (float)event.player.posY,
                    (float)event.player.posZ,
                    event.player.getUniqueID().toString(),
                    event.player.getEntityId(),
                    event.player.getCapability(KonoFatProvider.Fat_CAP, null).getThickness()), (EntityPlayerMP)event.player);
        }
    }
    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post e) {
        if (e.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;
        //new GuiWeightMeter(Minecraft.getMinecraft(),fatClient);
    }


    static int oneCount = 0;

    public static boolean logged = false;

    @SubscribeEvent
    public void onPlayerLoggin(PlayerLoggedInEvent event) {
        this.Count2 = 0;
        this.Count3 = 0;
        logged = true;
        IKonoFat fat = event.player.getCapability(KonoFatProvider.Fat_CAP, null);
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)  {
        if (event.getWorld().isRemote && event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntity();
            PacketHander.INSTANCE.sendToServer(new MessagePlayerJoinInAnnouncement(player));
        }
    }

    @SubscribeEvent
    public void onPlayerLoggout(PlayerLoggedOutEvent event) {
        logged = false;
        IKonoFat fat = event.player.getCapability(KonoFatProvider.Fat_CAP, null);
    }

    @SideOnly(Side.CLIENT)
    public static void fatCO(int fats){
        fatClient = fats;
    }
    @SideOnly(Side.CLIENT)
    public static void fatUUID(String uuid){
        fatEntity = uuid;
    }
    @SideOnly(Side.CLIENT)
    public static void fatUserlist(String uuid,int fats){
        fatlist.put(uuid,fats);
    }
    @SideOnly(Side.SERVER)
    public static void DamageEntity(Entity entity,int Damage){
        entity.attackEntityFrom(DamageSource.MAGIC, Damage);

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderEvent(RenderPlayerEvent.Pre e) {
        if (!(e.getEntity() instanceof EntityPlayer))
            return;
        //e.getEntity().sendMessage(new TextComponentString("Name:" + e.getEntity().getName()));
        //IKonoFat fat = e.getEntityPlayer().getCapability(KonoFatProvider.Fat_CAP, null);
        //e.getEntity().sendMessage(new TextComponentString("Co:" + String.valueOf(fatClient)));

        if(fatlist.get(e.getEntityPlayer().getUniqueID().toString())==null)return;
        if(fatlist.get(e.getEntityPlayer().getUniqueID().toString())<1)return;
        e.setCanceled(true);
        render = new RenderFatPlayer3(e.getRenderer().getRenderManager(), e.getEntityPlayer());
        render.setT(fatlist.get(e.getEntityPlayer().getUniqueID().toString()));
        render.doRender(e.getEntityPlayer(), e.getX(), e.getY(), e.getZ(), (e.getEntityPlayer()).rotationYawHead, e.getPartialRenderTick());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderEvent(RenderSpecificHandEvent e) {
        if(!Minecraft.getMinecraft().player.getUniqueID().toString().equals(fatEntity))return;

        e.setCanceled(true);
        if (render != null)
            (new HandRenderer()).renderItemInFirstPerson((AbstractClientPlayer)render.p, e.getPartialTicks(), render.p.rotationPitch, e.getHand(), e.getSwingProgress(), e.getItemStack(), e.getEquipProgress());
    }

    int Count = 0;

    int time = 0;
    boolean flag;
    static int jumpCount = 0;
    @SubscribeEvent
    public void tick(TickEvent.PlayerTickEvent e) {
        //Minecraft minecraft = Minecraft.getMinecraft();
        PotionEffect effect1 = new PotionEffect(MobEffects.SLOWNESS, 40, 0);
        PotionEffect effect2 = new PotionEffect(MobEffects.SLOWNESS, 40, 1);
        PotionEffect effect3 = new PotionEffect(MobEffects.SLOWNESS, 40, 2);
        PotionEffect effect4 = new PotionEffect(MobEffects.ABSORPTION, 500, 0);
        PotionEffect effect5 = new PotionEffect(MobEffects.ABSORPTION, 600, 1);

        if (e.player.hasCapability(KonoFatProvider.Fat_CAP, null)) {
            IKonoFat fat = e.player.getCapability(KonoFatProvider.Fat_CAP, null);
            if (e.side.isClient()) {
                if(e.player.capabilities != null) {
                    //e.player.isSprinting();
                    EntityPlayer player = e.player;
                }
                if (render != null){
                    render.setT(fat.getThickness());
                }
            }
        }
        if (logged) {
            EntityPlayer player = e.player;

            if (player.hasCapability(KonoFatProvider.Fat_CAP, null)) {
                IKonoFat fat = player.getCapability(KonoFatProvider.Fat_CAP, null);

                int bellyFatModifier = fat.getThickness();//fatlist.get(player.getUniqueID().toString());

                if (bellyFatModifier / 2 > 20) {

                    if (e.side == Side.SERVER && true) {
                        do {
                            if(player == null)break;;
                            if(!player.isSprinting())break;
                            AxisAlignedBB axis = player.getEntityBoundingBox();//new AxisAlignedBB(player.posX, player.posY, player.posZ, player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ).grow(0.25);
                            List<EntityLivingBase> entities = player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, axis);
                            for (Entity entity : entities) {
                                if(player.getUniqueID().equals(entity.getUniqueID()))continue;

                                float f = 5;
                                    entity.motionX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f);
                                    entity.motionZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f);
                                    entity.motionY = (double)(-MathHelper.sin((player.rotationPitch / 180.0F * (float)Math.PI) * f));

                                entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), 5);
                            }
                        }while (false);

                        int i = 0;
                        List<BlockPos> BreakPostion = new ArrayList<BlockPos>(new HashSet<>(Arrays.asList(
                                new BlockPos(Math.floor(player.posX-.5f), Math.floor(player.posY) - 1, Math.floor(player.posZ-.5f)),
                                new BlockPos(Math.round(player.posX), Math.floor(player.posY) - 1, Math.floor(player.posZ-.5f)),
                                new BlockPos(Math.floor(player.posX-.5f), Math.floor(player.posY) - 1, Math.round(player.posZ)),
                                new BlockPos(Math.round(player.posX), Math.floor(player.posY) - 1, Math.round(player.posZ)))));
                        for (BlockPos BreakPos : BreakPostion) {
                            flag = true;
                            if (Bcount != 0) {
                                for (BreakEv b : arr) {

                                    if (b.getBpos().getX() == BreakPos.getX() && b.getBpos().getZ() == BreakPos.getZ() && b.getBpos().getY() == BreakPos.getY()) {

                                        flag = false;
                                        if (!b.Breakprg(0.1f, player.world)) {
                                            World wc = e.player.world;
                                            IBlockState old = wc.getBlockState(b.getBpos());
                                            wc.destroyBlock(b.getBpos(), true);
                                            wc.setBlockState(b.getBpos(),
                                                    Blocks.AIR.getBlockState().getBaseState());
                                            wc.notifyBlockUpdate(b.getBpos(), old,
                                                    Blocks.AIR.getBlockState().getBaseState(), 2);

                                            arr.remove(b);
                                            break;

                                        }
                                    }
                                }
                                i++;
                            }


                            if (flag) arr.add(new BreakEv(BreakPos, player.world));
                        }
                    }
                    if (player.isAirBorne)
                        this.Count++;
                    if (player.isAirBorne && this.Count >= 2)
                        this.Count = 0;
                    if (jumpCount == 20)
                        jumpCount = 0;
                }
                if (bellyFatModifier / 2 > 12) {
                    player.addPotionEffect(effect3);
                    if (oneCount == 0) {
                        player.addPotionEffect(effect5);
                        oneCount = 1;
                    }
                    if (player.isRiding())
                        player.dismountRidingEntity();
                } else if (bellyFatModifier / 2 > 8 && bellyFatModifier / 2 < 13) {
                    player.addPotionEffect(effect2);
                    if (oneCount == 0) {
                        player.addPotionEffect(effect4);
                        oneCount = 1;
                    }
                    if (player.isRiding())
                        player.dismountRidingEntity();
                } else if (bellyFatModifier / 2 > 5 && bellyFatModifier / 2 < 9) {
                    player.addPotionEffect(effect1);
                    if (player.isRiding())
                        player.dismountRidingEntity();
                } else {
                    player.removePotionEffect(MobEffects.SLOWNESS);
                    player.removePotionEffect(MobEffects.ABSORPTION);
                }
            }
        }
    }
    int Count2 = 0;
    int Count3 = 0;
    static int Bcount = 0;
    public static String getNewTranslation(int lineNumber) {
        return "bad translations, fix soon";
    }
    public static void BlockBre(BlockPos pos, World world){
        world.setBlockState(pos, Blocks.AIR.getDefaultState(),0);
    }
}
