package com.minercraftstyle.labeledredstone.client.gui;

import com.minercraftstyle.labeledredstone.LabeledRedstone;
import com.minercraftstyle.labeledredstone.network.message.LabeledRedstoneMessage;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiLabeledRedstone extends GuiScreen
{
    private TELabeledRedstone teLabeledRedstone;
    private int updateCounter;
    private int editLine;
    private GuiButton doneBtn;

    public GuiLabeledRedstone(TELabeledRedstone te)
    {
        teLabeledRedstone = te;

        LogHelper.info("Gui constructor");
    }

    public void initGui()
    {
        LogHelper.info("Gui init start");

        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done", new Object[0])));
        this.teLabeledRedstone.setEditable(false);

        LogHelper.info("   end");
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        LabeledRedstoneMessage message = new LabeledRedstoneMessage(teLabeledRedstone);
        LabeledRedstone.network.sendToServer(message);
    }

    public void updateScreen()
    {
        ++updateCounter;
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 0)
            {
                this.teLabeledRedstone.markDirty();
                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 200)
        {
            this.editLine = this.editLine - 1 & 3;
        }

        if (keyCode == 208 || keyCode == 28 || keyCode == 156)
        {
            this.editLine = this.editLine + 1 & 3;
        }

        String s = this.teLabeledRedstone.signText[this.editLine].getFormattedText();

        if (keyCode == 14 && s.length() > 0)
        {
            s = s.substring(0, s.length() - 1);
        }

        if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && this.fontRendererObj.getStringWidth(s + typedChar) <= 90)
        {
            s = s + typedChar;
        }

        this.teLabeledRedstone.signText[this.editLine] = new ChatComponentText(s);

        if (keyCode == 1)
        {
            this.actionPerformed(this.doneBtn);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        LogHelper.info("Gui draw screen start");

        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("sign.edit", new Object[0]), this.width / 2, 40, 16777215);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2), 0.0F, 50.0F);
        float f1 = 93.75F;
        GlStateManager.scale(-f1, -f1, -f1);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        Block block = this.teLabeledRedstone.getBlockType();

        float f = (float)(this.teLabeledRedstone.rotation * 360) / 16.0F;

        GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, -1.0625F, 0.0F);

        if (this.updateCounter / 6 % 2 == 0)
        {
            this.teLabeledRedstone.lineBeingEdited = this.editLine;
        }

        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.teLabeledRedstone, -0.5D, -0.75D, -0.5D, 0.0F);
        this.teLabeledRedstone.lineBeingEdited = -1;
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);

        LogHelper.info("   end");
    }
}
