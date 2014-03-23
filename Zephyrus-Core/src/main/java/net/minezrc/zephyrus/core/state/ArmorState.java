package net.minezrc.zephyrus.core.state;

import net.minezrc.zephyrus.Zephyrus;
import net.minezrc.zephyrus.core.util.Language;
import net.minezrc.zephyrus.state.State;
import net.minezrc.zephyrus.user.User;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus - ArmourState.java
 * 
 * @author minnymin3
 * 
 */

public class ArmorState implements State {

	public static ItemStack[] armor;

	public ArmorState() {
		ItemStack helm = new ItemStack(Material.GOLD_HELMET);
		ItemStack chest = new ItemStack(Material.GOLD_CHESTPLATE);
		ItemStack legs = new ItemStack(Material.GOLD_LEGGINGS);
		ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
		ItemMeta meta = helm.getItemMeta();
		meta.setDisplayName(Language.get("spell.armor.item", ChatColor.GOLD + "Arcane Armour"));
		helm.setItemMeta(meta);
		chest.setItemMeta(meta);
		legs.setItemMeta(meta);
		boots.setItemMeta(meta);
		armor = new ItemStack[] { boots, legs, chest, helm };
	}

	@Override
	public int getTickTime() {
		return 0;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onApplied(User user) {
		user.getPlayer().getInventory().setArmorContents(armor);
		user.getPlayer().updateInventory();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRemoved(User user) {
		user.getPlayer().getInventory().setArmorContents(null);
		user.getPlayer().updateInventory();
	}

	@Override
	public void onStartup(User user) {
		for (ItemStack item : user.getPlayer().getInventory().getArmorContents()) {
			if (checkStack(item)) {
				onRemoved(user);
			}
		}
	}

	private boolean checkStack(ItemStack item) {
		return item != null
				&& item.hasItemMeta()
				&& item.getItemMeta().hasDisplayName()
				&& item.getItemMeta().getDisplayName()
						.equals(Language.get("spell.armor.item", ChatColor.GOLD + "Arcane Armour"));
	}

	@Override
	public void onTick(User user) {
	}

	@Override
	public void onWarning(User user) {
		Language.sendMessage("spell.armor.warning", "Your magic armor begins to fade", user.getPlayer());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.ARMOR && Zephyrus.getUser(e.getWhoClicked().getName()).isStateApplied(this)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		for (ItemStack item : e.getDrops()) {
			if (checkStack(item)) {
				e.getDrops().remove(item);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && e.getCause() != null && e.getCause() != DamageCause.VOID) {
			if (Zephyrus.getUser(((Player) e.getEntity()).getName()).isStateApplied(this)) {
				e.setDamage(e.getDamage() / 4D);
			}
		}
	}

}
