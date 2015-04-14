package com.BroadSight.labeledredstone.creativetab;

import com.BroadSight.labeledredstone.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabLR
{
    public static final CreativeTabs LR_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase())
    {
        @Override
        public Item getTabIconItem()
        {
            return Items.redstone;
        }
    };
}
