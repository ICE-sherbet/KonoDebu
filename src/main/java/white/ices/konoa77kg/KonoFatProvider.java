package white.ices.konoa77kg;


import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class KonoFatProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IKonoFat.class)
    public static final Capability<IKonoFat> Fat_CAP = null;

    private IKonoFat instance = Fat_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return (capability == Fat_CAP);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return (capability == Fat_CAP) ? Fat_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return Fat_CAP.getStorage().writeNBT(Fat_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        Fat_CAP.getStorage().readNBT(Fat_CAP, this.instance, null, nbt);
    }
}
