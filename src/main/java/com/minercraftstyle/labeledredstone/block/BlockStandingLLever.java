package com.minercraftstyle.labeledredstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import java.util.Iterator;

public class BlockStandingLLever extends BlockLabeledLever
{
    public static final PropertyInteger ROTATION_PROP = PropertyInteger.create("rotation", 0, 15);
    public static final PropertyEnum FACING_PROP = PropertyEnum.create("facing", BlockStandingLLever.EnumOrientation.class);
    public static final PropertyBool POWERED_PROP = PropertyBool.create("powered");

    public BlockStandingLLever()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION_PROP, Integer.valueOf(0)).withProperty(FACING_PROP, BlockStandingLLever.EnumOrientation.UP_X).withProperty(POWERED_PROP, Boolean.valueOf(false)));
    }

    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!worldIn.getBlockState(pos.offsetDown()).getBlock().getMaterial().isSolid() || !worldIn.getBlockState(pos.offsetUp()).getBlock().getMaterial().isSolid())
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }

        super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ROTATION_PROP, Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(ROTATION_PROP)).intValue();
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {ROTATION_PROP, FACING_PROP, POWERED_PROP});
    }

    /*
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP || side == EnumFacing.DOWN;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isSideSolid(pos.offsetDown(), EnumFacing.UP) ||
               worldIn.isSideSolid(pos.offsetUp(),   EnumFacing.DOWN);
    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(POWERED_PROP, Boolean.valueOf(false));

        if (worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing))
        {
            return iblockstate.withProperty(FACING_PROP, BlockStandingLLever.EnumOrientation.getState(facing, placer.func_174811_aO(), placer.isSneaking()));
        }
        else
            return iblockstate;
    }
    */

    public static enum EnumOrientation implements IStringSerializable
    {
        UP_X(0, "up_x", EnumFacing.UP),
        UP_Z(1, "up_z", EnumFacing.UP),
        DOWN_X(2, "down_x", EnumFacing.DOWN),
        DOWN_Z(3, "down_z", EnumFacing.DOWN);
        private static final BlockStandingLLever.EnumOrientation[] instance = new BlockStandingLLever.EnumOrientation[values().length];
        private final int index;
        private final String name;
        private final EnumFacing direction;

        private EnumOrientation(int index, String name, EnumFacing direction)
        {
            this.index = index;
            this.name = name;
            this.direction = direction;
        }

        public int getIndex()
        {
            return this.index;
        }

        public String getName()
        {
            return this.name;
        }

        public EnumFacing getDirection()
        {
            return this.direction;
        }

        public String toString()
        {
            return this.name;
        }

        public static BlockStandingLLever.EnumOrientation getDirectionFromIndex(int index)
        {
            if (index < 0 || index >= instance.length)
            {
                index = 0;
            }

            return instance[index];
        }

        public static BlockStandingLLever.EnumOrientation getState(EnumFacing blockFacing, EnumFacing playerFacing, boolean playerSneaking)
        {
            switch (BlockStandingLLever.SwitchEnumFacing.FACING_LOOKUP[blockFacing.ordinal()])
            {
                case 1:
                    if (playerSneaking)
                    {
                        switch (BlockStandingLLever.SwitchEnumFacing.AXIS_LOOKUP[playerFacing.getAxis().ordinal()])
                        {
                            case 1:
                                return DOWN_Z;
                            case 2:
                                return DOWN_X;
                            default:
                                throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + "for facing " + blockFacing);
                        }
                    }
                    else
                    {
                        switch (BlockStandingLLever.SwitchEnumFacing.AXIS_LOOKUP[playerFacing.getAxis().ordinal()])
                        {
                            case 1:
                                return DOWN_X;
                            case 2:
                                return DOWN_Z;
                            default:
                                throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + "for facing " + blockFacing);
                        }
                    }
                case 2:
                    if (playerSneaking)
                    {
                        switch (BlockStandingLLever.SwitchEnumFacing.AXIS_LOOKUP[playerFacing.getAxis().ordinal()])
                        {
                            case 1:
                                return UP_Z;
                            case 2:
                                return UP_X;
                            default:
                                throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + "for facing " + blockFacing);
                        }
                    }
                    else
                    {
                        switch (BlockStandingLLever.SwitchEnumFacing.AXIS_LOOKUP[playerFacing.getAxis().ordinal()])
                        {
                            case 1:
                                return UP_X;
                            case 2:
                                return UP_Z;
                            default:
                                throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + "for facing " + blockFacing);
                        }
                    }
                default:
                    throw new IllegalArgumentException("Invalid facing: " + blockFacing);
            }
        }

        static
        {
            BlockStandingLLever.EnumOrientation[] var0 = values();

            for (int i = 0; i < var0.length; ++i)
            {
                BlockStandingLLever.EnumOrientation var3 = var0[i];
                instance[var3.getIndex()] = var3;
            }
        }
    }

    static final class SwitchEnumFacing
    {
        static final int[] FACING_LOOKUP;

        static final int[] ORIENTATION_LOOKUP;

        static final int[] AXIS_LOOKUP = new int[EnumFacing.Axis.values().length];

        static
        {
            try
            {
                AXIS_LOOKUP[EnumFacing.Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError err1)
            {
                ;
            }

            try
            {
                AXIS_LOOKUP[EnumFacing.Axis.Z.ordinal()] = 2;
            }
            catch (NoSuchFieldError err2)
            {
                ;
            }

            ORIENTATION_LOOKUP = new int[BlockStandingLLever.EnumOrientation.values().length];

            try
            {
                ORIENTATION_LOOKUP[BlockStandingLLever.EnumOrientation.UP_Z.ordinal()] = 5;
            }
            catch (NoSuchFieldError err3)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockStandingLLever.EnumOrientation.UP_X.ordinal()] = 6;
            }
            catch (NoSuchFieldError err4)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockStandingLLever.EnumOrientation.DOWN_X.ordinal()] = 7;
            }
            catch (NoSuchFieldError err5)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockStandingLLever.EnumOrientation.DOWN_Z.ordinal()] = 8;
            }
            catch (NoSuchFieldError err6)
            {
                ;
            }

            FACING_LOOKUP = new int[EnumFacing.values().length];

            try
            {
                FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError err7)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError err8)
            {
                ;
            }
        }
    }
}