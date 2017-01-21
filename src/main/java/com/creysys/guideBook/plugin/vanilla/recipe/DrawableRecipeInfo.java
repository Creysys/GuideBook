package com.creysys.guideBook.plugin.vanilla.recipe;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.api.IGuiAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import java.util.ArrayList;

/**
 * Created by Creysys on 27 Mar 16.
 */
public class DrawableRecipeInfo extends DrawableRecipe {
    public static final ResourceLocation bigSlotTexture = new ResourceLocation("guidebook", "textures/gui/bigslot.png");

    public ItemStack stack;
    public String[] lines;

    public String makeLine(ArrayList<String> list) {
        String ret = "";
        for(String word : list) {
            if(ret.equals("")) ret = word;
            else ret += " " + word;
        }
        return ret;
    }

    public DrawableRecipeInfo(ItemStack stack, String localizationKey) {
        this.stack = stack.copy();

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        String[] words = I18n.translateToLocal(localizationKey).split(" ");

        final int maxLineWIdth = 120;
        int currentLineWidth = 0;

        ArrayList<String> linesList = new ArrayList<String>();
        ArrayList<String> currentLine = new ArrayList<String>();

        for(String word : words) {
            int wordWidth = fontRenderer.getStringWidth(word + " ");
            if(currentLineWidth + wordWidth > maxLineWIdth || word.equals("\\n")) {
                linesList.add(makeLine(currentLine));
                currentLine.clear();
                currentLineWidth = 0;
            }

            if(!word.equals("\\n")){
                currentLine.add(word);
                currentLineWidth += wordWidth;
            }
        }

        linesList.add(makeLine(currentLine));
        lines = linesList.toArray(new String[0]);
    }

    @Override
    public ItemStack[] getInput() {
        return new ItemStack[0];
    }

    @Override
    public ItemStack getOutput() {
        return stack;
    }

    @Override
    public void draw(IGuiAccessor gui, int pageRecipeIndex) {
        gui.getMc().getTextureManager().bindTexture(bigSlotTexture);
        Gui.drawScaledCustomSizeModalRect(gui.getLeft() + 80, gui.getTop(), 0, 0, 26, 26, 26, 26, 26, 26);
        drawItemStack(gui, stack, gui.getLeft() + 85, gui.getTop() + 5, false);
    }

    @Override
    public void drawForeground(IGuiAccessor gui, int pageRecipeIndex, int mouseX, int mouseY) {
        drawItemStackTooltip(gui, stack, gui.getLeft() + 85, gui.getTop(), mouseX, mouseY);

        RenderHelper.disableStandardItemLighting();
        for(int i = 0; i < lines.length; i++) gui.getFontRenderer().drawString(lines[i], gui.getLeft() + 35, gui.getTop() + 34 + i * 10, 0xFF000000);
    }

    @Override
    public void mouseClick(IGuiAccessor gui, int pageRecipeIndex, int mouseX, int mouseY, int mouseButton) {
        clickItemStack(gui, stack, gui.getLeft() + 85, gui.getTop(), mouseX, mouseY, mouseButton);
    }
}
