
package me.supermaxman.TheNoom;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
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

public class TheNoom extends JavaPlugin implements Listener
{
    private Logger log = Logger.getLogger("Minecraft");
    PluginDescriptionFile pluginDescriptionFile;

    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new TheNoom(), this);
        pluginDescriptionFile = getDescription();
        log.info("[TheNoom] " + pluginDescriptionFile.getFullName() + " enabled");
    }

    public void onDisable()
    {
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
    {
        return new TheNoomChunkGenerator(id);
    }
    @EventHandler
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
				event.setCancelled(true);
			}
	}
		
}
