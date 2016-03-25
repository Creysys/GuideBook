package com.creysys.guideBook.client;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.api.RecipeHandler;
import com.creysys.guideBook.api.RecipeManager;
import com.creysys.guideBook.common.GuiBookContainer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;
import java.util.*;

/**
 * Created by Creysys on 20 Mar 16.
 */
public class GuideBookGui extends GuiContainer {

    public abstract class State {
        public class GuiButton {
            private int id;
            private int x;
            private int y;
            private int textureX;
            private int textureY;
            private int width;
            private int height;
            private String text;

            public GuiButton(int id, int x, int y, int textureX, int textureY, int width, int height, String text) {
                this.id = id;
                this.x = x;
                this.y = y;
                this.textureX = textureX;
                this.textureY = textureY;
                this.width = width;
                this.height = height;
                this.text = text;
            }

            public void mouseClicked(int mouseX, int mouseY) {
                if(x <= mouseX && mouseX <= x + width && y <= mouseY && mouseY <= y + height) buttonClicked(id);
            }

            public void draw(int mouseX, int mouseY) {
                mc.getTextureManager().bindTexture(bookGuiTextures);
                RenderHelper.disableStandardItemLighting();
                if (x <= mouseX && mouseX <= x + width && y <= mouseY && mouseY <= y + height) {
                    drawTexturedModalRect(x, y, textureX + 23, textureY, width, height);
                    drawHoveringText(Arrays.asList(new String[]{text}), mouseX, mouseY);
                } else drawTexturedModalRect(x, y, textureX, textureY, width, height);
            }
        }

        public State lastState;

        public State(State lastState) {
            this.lastState = lastState;
        }

        public abstract void draw(int mouseX, int mouseY);
        public abstract void drawForeground(int mouseX, int mouseY);
        public abstract void update();
        public abstract boolean keyTyped(char typedChar, int keyCode);
        public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);
        public abstract void buttonClicked(int id);
    }
    public class StateHome extends State {
        private final int itemSize = 18;
        private final int itemsPerRow = 6;


        private GuiTextField searchBar;
        private ArrayList<ItemStack> searchResult;
        private int page;

        private GuiButton previous;
        private GuiButton next;

        public StateHome() {
            super(null);
            searchBar = new GuiTextField(0, fontRendererObj, left + 38, top + 22, 110, 16);
            searchBar.setFocused(true);
            updateSearchResult();
            page = 0;

            previous = new GuiButton(0, left + 28, top + 150, 3, 207, 18, 10, I18n.translateToLocal("guideBook.previousPage"));
            next = new GuiButton(1, left + 134, top + 150, 3, 194, 18, 10, I18n.translateToLocal("guideBook.nextPage"));

        }

        private void updateSearchResult() {
            String pattern = searchBar.getText().toLowerCase();
            searchResult = new ArrayList<ItemStack>();
            for (ItemStack stack : RecipeManager.craftableItems) {
                if(stack.getDisplayName().toLowerCase().contains(pattern) && !RecipeManager.containsItemStack(searchResult, stack)) searchResult.add(stack);
            }

            page = 0;
        }

        @Override
        public void draw(int mouseX, int mouseY) {
            fontRendererObj.drawString(I18n.translateToLocal("guideBook.search"), left + 38, top + 10, 0xAA1111);

            RenderHelper.disableStandardItemLighting();
            drawRect(left + 38, top + 36, left + 38 + 110, top + 42 + 110, 0x99555555);

            searchBar.drawTextBox();

            RenderHelper.enableGUIStandardItemLighting();
            if(searchResult != null) {
                for(int i = page * 36; i < searchResult.size() && i < page * 36 + 36; i++) {
                    int x = left + 40 + (i % itemsPerRow) * itemSize;
                    int y = top + 42 + i / itemsPerRow * itemSize - page * itemSize * 6;
                    itemRender.renderItemAndEffectIntoGUI(searchResult.get(i), x, y);
                }
            }

            if(page > 0){
                previous.draw(mouseX, mouseY);
            }
            if(searchResult != null && searchResult.size() > page * 36 + 36) {
                next.draw(mouseX, mouseY);
            }
        }

        @Override
        public void drawForeground(int mouseX, int mouseY) {
            if(searchResult != null) {
                for(int i = page * 36; i < searchResult.size() && i < page * 36 + 36; i++) {
                    int x = left + 40 + (i % itemsPerRow) * itemSize;
                    int y = top + 42 + i / itemsPerRow * itemSize - page * itemSize * 6;
                    String itemName = searchResult.get(i).getDisplayName();

                    if(x < mouseX && mouseX < x + itemSize && y < mouseY && mouseY < y + itemSize) drawHoveringText(Arrays.asList(new String[]{itemName}), mouseX, mouseY);
                }
            }
        }

        @Override
        public void update() {
            searchBar.updateCursorCounter();
        }

        @Override
        public boolean keyTyped(char typedChar, int keyCode) {
            if(searchBar.textboxKeyTyped(typedChar, keyCode)) {
                updateSearchResult();
                return true;
            }
            return false;
        }

        @Override
        public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            searchBar.mouseClicked(mouseX, mouseY, mouseButton);

            if (mouseButton == 0) {
                previous.mouseClicked(mouseX, mouseY);
                next.mouseClicked(mouseX, mouseY);
            }

            if (searchResult != null) {
                for (int i = page * 36; i < searchResult.size() && i < page * 36 + 36; i++) {
                    int x = left + 40 + (i % itemsPerRow) * itemSize;
                    int y = top + 42 + i / itemsPerRow * itemSize - page * itemSize * 6;

                    if (x < mouseX && mouseX < x + itemSize && y < mouseY && mouseY < y + itemSize) {
                        if(mouseButton == 0){
                            openRecipeState(searchResult.get(i));
                            break;
                        }
                        else if(mouseButton == 1){
                            openUsageState(searchResult.get(i));
                            break;
                        }
                    }
                }
            }
        }

        @Override
        public void buttonClicked(int id) {
            if(id == 0 && page > 0){
                page--;
            }
            if(id == 1 && (searchResult != null && searchResult.size() > page * 36 + 36)) {
                page++;
            }
        }
    }
    public class StateRecipe extends State {
        private LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>> handlers;
        private int tab;
        private int page;
        public String cmd;
        public Object arg;

        private GuiButton previous;
        private GuiButton next;

        public StateRecipe(State lastState, LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>> handlers, String cmd, Object arg){
            super(lastState);
            this.handlers = handlers;
            this.tab = 0;
            this.page = 0;
            this.cmd = cmd;
            this.arg = arg;

            previous = new GuiButton(0, left + 28, top + 150, 3, 207, 18, 10, I18n.translateToLocal("guideBook.previousPage"));
            next = new GuiButton(1, left + 134, top + 150, 3, 194, 18, 10, I18n.translateToLocal("guideBook.nextPage"));
        }


        public Map.Entry<RecipeHandler, ArrayList<DrawableRecipe>> getTab() {
            int j = tab;
            for (Map.Entry<RecipeHandler, ArrayList<DrawableRecipe>> e : handlers.entrySet()) {
                if(j == 0){
                    return e;
                }
                j--;
            }
            return null;
        }


        @Override
        public void draw(int mouseX, int mouseY) {
            Map.Entry<RecipeHandler, ArrayList<DrawableRecipe>> tab = getTab();
            RecipeHandler handler = tab.getKey();
            ArrayList<DrawableRecipe> recipes = tab.getValue();

            for (int i = 0; i < handler.recipesPerPage(); i++) {
                if (recipes.size() > page * handler.recipesPerPage() + i) recipes.get(page * handler.recipesPerPage() + i).draw(GuideBookGui.this, i);
            }

            int i = 0;
            for (RecipeHandler h : handlers.keySet()) {
                int x = left + 155;
                int y = top + 8 + i * 22;

                mc.getTextureManager().bindTexture(bookGuiTextures);
                if(h == handler ) {
                    RenderHelper.disableStandardItemLighting();
                    drawTexturedModalRect(x, y, 3, 233, 20, 22);
                    RenderHelper.enableGUIStandardItemLighting();
                    itemRender.renderItemAndEffectIntoGUI(h.getTabIcon(), x + 1, y + 3);
                } else{
                    RenderHelper.disableStandardItemLighting();
                    drawTexturedModalRect(x, y, 26, 233, 22, 22);
                    RenderHelper.enableGUIStandardItemLighting();
                    itemRender.renderItemAndEffectIntoGUI(h.getTabIcon(), x + 3, y + 3);
                }

                i++;
            }

            if (page > 0) previous.draw(mouseX, mouseY);
            if (recipes.size() > page * handler.recipesPerPage() + handler.recipesPerPage()) next.draw(mouseX, mouseY);

        }

        @Override
        public void drawForeground(int mouseX, int mouseY) {
            Map.Entry<RecipeHandler, ArrayList<DrawableRecipe>> tab = getTab();
            RecipeHandler handler = tab.getKey();
            ArrayList<DrawableRecipe> recipes = tab.getValue();

            for (int i = 0; i < handler.recipesPerPage(); i++) {
                if (recipes.size() > page * handler.recipesPerPage() + i)
                    recipes.get(page * handler.recipesPerPage() + i).drawForeground(GuideBookGui.this, i, mouseX, mouseY);
            }

            int i = 0;
            for (RecipeHandler h : handlers.keySet()) {
                int x = left + 155;
                int y = top + 8 + i * 22;

                mc.getTextureManager().bindTexture(bookGuiTextures);
                if (x < mouseX && mouseX < x + 20 && y < mouseY && mouseY < y + 22) {
                    drawHoveringString(I18n.translateToLocal(h.getName()), mouseX, mouseY);
                }

                i++;
            }
        }

        @Override
        public void update() {
            Map.Entry<RecipeHandler, ArrayList<DrawableRecipe>> tab = getTab();
            RecipeHandler handler = tab.getKey();
            ArrayList<DrawableRecipe> recipes = tab.getValue();

            for (int i = 0; i < handler.recipesPerPage(); i++) {
                if (recipes.size() > page * handler.recipesPerPage() + i) recipes.get(page * handler.recipesPerPage() + i).update();
            }
        }

        @Override
        public boolean keyTyped(char typedChar, int keyCode) {
            return false;
        }

        @Override
        public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            Map.Entry<RecipeHandler, ArrayList<DrawableRecipe>> tab = getTab();
            RecipeHandler handler = tab.getKey();
            ArrayList<DrawableRecipe> recipes = tab.getValue();

            for (int i = 0; i < handler.recipesPerPage(); i++) {
                if (recipes.size() > page * handler.recipesPerPage() + i)
                    recipes.get(page * handler.recipesPerPage() + i).mouseClick(GuideBookGui.this, i, mouseX, mouseY, mouseButton);
            }

            if (mouseButton == 0) {
                previous.mouseClicked(mouseX, mouseY);
                next.mouseClicked(mouseX, mouseY);
            }

            int i = 0;
            for (RecipeHandler h : handlers.keySet()) {
                int x = left + 155;
                int y = top + 8 + i * 22;

                mc.getTextureManager().bindTexture(bookGuiTextures);
                if (x < mouseX && mouseX < x + 20 && y < mouseY && mouseY < y + 22) {
                    if(mouseButton == 0 && h != handler) {
                        this.tab = i;
                        this.page = 0;
                        break;
                    }
                    else if(mouseButton == 1) {
                        openHandlerUsageState(h);
                        break;
                    }
                }

                i++;
            }
        }

        @Override
        public void buttonClicked(int id) {

            Map.Entry<RecipeHandler, ArrayList<DrawableRecipe>> tab = getTab();
            RecipeHandler handler = tab.getKey();
            ArrayList<DrawableRecipe> recipes = tab.getValue();

            if(id == 0 && page > 0){
                page--;
            }
            if(id == 1 && recipes.size() > page * handler.recipesPerPage() + handler.recipesPerPage()) {
                page++;
            }
        }
    }

    public static final ResourceLocation bookGuiTextures = new ResourceLocation("guidebook", "textures/gui/guideBook.png");
    public static final int bookImageWidth = 192;
    public static final int bookImageHeight = 192;

    public static String onOpenCmd = null;
    public static Object onOpenArg = null;

    public int left;
    public int top;

    public State state;

    public GuideBookGui() {
        super(new GuiBookContainer());
    }

    public ArrayList<DrawableRecipe> getRecipesFor(ArrayList<DrawableRecipe> recipes, ItemStack stack) {
        ArrayList<DrawableRecipe> ret = new ArrayList<DrawableRecipe>();
        for (DrawableRecipe recipe : recipes){
            ItemStack output = recipe.getOutput();
            if(output.isItemEqual(stack) || (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE && stack.getItem() == output.getItem())) ret.add(recipe);
        }
        return ret;
    }

    public ArrayList<DrawableRecipe> getRecipesWith(ArrayList<DrawableRecipe> recipes, ItemStack stack) {
        ArrayList<DrawableRecipe> ret = new ArrayList<DrawableRecipe>();
        for (DrawableRecipe recipe : recipes)
            for (ItemStack input : recipe.getInput())
                if (input != null)
                    if (input.isItemEqual(stack) || (input.getItemDamage() == OreDictionary.WILDCARD_VALUE && input.getItem() == stack.getItem())) {
                        ret.add(recipe);
                        break;
                    }
        return ret;
    }

    public void openRecipeState(ItemStack stack) {
        if(state != null && state instanceof StateRecipe) {
            StateRecipe r = (StateRecipe) state;
            if (r.cmd.equals("recipe") && r.arg instanceof ItemStack)
                if (((ItemStack) r.arg).isItemEqual(stack)) return;
        }
        if(state != null && state.lastState instanceof StateRecipe) {
            StateRecipe r = (StateRecipe) state.lastState;
            if (r.cmd.equals("recipe") && r.arg instanceof ItemStack)
                if (((ItemStack) r.arg).isItemEqual(stack)){
                    state = state.lastState;
                    return;
                }
        }

        LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>> handlers = new LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>>();
        for (Map.Entry<RecipeHandler, ArrayList<DrawableRecipe>> entry : RecipeManager.loadedRecipes.entrySet()){
            ArrayList<DrawableRecipe> recipes = getRecipesFor(entry.getValue(), stack);
            if(recipes.size() > 0) handlers.put(entry.getKey(), recipes);
        }

        if(handlers.size() > 0) state = new StateRecipe(state, handlers, "recipe", stack);
    }

    public void openUsageState(ItemStack stack) {
        if(state != null && state instanceof StateRecipe) {
            StateRecipe r = (StateRecipe) state;
            if (r.cmd.equals("usage") && r.arg instanceof ItemStack)
                if (((ItemStack) r.arg).isItemEqual(stack)) return;
        }
        if(state != null && state.lastState instanceof StateRecipe) {
            StateRecipe r = (StateRecipe) state.lastState;
            if (r.cmd.equals("usage") && r.arg instanceof ItemStack)
                if (((ItemStack) r.arg).isItemEqual(stack)){
                    state = state.lastState;
                    return;
                }
        }

        LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>> handlers = new LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>>();
        for (Map.Entry<RecipeHandler, ArrayList<DrawableRecipe>> entry : RecipeManager.loadedRecipes.entrySet()){
            ArrayList<DrawableRecipe> recipes = getRecipesWith(entry.getValue(), stack);
            if(recipes.size() > 0) handlers.put(entry.getKey(), recipes);
        }

        if(handlers.size() > 0) state = new StateRecipe(state, handlers, "usage", stack);
    }

    public void openHandlerUsageState(RecipeHandler handler) {
        if(state != null && state instanceof StateRecipe) {
            StateRecipe r = (StateRecipe) state;
            if (r.cmd.equals("recipe") && r.arg == handler) return;
        }
        if(state != null && state.lastState instanceof StateRecipe) {
            StateRecipe r = (StateRecipe) state.lastState;
            if (r.cmd.equals("recipe") && r.arg == handler){
                state = state.lastState;
                return;
            }
        }

        LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>> handlers = new LinkedHashMap<RecipeHandler, ArrayList<DrawableRecipe>>();
        handlers.put(handler, RecipeManager.loadedRecipes.get(handler));
        state = new StateRecipe(state, handlers, "usage", handler);
    }

    public RenderItem getRenderItem() { return itemRender; }
    public FontRenderer getFontRenderer() { return fontRendererObj; }
    public void drawHoveringString(String s, int x, int y) { drawHoveringText(Arrays.asList(s), x, y); }

    @Override
    public void initGui() {
        super.initGui();

        left = (width - bookImageWidth) / 2;
        top = (height - bookImageHeight) / 2;


        if(onOpenCmd != null && onOpenArg != null) {
            if (onOpenCmd.equals("recipe") ) {
                if (onOpenArg instanceof ItemStack) openRecipeState((ItemStack) onOpenArg);
            } else if (onOpenCmd.equals("usage")) {
                if (onOpenArg instanceof ItemStack) openUsageState((ItemStack) onOpenArg);
                else if (onOpenArg instanceof RecipeHandler) openHandlerUsageState((RecipeHandler) onOpenArg);
            }

            onOpenCmd = null;
            onOpenArg = null;
        }
        else state = new StateHome();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(bookGuiTextures);
        RenderHelper.disableStandardItemLighting();
        drawTexturedModalRect(left, top - 10, 0, 0, bookImageWidth, bookImageHeight);

        state.draw(mouseX, mouseY);

        if(state.lastState != null) {
            mc.getTextureManager().bindTexture(bookGuiTextures);
            RenderHelper.disableStandardItemLighting();
            int x = left + 28;
            int y = top - 6;

            if(x < mouseX && mouseX < x + 18 && y < mouseY && mouseY < y + 10){
                drawTexturedModalRect(x, y, 26, 220, 18, 10);
                drawHoveringText(Arrays.asList(I18n.translateToLocal("guideBook.back")), mouseX, mouseY);
            }
            else drawTexturedModalRect(x, y, 3, 220, 18, 10);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        state.drawForeground(mouseX, mouseY);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        state.update();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(!state.keyTyped(typedChar, keyCode)) super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        state.mouseClicked(mouseX, mouseY, mouseButton);

        if(state.lastState != null) {
            int x = left + 28;
            int y = top - 6;

            if(x < mouseX && mouseX < x + 18 && y < mouseY && mouseY < y + 10) state = state.lastState;
        }
    }
}
