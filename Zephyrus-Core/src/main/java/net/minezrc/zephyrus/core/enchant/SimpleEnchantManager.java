package net.minezrc.zephyrus.core.enchant;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minezrc.zephyrus.Zephyrus;
import net.minezrc.zephyrus.core.util.reflection.ReflectionUtils;
import net.minezrc.zephyrus.enchant.ArmorEnchant;
import net.minezrc.zephyrus.enchant.BowEnchant;
import net.minezrc.zephyrus.enchant.Enchant;
import net.minezrc.zephyrus.enchant.EnchantManager;
import net.minezrc.zephyrus.enchant.PickaxeEnchant;
import net.minezrc.zephyrus.enchant.SwordEnchant;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus - SimpleEnchantmentManager.java
 * 
 * @author minnymin3
 * 
 */

public class SimpleEnchantManager implements EnchantManager, Listener {

	//TODO apply enchantments
	
	private Map<Integer, Enchant> enchantmentMap;
	private int id;
	private boolean accepting;

	public SimpleEnchantManager() {
		enchantmentMap = new HashMap<Integer, Enchant>();
		id = 120;
	}

	public Enchant getEnchant(int id) {
		return enchantmentMap.get(id);
	}

	@Override
	public void load() {
		Bukkit.getPluginManager().registerEvents(this, Zephyrus.getPlugin());
		ReflectionUtils.setField(Enchantment.class, true, "acceptingNew");
		accepting = true;
		registerEnchantment(new GlowEnchant());
		ReflectionUtils.setField(Enchantment.class, false, "acceptingNew");
		accepting = false;
	}

	@Override
	public void unload() {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void registerEnchantment(Enchant enchantment) {
		if (Enchantment.getById(id) == null) {
			boolean modified = false;
			if (accepting == false) {
				ReflectionUtils.setField(Enchantment.class, true, "acceptingNew");
				modified = true;
				accepting = true;
			}
			enchantmentMap.put(id, enchantment);
			Enchantment.registerEnchantment(new RegisteredEnchant(id, enchantment));
			id++;
			if (modified) {
				ReflectionUtils.setField(Enchantment.class, false, "acceptingNew");
				accepting = false;
			}
		}
	}

	@EventHandler
	@SuppressWarnings("deprecation")
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			for (ItemStack item : player.getInventory().getArmorContents()) {
				for (Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
					if (enchantmentMap.containsKey(entry.getKey().getId())) {
						Enchant ench = getEnchant(entry.getKey().getId());
						if (ench instanceof ArmorEnchant) {
							((ArmorEnchant) ench).onDamage(entry.getValue(), event);
						}
					}
				}
			}
		}
	}

	@EventHandler
	@SuppressWarnings("deprecation")
	public void onBowShoot(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {
			for (Entry<Enchantment, Integer> entry : event.getBow().getEnchantments().entrySet()) {
				if (enchantmentMap.containsKey(entry.getKey().getId())) {
					Enchant ench = getEnchant(entry.getKey().getId());
					if (ench instanceof BowEnchant) {
						((BowEnchant) ench).onBowShoot(entry.getValue(), event);
					}
				}
			}
		}
	}

	@EventHandler
	@SuppressWarnings("deprecation")
	public void onBlockBreak(BlockBreakEvent event) {
		ItemStack item = event.getPlayer().getItemInHand();
		if (item != null) {
			for (Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
				if (enchantmentMap.containsKey(entry.getKey().getId())) {
					Enchant ench = getEnchant(entry.getKey().getId());
					if (ench instanceof PickaxeEnchant) {
						((PickaxeEnchant) ench).onBlockBreak(entry.getValue(), event);
					}
				}
			}
		}
	}
	
	@EventHandler
	@SuppressWarnings("deprecation")
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			for (ItemStack item : player.getInventory().getArmorContents()) {
				for (Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
					if (enchantmentMap.containsKey(entry.getKey().getId())) {
						Enchant ench = getEnchant(entry.getKey().getId());
						if (ench instanceof SwordEnchant) {
							((SwordEnchant) ench).onDamage(entry.getValue(), event);
						}
					}
				}
			}
		}
	}
	
}
