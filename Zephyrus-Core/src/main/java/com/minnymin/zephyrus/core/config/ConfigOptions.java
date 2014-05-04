package com.minnymin.zephyrus.core.config;


import org.bukkit.configuration.file.FileConfiguration;

import com.minnymin.zephyrus.Zephyrus;

/**
 * Zephyrus - ConfigOptions.java
 * 
 * @author minnymin3
 * 
 */

public class ConfigOptions {

	public static boolean UPDATE_CHECKING;
	public static boolean SPELL_RECIPES;
	public static boolean PARTICLE_EFFECTS;
	public static int MANA_REGEN;
	public static boolean FACTION_CASTING;
	public static boolean TOWNY_CASTING;
	
	public static void loadOptions(FileConfiguration config) {
		config.addDefaults(Zephyrus.getPlugin().getConfig().getDefaults());
		UPDATE_CHECKING = config.getBoolean("Update-Checking");
		MANA_REGEN = config.getInt("Mana-Regen");
		SPELL_RECIPES = config.getBoolean("Spell-Recipes");
		PARTICLE_EFFECTS = config.getBoolean("Particle-Effects");
		FACTION_CASTING = config.getBoolean("Faction-Casting");
		TOWNY_CASTING = config.getBoolean("Towny-Casting");
	}
		
}