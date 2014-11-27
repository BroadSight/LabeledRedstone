package com.minercraftstyle.labeledredstone.init;

import com.minercraftstyle.labeledredstone.reference.Reference;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    //put items here

    public static void init()
    {

        LogHelper.info("Item Initialization Complete");
    }
}
