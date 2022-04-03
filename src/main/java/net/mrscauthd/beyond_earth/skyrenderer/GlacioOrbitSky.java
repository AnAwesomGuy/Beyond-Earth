package net.mrscauthd.beyond_earth.skyrenderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

import javax.annotation.Nullable;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class GlacioOrbitSky {

    private static final ResourceLocation DIM_RENDER_INFO = new ResourceLocation(BeyondEarthMod.MODID, "glacio_orbit");

    @Nullable
    public static VertexBuffer starBuffer;
    private static final ResourceLocation GLACIO_TEXTURE = new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/glacio.png");
    private static final ResourceLocation SUN_TEXTURE = new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/no_a_sun.png");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void clientSetup(FMLClientSetupEvent event) {
        DimensionSpecialEffects.EFFECTS.put(DIM_RENDER_INFO, new DimensionSpecialEffects(192, false, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 p_108878_, float p_108879_) {
                return p_108878_;
            }

            @Override
            public boolean isFoggyAt(int p_108874_, int p_108875_) {
                return false;
            }

            @Override
            public ICloudRenderHandler getCloudRenderHandler() {
                return new ICloudRenderHandler() {
                    @Override
                    public void render(int ticks, float partialTicks, PoseStack matrixStack, ClientLevel world, Minecraft mc, double viewEntityX, double viewEntityY, double viewEntityZ) {

                    }
                };
            }

            @Override
            public float[] getSunriseColor(float p_108872_, float p_108873_) {
                return null;
            }

            @Override
            public ISkyRenderHandler getSkyRenderHandler() {
                return new ISkyRenderHandler() {
                    @Override
                    public void render(int ticks, float p_181412_, PoseStack p_181410_, ClientLevel level, Minecraft minecraft) {
                        Matrix4f matrix4f = RenderSystem.getProjectionMatrix();
                        Matrix4f starMatrix4f = RenderSystem.getProjectionMatrix();
                        RenderSystem.disableTexture();
                        Vec3 vec3 = level.getSkyColor(minecraft.gameRenderer.getMainCamera().getPosition(), p_181412_);
                        float f = (float)vec3.x;
                        float f1 = (float)vec3.y;
                        float f2 = (float)vec3.z;
                        FogRenderer.levelFogColor();
                        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                        RenderSystem.depthMask(false);
                        RenderSystem.setShaderColor(f, f1, f2, 1.0F);
                        ShaderInstance shaderinstance = RenderSystem.getShader();
                        minecraft.levelRenderer.skyBuffer.drawWithShader(p_181410_.last().pose(), matrix4f, shaderinstance);

                        /** ENABLE BLEND SYSTEM */
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();

                        /** STAR ROT */
                        p_181410_.pushPose();
                        p_181410_.mulPose(Vector3f.YP.rotationDegrees(0.0F));
                        p_181410_.mulPose(Vector3f.ZP.rotationDegrees(level.getTimeOfDay(p_181412_) * 360.0F));
                        p_181410_.mulPose(Vector3f.XP.rotationDegrees(-30.0F));

                        /** STAR */
                        createStars();
                        RenderSystem.setShaderColor(0.8F, 0.8F, 0.8F, 0.8F);
                        FogRenderer.setupNoFog();
                        starBuffer.drawWithShader(p_181410_.last().pose(), starMatrix4f, GameRenderer.getPositionShader());
                        p_181410_.popPose();

                        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                        p_181410_.pushPose();
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.enableTexture();

                        RenderSystem.setShader(GameRenderer::getPositionTexShader);

                        /** DEFAULT ROT */
                        p_181410_.pushPose();
                        p_181410_.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                        p_181410_.mulPose(Vector3f.XP.rotationDegrees(level.getTimeOfDay(p_181412_) * 360.0F));
                        Matrix4f matrix4f1 = p_181410_.last().pose();
                        p_181410_.popPose();

                        /** SUN */
                        float f12 = 30.0F;

                        RenderSystem.setShaderTexture(0, SUN_TEXTURE);
                        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
                        bufferbuilder.end();
                        BufferUploader.end(bufferbuilder);


                        /** GLACIO ROT */
                        p_181410_.mulPose(Vector3f.YP.rotationDegrees(0.0F));
                        p_181410_.mulPose(Vector3f.XP.rotationDegrees(0.0F));
                        matrix4f1 = p_181410_.last().pose();

                        /** GLACIO */
                        RenderSystem.disableBlend();

                        float var20 = -3000.0F + (float) Minecraft.getInstance().player.getY() * 6F;

                        float scale = 100 * (0.2F - var20 / 10000.0F);
                        scale = Math.max(scale, 4.0F);

                        RenderSystem.setShaderTexture(0, GLACIO_TEXTURE);
                        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferbuilder.vertex(matrix4f1, -scale, -100.0F, scale).uv(0.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, scale, -100.0F, scale).uv(1.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, scale, -100.0F, -scale).uv(1.0F, 1.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, -scale, -100.0F, -scale).uv(0.0F, 1.0F).endVertex();
                        bufferbuilder.end();
                        BufferUploader.end(bufferbuilder);

                        RenderSystem.enableBlend();

                        RenderSystem.disableTexture();
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.disableBlend();
                        p_181410_.popPose();

                        /** CUT WAY SYSTEM */
                        RenderSystem.disableTexture();
                        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);

                        if (level.effects().hasGround()) {
                            RenderSystem.setShaderColor(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F, 1.0F);
                        } else {
                            RenderSystem.setShaderColor(f, f1, f2, 1.0F);
                        }

                        RenderSystem.enableTexture();
                        RenderSystem.depthMask(true);
                    }
                };
            }

            private void createStars() {
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder bufferbuilder = tesselator.getBuilder();
                RenderSystem.setShader(GameRenderer::getPositionShader);
                if (starBuffer != null) {
                    starBuffer.close();
                }

                starBuffer = new VertexBuffer();
                this.drawStars(bufferbuilder);
                bufferbuilder.end();
                starBuffer.upload(bufferbuilder);
            }

            private void drawStars(BufferBuilder p_109555_) {
                Random random = new Random(10842L);
                p_109555_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
                int stars = 0;
                if (Minecraft.getInstance().options.graphicsMode == GraphicsStatus.FANCY || Minecraft.getInstance().options.graphicsMode == GraphicsStatus.FABULOUS) {
                    stars = 13000;
                } else {
                    stars = 6000;
                }

                for(int i = 0; i < stars; ++i) {
                    double d0 = (double)(random.nextFloat() * 2.0F - 1.0F);
                    double d1 = (double)(random.nextFloat() * 2.0F - 1.0F);
                    double d2 = (double)(random.nextFloat() * 2.0F - 1.0F);
                    double d3 = (double)(0.075F + random.nextFloat() * 0.1F);
                    double d4 = d0 * d0 + d1 * d1 + d2 * d2;
                    if (d4 < 1.0D && d4 > 0.01D) {
                        d4 = 1.0D / Math.sqrt(d4);
                        d0 *= d4;
                        d1 *= d4;
                        d2 *= d4;
                        double d5 = d0 * 100.0D;
                        double d6 = d1 * 100.0D;
                        double d7 = d2 * 100.0D;
                        double d8 = Math.atan2(d0, d2);
                        double d9 = Math.sin(d8);
                        double d10 = Math.cos(d8);
                        double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                        double d12 = Math.sin(d11);
                        double d13 = Math.cos(d11);
                        double d14 = random.nextDouble() * Math.PI * 2.0D;
                        double d15 = Math.sin(d14);
                        double d16 = Math.cos(d14);

                        for(int j = 0; j < 4; ++j) {
                            double d17 = 0.0D;
                            double d18 = (double)((j & 2) - 1) * d3;
                            double d19 = (double)((j + 1 & 2) - 1) * d3;
                            double d20 = 0.0D;
                            double d21 = d18 * d16 - d19 * d15;
                            double d22 = d19 * d16 + d18 * d15;
                            double d23 = d21 * d12 + 0.0D * d13;
                            double d24 = 0.0D * d12 - d21 * d13;
                            double d25 = d24 * d9 - d22 * d10;
                            double d26 = d22 * d9 + d24 * d10;
                            p_109555_.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
                        }
                    }
                }
            }
        });
    }
}
