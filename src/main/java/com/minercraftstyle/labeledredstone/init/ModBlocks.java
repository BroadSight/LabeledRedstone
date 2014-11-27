package com.minercraftstyle.labeledredstone.init;

import com.minercraftstyle.labeledredstone.reference.Reference;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    //put blocks here

    public static void init()
    {

        LogHelper.info("Block Initialization Complete");
    }
}
