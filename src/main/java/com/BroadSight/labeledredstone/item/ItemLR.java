package com.BroadSight.labeledredstone.item;

import com.BroadSight.labeledredstone.creativetab.CreativeTabLR;
import com.BroadSight.labeledredstone.reference.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemLR extends Item
{
    public ItemLR()
    {
        super();
        this.setCreativeTab(CreativeTabLR.LR_TAB);
    }

    public ItemLR(String name)
    {
        super();
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabLR.LR_TAB);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    public static void registerItems()
    {

    }
}
