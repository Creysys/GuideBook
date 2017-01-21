package com.creysys.guideBook.api;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.LinkedHashMap;

/**
 * Created by Creysys on 27 Mar 16.
 */
public final class ItemInfoManager {
    public static LinkedHashMap<ItemStack, String> infos = new LinkedHashMap<ItemStack, String>();

    public static void setBlockInfo(String mod, String name, int meta, String localizationKey){
        Block block = GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(mod, name));
        if(block != null) setBlockInfo(block, meta, localizationKey);
    }

    public static void setBlockInfo(Block block, String localizationKey) {
        setBlockInfo(block, OreDictionary.WILDCARD_VALUE, localizationKey);
    }

    public static void setBlockInfo(Block block, int meta, String localizationKey) {
        setItemInfo(new ItemStack(block, 1, meta), localizationKey);
    }

    public static void setItemInfo(String mod, String name, int meta, String localizationKey){
        Item item = Item.REGISTRY.getObject(new ResourceLocation(mod, name));
        if(item != null) 
        	setItemInfo(item, meta, localizationKey);
    }

    public static void setItemInfo(Item item, String localizationKey) {
        setItemInfo(item, OreDictionary.WILDCARD_VALUE, localizationKey);
    }

    public static void setItemInfo(Item item, int meta, String localizationKey) {
        setItemInfo(new ItemStack(item, 1, meta), localizationKey);
    }

    public static void setItemInfo(ItemStack stack, String localizationKey) {
        infos.put(stack, localizationKey);
    }
}
