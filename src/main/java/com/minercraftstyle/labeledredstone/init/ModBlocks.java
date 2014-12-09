package com.minercraftstyle.labeledredstone.init;

import com.minercraftstyle.labeledredstone.block.BlockStandingLLever;
import com.minercraftstyle.labeledredstone.block.BlockWallLLever;
import com.minercraftstyle.labeledredstone.reference.Reference;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    //put blocks here
    public static final BlockStandingLLever standing_llever = new BlockStandingLLever();
    public static final BlockWallLLever wall_llever = new BlockWallLLever();

    public static void init()
    {
        GameRegistry.registerBlock(standing_llever, "standing_llever");
        GameRegistry.registerBlock(wall_llever, "wall_llever");

        LogHelper.info("Block Initialization Complete");
    }
}
