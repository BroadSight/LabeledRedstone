package com.minercraftstyle.labeledredstone.init;

import com.minercraftstyle.labeledredstone.item.ItemLR;
import com.minercraftstyle.labeledredstone.reference.Reference;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    //put items here
    public static final ItemLR test = new ItemLR("test");

    public static void init()
    {
        GameRegistry.registerItem(test, "test");

        LogHelper.info("Item Initialization Complete");
    }
}
