package white.ices.konoa77kg;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerFat {

    private float mana = 0.0f;

    public PlayerFat() {
    }

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public void copyFrom(PlayerFat source) {
        mana = source.mana;
    }


    public void saveNBTData(NBTTagCompound compound) {
        compound.setFloat("mana", mana);
    }

    public void loadNBTData(NBTTagCompound compound) {
        mana = compound.getFloat("mana");
    }
}
