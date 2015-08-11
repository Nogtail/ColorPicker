package com.koubal.colorpicker.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.koubal.colorpicker.ColorPicker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ChatListener extends PacketAdapter {
	public ChatListener(Plugin plugin) {
		super(plugin, PacketType.Play.Client.CHAT);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPermission("colorpicker.use")) {
			return;
		}

		if (!player.hasPermission("colorpicker.preview")) {
			return;
		}

		String text = event.getPacket().getStrings().read(0);
		event.getPacket().getStrings().write(0, text.replace(ChatColor.COLOR_CHAR + "", ColorPicker.getColorCharacters().get(0).toString()));
	}
}
