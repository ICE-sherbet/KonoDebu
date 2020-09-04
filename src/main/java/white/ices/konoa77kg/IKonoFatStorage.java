package white.ices.konoa77kg;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;

public class IKonoFatStorage  implements Capability.IStorage<IKonoFat>{

    @Override
    public NBTBase writeNBT(Capability<IKonoFat> capability, IKonoFat instance, EnumFacing side) {
        return (NBTBase)new NBTTagInt(instance.getThickness());
    }

    @Override
    public void readNBT(Capability<IKonoFat> capability, IKonoFat instance, EnumFacing side, NBTBase nbt) {

        instance.setThickness(((NBTPrimitive)nbt).getInt());
    }

}
