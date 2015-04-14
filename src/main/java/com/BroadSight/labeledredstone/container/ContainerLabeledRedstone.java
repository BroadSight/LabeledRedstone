package com.BroadSight.labeledredstone.container;

import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.MathHelper;

public class ContainerLabeledRedstone extends Container
{
    private TELabeledRedstone teLabeledRedstone;

    public ContainerLabeledRedstone(EntityPlayer player, TELabeledRedstone tile)
    {
        int rotation;

        teLabeledRedstone = tile;

        teLabeledRedstone.setPlayer(player);

        rotation = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
        teLabeledRedstone.setRotation(rotation);

        teLabeledRedstone.markDirty();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}
