package com.minercraftstyle.labeledredstone.creativetab;

import com.minercraftstyle.labeledredstone.reference.Reference;
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
