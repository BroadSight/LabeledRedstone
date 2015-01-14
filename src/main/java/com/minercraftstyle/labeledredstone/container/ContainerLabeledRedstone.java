package com.minercraftstyle.labeledredstone.container;

import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerLabeledRedstone extends Container
{
    private TELabeledRedstone teLabeledRedstone;

    public ContainerLabeledRedstone(EntityPlayer player, TELabeledRedstone tile)
    {
        teLabeledRedstone = tile;

        teLabeledRedstone.setPlayer(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}
