package com.creysys.guideBook.api;

import com.creysys.guideBook.client.GuideBookGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * Created by Creysys on 21 Mar 16.
 */
public abstract class DrawableRecipe {
    public int ticks = 0;

    public void update() { ticks++; }

    public void drawItemStack(GuideBookGui gui, ItemStack stack, int x, int y, boolean showAmount) {
        if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            if (stack.getHasSubtypes()) {
                ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();
                stack.getItem().getSubItems(stack.getItem(), null, subItems);
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

    public void drawItemStackTooltip(GuideBookGui gui, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            if (stack.getHasSubtypes()) {
                ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();
                stack.getItem().getSubItems(stack.getItem(), null, subItems);
                int index = ticks / 20 % (subItems.size());

                stack = subItems.get(index);
            }
            else {
                stack = stack.copy();
                stack.setItemDamage(0);
            }
        }

        if(x < mouseX && mouseX < x + 18 && y < mouseY && mouseY < y + 18) gui.drawHoveringText(stack.getTooltip(Minecraft.getMinecraft().thePlayer, false), mouseX, mouseY);
    }

    public void clickItemStack(GuideBookGui gui, ItemStack stack, int x, int y, int mouseX, int mouseY, int mouseButton) {
        if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            if (stack.getHasSubtypes()) {
                ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();
                stack.getItem().getSubItems(stack.getItem(), null, subItems);
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

    public abstract ItemStack[] getInput();
    public abstract ItemStack getOutput();
    public abstract void draw(GuideBookGui gui, int pageRecipeIndex);
    public abstract void drawForeground(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY);
    public abstract void mouseClick(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY, int mouseButton);
}
