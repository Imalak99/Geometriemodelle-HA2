package app;

import com.jme3.system.AppSettings;

import jme3D.SimpleBoxPicking;

public class JMonkeyWorldApp {
	public static void main(String[] args) {
		SimpleBoxPicking app = new SimpleBoxPicking();

		// Configure before start
		AppSettings settings = new AppSettings(true);
		settings.setWidth(1400);
		settings.setHeight(1000);
		settings.setResizable(true);
		settings.setSamples(8);
		settings.setBitsPerPixel(32);
		settings.setVSync(true);

		app.setSettings(settings);
		app.setShowSettings(false);
		app.setDisplayStatView(false);

		app.start();

	}
}
