package com.BroadSight.labeledredstone.block;

import com.BroadSight.labeledredstone.init.ModItems;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockLabeledButton extends BlockLRContainer
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    private final boolean wooden;

    public BlockLabeledButton(boolean wooden)
    {
        super();
        this.setTickRandomly(true);
        this.wooden = wooden;
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TELabeledRedstone();
    }

    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

    public int tickRate(World worldIn)
    {
        return this.wooden ? 30 : 20;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean isFullCube()
    {
        return false;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if (this.wooden)
            return ModItems.labeled_button_wood;
        else
            return ModItems.labeled_button_stone;
    }

    public Item getItem(World worldIn, BlockPos pos)
    {
        if (this.wooden)
            return ModItems.labeled_button_wood;
        else
            return ModItems.labeled_button_stone;
    }

    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return worldIn.isSideSolid(pos.offset(side.getOpposite()), side, true);
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        EnumFacing[] aenumfacing = EnumFacing.values();
        int i = aenumfacing.length;

        for (int j = 0; j < i; ++j)
        {
            EnumFacing enumfacing = aenumfacing[j];

            if (worldIn.isSideSolid(pos.offset(enumfacing), enumfacing.getOpposite(), true))
            {
                return true;
            }
        }

        return false;
    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing, true) ? this.getDefaultState().withProperty(FACING, facing).withProperty(POWERED, false) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN).withProperty(POWERED, false);
    }

    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (this.checkForDrop(worldIn, pos, state))
        {
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (!worldIn.isSideSolid(pos.offset(enumfacing.getOpposite()), enumfacing, true))
            {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
        }
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canPlaceBlockAt(worldIn, pos))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
        else
        {
            return true;
        }
    }

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        this.updateBlockBounds(worldIn.getBlockState(pos));
    }

    private void updateBlockBounds(IBlockState state)
    {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
        boolean flag = (Boolean) state.getValue(POWERED);
        float f2 = (float)(flag ? 1 : 2) / 16.0F;
        float f3 = 0.125F;

        switch (BlockLabeledButton.SwitchEnumFacing.FACING_LOOKUP[enumfacing.ordinal()])
        {
            case 1:
                this.setBlockBounds(0.0F, f3, 0.3125F, f2, 0.5F - f3, 0.6875F);
                break;
            case 2:
                this.setBlockBounds(1.0F - f2, f3, 0.3125F, 1.0F, 0.5F - f3, 0.6875F);
                break;
            case 3:
                this.setBlockBounds(0.3125F, f3, 0.0F, 0.6875F, 0.5F - f3, f2);
                break;
            case 4:
                this.setBlockBounds(0.3125F, f3, 1.0F - f2, 0.6875F, 0.5F - f3, 1.0F);
                break;
            case 5:
                this.setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
                break;
            case 6:
                this.setBlockBounds(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if ((Boolean) state.getValue(POWERED))
        {
            return true;
        }
        else
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, true), 3);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
            this.notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue(FACING));
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
            return true;
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if ((Boolean) state.getValue(POWERED))
        {
            this.notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue(FACING));
        }

        super.breakBlock(worldIn, pos, state);
    }

    public int isProvidingWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return ((Boolean) state.getValue(POWERED)) ? 15 : 0;
    }

    public int isProvidingStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return !((Boolean) state.getValue(POWERED)) ? 0 : (state.getValue(FACING) == side ? 15 : 0);
    }

    public boolean canProvidePower()
    {
        return true;
    }

    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if ((Boolean) state.getValue(POWERED))
            {
                if (this.wooden)
                {
                    this.checkForArrows(worldIn, pos, state);
                }
                else
                {
                    worldIn.setBlockState(pos, state.withProperty(POWERED, false));
                    this.notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue(FACING));
                    worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
                    worldIn.markBlockRangeForRenderUpdate(pos, pos);
                }
            }
        }
    }

    public void setBlockBoundsForItemRender()
    {
        float f = 0.1875F;
        float f1 = 0.125F;
        float f2 = 0.125F;
        this.setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!worldIn.isRemote)
        {
            if (this.wooden)
            {
                if (!(Boolean) state.getValue(POWERED))
                {
                    this.checkForArrows(worldIn, pos, state);
                }
            }
        }
    }

    private void checkForArrows(World worldIn, BlockPos pos, IBlockState state)
    {
        this.updateBlockBounds(state);
        List list = worldIn.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB((double)pos.getX() + this.minX, (double)pos.getY() + this.minY, (double)pos.getZ() + this.minZ, (double)pos.getX() + this.maxX, (double)pos.getY() + this.maxY, (double)pos.getZ() + this.maxZ));
        boolean flag = !list.isEmpty();
        boolean flag1 = (Boolean) state.getValue(POWERED);

        if (flag && !flag1)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, true));
            this.notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue(FACING));
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!flag && flag1)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, false));
            this.notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue(FACING));
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (flag)
        {
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
        }
    }

    private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing)
    {
        worldIn.notifyNeighborsOfStateChange(pos, this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing;

        switch (meta & 7)
        {
            case 0:
                enumfacing = EnumFacing.DOWN;
                break;
            case 1:
                enumfacing = EnumFacing.EAST;
                break;
            case 2:
                enumfacing = EnumFacing.WEST;
                break;
            case 3:
                enumfacing = EnumFacing.SOUTH;
                break;
            case 4:
                enumfacing = EnumFacing.NORTH;
                break;
            case 5:
            default:
                enumfacing = EnumFacing.UP;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(POWERED, ((meta & 8) > 0));
    }

    public int getMetaFromState(IBlockState state)
    {
        int i;

        switch (BlockLabeledButton.SwitchEnumFacing.FACING_LOOKUP[((EnumFacing)state.getValue(FACING)).ordinal()])
        {
            case 1:
                i = 1;
                break;
            case 2:
                i = 2;
                break;
            case 3:
                i = 3;
                break;
            case 4:
                i = 4;
                break;
            case 5:
            default:
                i = 5;
                break;
            case 6:
                i = 0;
        }

        if ((Boolean) state.getValue(POWERED))
        {
            i |= 8;
        }

        return i;
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING, POWERED});
    }

    static final class SwitchEnumFacing
    {
        static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];
        private static final String __OBFID = "CL_00002131";

        static
        {
            try
            {
                FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError var6)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError var5)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.UP.ordinal()] = 5;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 6;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
}
