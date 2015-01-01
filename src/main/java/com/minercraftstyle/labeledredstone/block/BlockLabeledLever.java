package com.minercraftstyle.labeledredstone.block;

import com.minercraftstyle.labeledredstone.init.ModItems;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockLabeledLever extends BlockLRContainer
{
    protected BlockLabeledLever()
    {
        super();
        float f = 0.25F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
    }

    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
    {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        return super.getSelectedBoundingBox(worldIn, pos);
    }

    public boolean isFullCube()
    {
        return false;
    }

    public boolean isPassable(IBlockAccess blockAccess, BlockPos pos)
    {
        return true;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TELabeledLever();
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ModItems.labeled_lever;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            return tileentity instanceof TELabeledLever ? ((TELabeledLever)tileentity).guiEventHandler(playerIn) : false;
        }
    }


    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return ModItems.labeled_lever;
    }
}
