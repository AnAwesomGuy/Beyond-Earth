package net.mrscauthd.beyond_earth.entities.renderer.starcrawler;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entities.StarCrawlerEntity;

@OnlyIn(Dist.CLIENT)
public class StarCrawlerRenderer extends MobRenderer<StarCrawlerEntity, StarCrawlerModel<StarCrawlerEntity>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BeyondEarthMod.MODID, "textures/entities/starfish.png");

    public StarCrawlerRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new StarCrawlerModel<>(renderManagerIn.bakeLayer(StarCrawlerModel.LAYER_LOCATION)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(StarCrawlerEntity p_114482_) {
        return TEXTURE;
    }
}