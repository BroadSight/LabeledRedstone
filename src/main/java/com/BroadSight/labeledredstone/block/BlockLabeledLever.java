package com.BroadSight.labeledredstone.block;

import com.BroadSight.labeledredstone.init.ModItems;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
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
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING_PROP, BlockLabeledLever.EnumOrientation.UP_X).withProperty(POWERED_PROP, false));
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

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        float f = 0.1875F;
        float f1 = 0.0625F;

        switch ((BlockLabeledLever.EnumOrientation)worldIn.getBlockState(pos).getValue(FACING_PROP))
        {
            case EAST:
                this.setBlockBounds(0.0F, f1, 0.2F, f * 2.0F, 0.5F - f1, 0.8F);
                break;
            case WEST:
                this.setBlockBounds(1.0F - f * 2.0F, f1, 0.2F, 1.0F,  0.5F - f1, 0.8F);
                break;
            case SOUTH:
                this.setBlockBounds(0.2F, f1, 0.0F, 0.8F,  0.5F - f1, f * 2.0F);
                break;
            case NORTH:
                this.setBlockBounds(0.2F, f1, 1.0F - f * 2.0F, 0.8F,  0.5F - f1, 1.0F);
                break;
            case DOWN_X:
            case DOWN_Z:
                f = 0.25F;
                this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
                break;
            case UP_X:
            case UP_Z:
                f = 0.25F;
                this.setBlockBounds(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
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
        TELabeledRedstone tile = new TELabeledRedstone();
        tile.setWorldObj(worldIn);
        return tile;
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
        return this.getDefaultState().withProperty(FACING_PROP, BlockLabeledLever.EnumOrientation.getDirectionFromIndex(meta & 7)).withProperty(POWERED_PROP, (meta & 8) > 0);
    }

    public int getMetaFromState(IBlockState state)
    {
        byte b = 0;
        int meta = b | ((BlockLabeledLever.EnumOrientation)state.getValue(FACING_PROP)).getIndex();

        if (state.getValue(POWERED_PROP))
        {
            meta |= 8;
        }
        return meta;
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, FACING_PROP, POWERED_PROP);
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isSideSolid(pos.offset(EnumFacing.DOWN),  EnumFacing.UP)    ||
               worldIn.isSideSolid(pos.offset(EnumFacing.UP),    EnumFacing.DOWN)  ||
               worldIn.isSideSolid(pos.offset(EnumFacing.NORTH), EnumFacing.SOUTH) ||
               worldIn.isSideSolid(pos.offset(EnumFacing.SOUTH), EnumFacing.NORTH) ||
               worldIn.isSideSolid(pos.offset(EnumFacing.EAST),  EnumFacing.WEST)  ||
               worldIn.isSideSolid(pos.offset(EnumFacing.WEST),  EnumFacing.EAST);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
        {
            return false;
        }
        else
        {
            state = state.cycleProperty(POWERED_PROP);
            worldIn.setBlockState(pos, state, 3);
            worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.click", 0.3F, state.getValue(POWERED_PROP) ? 0.6F : 0.5F);
            worldIn.notifyNeighborsOfStateChange(pos, this);
            EnumFacing enumFacing = ((BlockLabeledLever.EnumOrientation)state.getValue(FACING_PROP)).getDirection();
            worldIn.notifyNeighborsOfStateChange(pos.offset(enumFacing.getOpposite()), this);

            TileEntity tile = worldIn.getTileEntity(pos);
            return tile instanceof TELabeledRedstone && ((TELabeledRedstone)tile).executeCommand(playerIn);
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getValue(POWERED_PROP))
        {
            worldIn.notifyNeighborsOfStateChange(pos, this);
            EnumFacing enumFacing = ((BlockLabeledLever.EnumOrientation)state.getValue(FACING_PROP)).getDirection();
            worldIn.notifyNeighborsOfStateChange(pos.offset(enumFacing.getOpposite()), this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    public int isProvidingWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return state.getValue(POWERED_PROP) ? 15 : 0;
    }

    public int isProvidingStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return !state.getValue(POWERED_PROP) ? 0 : (((BlockLabeledLever.EnumOrientation)state.getValue(FACING_PROP)).getDirection() == side ? 15 : 0);
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
            switch (blockFacing)
            {
                case DOWN:

                    switch (playerFacing.getAxis())
                    {
                        case X:
                            return playerSneaking ? DOWN_Z : DOWN_X;
                        case Z:
                            return playerSneaking ? DOWN_X : DOWN_Z;
                        default:
                            throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + " for facing " + blockFacing);
                    }

                case UP:

                    switch (playerFacing.getAxis())
                    {
                        case X:
                            return playerSneaking ? UP_Z : UP_X;
                        case Z:
                            return playerSneaking ? UP_X : UP_Z;
                        default:
                            throw new IllegalArgumentException("Invalid entityFacing " + playerFacing + " for facing " + blockFacing);
                    }

                case NORTH:
                    return NORTH;
                case SOUTH:
                    return SOUTH;
                case WEST:
                    return WEST;
                case EAST:
                    return EAST;
                default:
                    throw new IllegalArgumentException("Invalid facing: " + blockFacing);
            }
        }

        static
        {
            for (BlockLabeledLever.EnumOrientation blocklabeledlever$enumorientation : values())
            {
                instance[blocklabeledlever$enumorientation.getIndex()] = blocklabeledlever$enumorientation;
            }
        }
    }
}