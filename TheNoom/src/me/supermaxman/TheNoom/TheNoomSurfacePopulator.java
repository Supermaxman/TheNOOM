package me.supermaxman.TheNoom;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Villager;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.HashMap;
import java.util.Random;

public class TheNoomSurfacePopulator extends BlockPopulator {
    public int CRATER_CHANCE = 2;
    public int TOWER_CHANCE = 2;
    public int FORTRESS_CHANCE = 2;
    public int HILL_CHANCE = 60;
    public int GLOWSTONE_CHANCE = 10000;
    public int ENDSTONE_CHANCE = 30000;
    public int ROOM_HEIGHT = 10;
    public int D_HEIGHT = 41;
    public int T_CHANCE = 10;
    public int S_CHANCE = 5;
    public int CLEAR_CACHE = 1000;

    public Material STONE = Material.NETHERRACK;
    public Material AIR = Material.AIR;
    public Material ESTONE = Material.ENDER_STONE;
    public Material GSTONE = Material.GLOWSTONE;
    public Material SSAND = Material.SOUL_SAND;
    public Material SANDSTONE = Material.SANDSTONE;
    public Material BEDROCK = Material.BEDROCK;
    public Material BRICK = Material.NETHER_BRICK;
    public Material STAIRS = Material.NETHER_BRICK_STAIRS;
    public Material SPAWNER = Material.MOB_SPAWNER;
    public Material CHEST = Material.CHEST;
    public Material WORKBENCH = Material.WORKBENCH;
    public Material FURNACE = Material.FURNACE;
    public Material NWARTS = Material.NETHER_WARTS;
    public Material OBSIDIAN = Material.OBSIDIAN;
    public Material FENCE = Material.NETHER_FENCE;
    public Material LAVA = Material.LAVA;
    public Material RSHROOM = Material.RED_MUSHROOM;
    public Material BSHROOM = Material.BROWN_MUSHROOM;
    public Material RTORCH = Material.REDSTONE_TORCH_ON;
    public Material RLAMP = Material.REDSTONE_LAMP_ON;
    public Material IBARS = Material.IRON_FENCE;
    public Material WATER = Material.WATER;
    public Material CMATERIAL = SSAND;
    public Material TSOIL = SANDSTONE;
    private Random hillseed = null;
    private SimplexOctaveGenerator gen = null;
	public HashMap<Chunk, Integer> hasDungeon = new HashMap<Chunk, Integer>();
	static HashMap<Chunk, Integer> spaceShip = new HashMap<Chunk, Integer>();

	public BlockFace[] directions = {
            BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN
    };
    
	
	
    public ItemStack[] items = {
            new ItemStack(Material.GOLDEN_APPLE),
            new ItemStack(Material.APPLE),
            new ItemStack(Material.BOW),
            new ItemStack(Material.IRON_SWORD),
            new ItemStack(Material.GOLD_HELMET),
            new ItemStack(Material.BREAD, 3),
            new ItemStack(Material.BREAD, 2),
            new ItemStack(Material.BREAD, 6),
            new ItemStack(Material.DIAMOND, 1),
            new ItemStack(Material.NETHER_WARTS,2),
            new ItemStack(Material.NETHER_WARTS, 3),
            new ItemStack(Material.NETHER_WARTS, 9),
            new ItemStack(Material.NETHER_WARTS, 4),
            new ItemStack(Material.NETHER_WARTS, 6),
            new ItemStack(Material.NETHER_WARTS, 1)
    };
    
    public ItemStack[] ditems = {
            new ItemStack(Material.GOLDEN_APPLE),
            new ItemStack(Material.APPLE),
            new ItemStack(Material.BOW),
            new ItemStack(Material.IRON_SWORD),
            new ItemStack(Material.GOLD_HELMET),
            new ItemStack(Material.BREAD, 3),
            new ItemStack(Material.BREAD, 2),
            new ItemStack(Material.BREAD, 6),
            new ItemStack(Material.DIAMOND, 1)
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
        gen.setScale(1 / 64.0);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                double noise = gen.noise(x + chunkX * 16, z + chunkZ * 16, 0.5, 0.5) * 8;
                for (int y = 40; y > 30 + noise; y--) {
                    source.getBlock(x, y, z).setType(this.AIR);

                }
                for (int y = 40; y > 40 - (noise * 3); y--) {
                    source.getBlock(x, y, z).setType(this.STONE);

                }
                noise = gen.noise(x + chunkX * 16, z + chunkZ * 16, 0.5, 0.5) * 32;
                for (int y = 67; y < 67 + noise; y++) {
                    source.getBlock(x, y, z).setType(this.TSOIL);
                }
            }
        }
        
        int startY = snapshot.getHighestBlockYAt(startX, startZ);
        
        int startYN = startY + 1;
        
        if (hasDungeon.containsKey(source)){
        	
           if(hasDungeon.get(source)==0){
               int height = (D_HEIGHT);
               String type = "spawner";
               createLinkedFortress(source, height, this.BRICK, random, type);
               if((!(hasDungeon.containsKey((source.getBlock(0, 1, 0).getLocation().add(20, 0, 0).getChunk()))))&&((random.nextInt(this.S_CHANCE)  < 2))){hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(20, 0, 0).getChunk(),0);}
               if((!(hasDungeon.containsKey((source.getBlock(0, 1, 0).getLocation().add(0, 0, 20).getChunk()))))&&((random.nextInt(this.S_CHANCE)  < 2))){hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(0, 0, 20).getChunk(),0);}
               if((!(hasDungeon.containsKey((source.getBlock(0, 1, 0).getLocation().add(-10, 0, 0).getChunk()))))&&((random.nextInt(this.S_CHANCE)  < 2))){hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(-10, 0, 0).getChunk(),0);}
               if((!(hasDungeon.containsKey((source.getBlock(0, 1, 0).getLocation().add(0, 0, -10).getChunk()))))&&((random.nextInt(this.S_CHANCE)  < 2))){hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(0, 0, -10).getChunk(),0);}
               
               if((random.nextInt(this.T_CHANCE)  < 2)){hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(20, 0, 0).getChunk(),2);}
               if((random.nextInt(this.T_CHANCE)  < 2)){hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(0, 0, 20).getChunk(),2);}
               if((random.nextInt(this.T_CHANCE)  < 2)){hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(-10, 0, 0).getChunk(),2);}
               if((random.nextInt(this.T_CHANCE)  < 2)){hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(0, 0, -10).getChunk(),2);}
               
           }else if(hasDungeon.get(source)==2){
               int height = (D_HEIGHT);
               String type = "treasure";
               createLinkedFortress(source, height, this.BRICK, random, type);
               hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(20, 0, 0).getChunk(),4);
               hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(0, 0, 20).getChunk(),4);
               hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(-10, 0, 0).getChunk(),4);
               hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(0, 0, -10).getChunk(),4);

           }
           
        }
        
       
        
        
        if ((random.nextInt(300) < this.FORTRESS_CHANCE)) {
        	//31 = perfect base height
            int height = (D_HEIGHT);
            String type = "hub";
            createLinkedFortress(source, height, this.BRICK, random, type);
            
            hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(20, 0, 0).getChunk(),0);
            hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(0, 0, 20).getChunk(),0);
            hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(-10, 0, 0).getChunk(),0);
            hasDungeon.put(source.getBlock(0, 1, 0).getLocation().add(0, 0, -10).getChunk(),0);
            
            hasDungeon.put(source, 1);
            
            
        }
        
        if ((source.getBlock(0, 68, 0).getTypeId()==0)&&
        	(source.getBlock(15, 68, 15).getTypeId()==0)&&	
        	(source.getBlock(0, 68, 15).getTypeId()==0)&&	
        	(source.getBlock(15, 68, 0).getTypeId()==0)){
        if ((random.nextInt(200) < this.CRATER_CHANCE)) {

            int height = startYN - 2;

            createCrater(source, height, startYN);
            if ((random.nextInt(10) < 2)) {
                createFortress(source, 54, this.BEDROCK, random);
            }
        }
        }
        if ((random.nextInt(400)  < this.TOWER_CHANCE)) {
            int roomsLeft = random.nextInt(6) + 4;
            int size = 10;
            int height = startYN;
            createTower(source, height, size, roomsLeft, this.OBSIDIAN, random);
            
        }
        
        if ((random.nextInt(300)  < 2)) {
        	
            int height = source.getChunkSnapshot().getHighestBlockYAt(8, 8);
            
            int floor = source.getChunkSnapshot().getHighestBlockYAt(8, 8);
            
            floor=floor+2;
            
            String type = "house";
            createVillage(source, height, floor, random, type);
            
        }
        if ((source.getBlock(0, 68, 0).getTypeId()==0)&&
            	(source.getBlock(15, 68, 15).getTypeId()==0)&&	
            	(source.getBlock(0, 68, 15).getTypeId()==0)&&	
            	(source.getBlock(15, 68, 0).getTypeId()==0)){
        if ((random.nextInt(50)  < 2)) {
        	
            int height = 200;
            int floor = source.getChunkSnapshot().getHighestBlockYAt(8, 8);
            
            String type = "ship1";
            spaceShip.put(source, 1);
            createSpaceShip(source, height, floor, this.OBSIDIAN, random, type);
        }
        }
        
        
        
        
        
        if ((random.nextInt(10)  < 2)) {
            for (int y = 1; y <= 35; y++) {
                for (int x = 0; x <= 15; x++) {
                    for (int z = 0; z <= 15; z++) {
                    	if(source.getBlock(x, y, z).getType()==Material.AIR){
                        	if(source.getBlock(x, y-1, z).getType()==Material.NETHERRACK){
                                if ((random.nextInt(30)  < 2)) {

                    		if((random.nextInt(2)  < 1)){
                    			source.getBlock(x, y, z).setType(this.RSHROOM);
                    		}else{
                                source.getBlock(x, y, z).setType(this.BSHROOM);
                    		}
                           }
                    	}
                    }
                    }
                }
            }
        }
        
        
        
        
        for (int y = 41; y <= 41; y++) {
            for (int x = 0; x <= 15; x++) {
                for (int z = 0; z <= 15; z++) {
                	if(source.getBlock(x, y, z).getType()==Material.NETHERRACK){
                		if((random.nextInt(3000)  < 2)){
                        source.getBlock(x, y, z).setType(this.LAVA);
                	}
                	}
                }
            }
        }
        
        for (int y = 14; y <= 24; y++) {
            for (int x = 0; x <= 15; x++) {
                for (int z = 0; z <= 15; z++) {
                	if(source.getBlock(x, y, z).getTypeId()==0){
                    source.getBlock(x, y, z).setType(this.LAVA);
                	}
                }
            }
        }
        
        for (int y = 255; y <= 255; y++) {
            for (int x = 0; x <= 15; x++) {
                for (int z = 0; z <= 15; z++) {
                    source.getBlock(x, y, z).setType(this.BEDROCK);
                    
                }
            }
        }
        
        if(hasDungeon.size()>=this.CLEAR_CACHE){
        hasDungeon.clear();
        System.out.println("[TheNoom]: Cleared Chunk Cashe.");
        }
    }
    
    
    
    public void createCrater(Chunk source, int height, int startYN) {

        for (int y = height; y <= startYN + 20; y++) {
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
                            } else {
                                source.getBlock(x, y, z).setType(this.AIR);
                            }
                        } else {
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
                    } else {
                        source.getBlock(x, y, z).setType(this.AIR);
                    }


                }
            }
        }


    }


    public void createFortress(Chunk source, int height, Material mat, Random random) {
        for (int y = height; y <= height + this.ROOM_HEIGHT; y++) {
            for (int x = 1; x <= 15; x++) {
                for (int z = 1; z <= 15; z++) {
                	
                    if ((y == height) || (y == height + this.ROOM_HEIGHT)) {
                        if ((mat == Material.BEDROCK) && ((y == height + this.ROOM_HEIGHT)) && (((z <= 9) && (x <= 9) && (z >= 7) && (x >= 7)))) {
                            source.getBlock(x, y, z).setType(this.AIR);
                        } else {
                            source.getBlock(x, y, z).setType(mat);
                        }
                    } else if ((mat != Material.BEDROCK) && (y == height + 1) && (x == 8) && (z == 8)) {
                        Block b = source.getBlock(x, y, z);
                        b.setType(this.CHEST);
                        ((Chest) b.getState()).getBlockInventory().setItem(((Chest) b.getState()).getBlockInventory().firstEmpty(), items[random.nextInt(14)]);
                        while(random.nextInt(4)>1){
                        ((Chest) b.getState()).getBlockInventory().setItem(((Chest) b.getState()).getBlockInventory().firstEmpty(), items[random.nextInt(14)]);
                        }
                        
                    } else if ((y == height + 2) && (x == 8) && (z == 8)) {
                        Block b = source.getBlock(x, y, z);
                        b.setType(this.SPAWNER);
                        CreatureSpawner spawner = (CreatureSpawner) b.getState();
                        spawner.setSpawnedType(EntityType.BLAZE);
                    } else if ((mat != Material.BEDROCK) && (y == height + 1) && (x != 8) && (z != 8) && (z != 15) && (z != 1) && (x != 15) && (x != 1)) {
                        Block b = source.getBlock(x, y, z);
                        b.setType(this.AIR);
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


    public void createTower(Chunk source, int height, int size, int roomsLeft, Material mat, Random random) {
    	int bfloor = roomsLeft;
    	int tfloor = 1;
    	while(roomsLeft>0){
    	boolean opening = false;
        for (int y = height; y <= height + (size); y++) {
            for (int x = 1; x <= 15; x++) {
                for (int z = 1; z <= 15; z++) {
                	
                    if ((y == height) || (y == height + size)) {
                        source.getBlock(x, y, z).setType(this.BEDROCK);
                    	if(((x == 1)&&(z == 1))||((x == 15)&&(z == 15))||((x == 15)&&(z == 1))||((x == 1)&&(z == 15))){
                            source.getBlock(x, y, z).setType(this.AIR);
                    	} else if ((y == height)&& (x == 8) && (z == 13)) {
                        	if (opening == false){
                            Block b = source.getBlock(x, y, z);                      
                            if(b.getRelative(BlockFace.DOWN, 10).getType()==Material.AIR){
                            	b.setType(this.BEDROCK);
                            }else if(b.getRelative(BlockFace.DOWN, 10).getType()==Material.BEDROCK){
                            	opening = true;
                            	b.setType(this.AIR);
                            	b.getRelative(BlockFace.DOWN, 5).setType(this.BEDROCK);	
                            }
                        	}
                        
                        }else if ((y == height)&& (x == 8) && (z == 3)) {
                        	if (opening == false){
                                Block b = source.getBlock(x, y, z);                      
                                if(b.getRelative(BlockFace.DOWN, 10).getType()==Material.AIR){
                                	b.setType(this.BEDROCK);
                                }else if(b.getRelative(BlockFace.DOWN, 10).getType()==Material.BEDROCK){
                                	opening = true;
                                	b.setType(this.AIR);
                                	b.getRelative(BlockFace.DOWN, 5).setType(this.BEDROCK);	
                                }
                            	}
                    	}else{
                    	}
                    } else if ((y == height + 1) && (x == 8) && (z == 8)) {
                        Block b = source.getBlock(x, y, z);
                        if (roomsLeft==bfloor){
                            b.setType(this.AIR);
                        }else if(roomsLeft == tfloor){
                        	//add crystal
                            b.setType(this.BEDROCK);
                            b.getWorld().spawn(b.getLocation(), EnderCrystal.class);
                            
                        }else{
                            b.setType(this.SPAWNER);
                            CreatureSpawner spawner = (CreatureSpawner) b.getState();
                            //add random
                            int s = random.nextInt(4);
                            if (s == 1){
                            	spawner.setSpawnedType(EntityType.BLAZE);
                            }else if (s == 2){
                            	spawner.setSpawnedType(EntityType.MAGMA_CUBE);
                            }else if (s == 3){
                            	spawner.setSpawnedType(EntityType.ENDERMAN);
                            }else if (s == 4){
                            	spawner.setSpawnedType(EntityType.CAVE_SPIDER);
                            }else{
                            	spawner.setSpawnedType(EntityType.BLAZE);
                            }
                            
                        }
                    } else if ((y == height + 1)&&(roomsLeft == bfloor)&& (x == 8) && (z == 14)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 1)&&(roomsLeft == bfloor)&& (x == 8) && (z == 15)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 2)&&(roomsLeft == bfloor)&& (x == 8) && (z == 15)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 2)&&(roomsLeft == bfloor)&& (x == 8) && (z == 14)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 2)&&(roomsLeft == bfloor)&& (x == 14) && (z == 8)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 2)&&(roomsLeft == bfloor)&& (x == 15) && (z == 8)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 1)&&(roomsLeft == bfloor)&& (x == 15) && (z == 8)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 1)&&(roomsLeft == bfloor)&& (x == 14) && (z == 8)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 1)&&(roomsLeft == bfloor)&& (x == 8) && (z == 1)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 1)&&(roomsLeft == bfloor)&& (x == 8) && (z == 2)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 2)&&(roomsLeft == bfloor)&& (x == 8) && (z == 1)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 2)&&(roomsLeft == bfloor)&& (x == 8) && (z == 2)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 2)&&(roomsLeft == bfloor)&& (x == 1) && (z == 8)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 2)&&(roomsLeft == bfloor)&& (x == 2) && (z == 8)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 1)&&(roomsLeft == bfloor)&& (x == 1) && (z == 8)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    } else if ((y == height + 1)&&(roomsLeft == bfloor)&& (x == 2) && (z == 8)) {
                        source.getBlock(x, y, z).setType(this.AIR);                        
                        
                    }else {
                        if ((z == 1) || (z == 15) || (x == 1) || (x == 15)) {
                        	if(((x == 1)&&(z == 1))||((x == 15)&&(z == 15))||((x == 15)&&(z == 1))||((x == 1)&&(z == 15))){
                        	}else{
                            source.getBlock(x, y, z).setType(mat);
                            }
                        } else if ((z == 2) || (z == 14) || (x == 2) || (x == 14)) {
                            source.getBlock(x, y, z).setType(this.BEDROCK);
                        } else {
                            source.getBlock(x, y, z).setType(this.AIR);
                        }
                    }
                    }
                
                
            }
        }
        roomsLeft--;
        height = height + size;
    }
    }
    

    public void createLinkedFortress(Chunk source, int height, Material mat, Random random, String type) {
    	if (type.equalsIgnoreCase("hub")){
    		for (int y = height; y <= height + this.ROOM_HEIGHT; y++) {
    			for (int x = 0; x <= 15; x++) {
    				for (int z = 0; z <= 15; z++) {
    					
    					if ((y == height) || (y == height + this.ROOM_HEIGHT)) {
    						source.getBlock(x, y, z).setType(mat); 
                        } else if ((y == height + 1)&& (x == 8) && (z == 15)) {
                            source.getBlock(x, y, z).setType(this.AIR);                        
                            
                        } else if ((y == height + 2)&& (x == 8) && (z == 15)) {
                            source.getBlock(x, y, z).setType(this.AIR);                        
                            
                        } else if ((y == height + 2)&& (x == 15) && (z == 8)) {
                            source.getBlock(x, y, z).setType(this.AIR);                        
                            
                        } else if ((y == height + 1)&& (x == 15) && (z == 8)) {
                            source.getBlock(x, y, z).setType(this.AIR);                        
                            
                        } else if ((y == height + 1)&& (x == 8) && (z == 0)) {
                            source.getBlock(x, y, z).setType(this.AIR);                        
                            
                        } else if ((y == height + 2)&& (x == 8) && (z == 0)) {
                            source.getBlock(x, y, z).setType(this.AIR);                        
                                                 
                            
                        } else if ((y == height + 2)&& (x == 0) && (z == 8)) {
                            source.getBlock(x, y, z).setType(this.AIR);                        
                                                 
                            
                        } else if ((y == height + 1)&& (x == 0) && (z == 8)) {
                            source.getBlock(x, y, z).setType(this.AIR);                        
                              
    					}else if ((z == 0) || (z == 15) || (x == 0) || (x == 15)) {
    						source.getBlock(x, y, z).setType(mat);
    					} else {
    						source.getBlock(x, y, z).setType(this.AIR);
    					}
    					
    				}
    			}	
    		}
        }else if (type.equalsIgnoreCase("spawner")){
    		for (int y = height; y <= height + this.ROOM_HEIGHT; y++) {
    			for (int x = 0; x <= 15; x++) {
    				for (int z = 0; z <= 15; z++) {
    					
    					if ((y == height) || (y == height + this.ROOM_HEIGHT)) {
    						source.getBlock(x, y, z).setType(mat); 
    						
                        }else if ((y == height + 1) && (x == 8) && (z == 8)) {
    						Block b = source.getBlock(x, y, z);
                            b.setType(this.SPAWNER);
                            CreatureSpawner spawner = (CreatureSpawner) b.getState();
                            //add random
                            int s = random.nextInt(4);
                            if (s == 1){
                            	spawner.setSpawnedType(EntityType.BLAZE);
                            }else if (s == 2){
                            	spawner.setSpawnedType(EntityType.MAGMA_CUBE);
                            }else if (s == 3){
                            	spawner.setSpawnedType(EntityType.ENDERMAN);
                            }else if (s == 4){
                            	spawner.setSpawnedType(EntityType.CAVE_SPIDER);
                            }else{
                            	spawner.setSpawnedType(EntityType.BLAZE);
                            }
                            
                        }else if ((y == height + 1)&& (x == 8) && (z == 15)) {
                            source.getBlock(x, y, z).setType(this.FENCE);                        
                            
                        } else if ((y == height + 2)&& (x == 8) && (z == 15)) {
                            source.getBlock(x, y, z).setType(this.FENCE);                        
                            
                        } else if ((y == height + 2)&& (x == 15) && (z == 8)) {
                            source.getBlock(x, y, z).setType(this.FENCE);                        
                            
                        } else if ((y == height + 1)&& (x == 15) && (z == 8)) {
                            source.getBlock(x, y, z).setType(this.FENCE);                        
                            
                        } else if ((y == height + 1)&& (x == 8) && (z == 0)) {
                            source.getBlock(x, y, z).setType(this.FENCE);                        
                            
                        } else if ((y == height + 2)&& (x == 8) && (z == 0)) {
                            source.getBlock(x, y, z).setType(this.FENCE);                        
                                                 
                            
                        } else if ((y == height + 2)&& (x == 0) && (z == 8)) {
                            source.getBlock(x, y, z).setType(this.FENCE);                        
                                                 
                            
                        } else if ((y == height + 1)&& (x == 0) && (z == 8)) {
                            source.getBlock(x, y, z).setType(this.FENCE);                        
                              
    					}else if ((z == 0) || (z == 15) || (x == 0) || (x == 15)) {
    						source.getBlock(x, y, z).setType(mat);
    					}else if ((y == height + 1)&&(((x == 1) && (z == 1)) || ((x == 1) && (z == 14)) || ((x == 14) && (z == 14)) || ((x == 14) && (z == 1)))) {
    						source.getBlock(x, y, z).setType(this.SSAND);
    					}else if ((y == height + 2)&&(((x == 1) && (z == 1)) || ((x == 1) && (z == 14)) || ((x == 14) && (z == 14)) || ((x == 14) && (z == 1)))) {
    						source.getBlock(x, y, z).setType(this.NWARTS);
    					//}else if ((y == height + 1)&&(((x == 9) && (z == 8)) || ((x == 8) && (z == 7)) || ((x == 8) && (z == 9)) || ((x == 7) && (z == 8)))) {
    					//	source.getBlock(x, y, z).setType(this.STAIRS);
    					} else {
    						source.getBlock(x, y, z).setType(this.AIR);
    					}
    				}
    			}	
    		}
            }else if (type.equalsIgnoreCase("treasure")){
        		for (int y = height; y <= height + this.ROOM_HEIGHT; y++) {
        			for (int x = 0; x <= 15; x++) {
        				for (int z = 0; z <= 15; z++) {
        					
        					if ((y == height) || (y == height + this.ROOM_HEIGHT)) {
        						source.getBlock(x, y, z).setType(mat); 
        						
                            } else if ((y == height + 1) && (x == 8) && (z == 8)) {
                                Block b = source.getBlock(x, y, z);
                                b.setType(this.CHEST);
                                ((Chest) b.getState()).getBlockInventory().setItem(((Chest) b.getState()).getBlockInventory().firstEmpty(), ditems[random.nextInt(ditems.length)]);
                                while(random.nextInt(4)>1){
                                ((Chest) b.getState()).getBlockInventory().setItem(((Chest) b.getState()).getBlockInventory().firstEmpty(), ditems[random.nextInt(ditems.length)]);
                                }
                            } else if ((y == height + 1)&& (x == 8) && (z == 15)) {
                                source.getBlock(x, y, z).setType(this.GSTONE);                        
                                
                            } else if ((y == height + 2)&& (x == 8) && (z == 15)) {
                                source.getBlock(x, y, z).setType(this.GSTONE);                        
                                
                            } else if ((y == height + 2)&& (x == 15) && (z == 8)) {
                                source.getBlock(x, y, z).setType(this.GSTONE);                        
                                
                            } else if ((y == height + 1)&& (x == 15) && (z == 8)) {
                                source.getBlock(x, y, z).setType(this.GSTONE);                        
                                
                            } else if ((y == height + 1)&& (x == 8) && (z == 0)) {
                                source.getBlock(x, y, z).setType(this.GSTONE);                        
                                
                            } else if ((y == height + 2)&& (x == 8) && (z == 0)) {
                                source.getBlock(x, y, z).setType(this.GSTONE);                        
                                                     
                                
                            } else if ((y == height + 2)&& (x == 0) && (z == 8)) {
                                source.getBlock(x, y, z).setType(this.GSTONE);                        
                                                     
                                
                            } else if ((y == height + 1)&& (x == 0) && (z == 8)) {
                                source.getBlock(x, y, z).setType(this.GSTONE);                        
                                  
        					}else if ((z == 0) || (z == 15) || (x == 0) || (x == 15)) {
        						source.getBlock(x, y, z).setType(mat);
        					} else {
        						source.getBlock(x, y, z).setType(this.AIR);
        					}
        				}
        			}	
        		}
                }
    }
    
    
    public void createVillage(Chunk source, int height, int floor, Random random, String type){
    	if (type.equalsIgnoreCase("house")){
    		for (int y = height-40; y <= floor + 4; y++) {
    			for (int x = 2; x <= 13; x++) {
    				for (int z = 4; z <= 11; z++) {
    					 if(((y == floor-1))&&((x == 3 || x == 14 || x == 1 || x == 12)&&(z == 4 || z == 11))) {
     						if (source.getBlock(x, y, z).getTypeId()==0){

                             source.getBlock(x, y, z).setType(this.FENCE);
     						}
     					} else if(((y == floor-1))&&((x == 2 || x == 13)&&(z == 5 || z == 12 || z == 3 || z == 10))) {
     						if (source.getBlock(x, y, z).getTypeId()==0){

                                source.getBlock(x, y, z).setType(this.FENCE);
        					}
     					} else if ((y < floor)&&((x == 2 || x == 13)&&(z == 4 || z == 11))) {
    						if (source.getBlock(x, y, z).getTypeId()==0){
    							if(source.getBlock(x, y, z).getLocation().getY()>65){
    								source.getBlock(x, y, z).setType(this.FENCE);
    							}
    						}
    						
                        } else if ((y == floor &&((z == 4) || (z == 11) || (x == 2) || (x == 13)))||((y == floor+4) &&((z != 4) && (z != 11)))) {
                        	
                            source.getBlock(x, y, z).setType(this.OBSIDIAN);
                            
    					} else if(((y == floor+1)||(y == floor+2)||(y == floor+3))&&((x == 2 || x == 13)&&(z == 4 || z == 11))) {
                        	
                            source.getBlock(x, y, z).setType(this.OBSIDIAN);
                            
    					} else if(((y == floor+1))&&((x == 3 || x == 14 || x == 1 || x == 12)&&(z == 5 || z == 12 || z == 3 || z == 10))) {
                        	
                            source.getBlock(x, y, z).setType(this.OBSIDIAN);
                              
    					} else if(((y == floor+2))&&((x == 3 || x == 14 || x == 1 || x == 12)&&(z == 5 || z == 12 || z == 3 || z == 10))) {
                        	
                            source.getBlock(x, y, z).setType(this.RTORCH);
                            
    					} else if(((y == floor+3))&&((x == 3 || x == 14 || x == 1 || x == 12)&&(z == 5 || z == 12 || z == 3 || z == 10))) {
                        	
                            source.getBlock(x, y, z).setType(this.RLAMP);
                            
    					} else if(((y == floor+3))&&((x == 3 || x == 14 || x == 1 || x == 12)&&(z == 4 || z == 11))) {
                        	
                            source.getBlock(x, y, z).setType(this.OBSIDIAN);
                            
    					} else if(((y == floor+3))&&((x == 2 || x == 13)&&(z == 5 || z == 12 || z == 3 || z == 10))) {
                        	
                            source.getBlock(x, y, z).setType(this.OBSIDIAN);
                            
    					} else if (((y == floor) &&((z != 4) && (z != 11)&& (x != 2)&& (x != 13)))) {
                        	
                            source.getBlock(x, y, z).setType(this.BRICK);
                            
    					} else if(((y == floor+1))&&((x == 2)&&(z == 7 || z == 8))) {
                        	
                            source.getBlock(x, y, z).setType(this.AIR);
                            
    					} else if(((y == floor+2))&&((x == 2)&&(z == 7 || z == 8))) {
                        	
                            source.getBlock(x, y, z).setType(this.AIR);
                            
    					} else if(((y == floor+2))&&((x == 4 || x == 6 || x == 9 || x == 11)&&(z == 4 || z == 11))) {
                        	
                            source.getBlock(x, y, z).setType(this.IBARS);
                            
    					} else if(((y == floor+1))&&((x == 12)&&(z == 6 || z == 9))) {
                        	
                            source.getBlock(x, y, z).setType(this.FURNACE);
                            
    					} else if(((y == floor+1))&&((x == 12)&&(z == 7))) {
                        	
                            source.getBlock(x, y, z).setType(this.WORKBENCH);
                            
    					} else if(((y == floor+1))&&((x == 12)&&(z == 8))) {
                        	
    						//add item gen
                            source.getBlock(x, y, z).setType(this.CHEST);
                            
                            
                            
    					} else if(((y == floor+1))&&((x == 2 || x == 13)||(z == 4 || z == 11))) {
                        	
                            source.getBlock(x, y, z).setType(this.BRICK);
                            
    					} else if(((y == floor+2))&&((x == 2 || x == 13)||(z == 4 || z == 11))) {
                        	
                            source.getBlock(x, y, z).setType(this.BRICK);
                          
    					} else if(((y == floor+3))&&((x == 2 || x == 13)||(z == 4 || z == 11))) {
                        	
                            source.getBlock(x, y, z).setType(this.BRICK);
                            
    					}else if ((y > floor)) {
    						
    						source.getBlock(x, y, z).setType(this.AIR);
    						if((y==floor+1)){
    							Block b = source.getBlock(x, y, z);
    	                        //add crystal
    							 if(((x == 6)&&(z == 7 || z == 8))) {
    								 
    								 b.getWorld().spawn(b.getLocation(), Villager.class);
    		                            
    		    				}
    						}
    					}
    					
    				}
    			}	
    		}
    	}
	
    }

    
    public void createSpaceShip(Chunk source, int height, int floor, Material mat, Random random, String type){
    	if (type.equalsIgnoreCase("ship1")){
    		for (int y = floor-2; y <= height + 8; y++) {
    			for (int x = 0; x <= 15; x++) {
    				for (int z = 0; z <= 15; z++) {
    					
    					if (((y == height) || (y == height + 8))&&((z <= 13) && (x <= 13) && (z >= 2) && (x >= 2))) {
    						if(y==height&&(x==8 && z == 8)){
    							source.getBlock(x, y, z).setType(Material.STATIONARY_WATER); 
    						}else if(y==height&&((z <= 9) && (x <= 9) && (z >= 7) && (x >= 7))) {
    							source.getBlock(x, y, z).setType(this.WATER); 
    						}else if((x == 6 || x == 10)&&(z == 6 || z == 10)){
                        		source.getBlock(x, y, z).setType(this.GSTONE);
                        	}else{
        						source.getBlock(x, y, z).setType(mat); 
    						}
                        } else if (((y == height+1) || (y == height + 7))) {
                        	if((y==height+1)&&(x == 2 || x == 13)&&(z == 2 || z == 13)){
                        		Block b = source.getBlock(x, y, z);
                        		b.setType(this.SPAWNER);
                                CreatureSpawner spawner = (CreatureSpawner) b.getState();
                                spawner.setSpawnedType(EntityType.PIG_ZOMBIE);
                        	}else if((y==height+7)&&(x == 2 || x == 13)&&(z == 2 || z == 13)){
                        		source.getBlock(x, y, z).setType(this.GSTONE);
                        	}else if ((z == 1) || (z == 14) || (x == 1) || (x == 14)) {
                                source.getBlock(x, y, z).setType(mat);
                            } else {
                                source.getBlock(x, y, z).setType(this.AIR);
                            }
                        } else if (((y >= height+2) && (y <= height + 6))) {
                        	if((y==height+2||y==height+6)&&(x == 0 || x == 15)&&(z == 0 || z == 15)){
                       	 		source.getBlock(x, y, z).setType(this.GSTONE);
                        	}else if ((z == 0) || (z == 15) || (x == 0) || (x == 15)) {
                       	 		source.getBlock(x, y, z).setType(mat);
                       	 	} else {
                       	 		source.getBlock(x, y, z).setType(this.AIR);
                       	 	}
                        } else if ((y < height)) {
                        	if(x==8 && z == 8){
                        		source.getBlock(x, y, z).setType(this.WATER);
                        	}else if(y<=floor&&((z <= 9) && (x <= 9) && (z >= 7) && (x >= 7))){
        						source.getBlock(x, y, z).setType(this.AIR);
                        	}
    					} else if ((y > height)&&(y < height + 8)) {
    						source.getBlock(x, y, z).setType(this.AIR);
    					}
    					
    					
    				}
    			}	
    		}
    	}
    }










































}