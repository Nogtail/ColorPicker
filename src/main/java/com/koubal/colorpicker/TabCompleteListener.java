package com.koubal.colorpicker;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TabCompleteListener extends PacketAdapter {
	public TabCompleteListener(Plugin plugin) {
		super(plugin, PacketType.Play.Client.TAB_COMPLETE);
	}

	public void onPacketReceiving(PacketEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPermission("colorpicker.use")) {
			return;
		}

		PacketContainer packet = event.getPacket();
		String text = packet.getStrings().read(0);
		char character = text.charAt(text.length() - 1);

		for (char colorCharacter : ColorPicker.getColorCharacters()) {
			if (colorCharacter != character) {
				continue;
			}

			ChatColor[] colors = ChatColor.values();
			TextComponent messages = new TextComponent("");

			for (ChatColor color : colors) {
				StringBuilder builder = new StringBuilder();

				if (color.isFormat() || color == ChatColor.RESET) {
					builder.append(color.name().charAt(0));
				} else {
					builder.append("█");
				}

				TextComponent message = new TextComponent(builder.toString());
				applyFormatting(message, color);

				BaseComponent hoverText = new TextComponent(WordUtils.capitalizeFully(color.name().replace('_', ' ')) + " (" + character + color.getChar() + ')');
				applyFormatting(hoverText, color);
				message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{hoverText}));

				message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, text + color.getChar()));

				messages.addExtra(message);
				messages.addExtra(" ");
			}

			Util.sendRaw(player, ComponentSerializer.toString(messages));
			break;
		}
	}

	private static void applyFormatting(BaseComponent component, ChatColor color) {
		if (color.isColor() && color != ChatColor.RESET) {
			component.setColor(net.md_5.bungee.api.ChatColor.valueOf(color.name()));
		} else {
			switch (color) {
				case BOLD: component.setBold(true); break;
				case STRIKETHROUGH: component.setStrikethrough(true); break;
				case UNDERLINE: component.setUnderlined(true); break;
				case ITALIC: component.setItalic(true); break;
			}

			component.setColor(net.md_5.bungee.api.ChatColor.WHITE);
		}
	}
}
