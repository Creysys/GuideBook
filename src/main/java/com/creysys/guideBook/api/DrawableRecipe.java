package com.creysys.guideBook.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 21 Mar 16.
 */
public abstract class DrawableRecipe {
    public int ticks = 0;

    public void update() { ticks++; }

    public void drawOres(IGuiAccessor gui, List<ItemStack> ores, int x, int y, boolean showAmount){

    }

    public void drawItemStack(IGuiAccessor gui, ItemStack stack, int x, int y, boolean showAmount) {
        if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            if (stack.getHasSubtypes()) {
                NonNullList<ItemStack> subItems = NonNullList.<ItemStack>create();
                stack.getItem().getSubItems(stack.getItem(), (CreativeTabs)null, subItems);
                int index = ticks / 20 % (subItems.size());

                stack = subItems.get(index);
            }
            else {
                stack = stack.copy();
                stack.setItemDamage(0);
            }
        }

        RenderHelper.enableGUIStandardItemLighting();
        gui.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        if(showAmount) gui.getRenderItem().renderItemOverlayIntoGUI(gui.getFontRenderer(), stack, x, y, null);
    }

    public void drawItemStackTooltip(IGuiAccessor gui, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            if (stack.getHasSubtypes()) {
            	NonNullList<ItemStack> subItems = NonNullList.<ItemStack>create();
                stack.getItem().getSubItems(stack.getItem(), (CreativeTabs)null, subItems);
                int index = ticks / 20 % (subItems.size());

                stack = subItems.get(index);
            }
            else {
                stack = stack.copy();
                stack.setItemDamage(0);
            }
        }

        if(x < mouseX && mouseX < x + 18 && y < mouseY && mouseY < y + 18) gui.drawHoveringStrings(stack.getTooltip(Minecraft.getMinecraft().player, false), mouseX, mouseY);
    }

    public void drawOresTooltip(IGuiAccessor gui, List<ItemStack> ores, int x, int y, int mouseX, int mouseY) {

    }

    public void clickItemStack(IGuiAccessor gui, ItemStack stack, int x, int y, int mouseX, int mouseY, int mouseButton) {
        if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            if (stack.getHasSubtypes()) {
            	NonNullList<ItemStack> subItems = NonNullList.<ItemStack>create();
                stack.getItem().getSubItems(stack.getItem(), (CreativeTabs)null, subItems);
                int index = ticks / 20 % (subItems.size());

                stack = subItems.get(index);
            }
            else {
                stack = stack.copy();
                stack.setItemDamage(0);
            }
        }

        if(x < mouseX && mouseX < x + 18 && y < mouseY && mouseY < y + 18){
            if(mouseButton == 0) gui.openRecipeState(stack);
            else if(mouseButton == 1) gui.openUsageState(stack);
        }
    }

    public void clickOres(IGuiAccessor gui, List<ItemStack> ores, int x, int y, int mouseX, int mouseY, int mouseButton) {

    }

    public abstract ItemStack[] getInput();
    public abstract ItemStack getOutput();
    public abstract void draw(IGuiAccessor gui, int pageRecipeIndex);
    public abstract void drawForeground(IGuiAccessor gui, int pageRecipeIndex, int mouseX, int mouseY);
    public abstract void mouseClick(IGuiAccessor gui, int pageRecipeIndex, int mouseX, int mouseY, int mouseButton);
}
