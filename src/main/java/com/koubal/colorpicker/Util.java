package com.koubal.colorpicker;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public final class Util {
	private Util() {
	}

	public static void sendRaw(Player player, String message) {
		PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);
		chat.getChatComponents().write(0, WrappedChatComponent.fromJson(message));
		chat.getBytes().write(0, (byte) 0);

		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(player, chat);
		} catch (InvocationTargetException exception) {
			ColorPicker.getColorPickerLogger().severe("Unable to send raw message packet!");
		}
	}
}
