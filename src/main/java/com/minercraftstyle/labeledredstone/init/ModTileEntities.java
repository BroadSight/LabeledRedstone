package com.minercraftstyle.labeledredstone.init;

import com.minercraftstyle.labeledredstone.reference.Reference;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledLever;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModTileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TELabeledLever.class, "te_labeled_lever");

        LogHelper.info("Tile Entity Initialization Complete");
    }
}
