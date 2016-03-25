package com.creysys.guideBook.api;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class RecipeManager {
    public static ArrayList<RecipeHandler> handlers = new ArrayList<RecipeHandler>();
    public static HashMap<RecipeHandler, ArrayList<DrawableRecipe>> loadedRecipes;
    public static ArrayList<ItemStack> craftableItems;

    public static void registerHandler(RecipeHandler handler) { handlers.add(handler); }

    public static boolean containsItemStack(ArrayList<ItemStack> list, ItemStack stack) {
        for (ItemStack s : list) if (s.isItemEqual(stack)) return true;
        return false;
    }

    public static void load() {
        loadedRecipes = new HashMap<RecipeHandler, ArrayList<DrawableRecipe>>();
        for (int i = handlers.size() - 1; i >= 0; i--) loadedRecipes.put(handlers.get(i), handlers.get(i).getRecipes());

        craftableItems = new ArrayList<ItemStack>();
        for (ArrayList<DrawableRecipe> recipes : loadedRecipes.values())
            for (DrawableRecipe recipe : recipes) if(!containsItemStack(craftableItems, recipe.getOutput())) craftableItems.add(recipe.getOutput());
    }

    public static boolean hasRecipes(ItemStack stack) {
        for (ItemStack s : craftableItems)
            if (s != null && s.isItemEqual(stack)) return true;
        return false;
    }

    public static boolean hasUsages(ItemStack stack) {
        for (ArrayList<DrawableRecipe> recipes : loadedRecipes.values())
            for (DrawableRecipe recipe : recipes)
                for (ItemStack s : recipe.getInput())
                    if (s != null && s.isItemEqual(stack)) return true;
        return false;
    }
}
