package com.koubal.colorpicker;

import com.comphenix.protocol.ProtocolLibrary;
import com.koubal.colorpicker.listener.ChatListener;
import com.koubal.colorpicker.listener.TabCompleteListener;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ColorPicker extends JavaPlugin {
	private static Logger colorPickerLogger;
	private static boolean prependInsert;
	private static List<Character> colorCharacters;

	@Override
	public void onEnable() {
		colorPickerLogger = getLogger();

		saveDefaultConfig();
		Configuration configuration = getConfig();

		prependInsert = configuration.getBoolean("prepend-insert", false);
		colorCharacters = configuration.getCharacterList("characters");

		if (colorCharacters == null || colorCharacters.isEmpty()) {
			colorPickerLogger.warning("No color characters found, using default!");
			colorCharacters = Arrays.asList('&');
		}

		ProtocolLibrary.getProtocolManager().addPacketListener(new TabCompleteListener(this));
		ProtocolLibrary.getProtocolManager().addPacketListener(new ChatListener(this));

		colorPickerLogger.info("Color Picker " + getDescription().getVersion() + " enabled!");
	}

	@Override
	public void onDisable() {
		colorPickerLogger.info("Color Picker disabled!");
	}

	public static Logger getColorPickerLogger() {
		return colorPickerLogger;
	}

	public static boolean isPrependInsert() {
		return prependInsert;
	}

	public static List<Character> getColorCharacters() {
		return colorCharacters;
	}
}
