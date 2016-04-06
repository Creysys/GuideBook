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

            ItemInfoManager.setBlockInfo(Blocks.dispenser, 0, "guideBook.info.dispenser");
            ItemInfoManager.setBlockInfo(Blocks.noteblock, 0, "guideBook.info.noteBlock");
            ItemInfoManager.setBlockInfo(Blocks.golden_rail, 0, "guideBook.info.poweredRail");
            ItemInfoManager.setBlockInfo(Blocks.detector_rail, 0, "guideBook.info.detectorRail");
            ItemInfoManager.setBlockInfo(Blocks.activator_rail, 0, "guideBook.info.activatorRail");
            ItemInfoManager.setBlockInfo(Blocks.piston, 0, "guideBook.info.piston");
            ItemInfoManager.setBlockInfo(Blocks.sticky_piston, 0, "guideBook.info.stickyPiston");
            ItemInfoManager.setBlockInfo(Blocks.tnt, 0, "guideBook.info.tnt");
            ItemInfoManager.setBlockInfo(Blocks.mossy_cobblestone, 0, "guideBook.info.mossyCobblestone");
            ItemInfoManager.setBlockInfo(Blocks.stone_pressure_plate, 0, "guideBook.info.stonePressurePlate");
            ItemInfoManager.setBlockInfo(Blocks.wooden_pressure_plate, 0, "guideBook.info.woodPressurePlate");
            ItemInfoManager.setBlockInfo(Blocks.stone_button, 0, "guideBook.info.stoneButton");
            ItemInfoManager.setBlockInfo(Blocks.wooden_button, 0, "guideBook.info.woodButton");
            ItemInfoManager.setBlockInfo(Blocks.vine, 0, "guideBook.info.vines");

            ItemInfoManager.setItemInfo(Items.lingering_potion, 0, "guideBook.info.lingeringPotion");
            ItemInfoManager.setItemInfo(Items.spectral_arrow, 0, "guideBook.info.spectralArrow");
            ItemInfoManager.setItemInfo(Items.glass_bottle, 0, "guideBook.info.glassBottle");
            ItemInfoManager.setItemInfo(Items.dragon_breath, 0, "guideBook.info.dragonBreath");
    }

    public static void postInit() {
        RecipeManager.registerHandler(new RecipeHandlerInfo());
    }
}
