package com.creysys.guideBook.plugin.vanilla.recipe;

import com.creysys.guideBook.GuideBookMod;
import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.api.IGuiAccessor;
import com.creysys.guideBook.api.RecipeManager;
import com.creysys.guideBook.common.helpers.ItemStackHelper;
import com.creysys.guideBook.network.message.MessagePutItemsInWorkbench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class DrawableRecipeCrafting extends DrawableRecipe {

    public static final ResourceLocation craftingGridTexture = new ResourceLocation("guidebook", "textures/gui/craftinggrid.png");

    @SuppressWarnings("unchecked")
    public static DrawableRecipeCrafting parse(IRecipe recipe) {
        if(recipe instanceof ShapedRecipes) {
            ShapedRecipes r = (ShapedRecipes) recipe;
            return new DrawableRecipeCrafting(r.getRecipeOutput(), r.recipeItems, r.recipeWidth);
        } else if(recipe instanceof ShapelessRecipes) {
            ShapelessRecipes r = (ShapelessRecipes) recipe;
            return new DrawableRecipeCrafting(r.getRecipeOutput(), r.recipeItems.toArray(new ItemStack[0]), 3);
        } else if(recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe r = (ShapedOreRecipe) recipe;
            ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
            for (Object obj : r.getInput()) {
                if(obj instanceof ItemStack) {
                    ret.add((ItemStack) obj);
                } else if(obj instanceof List) {
                    List<ItemStack> l = (List<ItemStack>) obj;
                    if (l.size() == 0) return null;
                    ret.add(l.get(0));
                } else if(obj == null) {
                    ret.add(null);
                } else return null;
            }

            int width = ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, r, 4);

            return new DrawableRecipeCrafting(r.getRecipeOutput(), ret.toArray(new ItemStack[0]), width);
        } else if(recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe r = (ShapelessOreRecipe) recipe;
            ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
            for (Object obj : r.getInput()) {
                if(obj instanceof ItemStack) {
                    ret.add((ItemStack) obj);
                } else if(obj instanceof List) {
                    List<ItemStack> l = (List<ItemStack>) obj;
                    if (l.size() == 0) return null;
                    ret.add(l.get(0));
                } else if(obj == null) {
                    ret.add(null);
                } else return null;
            }

            return new DrawableRecipeCrafting(r.getRecipeOutput(), ret.toArray(new ItemStack[0]), 3);
        }

        return null;
    }

    public ItemStack output;
    public ItemStack[] input;
    public int width;

    private List<Integer> missing;
    private int flashUntil;

    public DrawableRecipeCrafting(ItemStack output, ItemStack[] input, int width) {
        this.output = output.copy();
        this.input = new ItemStack[input.length];
        for(int i = 0; i < input.length; i++){
            if(input[i] != null) this.input[i] = input[i].copy();
        }
        this.width = width;

        this.missing = null;
        this.flashUntil = -1;
    }

    @Override
    public ItemStack[] getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public void draw(IGuiAccessor gui, int pageRecipeIndex) {
        if(pageRecipeIndex == 0) drawRecipe(gui, gui.getLeft() + 38,  gui.getTop() + 14);
        else if(pageRecipeIndex == 1) drawRecipe(gui, gui.getLeft() + 38,  gui.getTop() + 94);
    }

    @Override
    public void drawForeground(IGuiAccessor gui, int pageRecipeIndex, int mouseX, int mouseY) {
        if(pageRecipeIndex == 0) drawRecipeTooltip(gui, gui.getLeft() + 38,  gui.getTop() + 14, mouseX, mouseY);
        else if(pageRecipeIndex == 1) drawRecipeTooltip(gui, gui.getLeft() + 38,  gui.getTop() + 94, mouseX, mouseY);
    }

    @Override
    public void mouseClick(IGuiAccessor gui, int pageRecipeIndex, int mouseX, int mouseY, int mouseButton) {
        if(pageRecipeIndex == 0) clickRecipe(gui, gui.getLeft() + 38,  gui.getTop() + 14, mouseX, mouseY, mouseButton);
        else if(pageRecipeIndex == 1) clickRecipe(gui, gui.getLeft() + 38,  gui.getTop() + 94, mouseX, mouseY, mouseButton);
    }

    private BlockPos findNearbyWorkbench() {
        World world = Minecraft.getMinecraft().world;
        EntityPlayer player = Minecraft.getMinecraft().player;

        int range = 4;
        int posX = (int)Math.round(player.posX - .5d);
        int posY = (int)Math.round(player.posY - .5d);
        int posZ = (int)Math.round(player.posZ - .5d);

        for(int x = posX - range; x <= posX + range; x++)
            for(int y = posY - range; y <= posY + range; y++)
                for(int z = posZ - range; z <= posZ + range; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if(world.getBlockState(pos).getBlock() == Blocks.CRAFTING_TABLE) return pos;
                }

        return null;
    }

    public boolean containsItem(ItemStack[] inventory, ItemStack stack, ArrayList<Integer> used) {
        if (stack == null){
            used.add(null);
            return true;
        }

        for (int i = 0; i < inventory.length; i++)
            for (ItemStack subItem : ItemStackHelper.getSubItems(stack))
                if (RecipeManager.equalItems(inventory[i], subItem) && inventory[i].getCount() > 0) {
                    inventory[i].setCount(inventory[i].getCount() - 1);
                    used.add(i);
                    return true;
                }
        return false;
    }

    public void canPlayerCraft(ItemStack[] inventory, ArrayList<Integer> missing, ArrayList<Integer> used){
        for (int i = 0; i < input.length; i++) {
            if (!containsItem(inventory, input[i], used)) missing.add(i);
        }
    }

    private void putItemsInWorkbench() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        ItemStack[] inventory = new ItemStack[player.inventory.mainInventory.size()];
        for(int i = 0; i < inventory.length; i++)
        {
            ItemStack stack = player.inventory.mainInventory.get(i);
            if(stack == null) inventory[i] = null;
            else inventory[i] = stack.copy();
        }

        ArrayList<Integer> missing = new ArrayList<Integer>();
        ArrayList<Integer> used = new ArrayList<Integer>();

        canPlayerCraft(inventory, missing, used);

        ArrayList<Integer> usedConverted = new ArrayList<Integer>();

        int slot = 0;
        for (Integer integer : used) {
            while(slot % 3 >= width) {
                usedConverted.add(null);
                slot++;
            }

            usedConverted.add(integer);
            slot++;
        }

        if(missing.size() == 0){
            GuideBookMod.network.sendToServer(new MessagePutItemsInWorkbench(usedConverted.toArray(new Integer[0])));
        }
        else {
            this.missing = missing;
            this.flashUntil = ticks + 20;
        }
    }

    private void drawRecipe(IGuiAccessor gui, int left, int top) {
        gui.getMc().getTextureManager().bindTexture(craftingGridTexture);
        RenderHelper.disableStandardItemLighting();
        Gui.drawModalRectWithCustomSizedTexture(left, top, 0, 0, 112, 54, 126, 54);

        drawItemStack(gui, output, left + 91, top + 19, true);
        for(int i = 0; i < input.length; i++)
            if(input[i] != null)
                drawItemStack(gui, input[i], left + (i % width) * 18 + 1, top + i / width * 18 + 1, false);

    }

    private void drawRecipeTooltip(IGuiAccessor gui, int left, int top, int mouseX, int mouseY) {
        if(findNearbyWorkbench() != null) {
            int x1 = left + 58;
            int y1 = top + 37;

            RenderHelper.disableStandardItemLighting();
            gui.getMc().getTextureManager().bindTexture(craftingGridTexture);
            if (x1 < mouseX && mouseX < x1 + 12 && y1 < mouseY && mouseY < y1 + 12) {
                Gui.drawModalRectWithCustomSizedTexture(x1, y1, 112, 14, 14, 14, 126, 54);
                gui.drawHoveringString(I18n.translateToLocal("guideBook.putInWorkbench"), mouseX, mouseY);
            } else Gui.drawModalRectWithCustomSizedTexture(x1, y1, 112, 0, 14, 14, 126, 54);
        }

        if(missing != null && flashUntil > ticks && (flashUntil - ticks) / 3 % 2 == 1) {
            for (Integer id : missing) {
                int x = left + (id % width) * 18 + 2;
                int y = top + id / width * 18 + 2;

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.enableAlpha();
                GlStateManager.enableBlend();
                gui.getMc().getTextureManager().bindTexture(craftingGridTexture);
                Gui.drawModalRectWithCustomSizedTexture(x, y, 112, 28, 14, 14, 126, 54);
            }
        }

        drawItemStackTooltip(gui, output, left + 91, top + 19, mouseX, mouseY);
        for(int i = 0; i < input.length; i++) {
            if(input[i] != null) {
                int x = left + (i % width) * 18 + 1;
                int y = top + i / width * 18 + 1;
                drawItemStackTooltip(gui, input[i], x, y, mouseX, mouseY);
            }
        }
    }

    private void clickRecipe(IGuiAccessor gui, int left, int top, int mouseX, int mouseY, int mouseButton) {
        if(findNearbyWorkbench() != null && mouseButton == 0) {
            int x1 = left + 58;
            int y1 = top + 37;

            if (x1 < mouseX && mouseX < x1 + 12 && y1 < mouseY && mouseY < y1 + 12) putItemsInWorkbench();
        }

        clickItemStack(gui, output,left + 91, top + 19, mouseX, mouseY, mouseButton);
        for(int i = 0; i < input.length; i++) {
            if(input[i] != null) {
                int x = left + (i % width) * 18 + 1;
                int y = top + i / width * 18 + 1;
                clickItemStack(gui, input[i], x, y, mouseX, mouseY, mouseButton);
            }
        }
    }
}
