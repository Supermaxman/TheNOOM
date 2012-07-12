package me.supermaxman.TheNoom;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Random;

public class TheNoomSurfacePopulator extends BlockPopulator {
    public int CRATER_CHANCE = 2;
    public int HILL_CHANCE = 60;
    public int GLOWSTONE_CHANCE = 10000;
    public int ENDSTONE_CHANCE = 30000;
    public int FORTRESS_CHANCE = 2;
    public int ROOM_HEIGHT = 10;
    public Material STONE = Material.NETHERRACK;
    public Material AIR = Material.AIR;
    public Material ESTONE = Material.ENDER_STONE;
    public Material GSTONE = Material.GLOWSTONE;
    public Material SSAND = Material.SOUL_SAND;
    public Material SANDSTONE = Material.SANDSTONE;
    public Material BEDROCK = Material.BEDROCK;
    public Material BRICK = Material.NETHER_BRICK;
    public Material SPAWNER = Material.MOB_SPAWNER;
    public Material CHEST = Material.CHEST;
    public Material NWARTS = Material.NETHER_WARTS;
    public Material CMATERIAL = SSAND;
    public Material TSOIL = SANDSTONE;
    private Random hillseed = null;
    private SimplexOctaveGenerator gen = null;

    public BlockFace[] directions = {
            BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN
    };

    public void populate(World world, Random random, Chunk source) {
        random.setSeed(System.nanoTime());
        ChunkSnapshot snapshot = source.getChunkSnapshot();
        int startX = 0;
        int startZ = 0;
        for (int y = 5; y <= 56; y++) {
            for (int x = 1; x <= 16; x++) {
                for (int z = 1; z <= 16; z++) {
                    if ((random.nextInt(this.GLOWSTONE_CHANCE) < 2)) {
                        Block b = source.getBlock(x, y, z);
                        b.setType(this.GSTONE);
                        int i = 0;
                        while (i < 6) {
                            if (random.nextInt(7) + 1 > 1) {
                                b = b.getRelative(directions[random.nextInt(5) + 1], 1);
                                b.setType(this.GSTONE);
                            }
                            i++;
                        }
                    } else if ((random.nextInt(this.ENDSTONE_CHANCE) < 2)) {
                        Block b = source.getBlock(x, y, z);
                        b.setType(this.ESTONE);
                        int i = 0;
                        while (i < 5) {
                            if (random.nextInt(25) + 1 > 1) {
                                b = b.getRelative(directions[random.nextInt(5) + 1], 1);
                                b.setType(this.ESTONE);
                            }
                            i++;
                        }
                    }

                }
            }
        }

        for (int y = 65; y <= 66; y++) {
            for (int x = 1; x <= 16; x++) {
                for (int z = 1; z <= 16; z++) {

                    source.getBlock(x, y, z).setType(this.TSOIL);


                }
            }
        }
        if (hillseed == null || gen == null) {
            hillseed = new Random(source.getWorld().getSeed());
            gen = new SimplexOctaveGenerator(hillseed, 8);
        }
        double chunkX = source.getX();
        double chunkZ = source.getZ();
        gen.setScale(1/64.0);
        for (int x=0; x<16; x++){
            for (int z=0; z<16; z++){
                double noise = gen.noise(x+chunkX*16, z+chunkZ*16, 0.5, 0.5)*8;
                for(int y=40; y>30+noise; y--){
                    source.getBlock(x, y, z).setType(this.AIR);
                    
                }
                for(int y=40; y>40-(noise*3); y--){
                    source.getBlock(x, y, z).setType(this.STONE);

                }
                noise = gen.noise(x+chunkX*16, z+chunkZ*16, 0.5, 0.5)*8;

                for(int y=67; y<67 + noise; y++){
                    source.getBlock(x, y, z).setType(this.TSOIL);

                }
            }
        }
        
        int startY = snapshot.getHighestBlockYAt(startX, startZ);

        int startYN = startY + 1;
        if ((random.nextInt(200) < this.FORTRESS_CHANCE)) {
            int height = (random.nextInt(30) + 10);
            createFortress(source, height, this.BRICK);
        }

        //if((source.getBlock(1, 67, 1).getTypeId()==0)&&(source.getBlock(16, 67, 16).getTypeId()==0)&&(source.getBlock(1, 67, 16).getTypeId()==0)&&(source.getBlock(16, 67, 1).getTypeId()==0)){
        if ((random.nextInt(200) < this.CRATER_CHANCE)) {
        	
            int height = startYN - 2;

            createCrater(source, height, startYN);
            if ((random.nextInt(10) < 2)) {
                createFortress(source, 54, this.BEDROCK);
            }
        }
        //}


        for (int y = 250; y <= 250; y++) {
            for (int x = 1; x <= 16; x++) {
                for (int z = 1; z <= 16; z++) {
                    source.getBlock(x, y, z).setType(this.BEDROCK);

                }
            }
        }
    }

    public void createCrater(Chunk source, int height, int startYN) {

        for (int y = height; y <= startYN + 10; y++) {
            for (int x = 2; x <= 14; x++) {
                for (int z = 2; z <= 14; z++) {
                    if (y == startYN + 1) {
                        if ((z == 2) || (z == 14) || (x == 2) || (x == 14)) {
                            if (((z == 2) && (x == 2)) || ((z == 14) && (x == 14)) || ((z == 2) && (x == 14)) || ((z == 14) && (x == 2))
                                    || ((z == 14) && (x == 3)) || ((z == 3) && (x == 14)) || ((z == 2) && (x == 13)) || ((z == 13) && (x == 2)) || ((z == 2) && (x == 3)) || ((z == 3) && (x == 2)) || ((z == 13) && (x == 14)) || ((z == 14) && (x == 13))) {
                                source.getBlock(x, y, z).setType(this.AIR);
                            } else {

                                source.getBlock(x, y, z).setType(this.CMATERIAL);

                            }
                        } else if ((z == 3) || (z == 13) || (x == 3) || (x == 13)) {
                            if (((z == 3) && (x == 3)) || ((z == 13) && (x == 13)) || ((z == 3) && (x == 13)) || ((z == 13) && (x == 3))) {
                                source.getBlock(x, y, z).setType(this.CMATERIAL);
                            }else{
                                source.getBlock(x, y, z).setType(this.AIR);
                            }
                        }else{
                            source.getBlock(x, y, z).setType(this.AIR);
                        }

                    } else if (y == startYN) {
                        if ((z == 3) || (z == 13) || (x == 3) || (x == 13) || (z == 2) || (z == 14) || (x == 2) || (x == 14)) {
                            if (((z == 2) && (x == 2)) || ((z == 14) && (x == 14)) || ((z == 2) && (x == 14)) || ((z == 14) && (x == 2)) ||
                                    ((z == 3) && (x == 3)) || ((z == 13) && (x == 13)) || ((z == 3) && (x == 13)) || ((z == 13) && (x == 3)) || ((z == 14) && (x == 3)) || ((z == 3) && (x == 14)) || ((z == 2) && (x == 13)) || ((z == 13) && (x == 2)) || ((z == 2) && (x == 3)) || ((z == 3) && (x == 2)) || ((z == 13) && (x == 14)) || ((z == 14) && (x == 13))) {
                                source.getBlock(x, y, z).setType(this.TSOIL);
                            } else {

                                source.getBlock(x, y, z).setType(this.CMATERIAL);

                            }
                        } else {
                            source.getBlock(x, y, z).setType(this.AIR);
                        }
                        
                        
                    } else if (y == startYN - 1) {
                        if (((z == 2) && (x == 2)) || ((z == 14) && (x == 14)) || ((z == 2) && (x == 14)) || ((z == 14) && (x == 2)) ||
                                ((z == 3) && (x == 3)) || ((z == 13) && (x == 13)) || ((z == 3) && (x == 13)) || ((z == 13) && (x == 3)) || ((z == 14) && (x == 3)) || ((z == 3) && (x == 14)) || ((z == 2) && (x == 13)) || ((z == 13) && (x == 2)) || ((z == 2) && (x == 3)) || ((z == 3) && (x == 2)) || ((z == 13) && (x == 14)) || ((z == 14) && (x == 13))) {
                            source.getBlock(x, y, z).setType(this.CMATERIAL);
                        } else {

                            source.getBlock(x, y, z).setType(this.CMATERIAL);

                        }
                    } else if (y == startYN - 2) {
                        if (((z <= 9) && (x <= 9) && (z >= 7) && (x >= 7))) {
                            source.getBlock(x, y, z).setType(Material.GLOWSTONE);
                        }
                    }else{
                        source.getBlock(x, y, z).setType(this.AIR);
                    }


                }
            }
        }


    }


    public void createFortress(Chunk source, int height, Material mat) {
        for (int y = height; y <= height + this.ROOM_HEIGHT; y++) {
            for (int x = 1; x <= 15; x++) {
                for (int z = 1; z <= 15; z++) {

                    if ((y == height) || (y == height + this.ROOM_HEIGHT)) {
                        if ((mat == Material.BEDROCK) && ((y == height + this.ROOM_HEIGHT)) && (((z <= 9) && (x <= 9) && (z >= 7) && (x >= 7)))) {
                            source.getBlock(x, y, z).setType(this.AIR);
                        } else {
                            source.getBlock(x, y, z).setType(mat);
                        }
                    } else if ((mat != Material.BEDROCK) &&(y == height + 1) && (x == 8) && (z == 8)) {
                        Block b = source.getBlock(x, y, z);
                        b.setType(this.CHEST);
                        ((Chest) b.getState()).getBlockInventory().setItem(1 ,new ItemStack(Material.GOLDEN_APPLE,1));
                        
                    } else if ((y == height + 2) && (x == 8) && (z == 8)) {
                        Block b = source.getBlock(x, y, z);
                        b.setType(this.SPAWNER);
                        CreatureSpawner spawner = (CreatureSpawner) b.getState();
                        spawner.setSpawnedType(EntityType.BLAZE);
                    } else if ((mat != Material.BEDROCK) &&(y == height + 1) && (x != 8) && (z != 8)&&(z != 15)&&(z != 1)&&(x != 15)&&(x != 1)) {
                        Block b = source.getBlock(x, y, z);
                        b.setType(this.SSAND);
                    } else if ((mat != Material.BEDROCK) &&(y == height + 2) && (x != 8) && (z != 8)&&(z != 15)&&(z != 1)&&(x != 15)&&(x != 1)) {
                        Block b = source.getBlock(x, y, z);
                        b.setType(this.NWARTS);
                    } else {
                    	
                        if ((z == 1) || (z == 15) || (x == 1) || (x == 15)) {
                            source.getBlock(x, y, z).setType(mat);
                        } else {
                            source.getBlock(x, y, z).setType(this.AIR);
                        }
                    }
                }

            }
        }
    }


}