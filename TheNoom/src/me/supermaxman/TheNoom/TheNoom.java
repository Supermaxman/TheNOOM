package me.supermaxman.TheNoom;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TheNoom extends JavaPlugin implements Listener
{
    private Logger log = Logger.getLogger("Minecraft");
    PluginDescriptionFile pluginDescriptionFile;
	public static int BrokenCrystals = 0;

    
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new TheNoom(), this);
        pluginDescriptionFile = getDescription();
        log.info("[TheNoom] " + pluginDescriptionFile.getFullName() + " enabled");
    }

    public void onDisable(){
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
    {
        return new TheNoomChunkGenerator(id);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		Inventory inventory = player.getInventory();
		ItemStack i = ((PlayerInventory) inventory).getBoots();
		if (player.getWorld().getName().equalsIgnoreCase("world_thenoom")){
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 5));
			if ((player.getInventory().getHelmet()==null)||(player.getInventory().getHelmet().getType()!=Material.GOLD_HELMET)){
				player.damage(1);
			
			}
		}
	}
	
    @EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageEvent event){
		Entity e = event.getEntity();
			if (event.getCause()==DamageCause.FALL){
			if (e.getWorld().getName().equalsIgnoreCase("world_thenoom")){
				event.setCancelled(true);
		}
	}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		Entity e = event.getEntity();
			if (e.getWorld().getName().equalsIgnoreCase("world_thenoom")){
				if (e instanceof PigZombie){
					if ((new Random().nextInt(3) < 2)) {
					event.setCancelled(true);
					}
				}
				
			}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityPortalEnter(EntityPortalEnterEvent event){
		Entity e = event.getEntity();
		if (e instanceof Player){
			if (e.getWorld().getName().equalsIgnoreCase("world_thenoom")){
				e.teleport(e.getServer().getWorld("world").getBlockAt(e.getLocation().getBlockX(), e.getServer().getWorld("world").getSeaLevel(), e.getLocation().getBlockZ()).getLocation());
			}else if (e.getWorld().getName().equalsIgnoreCase("world")){
				e.teleport(e.getServer().getWorld("world_thenoom").getBlockAt(e.getLocation().getBlockX(), 70, e.getLocation().getBlockZ()).getLocation());
			}
		}
	}	


    @EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityExplodeEvent event){
		Entity e = event.getEntity();
			if (e instanceof EnderCrystal){
			if (e.getWorld().getName().equalsIgnoreCase("world_thenoom")){
				BrokenCrystals++;
				if (BrokenCrystals == 1){
					e.getServer().broadcastMessage(ChatColor.DARK_RED+""+BrokenCrystals+ChatColor.DARK_AQUA+" Ender Crystal Has Been Destroyed!");
				}else{
					e.getServer().broadcastMessage(ChatColor.DARK_RED+""+BrokenCrystals+ChatColor.DARK_AQUA+" Ender Crystals Have Been Destroyed!");
				}
				if (BrokenCrystals == 5){
					e.getServer().broadcastMessage(ChatColor.DARK_RED+"The Noom Feels Restless. . .");
				}else if (BrokenCrystals == 9){
					e.getServer().broadcastMessage(ChatColor.DARK_RED+"The Noom Begins To Tremble. . .");
				}else if(BrokenCrystals == 10){
					BrokenCrystals = 0;
					e.getServer().broadcastMessage(ChatColor.DARK_RED+"The Noom Has Awakened!");
					e.getServer().getWorld("world_thenoom").spawn(e.getLocation(), EnderDragon.class);
				}
		}
	}
	}
    
    @EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		Player p = event.getPlayer();
		if ((p.getWorld().getName().equalsIgnoreCase("world_thenoom"))&&(event.isCancelled()==false)){
			for (Entity e : p.getNearbyEntities(50, 20, 50)){
				if (e instanceof PigZombie){
					((PigZombie) e).damage(0, p);
				}
			}
		}
	}
    @EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player p = event.getPlayer();
		if ((p.getWorld().getName().equalsIgnoreCase("world_thenoom"))&&(event.isCancelled()==false)){
			for (Entity e : p.getNearbyEntities(50, 20, 50)){
				if (e instanceof PigZombie){
					((PigZombie) e).damage(0, p);
				}
			}
		}
	}
}

