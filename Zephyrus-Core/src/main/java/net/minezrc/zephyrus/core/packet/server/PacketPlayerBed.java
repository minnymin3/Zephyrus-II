package net.minezrc.zephyrus.core.packet.server;

import net.minezrc.zephyrus.core.packet.ServerPacket;
import net.minezrc.zephyrus.core.packet.PacketType.OutgoingPacket;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Zephyrus - PacketPlayerBed.java
 *
 * @author minnymin3
 *
 */
public class PacketPlayerBed extends ServerPacket {

	/**
	 * Creates a new bed packet to place the player in the sleeping position
	 */
	public PacketPlayerBed(Player player) {
		this(player, player.getLocation());
	}
	
	/**
	 * Creates a new bed packet to place the player in the sleeping position at the given location
	 */
	public PacketPlayerBed(Player player, Location loc) {
		super(OutgoingPacket.PLAYER_BED);
		setValue(int.class, 0, player.getEntityId());
		setValue(int.class, 1, loc.getBlockX());
		setValue(int.class, 2, loc.getBlockY());
		setValue(int.class, 3, loc.getBlockZ());
	}
}
