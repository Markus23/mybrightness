package de.mybukit.mybrightness;

import de.mybukit.mybrightness.helper.McColor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class MyBrightness implements ClientModInitializer {
	public static  MinecraftClient client;
	public static TextRenderer fontRenderer;
	public static FabricKeyBinding toggle;
	public static FabricKeyBinding full;
	public static FabricKeyBinding increase;
	public static FabricKeyBinding decrease;
	public static boolean enabled=false;
	public static double gamma;
	@Override
	public void onInitializeClient() {
		client = MinecraftClient.getInstance();
		fontRenderer = client.textRenderer;

		KeyBindingRegistry.INSTANCE.addCategory("key.mybrightness.category");
		toggle = FabricKeyBinding.Builder.create(new Identifier("mybrightness", "toggle"), InputUtil.Type.KEYSYM, 325, "MyBrightness").build(); // Num 5
		increase = FabricKeyBinding.Builder.create(new Identifier("mybrightness", "increase"), InputUtil.Type.KEYSYM, 324, "MyBrightness").build(); // Num 4
		decrease = FabricKeyBinding.Builder.create(new Identifier("mybrightness", "decrease"), InputUtil.Type.KEYSYM, 326, "MyBrightness").build(); // Num 6

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
				gamma = client.options.gamma = 15.0;

				client.inGameHud.setOverlayMessage(McColor.aqua+(new TranslatableComponent("message.mybrightness.full",(int)gamma).getFormattedText()), false);

			}else {
				gamma = client.options.gamma = 0.0;

				client.inGameHud.setOverlayMessage(McColor.gold+(new TranslatableComponent("message.mybrightness.normal").getFormattedText()), false);
			}
		}

		if (enabled&&increase.wasPressed()) {
			if(gamma < 15.0D) {
				gamma = client.options.gamma = gamma +1.0D;
				client.inGameHud.setOverlayMessage(McColor.gold+(new TranslatableComponent("message.mybrightness.percent",(int)gamma*100,"%").getFormattedText()), false);
			}
		}
		if (enabled&&decrease.wasPressed()) {
			if(gamma > 0.0D) {
				gamma = client.options.gamma = gamma -1.0D;
				client.inGameHud.setOverlayMessage(McColor.gold+(new TranslatableComponent("message.mybrightness.percent",(int)gamma*100,"%").getFormattedText()), false);
			}
		}
	}
}
