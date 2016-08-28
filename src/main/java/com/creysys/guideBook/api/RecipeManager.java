package com.creysys.guideBook.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Creysys on 21 Mar 16.
 */
public final class RecipeManager {
    public static ArrayList<RecipeHandler> handlers = new ArrayList<RecipeHandler>();
    public static LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>> loadedRecipes;
    public static ArrayList<ItemStack> craftableItems;

    public static void registerHandler(RecipeHandler handler) { handlers.add(handler); }

    public static boolean equalNBT(ItemStack stack0, ItemStack stack1) {
        if(stack0.hasTagCompound() != stack1.hasTagCompound()) return false;
        if(!stack0.hasTagCompound()) return true;
        return NBTUtil.areNBTEquals(stack0.getTagCompound(), stack1.getTagCompound(), false);
    }

    public static boolean equalItems(ItemStack stack0, ItemStack stack1) {
        if(stack0 == null ||stack1 == null) return false;
        return stack0.isItemEqual(stack1) && equalNBT(stack0, stack1);
    }

    public static boolean containsItemStack(ArrayList<ItemStack> list, ItemStack stack) {
        for (ItemStack s : list) if (equalItems(s, stack)) return true;
        return false;
    }

    public static void load() {
        loadedRecipes = new LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>>();
        for (RecipeHandler handler : handlers) loadedRecipes.put(handler, handler.getRecipes());

        craftableItems = new ArrayList<ItemStack>();
        for (ArrayList<DrawableRecipe> recipes : loadedRecipes.values())
            for (DrawableRecipe recipe : recipes){
                if(recipe == null) continue;
                ItemStack output = recipe.getOutput();
                if(output == null || output.getItem() == null) continue;
                if(output.getItemDamage() != OreDictionary.WILDCARD_VALUE && !containsItemStack(craftableItems, output))
                    craftableItems.add(output);
            }
    }

    public static boolean hasRecipes(ItemStack stack) {
        for (ItemStack s : craftableItems)
            if (equalItems(s, stack)) return true;
        return false;
    }

    public static boolean hasUsages(ItemStack stack) {
        for (ArrayList<DrawableRecipe> recipes : loadedRecipes.values())
            for (DrawableRecipe recipe : recipes)
                for (ItemStack s : recipe.getInput())
                    if (equalItems(s, stack)) return true;
        return false;
    }
}
