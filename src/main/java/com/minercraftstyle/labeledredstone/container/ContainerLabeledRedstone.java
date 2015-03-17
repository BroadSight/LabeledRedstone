package com.minercraftstyle.labeledredstone.container;

import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.MathHelper;

public class ContainerLabeledRedstone extends Container
{
    private TELabeledRedstone teLabeledRedstone;

    public ContainerLabeledRedstone(EntityPlayer player, TELabeledRedstone tile)
    {
        int rotation;

        LogHelper.info("Container constructor");

        LogHelper.info(tile);
        teLabeledRedstone = tile;

        LogHelper.info(player);
        teLabeledRedstone.setPlayer(player);

        rotation = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
        LogHelper.info(rotation);
        teLabeledRedstone.setRotation(rotation);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}
