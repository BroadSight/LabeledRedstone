package com.BroadSight.labeledredstone.init;

import com.BroadSight.labeledredstone.reference.Reference;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import com.BroadSight.labeledredstone.util.LogHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModTileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TELabeledRedstone.class, "te_labeled_redstone");

        LogHelper.info("Tile Entity Initialization Complete");
    }
}
