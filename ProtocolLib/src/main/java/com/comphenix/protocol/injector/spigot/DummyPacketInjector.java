package com.comphenix.protocol.injector.spigot;

import java.util.Set;

import org.bukkit.entity.Player;

import com.comphenix.protocol.concurrency.IntegerSet;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.packet.PacketInjector;

/**
 * Dummy packet injector that simply delegates to its parent Spigot packet injector or receiving filters.
 * 
 * @author Kristian
 */
class DummyPacketInjector implements PacketInjector {
	private SpigotPacketInjector injector;
	private IntegerSet reveivedFilters;

	public DummyPacketInjector(SpigotPacketInjector injector, IntegerSet reveivedFilters) {
		this.injector = injector;
		this.reveivedFilters = reveivedFilters;
	}

	@Override
	public void undoCancel(Integer id, Object packet) {
		// Do nothing yet
	}

	@Override
	public boolean addPacketHandler(int packetID) {
		reveivedFilters.add(packetID);
		return true;
	}

	@Override
	public boolean removePacketHandler(int packetID) {
		reveivedFilters.remove(packetID);
		return true;
	}

	@Override
	public boolean hasPacketHandler(int packetID) {
		return reveivedFilters.contains(packetID);
	}

	@Override
	public Set<Integer> getPacketHandlers() {
		return reveivedFilters.toSet();
	}

	@Override
	public PacketEvent packetRecieved(PacketContainer packet, Player client, byte[] buffered) {
		return injector.packetReceived(packet, client);
	}

	@Override
	public void cleanupAll() {
		reveivedFilters.clear();
	}
}
