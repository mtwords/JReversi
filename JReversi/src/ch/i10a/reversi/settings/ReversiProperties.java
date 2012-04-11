package ch.i10a.reversi.settings;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ReversiProperties {

	private static ReversiProperties inst = new ReversiProperties();

	PropertiesConfiguration settings;

	private ReversiProperties() {
		load();
	}

	public static ReversiProperties inst() {
		return inst;
	}

	private void load() {
		try {
			settings = new PropertiesConfiguration("config/reversi.properties");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	// -------------- getters -------------
	public int getIntProperty(String key) {
		return settings.getInt(key);
	}

	public void setProperty(String key, Object value) {
		settings.setProperty(key, value);
	}

	public void save() {
		try {
			settings.save();
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
