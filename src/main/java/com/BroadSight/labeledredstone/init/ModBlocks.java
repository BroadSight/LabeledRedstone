package com.BroadSight.labeledredstone.init;

import com.BroadSight.labeledredstone.block.BlockLabeledButtonStone;
import com.BroadSight.labeledredstone.block.BlockLabeledButtonWood;
import com.BroadSight.labeledredstone.block.BlockLabeledLever;
import com.BroadSight.labeledredstone.reference.Reference;
import com.BroadSight.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    //put blocks here
    public static final BlockLabeledLever block_labeled_lever = new BlockLabeledLever();
    public static final BlockLabeledButtonWood block_labeled_button_wood = new BlockLabeledButtonWood();
    public static final BlockLabeledButtonStone block_labeled_button_stone = new BlockLabeledButtonStone();

    public static void init()
    {
        GameRegistry.registerBlock(block_labeled_lever, "block_labeled_lever");
        GameRegistry.registerBlock(block_labeled_button_wood, "block_labeled_button_wood");
        GameRegistry.registerBlock(block_labeled_button_stone, "block_labeled_button_stone");

        LogHelper.info("Block Initialization Complete");
    }
}
