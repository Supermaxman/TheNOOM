
package me.supermaxman.TheNoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import static java.lang.System.arraycopy;
import static java.lang.System.inheritedChannel;

public class TheNoomChunkGenerator extends ChunkGenerator
{
    private Logger log = Logger.getLogger("Minecraft");
    private short[] layer;
    private byte[] layerDataValues;

    public TheNoomChunkGenerator()
    {
        this("64,netherrack");
    }

    public TheNoomChunkGenerator(String id){
    	try{
    	layer = new short[128]; // Default to 128
    	layerDataValues = null;
    	layer = new short[65];
    	layer[0] = (short)Material.BEDROCK.getId();
    	Arrays.fill(layer, 1, 4, (short)Material.ICE.getId());
    	layer[4] = (short)Material.OBSIDIAN.getId();
    	Arrays.fill(layer, 5, 64, (short)Material.NETHERRACK.getId());
    	layer[64] = (short)Material.BEDROCK.getId();
    	Arrays.fill(layer, 65, 66, (short)Material.SANDSTONE.getId());

    	//layer[255] =  (short)Material.BEDROCK.getId();

    	
    	
    }catch (Exception e){
    	log.severe("[TheNoom] Error Generating TheNoom, check the stack trace.");
        e.printStackTrace();
    	}
    }

    @Override
    public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
    {
        int maxHeight = world.getMaxHeight();
        if (layer.length > maxHeight)
        {
            log.warning("[TheNoom] Error, chunk height " + layer.length + " is greater than the world max height (" + maxHeight + "). Trimming to world max height.");
            short[] newLayer = new short[maxHeight];
            arraycopy(layer, 0, newLayer, 0, maxHeight);
            layer = newLayer;
        }
        short[][] result = new short[maxHeight / 16][]; // 16x16x16 chunks
        for (int i = 0; i < layer.length; i += 16)
        {
            result[i >> 4] = new short[4096];
            for (int y = 0; y < Math.min(16, layer.length - i); y++)
            {
                Arrays.fill(result[i >> 4], y * 16 * 16, (y + 1) * 16 * 16, layer[i + y]);
            }
        }

        return result;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world)
    {
        if (layerDataValues != null)
        {
        	System.out.println(11231);
            return Arrays.asList((BlockPopulator)new TheNoomBlockPopulator(layerDataValues));
        } else
        {
        	return Arrays.asList((BlockPopulator)new TheNoomSurfacePopulator());
        }
        
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random)
    {
        if (!world.isChunkLoaded(0, 0))
        {
            world.loadChunk(0, 0);
            
        }

        if ((world.getHighestBlockYAt(0, 0) <= 0) && (world.getBlockAt(0, 0, 0).getType() == Material.AIR)) // SPACE!
        {
            return new Location(world, 0, 64, 0); // Lets allow people to drop a little before hitting the void then shall we?
        }

        return new Location(world, 0, world.getHighestBlockYAt(0, 0), 0);
    }
}
