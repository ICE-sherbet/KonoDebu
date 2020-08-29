package white.ices.konoa77kg;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

public class BreakEv {
    private BlockPos Bpos;
    private float Bprg = 0;
    private int counter;
    private World wc;

    public BreakEv(BlockPos breakPos,World world) {
        Bpos = breakPos;
        counter = Konoa77kg.Bcount;
        Konoa77kg.Bcount++;
        wc = world;
    }
    public boolean Breakprg(float prg,World world){
        if(wc.getBlockState(Bpos).getBlock() == Blocks.AIR)return true;
        wc = world;
        Bprg += prg;
        wc.sendBlockBreakProgress(Bpos.hashCode(),Bpos, (int) Bprg);
        if (Bprg>10) {
            wc.sendBlockBreakProgress(Bpos.hashCode(),Bpos, (int) -1);
            IBlockState state = wc.getBlockState(Bpos);

            Bprg = -1;
            Konoa77kg.Bcount--;
        }
        return (Bprg>=0);
    }
    public BlockPos getBpos(){
        return Bpos;
    }
}
