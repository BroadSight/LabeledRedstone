package com.minercraftstyle.labeledredstone.init;

import com.minercraftstyle.labeledredstone.item.ItemLR;
import com.minercraftstyle.labeledredstone.item.ItemLabeledLever;
import com.minercraftstyle.labeledredstone.reference.Reference;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    //put items here
    public static final ItemLR test = new ItemLR("test");
    public static final ItemLabeledLever labeled_lever = new ItemLabeledLever();

    public static void init()
    {
        GameRegistry.registerItem(test, "test");
        GameRegistry.registerItem(labeled_lever, "labeled_lever");

        LogHelper.info("Item Initialization Complete");
    }
}
