package com.creysys.guideBook.api;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.LinkedHashMap;

/**
 * Created by Creysys on 27 Mar 16.
 */
public final class ItemInfoManager {
    public static LinkedHashMap<ItemStack, String> infos = new LinkedHashMap<ItemStack, String>();

    public static void setItemInfo(Block block, String localizationKey) {
        setItemInfo(block, OreDictionary.WILDCARD_VALUE, localizationKey);
    }

    public static void setItemInfo(Block block, int meta, String localizationKey) {
        setItemInfo(new ItemStack(block, 1, meta), localizationKey);
    }

    public static void setItemInfo(Item item, String localizationKey) {
        setItemInfo(item, OreDictionary.WILDCARD_VALUE, localizationKey);
    }

    public static void setItemInfo(Item item, int meta, String localizationKey) {
        setItemInfo(item, meta, localizationKey);
    }

    public static void setItemInfo(ItemStack stack, String localizationKey) {
        infos.put(stack, localizationKey);
    }
}
