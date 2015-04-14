package com.BroadSight.labeledredstone.init;

import com.BroadSight.labeledredstone.item.ItemLabeledLever;
import com.BroadSight.labeledredstone.item.ItemLabeledButtonStone;
import com.BroadSight.labeledredstone.item.ItemLabeledButtonWood;
import com.BroadSight.labeledredstone.reference.Reference;
import com.BroadSight.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    //put items here
    public static final ItemLabeledLever labeled_lever = new ItemLabeledLever();
    public static final ItemLabeledButtonWood labeled_button_wood = new ItemLabeledButtonWood();
    public static final ItemLabeledButtonStone labeled_button_stone =  new ItemLabeledButtonStone();

    public static void init()
    {
        GameRegistry.registerItem(labeled_lever, "labeled_lever");
        GameRegistry.registerItem(labeled_button_wood, "labeled_button_wood");
        GameRegistry.registerItem(labeled_button_stone, "labeled_button_stone");

        LogHelper.info("Item Initialization Complete");
    }
}
