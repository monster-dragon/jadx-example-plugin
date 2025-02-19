package jadx.plugins.gcash;

import jadx.api.plugins.JadxPlugin;
import jadx.api.plugins.JadxPluginContext;
import jadx.api.plugins.JadxPluginInfo;
import jadx.api.plugins.JadxPluginInfoBuilder;

/**
 * A Jadx plugin implementation for processing GCash Android application decompilation.
 * This plugin replaces {@code StringIndexer._getString()} method calls with their actual string values
 * during the decompilation process.
 *
 * <p>The plugin provides:
 * <ul>
 *   <li>String replacement functionality through {@link StringReplacerPass}</li>
 *   <li>Configurable options through {@link GcashPluginOptions}</li>
 * </ul>
 *
 * <p>The plugin can be enabled or disabled via configuration options.
 * When enabled, it automatically processes and replaces string indexer calls
 * during the Jadx decompilation phase.
 *
 * @see JadxPlugin
 * @see StringReplacerPass
 * @see GcashPluginOptions
 */
public class JadxGcashPlugin implements JadxPlugin {
	public static final String PLUGIN_ID = "jadx-gcash-plugin";
	private final GcashPluginOptions options = new GcashPluginOptions();

	/**
	 * Provides information about the Jadx GCash plugin.
	 * This method is called by the Jadx framework to get plugin metadata.
	 *
	 * <p>The plugin info includes:
	 * <ul>
	 *   <li>Plugin ID: {@value #PLUGIN_ID}</li>
	 *   <li>Name: "Jadx Gcash plugin"</li>
	 *   <li>Description: Brief explanation of plugin functionality</li>
	 *   <li>Homepage: Link to the plugin's GitHub repository</li>
	 * </ul>
	 *
	 * @return a {@link JadxPluginInfo} object containing the plugin metadata
	 * @see JadxPluginInfoBuilder
	 */
	@Override
	public JadxPluginInfo getPluginInfo() {
		return JadxPluginInfoBuilder.pluginId(PLUGIN_ID)
				.name("Jadx Gcash plugin")
				.description("Jadx plugin for Android app Gcash.")
				.homepage("")
				.build();
	}

	/**
	 * Initializes the plugin within the Jadx decompilation framework.
	 * This method is called by the Jadx framework during plugin initialization.
	 *
	 * <p>The initialization process:
	 * <ul>
	 *   <li>Registers plugin options with the context</li>
	 *   <li>Checks if the plugin is enabled through {@link GcashPluginOptions}</li>
	 *   <li>If enabled, adds the {@link StringReplacerPass} to the decompilation pipeline</li>
	 * </ul>
	 *
	 * @param context the plugin context provided by Jadx framework
	 * @see JadxPluginContext
	 * @see StringReplacerPass
	 * @see GcashPluginOptions#isEnable()
	 */
	@Override
	public void init(JadxPluginContext context) {
		context.registerOptions(options);
		if (options.isEnable()) {
			context.addPass(new StringReplacerPass());
		}
	}

}
