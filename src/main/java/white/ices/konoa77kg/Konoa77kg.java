package white.ices.konoa77kg;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;


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


    public static RenderFatPlayer3 render;
    public static ArrayList<BreakEv> arr =  new ArrayList<BreakEv>();
    public static ArrayList<BlockPos> destroyblocks =  new ArrayList<BlockPos>();

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
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
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        Entity sourceEntity = event.getSource().getImmediateSource();
        if ((event.getEntityLiving() instanceof EntityIronGolem ) && (sourceEntity instanceof EntityPlayer)) {


            sourceEntity.attackEntityFrom(DamageSource.causeMobDamage(event.getEntityLiving()), event.getAmount());
        }
    }
    @SubscribeEvent
    public static void itemClickEvent(PlayerInteractEvent.RightClickItem e) {
        if (e.getItemStack().getItem() instanceof ItemFood)
            ((ItemFood)e.getItemStack().getItem()).setAlwaysEdible();

            if(e.getSide().isServer())BlockBre(e.getPos().down(),e.getWorld());
    }
    @SubscribeEvent
    public static void TickWorldEvent(LivingEvent.LivingUpdateEvent e){

        if (e.getEntity() instanceof EntityPlayer){
            EntityPlayer pl = (EntityPlayer) e.getEntity();
            BlockPos blockPos = pl.getPosition();
            EntityPlayer player = pl;
            if (player.hasCapability(KonoFatProvider.Fat_CAP, null)) {
                IKonoFat fat = player.getCapability(KonoFatProvider.Fat_CAP, null);
                int bellyFatModifier = fat.getThickness();
                if (bellyFatModifier > 0 && false) {
                        boolean flag;
                        BlockPos BreakPos = new BlockPos((int) player.posX, (int) player.posY - 1, (int) player.posZ);
                        flag = true;
                        int i = 0;
                        if (Bcount != 0) {
                            for (BreakEv b : arr) {
                                if (b.getBpos().getX() == BreakPos.getX() && b.getBpos().getZ() == BreakPos.getZ() && b.getBpos().getY() == BreakPos.getY()) {

                                    flag = false;
                                    if (!b.Breakprg(0.02f, player.world)) {
                                        BlockBre(b.getBpos(), player.world);
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
        }
    }
    static int testCo = 0;
    @SubscribeEvent
    public static void itemUseEvent(LivingEntityUseItemEvent.Finish e) {

        if (e.getItem().getItem() instanceof ItemFood &&
                e.getEntityLiving() instanceof EntityPlayer) {

            IKonoFat fat = e.getEntityLiving().getCapability(KonoFatProvider.Fat_CAP, null);

            if (!((EntityPlayer)e.getEntityLiving()).getFoodStats().needFood()) {
                fat.addThickness(-1);
                if (((ItemFood)e.getItem().getItem()).getSaturationModifier(e.getItem()) > 0.8F && fat.getThickness() / 2 > 8)
                    ((EntityPlayer)e.getEntityLiving()).getFoodStats().setFoodSaturationLevel(((EntityPlayer)e.getEntityLiving()).getFoodStats().getSaturationLevel() - 0.45F);
                oneCount = 0;
            }
        }
    }

    public static final ResourceLocation Fat_CAP = new ResourceLocation("konoa77kg", "fat");
    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof EntityPlayer)) return;

        event.addCapability(Fat_CAP, new KonoFatProvider());
    }
    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post e) {
        if (e.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;
        new GuiWeightMeter(Minecraft.getMinecraft());
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getEntityPlayer() instanceof EntityPlayer) {
            EntityPlayer player = event.getEntityPlayer();
            IKonoFat fat = player.getCapability(KonoFatProvider.Fat_CAP, null);
            //fat.setThickness(0);
        }
    }

    static int oneCount = 0;

    @SubscribeEvent
    public void onPlayerSleep(PlayerSleepInBedEvent event) {
        if (event.getEntityPlayer() instanceof EntityPlayer) {
            IKonoFat fat = (IKonoFat)event.getEntityPlayer().getCapability(KonoFatProvider.Fat_CAP, null);
            if (event.getResultStatus() == EntityPlayer.SleepResult.OK)
                //fat.removeThickness(2);
                ;
        }
    }

    public static boolean logged = false;

    @SubscribeEvent
    public void onPlayerLoggin(PlayerLoggedInEvent event) {
        this.Count2 = 0;
        this.Count3 = 0;
        logged = true;
        IKonoFat fat = event.player.getCapability(KonoFatProvider.Fat_CAP, null);
    }

    @SubscribeEvent
    public void onPlayerLoggout(PlayerLoggedOutEvent event) {
        logged = false;
        IKonoFat fat = event.player.getCapability(KonoFatProvider.Fat_CAP, null);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderEvent(RenderPlayerEvent.Pre e) {
        if (!(e.getEntity() instanceof EntityPlayer))
            return;
        e.setCanceled(true);
        render = new RenderFatPlayer3(e.getRenderer().getRenderManager(), e.getEntityPlayer());
        render.doRender(e.getEntityPlayer(), e.getX(), e.getY(), e.getZ(), (e.getEntityPlayer()).rotationYawHead, e.getPartialRenderTick());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderEvent(RenderSpecificHandEvent e) {
        e.setCanceled(true);
        if (render != null)
            (new HandRenderer()).renderItemInFirstPerson((AbstractClientPlayer)render.p, e.getPartialTicks(), render.p.rotationPitch, e.getHand(), e.getSwingProgress(), e.getItemStack(), e.getEquipProgress());
    }

    int Count = 0;

    int time = 0;
    boolean flag;
    static int jumpCount = 0;
    @SubscribeEvent
    public void tickw(TickEvent e) {

    }
    @SubscribeEvent
    public void tick(TickEvent.PlayerTickEvent e) {
        PotionEffect effect1 = new PotionEffect(MobEffects.SLOWNESS, 40, 0);
        PotionEffect effect2 = new PotionEffect(MobEffects.SLOWNESS, 40, 1);
        PotionEffect effect3 = new PotionEffect(MobEffects.SLOWNESS, 40, 2);
        PotionEffect effect4 = new PotionEffect(MobEffects.ABSORPTION, 500, 0);
        PotionEffect effect5 = new PotionEffect(MobEffects.ABSORPTION, 600, 1);
        if (e.player.hasCapability(KonoFatProvider.Fat_CAP, null)) {
            IKonoFat fat = e.player.getCapability(KonoFatProvider.Fat_CAP, null);
            if (e.side == Side.CLIENT && render != null)
                render.setT(fat.getThickness());
        }
        if (logged) {
            EntityPlayer player = e.player;
            if (player.hasCapability(KonoFatProvider.Fat_CAP, null)) {
                IKonoFat fat = player.getCapability(KonoFatProvider.Fat_CAP, null);
                int bellyFatModifier = fat.getThickness();
                if (bellyFatModifier / 2 > 0) {

                    if (e.side == Side.SERVER && true){
                        BlockPos BreakPos = new BlockPos((int) player.posX, (int) player.posY - 1, (int) player.posZ);
                        flag = true;
                        int i = 0;
                        if (Bcount != 0) {
                            for (BreakEv b : arr) {
                                if (b.getBpos().getX() == BreakPos.getX() && b.getBpos().getZ() == BreakPos.getZ() && b.getBpos().getY() == BreakPos.getY()) {

                                    flag = false;
                                    if (!b.Breakprg(0.1f, player.world)) {
                                        World wc = e.player.world;
                                        IBlockState oldState = wc.getBlockState(b.getBpos());
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
