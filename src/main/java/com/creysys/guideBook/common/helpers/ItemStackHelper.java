package com.creysys.guideBook.common.helpers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * Created by Creysys on 07 Apr 16.
 */
public final class ItemStackHelper {
    public static NonNullList<ItemStack> getSubItems(ItemStack stack)
    {
    	NonNullList<ItemStack> ret = NonNullList.<ItemStack>create();
        if(stack == null) return ret;

        if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            if (stack.getHasSubtypes()) {
                stack.getItem().getSubItems(stack.getItem(), (CreativeTabs)null, ret);
            }
            else {
                stack = stack.copy();
                stack.setItemDamage(0);
                ret.add(stack);
            }
        } else ret.add(stack);

        return ret;
    }
}
