package de.mybukkit.mybrightness;

import java.io.File;
import java.io.IOException;

import de.mybukkit.mybrightness.helper.McColor;
import de.mybukkit.mybrightness.helper.MyConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class MyBrightness implements ClientModInitializer {
	public static  MinecraftClient client;
	public static TextRenderer fontRenderer;
	public static FabricKeyBinding toggle;
	public static FabricKeyBinding full;
	public static FabricKeyBinding increase;
	public static FabricKeyBinding decrease;
	public static boolean enabled=false;
	public static int gamma;
	public static int gammadefault;
	public static int gammaprob;
	public static File configdir = FabricLoader.getInstance().getConfigDirectory();
	public static File configfile = new File(configdir,"mybrightness.cfg");

	public static MyConfig config =new MyConfig(configfile.toString());
	@Override
	public void onInitializeClient() {
		client = MinecraftClient.getInstance();
		
		if(!configfile.exists()) makeFile(configfile);
		config = MyConfig.getInstance();
		gammadefault = config.getInt("default", (int)client.options.gamma);
		fontRenderer = client.textRenderer;

		KeyBindingRegistry.INSTANCE.addCategory("key.mybrightness.category");
		toggle = FabricKeyBinding.Builder.create(new Identifier("mybrightness", "toggle"), InputUtil.Type.KEYSYM, 325, "MyBrightness").build(); // Num 5
		increase = FabricKeyBinding.Builder.create(new Identifier("mybrightness", "increase"), InputUtil.Type.KEYSYM, 326, "MyBrightness").build(); // Num 4
		decrease = FabricKeyBinding.Builder.create(new Identifier("mybrightness", "decrease"), InputUtil.Type.KEYSYM, 324, "MyBrightness").build(); // Num 6

		KeyBindingRegistry.INSTANCE.register(toggle);
		KeyBindingRegistry.INSTANCE.register(increase);
		KeyBindingRegistry.INSTANCE.register(decrease);
	}
	public static void handleKeybinds() {
		if (decrease == null || increase == null || toggle == null)
			return;

		if (toggle.wasPressed()) {
			enabled = !enabled;
			if(enabled) {
				gammaprob = config.getInt("gamma", 1500);
				if(gammaprob >1500)gammaprob=1500;
			
				setBrightness(gammaprob);
				client.inGameHud.setOverlayMessage(McColor.aqua+(new TranslatableText("message.mybrightness.full",getBrightness(),"%").asFormattedString()), false);

			}else {
				if(config.getInt("gamma") != getBrightness())config.saveParamChanges("gamma", Integer.toString(getBrightness()));
				setBrightness(gammadefault);

				client.inGameHud.setOverlayMessage(McColor.gold+(new TranslatableText("message.mybrightness.normal").asFormattedString()), false);
			}
		}

		if (enabled&&increase.wasPressed()) {
			if(getBrightness() < 1500) {
				gamma = getBrightness()+100;
				setBrightness(gamma);
				client.inGameHud.setOverlayMessage(McColor.gold+(new TranslatableText("message.mybrightness.percent",getBrightness(),"%").asFormattedString()), false);
			}
		}
		if (enabled&&decrease.wasPressed()) {
			if(getBrightness() > 0) {
				gamma = getBrightness()-100;
				setBrightness(gamma);
				client.inGameHud.setOverlayMessage(McColor.gold+(new TranslatableText("message.mybrightness.percent",getBrightness(),"%").asFormattedString()), false);
			}
		}
	}
	public void makeFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		}
	}
	public static void setBrightness(int brightness) {
		client.options.gamma = brightness/100;
	}
	public static int getBrightness() {
		int brightness = (int) (client.options.gamma * 100);
		return brightness;
	}
}
