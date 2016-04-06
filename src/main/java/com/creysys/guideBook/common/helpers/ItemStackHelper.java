package com.creysys.guideBook.common.helpers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * Created by Creysys on 07 Apr 16.
 */
public final class ItemStackHelper {
    public static ArrayList<ItemStack> getSubItems(ItemStack stack) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            if (stack.getHasSubtypes()) {
                stack.getItem().getSubItems(stack.getItem(), null, ret);
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
