package com.creysys.guideBook.api;

import java.util.ArrayList;

/**
 * Created by Creysys on 21 Mar 16.
 */
public abstract class RecipeHandler {
    public abstract String getName();
    public abstract Object getTabIcon();
    public abstract int recipesPerPage();
    public abstract ArrayList<DrawableRecipe> getRecipes();
}
