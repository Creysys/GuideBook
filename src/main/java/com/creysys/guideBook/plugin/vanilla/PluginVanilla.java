package com.creysys.guideBook.plugin.vanilla;

import com.creysys.guideBook.api.ItemInfoManager;
import com.creysys.guideBook.api.RecipeManager;
import com.creysys.guideBook.plugin.vanilla.recipe.handler.RecipeHandlerBrewing;
import com.creysys.guideBook.plugin.vanilla.recipe.handler.RecipeHandlerCrafting;
import com.creysys.guideBook.plugin.vanilla.recipe.handler.RecipeHandlerInfo;
import com.creysys.guideBook.plugin.vanilla.recipe.handler.RecipeHandlerSmelting;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

/**
 * Created by Creysys on 06 Apr 16.
 */
public final class PluginVanilla {
    public static void preInit() {
            RecipeManager.registerHandler(new RecipeHandlerCrafting());
            RecipeManager.registerHandler(new RecipeHandlerSmelting());
            RecipeManager.registerHandler(new RecipeHandlerBrewing());

            ItemInfoManager.setBlockInfo(Blocks.DISPENSER, 0, "guideBook.info.dispenser");
            ItemInfoManager.setBlockInfo(Blocks.NOTEBLOCK, 0, "guideBook.info.noteBlock");
            ItemInfoManager.setBlockInfo(Blocks.GOLDEN_RAIL, 0, "guideBook.info.poweredRail");
            ItemInfoManager.setBlockInfo(Blocks.DETECTOR_RAIL, 0, "guideBook.info.detectorRail");
            ItemInfoManager.setBlockInfo(Blocks.ACTIVATOR_RAIL, 0, "guideBook.info.activatorRail");
            ItemInfoManager.setBlockInfo(Blocks.PISTON, 0, "guideBook.info.piston");
            ItemInfoManager.setBlockInfo(Blocks.STICKY_PISTON, 0, "guideBook.info.stickyPiston");
            ItemInfoManager.setBlockInfo(Blocks.TNT, 0, "guideBook.info.tnt");
            ItemInfoManager.setBlockInfo(Blocks.MOSSY_COBBLESTONE, 0, "guideBook.info.mossyCobblestone");
            ItemInfoManager.setBlockInfo(Blocks.STONE_PRESSURE_PLATE, 0, "guideBook.info.stonePressurePlate");
            ItemInfoManager.setBlockInfo(Blocks.WOODEN_PRESSURE_PLATE, 0, "guideBook.info.woodPressurePlate");
            ItemInfoManager.setBlockInfo(Blocks.STONE_BUTTON, 0, "guideBook.info.stoneButton");
            ItemInfoManager.setBlockInfo(Blocks.WOODEN_BUTTON, 0, "guideBook.info.woodButton");
            ItemInfoManager.setBlockInfo(Blocks.VINE, 0, "guideBook.info.vines");

            ItemInfoManager.setItemInfo(Items.LINGERING_POTION, 0, "guideBook.info.lingeringPotion");
            ItemInfoManager.setItemInfo(Items.SPECTRAL_ARROW, 0, "guideBook.info.spectralArrow");
            ItemInfoManager.setItemInfo(Items.GLASS_BOTTLE, 0, "guideBook.info.glassBottle");
            ItemInfoManager.setItemInfo(Items.DRAGON_BREATH, 0, "guideBook.info.dragonBreath");
    }

    public static void postInit() {
        RecipeManager.registerHandler(new RecipeHandlerInfo());
    }
}
