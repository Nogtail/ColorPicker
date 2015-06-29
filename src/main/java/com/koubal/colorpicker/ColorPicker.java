package com.koubal.colorpicker;

import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

public class ColorPicker extends JavaPlugin {
	private static Logger colorPickerLogger;
	private static List<Character> colorCharacters;

	@Override
	public void onEnable() {
		colorPickerLogger = getLogger();

		saveDefaultConfig();
		colorCharacters = getConfig().getCharacterList("characters");

		ProtocolLibrary.getProtocolManager().addPacketListener(new TabCompleteListener(this));
		colorPickerLogger.info("Color Picker " + getDescription().getVersion() + " enabled!");
	}

	@Override
	public void onDisable() {
		colorPickerLogger.info("Color Picker disabled!");
	}

	public static Logger getColorPickerLogger() {
		return colorPickerLogger;
	}

	public static List<Character> getColorCharacters() {
		return colorCharacters;
	}
}
