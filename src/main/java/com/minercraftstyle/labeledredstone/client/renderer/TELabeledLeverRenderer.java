package com.minercraftstyle.labeledredstone.client.renderer;

import com.minercraftstyle.labeledredstone.client.model.ModelLabeledLever;
import com.minercraftstyle.labeledredstone.reference.Reference;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledLever;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TELabeledLeverRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation location = new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":textures/entity/labeled_lever.png");
    private final ModelLabeledLever model = new ModelLabeledLever();

    public void renderAt(TELabeledLever te, double x, double y, double z, float f1, int i1)
    {

    }

    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f1, int i1)
    {
        this.renderAt((TELabeledLever)te, x, y, z, f1, i1);
    }
}
