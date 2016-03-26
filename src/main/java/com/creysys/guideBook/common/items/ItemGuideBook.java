package com.creysys.guideBook.common.items;

import com.creysys.guideBook.GuideBookMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Creysys on 20 Mar 16.
 */
public class ItemGuideBook extends Item {
    public ItemGuideBook(){
        super();

        String name = "guideBook";

        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.tabMisc);

        GameRegistry.registerItem(this);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        if(world.isRemote) player.openGui(GuideBookMod.instance, GuideBookMod.GuiId.GuideBook, world, 0, 0, 0);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
    }


}
