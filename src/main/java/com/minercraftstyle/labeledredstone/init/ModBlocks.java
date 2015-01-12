package com.minercraftstyle.labeledredstone.init;

import com.minercraftstyle.labeledredstone.block.BlockLabeledLever;
import com.minercraftstyle.labeledredstone.reference.Reference;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    //put blocks here
    public static final BlockLabeledLever block_labeled_lever = new BlockLabeledLever();

    public static void init()
    {
        GameRegistry.registerBlock(block_labeled_lever, "block_labeled_lever");

        LogHelper.info("Block Initialization Complete");
    }
}
