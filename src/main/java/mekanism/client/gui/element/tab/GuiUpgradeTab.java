package mekanism.client.gui.element.tab;

import mekanism.api.Coord4D;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiTileEntityElement;
import mekanism.client.sound.SoundHandler;
import mekanism.common.Mekanism;
import mekanism.common.network.PacketSimpleGui;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiUpgradeTab extends GuiTileEntityElement<TileEntity> {

    public GuiUpgradeTab(IGuiWrapper gui, TileEntity tile, ResourceLocation def) {
        super(MekanismUtils.getResource(ResourceType.GUI_ELEMENT, "GuiUpgradeTab.png"), gui, def, tile);
    }

    @Override
    public Rectangle4i getBounds(int guiWidth, int guiHeight) {
        return new Rectangle4i(guiWidth + 176, guiHeight + 6, 26, 26);
    }

    @Override
    protected boolean inBounds(int xAxis, int yAxis) {
        return xAxis >= 179 && xAxis <= 197 && yAxis >= 10 && yAxis <= 28;
    }

    @Override
    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.textureManager.bindTexture(RESOURCE);
        guiObj.drawTexturedRect(guiWidth + 176, guiHeight + 6, 0, 0, 26, 26);
        guiObj.drawTexturedRect(guiWidth + 179, guiHeight + 10, 26, inBounds(xAxis, yAxis) ? 0 : 18, 18, 18);
        mc.textureManager.bindTexture(defaultLocation);
    }

    @Override
    public void renderForeground(int xAxis, int yAxis) {
        mc.textureManager.bindTexture(RESOURCE);
        if (inBounds(xAxis, yAxis)) {
            displayTooltip(LangUtils.localize("gui.upgrades"), xAxis, yAxis);
        }
        mc.textureManager.bindTexture(defaultLocation);
    }

    @Override
    public void preMouseClicked(int xAxis, int yAxis, int button) {
    }

    @Override
    public void mouseClicked(int xAxis, int yAxis, int button) {
        if (button == 0 && inBounds(xAxis, yAxis)) {
            Mekanism.packetHandler.sendToServer(new PacketSimpleGui(Coord4D.get(tileEntity), 0, 43));
            SoundHandler.playSound(SoundEvents.UI_BUTTON_CLICK);
        }
    }
}