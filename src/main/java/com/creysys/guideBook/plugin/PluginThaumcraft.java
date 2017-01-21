package com.creysys.guideBook.plugin;

import com.creysys.guideBook.api.ItemInfoManager;

/**
 * Created by Creysys on 06 Apr 16.
 */
public final class PluginThaumcraft {
    public static void preInit() {
        ItemInfoManager.setItemInfo("thaumcraft", "thaumonomicon", 0, "guideBook.info.thaumonomicon");
    }
}
