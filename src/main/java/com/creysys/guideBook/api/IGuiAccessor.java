package com.creysys.guideBook.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Creysys on 28.08.2016.
 */
public interface IGuiAccessor {
    RenderItem getRenderItem();
    FontRenderer getFontRenderer();
    Minecraft getMc();
    int getLeft();
    int getTop();
    void drawHoveringStrings(List<String> lines, int x, int y);
    void drawHoveringString(String s, int x, int y);
    void openRecipeState(ItemStack stack);
    void openUsageState(ItemStack stack);
}
