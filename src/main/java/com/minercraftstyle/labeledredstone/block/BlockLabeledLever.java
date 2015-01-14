package com.minercraftstyle.labeledredstone.block;

import com.minercraftstyle.labeledredstone.init.ModItems;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockLabeledLever extends BlockLRContainer
{
    public static final PropertyEnum FACING_PROP = PropertyEnum.create("facing", BlockLabeledLever.EnumOrientation.class);
    public static final PropertyBool POWERED_PROP = PropertyBool.create("powered");

    public BlockLabeledLever()
    {
        super();
        float f = 0.25F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING_PROP, BlockLabeledLever.EnumOrientation.UP_X).withProperty(POWERED_PROP, Boolean.valueOf(false)));
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

    public void setBlockBoundsBasedOnState(IBlockAccess access, BlockPos pos)
    {
        EnumFacing enumFacing = (EnumFacing)((EnumOrientation)access.getBlockState(pos).getValue(FACING_PROP)).getDirection();

        if (enumFacing == EnumFacing.UP || enumFacing == EnumFacing.DOWN)
        {
            float f = 0.5F,
                  f1 = 0.25F;
            this.setBlockBounds(f - f1, 0.0F, f - f1, f + f1, 1.0F, f + f1);
        }
        else
        {
            float f = 0.28125F,
                    f1 = 0.78125F,
                    f2 = 0.0F,
                    f3 = 1.0F,
                    f4 = 0.125F;
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

            switch (BlockLabeledLever.SwitchEnumFacing.FACING_LOOKUP[enumFacing.ordinal()])
            {
                case 3:
                    this.setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
                    break;
                case 4:
                    this.setBlockBounds(f2, f, 0.0F, f3, f1, f4);
                    break;
                case 5:
                    this.setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
                    break;
                case 6:
                    this.setBlockBounds(0.0F, f, f2, f4, f1, f3);
            }
        }
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
        return new TELabeledRedstone();
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ModItems.labeled_lever;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return ModItems.labeled_lever;
    }

    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        EnumFacing facing = ((EnumOrientation)state.getValue(FACING_PROP)).getDirection().getOpposite();

        if (!worldIn.isSideSolid(pos.offset(facing), facing.getOpposite()))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }

        super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING_PROP, BlockLabeledLever.EnumOrientation.getDirectionFromIndex(meta & 7)).withProperty(POWERED_PROP, Boolean.valueOf((meta & 8) > 0));
    }

    public int getMetaFromState(IBlockState state)
    {
        byte b = 0;
        int meta = b | ((BlockLabeledLever.EnumOrientation)state.getValue(FACING_PROP)).getIndex();

        if (((Boolean)state.getValue(POWERED_PROP)).booleanValue())
        {
            meta |= 8;
        }
        return meta;
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING_PROP, POWERED_PROP});
    }

    /*
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP || side == EnumFacing.DOWN;
    }
    */

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isSideSolid(pos.offset(EnumFacing.DOWN),  EnumFacing.UP)    ||
               worldIn.isSideSolid(pos.offset(EnumFacing.UP),    EnumFacing.DOWN)  ||
               worldIn.isSideSolid(pos.offset(EnumFacing.NORTH), EnumFacing.SOUTH) ||
               worldIn.isSideSolid(pos.offset(EnumFacing.SOUTH), EnumFacing.NORTH) ||
               worldIn.isSideSolid(pos.offset(EnumFacing.EAST),  EnumFacing.WEST)  ||
               worldIn.isSideSolid(pos.offset(EnumFacing.WEST),  EnumFacing.EAST);
    }

    /*
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

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
        {
            return true;
        }
        else
        {
            state = state.cycleProperty(POWERED_PROP);
            worldIn.setBlockState(pos, state, 3);
            worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.click", 0.3F, ((Boolean) state.getValue(POWERED_PROP)).booleanValue() ? 0.6F : 0.5F);
            worldIn.notifyNeighborsOfStateChange(pos, this);
            EnumFacing enumFacing = ((BlockLabeledLever.EnumOrientation)state.getValue(FACING_PROP)).getDirection();
            worldIn.notifyNeighborsOfStateChange(pos.offset(enumFacing.getOpposite()), this);
            return true;
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (((Boolean)state.getValue(POWERED_PROP)).booleanValue())
        {
            worldIn.notifyNeighborsOfStateChange(pos, this);
            EnumFacing enumFacing = ((BlockLabeledLever.EnumOrientation)state.getValue(FACING_PROP)).getDirection();
            worldIn.notifyNeighborsOfStateChange(pos.offset(enumFacing.getOpposite()), this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    public int isProvidingWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return ((Boolean)state.getValue(POWERED_PROP)).booleanValue() ? 15 : 0;
    }

    public int isProvidingStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return !((Boolean)state.getValue(POWERED_PROP)).booleanValue() ? 0 : (((BlockLabeledLever.EnumOrientation)state.getValue(FACING_PROP)).getDirection() == side ? 15 : 0);
    }

    public boolean canProvidePower()
    {
        return true;
    }

    public static enum EnumOrientation implements IStringSerializable
    {
        UP_X(0, "up_x", EnumFacing.UP),
        UP_Z(1, "up_z", EnumFacing.UP),
        DOWN_X(2, "down_x", EnumFacing.DOWN),
        DOWN_Z(3, "down_z", EnumFacing.DOWN),
        NORTH(4, "north", EnumFacing.NORTH),
        SOUTH(5, "south", EnumFacing.SOUTH),
        EAST(6, "east", EnumFacing.EAST),
        WEST(7, "west", EnumFacing.WEST);
        private static final BlockLabeledLever.EnumOrientation[] instance = new BlockLabeledLever.EnumOrientation[values().length];
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

        public static BlockLabeledLever.EnumOrientation getDirectionFromIndex(int index)
        {
            if (index < 0 || index >= instance.length)
            {
                index = 0;
            }

            return instance[index];
        }

        public static BlockLabeledLever.EnumOrientation getState(EnumFacing blockFacing, EnumFacing playerFacing, boolean playerSneaking)
        {
            switch (BlockLabeledLever.SwitchEnumFacing.FACING_LOOKUP[blockFacing.ordinal()])
            {
                case 1:
                    if (playerSneaking)
                    {
                        switch (BlockLabeledLever.SwitchEnumFacing.AXIS_LOOKUP[playerFacing.getAxis().ordinal()])
                        {
                            case 1:
                                return BlockLabeledLever.EnumOrientation.DOWN_Z;
                            case 2:
                                return BlockLabeledLever.EnumOrientation.DOWN_X;
                            default:
                                throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + "for facing " + blockFacing);
                        }
                    }
                    else
                    {
                        switch (BlockLabeledLever.SwitchEnumFacing.AXIS_LOOKUP[playerFacing.getAxis().ordinal()])
                        {
                            case 1:
                                return BlockLabeledLever.EnumOrientation.DOWN_X;
                            case 2:
                                return BlockLabeledLever.EnumOrientation.DOWN_Z;
                            default:
                                throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + "for facing " + blockFacing);
                        }
                    }
                case 2:
                    if (playerSneaking)
                    {
                        switch (BlockLabeledLever.SwitchEnumFacing.AXIS_LOOKUP[playerFacing.getAxis().ordinal()])
                        {
                            case 1:
                                return BlockLabeledLever.EnumOrientation.UP_Z;
                            case 2:
                                return BlockLabeledLever.EnumOrientation.UP_X;
                            default:
                                throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + "for facing " + blockFacing);
                        }
                    }
                    else
                    {
                        switch (BlockLabeledLever.SwitchEnumFacing.AXIS_LOOKUP[playerFacing.getAxis().ordinal()])
                        {
                            case 1:
                                return BlockLabeledLever.EnumOrientation.UP_X;
                            case 2:
                                return BlockLabeledLever.EnumOrientation.UP_Z;
                            default:
                                throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + "for facing " + blockFacing);
                        }
                    }
                case 3:
                    return NORTH;
                case 4:
                    return SOUTH;
                case 5:
                    return WEST;
                case 6:
                    return EAST;
                default:
                    throw new IllegalArgumentException("Invalid facing: " + blockFacing);
            }
        }

        static
        {
            BlockLabeledLever.EnumOrientation[] var0 = values();

            for (int i = 0; i < var0.length; ++i)
            {
                BlockLabeledLever.EnumOrientation var3 = var0[i];
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

            ORIENTATION_LOOKUP = new int[BlockLabeledLever.EnumOrientation.values().length];

            try
            {
                ORIENTATION_LOOKUP[BlockLabeledLever.EnumOrientation.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError err3)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockLabeledLever.EnumOrientation.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError err4)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockLabeledLever.EnumOrientation.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError err5)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockLabeledLever.EnumOrientation.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError err6)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockLabeledLever.EnumOrientation.UP_Z.ordinal()] = 5;
            }
            catch (NoSuchFieldError err7)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockLabeledLever.EnumOrientation.UP_X.ordinal()] = 6;
            }
            catch (NoSuchFieldError err8)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockLabeledLever.EnumOrientation.DOWN_X.ordinal()] = 7;
            }
            catch (NoSuchFieldError err9)
            {
                ;
            }

            try
            {
                ORIENTATION_LOOKUP[BlockLabeledLever.EnumOrientation.DOWN_Z.ordinal()] = 8;
            }
            catch (NoSuchFieldError err10)
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

            try
            {
                FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError err9)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError err10)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError err11)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError err12)
            {
                ;
            }
        }
    }
}