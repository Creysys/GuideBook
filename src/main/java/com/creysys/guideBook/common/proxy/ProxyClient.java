package com.creysys.guideBook.common.proxy;

import com.creysys.guideBook.GuideBookMod;
import com.creysys.guideBook.client.ClientPlayerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class ProxyClient extends ProxyServer {

    public static KeyBinding recipeKey;
    public static KeyBinding usageKey;

    @Override
    public void registerKeyBinds() {
        recipeKey = new KeyBinding("key.guideBook.recipe", Keyboard.KEY_R, "key.categories.guideBook");
        usageKey = new KeyBinding("key.guideBook.usage", Keyboard.KEY_U, "key.categories.guideBook");

        ClientRegistry.registerKeyBinding(recipeKey);
        ClientRegistry.registerKeyBinding(usageKey);
    }

    @Override
    public void registerHandlers() {
        super.registerHandlers();

        MinecraftForge.EVENT_BUS.register(new ClientPlayerHandler());
    }

    @Override
    public void registerModels() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GuideBookMod.guideBook, 0, new ModelResourceLocation("guidebook:guideBook", "inventory"));
    }
}
