package jadx.plugins.gcash;

import jadx.api.plugins.options.impl.BasePluginOptionsBuilder;


/**
 * GcashOptions is a configuration class for the Gcash plugin.
 * It extends the BasePluginOptionsBuilder to provide options
 * for enabling or disabling the plugin functionality.
 */
public class GcashPluginOptions extends BasePluginOptionsBuilder  {

	private boolean enable;

	/**
	 * Registers the options for the Gcash plugin.
	 * This method sets up a boolean option to enable or disable the plugin.
	 */
	@Override
	public void registerOptions() {
		boolOption(JadxGcashPlugin.PLUGIN_ID + ".enable")
				.description("enable comment")
				.defaultValue(true)
				.setter(v -> enable = v);
	}

	/**
	 * Checks if the Gcash plugin is enabled.
	 *
	 * @return true if the plugin is enabled, false otherwise
	 */
	public boolean isEnable() {
		return enable;
	}
}
