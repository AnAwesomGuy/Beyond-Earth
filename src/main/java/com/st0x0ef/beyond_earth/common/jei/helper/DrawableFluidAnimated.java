package com.st0x0ef.beyond_earth.common.jei.helper;

import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.common.util.TickTimer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.fluids.FluidStack;
import com.st0x0ef.beyond_earth.client.util.GuiHelper;

public class DrawableFluidAnimated implements IDrawableAnimated {

    private FluidStack fluid;
    private final ITickTimer tickTimer;
    private final boolean drain;
    private final boolean O2;

    public DrawableFluidAnimated(int timer, boolean drain, FluidStack fluid) {
        this(timer, drain, fluid, false);
    }

    public DrawableFluidAnimated(int timer, boolean drain, FluidStack fluid, boolean O2) {
        this.drain = drain;
        this.tickTimer = new TickTimer(timer, GuiHelper.FLUID_TANK_HEIGHT, drain);
        this.fluid = fluid;
        this.O2 = O2;
    }

    @Override
    public int getWidth() {
        return GuiHelper.FLUID_TANK_WIDTH;
    }

    @Override
    public int getHeight() {
        return GuiHelper.FLUID_TANK_HEIGHT;
    }

    public void setFluid(FluidStack stack) {
        this.fluid = stack;
    }

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {

        int animationValue = tickTimer.getValue();

        int fluidY = this.drain ? yOffset - animationValue + this.getHeight() : this.getHeight() - animationValue + yOffset;

        if (this.O2) {
            GuiHelper.drawOxygenTank(graphics, xOffset, yOffset,
                    animationValue / (double) GuiHelper.FLUID_TANK_HEIGHT);
        } else {
            GuiHelper.drawFluid(graphics, xOffset, fluidY, GuiHelper.FLUID_TANK_WIDTH, animationValue, fluid);
            GuiHelper.drawFluidTankOverlay(graphics, xOffset, yOffset);
        }
    }
}
