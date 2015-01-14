package com.minercraftstyle.labeledredstone.client.renderer;

import com.minercraftstyle.labeledredstone.block.BlockLabeledLever;
import com.minercraftstyle.labeledredstone.client.model.ModelLabeledRedstone;
import com.minercraftstyle.labeledredstone.reference.Reference;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TELabeledRedstoneRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation location = new ResourceLocation("minecraft:textures/entity/sign.png");
    //Reference.MOD_ID.toLowerCase() + ":textures/entity/labeled_lever.png"
    private final ModelLabeledRedstone model = new ModelLabeledRedstone();

    public void render(TELabeledRedstone te, double x, double y, double z, float f, int i1)
    {
        GlStateManager.pushMatrix();
        float f1 = 0.6666667F;
        float f3;

        if (testStanding(te))
        {
            GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f1, (float)z + 0.5F);
            float f2 = (float)(te.getBlockMetadata() * 360) / 16.0F;
            GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
            this.model.postLeft.showModel = true;
            this.model.postRight.showModel = true;
        }
        else
        {
            int k = te.getBlockMetadata();
            f3 = 0.0F;

            if (k == 2)
            {
                f3 = 180.0F;
            }

            if (k == 4)
            {
                f3 = 90.0F;
            }

            if (k == 5)
            {
                f3 = -90.0F;
            }

            GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f1, (float)z + 0.5F);
            GlStateManager.rotate(-f3, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
            this.model.postLeft.showModel = false;
            this.model.postRight.showModel = false;
        }

        if (i1 >= 0)
        {
            this.bindTexture(DESTROY_STAGES[i1]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            this.bindTexture(location);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale(f1, -f1, -f1);
        this.model.renderLabeledRedstone();
        GlStateManager.popMatrix();
        FontRenderer fontrenderer = this.getFontRenderer();
        f3 = 0.015625F * f1;
        GlStateManager.translate(0.0F, 0.5F * f1, 0.07F * f1);
        GlStateManager.scale(f3, -f3, f3);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
        GlStateManager.depthMask(false);
        byte b0 = 0;

        if (i1 < 0)
        {
            for (int j = 0; j < te.signText.length; ++j)
            {
                if (te.signText[j] != null)
                {
                    IChatComponent ichatcomponent = te.signText[j];
                    List list = GuiUtilRenderComponents.func_178908_a(ichatcomponent, 90, fontrenderer, false, true);
                    String s = list != null && list.size() > 0 ? ((IChatComponent)list.get(0)).getFormattedText() : "";

                    if (j == te.lineBeingEdited)
                    {
                        s = "> " + s + " <";
                        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, b0);
                    }
                    else
                    {
                        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, b0);
                    }
                }
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();

        if (i1 >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f1, int i1)
    {
        this.render((TELabeledRedstone) te, x, y, z, f1, i1);
    }

    private boolean testStanding(TELabeledRedstone te)
    {
        IBlockState state = getWorld().getBlockState(te.getPos());

        Block block = state.getBlock();

        if (block instanceof BlockLabeledLever)
        {
            EnumFacing facing = ((BlockLabeledLever.EnumOrientation)state.getValue(BlockLabeledLever.FACING_PROP)).getDirection();

            if (facing == EnumFacing.UP || facing == EnumFacing.DOWN)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}
