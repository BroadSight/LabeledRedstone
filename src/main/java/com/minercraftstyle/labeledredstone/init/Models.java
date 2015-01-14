package com.minercraftstyle.labeledredstone.init;

import com.minercraftstyle.labeledredstone.client.model.ModelHelper;
import com.minercraftstyle.labeledredstone.reference.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Models
{
    // Items
    public static final Item item_labeled_lever = GameRegistry.findItem(Reference.MOD_ID, "labeled_lever");

    // Blocks

    public static void init()
    {
        // Items
        ModelHelper.registerItem(item_labeled_lever, "labeled_lever");

        // Blocks
    }
}