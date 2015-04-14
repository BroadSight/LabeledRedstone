package com.BroadSight.labeledredstone.init;

import com.BroadSight.labeledredstone.reference.Reference;
import com.BroadSight.labeledredstone.client.model.ModelHelper;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Models
{
    // Items
    public static final Item item_labeled_lever = GameRegistry.findItem(Reference.MOD_ID, "labeled_lever");
    public static final Item item_labeled_button_wood = GameRegistry.findItem(Reference.MOD_ID, "labeled_button_wood");
    public static final Item item_labeled_button_stone = GameRegistry.findItem(Reference.MOD_ID, "labeled_button_stone");

    // Blocks

    public static void init()
    {
        // Items
        ModelHelper.registerItem(item_labeled_lever, "labeled_lever");
        ModelHelper.registerItem(item_labeled_button_wood, "labeled_button_wood");
        ModelHelper.registerItem(item_labeled_button_stone, "labeled_button_stone");

        // Blocks
    }
}