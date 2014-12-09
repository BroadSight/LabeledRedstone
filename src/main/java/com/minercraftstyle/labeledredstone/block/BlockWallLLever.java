package com.minercraftstyle.labeledredstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallLLever extends BlockLabeledLever
{
    public static final PropertyDirection FACING_PROP = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockWallLLever()
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING_PROP, EnumFacing.NORTH));
    }

    public void setBlockBoundsBasedOnState(IBlockAccess access, BlockPos pos)
    {
        EnumFacing enumFacing = (EnumFacing)access.getBlockState(pos).getValue(FACING_PROP);
        float f  = 0.28125F,
              f1 = 0.78125F,
              f2 = 0.0F,
              f3 = 1.0F,
              f4 = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        switch (BlockWallLLever.SwitchEnumFacing.enumOrdinals[enumFacing.ordinal()])
        {
            case 1:
                this.setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
                break;
            case 2:
                this.setBlockBounds(f2, f, 0.0F, f3, f1, f4);
                break;
            case 3:
                this.setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
                break;
            case 4:
                this.setBlockBounds(0.0F, f, f2, f4, f1, f3);
        }
    }

    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        EnumFacing enumFacing = (EnumFacing)state.getValue(FACING_PROP);

        if (!worldIn.getBlockState(pos.offset(enumFacing.getOpposite())).getBlock().getMaterial().isSolid())
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }

        super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumFacing = EnumFacing.getFront(meta);

        if (enumFacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumFacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING_PROP, enumFacing);
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING_PROP)).getIndex();
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING_PROP});
    }

    static final class SwitchEnumFacing
        {
            static final int[] enumOrdinals = new int[EnumFacing.values().length];

            static
            {
                try
                {
                    enumOrdinals[EnumFacing.NORTH.ordinal()] = 1;
                }
                catch (NoSuchFieldError var4)
                {
                    ;
                }

                try
                {
                    enumOrdinals[EnumFacing.SOUTH.ordinal()] = 2;
                }
                catch (NoSuchFieldError var3)
                {
                    ;
                }

                try
                {
                    enumOrdinals[EnumFacing.WEST.ordinal()] = 3;
                }
                catch (NoSuchFieldError var2)
                {
                    ;
                }

                try
                {
                    enumOrdinals[EnumFacing.EAST.ordinal()] = 4;
                }
                catch (NoSuchFieldError var1)
                {
                    ;
                }
            }
        }
}
