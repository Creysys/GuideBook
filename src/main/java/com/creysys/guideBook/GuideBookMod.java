package com.creysys.guideBook;

import com.creysys.guideBook.api.RecipeManager;
import com.creysys.guideBook.client.GuideBookGui;
import com.creysys.guideBook.common.GuiBookContainer;
import com.creysys.guideBook.common.PlayerEventHandler;
import com.creysys.guideBook.client.recipe.handler.RecipeHandlerCrafting;
import com.creysys.guideBook.client.recipe.handler.RecipeHandlerSmelting;
import com.creysys.guideBook.common.proxy.ProxyServer;
import com.creysys.guideBook.common.items.GuideBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = GuideBookMod.MODID, version = GuideBookMod.VERSION)
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

    public static GuideBook guideBook;

    @Mod.Instance
    public static GuideBookMod instance;

    public final GuiHandler guiHandler = new GuiHandler();

    @SidedProxy(serverSide = "com.creysys.guideBook.common.proxy.ProxyServer", clientSide = "com.creysys.guideBook.common.proxy.ProxyClient")
    public static ProxyServer proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);

        RecipeManager.registerHandler(new RecipeHandlerCrafting());
        RecipeManager.registerHandler(new RecipeHandlerSmelting());

        guideBook = new GuideBook();
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
    public void postIinit(FMLPostInitializationEvent event) { RecipeManager.load(); }
}
