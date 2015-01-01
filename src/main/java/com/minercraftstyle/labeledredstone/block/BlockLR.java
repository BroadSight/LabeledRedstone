package com.minercraftstyle.labeledredstone.block;

import com.minercraftstyle.labeledredstone.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockLR extends Block
{
    public BlockLR(Material material)
    {
        super(material);
        this.setCreativeTab(null);
    }

    public BlockLR()
    {
        this(Material.circuits);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
