package mekanism.client.gui.element.tab;

import mekanism.api.TileNetworkList;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiTileEntityElement;
import mekanism.client.sound.SoundHandler;
import mekanism.common.Mekanism;
import mekanism.common.network.PacketTileEntity;
import mekanism.common.tile.TileEntityLaserAmplifier;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiAmplifierTab extends GuiTileEntityElement<TileEntityLaserAmplifier> {

    public GuiAmplifierTab(IGuiWrapper gui, TileEntityLaserAmplifier tile, ResourceLocation def) {
        super(MekanismUtils.getResource(ResourceType.GUI_ELEMENT, "GuiAmplifierTab.png"), gui, def, tile);
    }

    @Override
    public Rectangle4i getBounds(int guiWidth, int guiHeight) {
        return new Rectangle4i(guiWidth - 26, guiHeight + 138, 26, 26);
    }

    @Override
    protected boolean inBounds(int xAxis, int yAxis) {
        return xAxis >= -21 && xAxis <= -3 && yAxis >= 142 && yAxis <= 160;
    }

    @Override
    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.textureManager.bindTexture(RESOURCE);
        guiObj.drawTexturedRect(guiWidth - 26, guiHeight + 138, 0, 0, 26, 26);
        int outputOrdinal = tileEntity.outputMode.ordinal();
        guiObj.drawTexturedRect(guiWidth - 21, guiHeight + 142, 26 + 18 * outputOrdinal, inBounds(xAxis, yAxis) ? 0 : 18, 18, 18);
        mc.textureManager.bindTexture(defaultLocation);
    }

    @Override
    public void renderForeground(int xAxis, int yAxis) {
        mc.textureManager.bindTexture(RESOURCE);
        if (inBounds(xAxis, yAxis)) {
            displayTooltip(LangUtils.localize("gui.redstoneOutput") + ": " + tileEntity.outputMode.getName(), xAxis, yAxis);
        }
        mc.textureManager.bindTexture(defaultLocation);
    }

    @Override
    public void preMouseClicked(int xAxis, int yAxis, int button) {
    }

    @Override
    public void mouseClicked(int xAxis, int yAxis, int button) {
        if (button == 0 && inBounds(xAxis, yAxis)) {
            Mekanism.packetHandler.sendToServer(new PacketTileEntity(tileEntity, TileNetworkList.withContents(3)));
            SoundHandler.playSound(SoundEvents.UI_BUTTON_CLICK);
        }
    }
}