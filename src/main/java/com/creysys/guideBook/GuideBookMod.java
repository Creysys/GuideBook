package com.creysys.guideBook;

import com.creysys.guideBook.api.RecipeManager;
import com.creysys.guideBook.client.GuideBookGui;
import com.creysys.guideBook.common.GuiBookContainer;
import com.creysys.guideBook.common.items.ItemGuideBook;
import com.creysys.guideBook.common.proxy.ProxyServer;
import com.creysys.guideBook.plugin.PluginThaumcraft;
import com.creysys.guideBook.plugin.vanilla.PluginVanilla;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = GuideBookMod.MODID, version = GuideBookMod.VERSION, dependencies = "required-after:Forge@[12.16.0.1805,);")
public class GuideBookMod
{
    public static class GuiId {
        public static final int GuideBook = 0;
    }

    public class GuiHandler implements IGuiHandler {
        @Override
        public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
            switch(i) {
                case GuiId.GuideBook:
                    return new GuiBookContainer();
            }

            return null;
        }

        @Override
        public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
            switch(i) {
                case GuiId.GuideBook:
                    return new GuideBookGui();
            }

            return null;
        }
    }

    public static final String MODID = "guideBook";
    public static final String VERSION = "1.0";

    public static ItemGuideBook guideBook;

    @Mod.Instance
    public static GuideBookMod instance;

    public final GuiHandler guiHandler = new GuiHandler();

    @SidedProxy(serverSide = "com.creysys.guideBook.common.proxy.ProxyServer", clientSide = "com.creysys.guideBook.common.proxy.ProxyClient")
    public static ProxyServer proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);

        if(event.getSide() == Side.CLIENT) {
            PluginVanilla.preInit();

            if(Loader.isModLoaded("thaumcraft")) PluginThaumcraft.preInit();
        }


        guideBook = new ItemGuideBook();
        GameRegistry.addShapedRecipe(new ItemStack(guideBook), "b", "c", 'b', Items.book, 'c', Blocks.crafting_table);

        proxy.registerHandlers();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.registerKeyBinds();
        proxy.registerModels();
    }

    @EventHandler
    public void postIinit(FMLPostInitializationEvent event) {
        if(event.getSide() == Side.CLIENT){
            PluginVanilla.postInit();
            RecipeManager.load();
        }
    }
}
