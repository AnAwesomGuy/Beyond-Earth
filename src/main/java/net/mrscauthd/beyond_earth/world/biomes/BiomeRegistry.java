package net.mrscauthd.beyond_earth.world.biomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeRegistry {

    public static final ResourceLocation MOON = new ResourceLocation(BeyondEarthMod.MODID, "moon_desert");

    public static Biome MARS;
    public static Biome MARS_ROCKY_PLAINS;
    public static Biome MARS_ICE_SPIKES;

    public static Biome VENUS;
    public static Biome INFERNAL_VENUS_BARRENS;

    public static Biome MERCURY;

    public static Biome GLACIO;
    public static Biome GLACIO_ICE_SPIKES;

    public static Biome ORBIT;

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {

        if (MARS == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            addLushCavesVegetationFeatures(biomeGenerationSettings);
            BiomeDefaultFeatures.addFossilDecoration(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModInit.MARTIAN_RAPTOR.get(), 20, 5, 5)).addMobCharge(ModInit.MARTIAN_RAPTOR.get(), 0.7D, 0.15D).build();

            MARS = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(MARS.setRegistryName(BeyondEarthMod.MODID, "mars"));
        }

        if (MARS_ROCKY_PLAINS == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            addLushCavesVegetationFeatures(biomeGenerationSettings);
            BiomeDefaultFeatures.addFossilDecoration(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModInit.MARTIAN_RAPTOR.get(), 20, 5, 5)).addMobCharge(ModInit.MARTIAN_RAPTOR.get(), 0.7D, 0.15D).build();

            MARS_ROCKY_PLAINS = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(MARS_ROCKY_PLAINS.setRegistryName(BeyondEarthMod.MODID, "mars_rocky_plains"));
        }

        if (MARS_ICE_SPIKES == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            addLushCavesVegetationFeatures(biomeGenerationSettings);
            BiomeDefaultFeatures.addFossilDecoration(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            MARS_ICE_SPIKES = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.ICY).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(MARS_ICE_SPIKES.setRegistryName(BeyondEarthMod.MODID, "mars_ice_spikes"));
        }

        if (VENUS == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            VENUS = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.5f).downfall(1f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(VENUS.setRegistryName(BeyondEarthMod.MODID, "venus"));
        }

        if (INFERNAL_VENUS_BARRENS == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_BLOBS);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BLACKSTONE_BLOBS);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_DELTA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED_DOUBLE);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            INFERNAL_VENUS_BARRENS = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.5f).downfall(1f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(INFERNAL_VENUS_BARRENS.setRegistryName(BeyondEarthMod.MODID, "infernal_venus_barrens"));
        }

        if (MERCURY == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-16777216).waterColor(4159204).waterFogColor(329011).skyColor(-16777216).foliageColorOverride(7842607).grassColorOverride(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_PILLAR);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_BLOBS);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BLACKSTONE_BLOBS);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_DELTA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED_DOUBLE);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            MERCURY = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.6f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(MERCURY.setRegistryName(BeyondEarthMod.MODID, "mercury"));
        }

        if (GLACIO == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(12638463).waterColor(4159204).waterFogColor(329011).skyColor(8756735).foliageColorOverride(0xFFFFFF).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            BiomeDefaultFeatures.addSurfaceFreezing(biomeGenerationSettings);
            BiomeDefaultFeatures.addDefaultCrystalFormations(biomeGenerationSettings);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            GLACIO = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.SNOW).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(1f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(GLACIO.setRegistryName(BeyondEarthMod.MODID, "glacio"));
        }

        if (GLACIO_ICE_SPIKES == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(12638463).waterColor(4159204).waterFogColor(329011).skyColor(8756735).foliageColorOverride(0xFFFFFF).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            BiomeDefaultFeatures.addSurfaceFreezing(biomeGenerationSettings);
            BiomeDefaultFeatures.addDefaultCrystalFormations(biomeGenerationSettings);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);

            biomeGenerationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);

            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            GLACIO_ICE_SPIKES = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.SNOW).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(1f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(GLACIO_ICE_SPIKES.setRegistryName(BeyondEarthMod.MODID, "glacio_ice_spikes"));
        }

        if (ORBIT == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-16777216).waterColor(4159204).waterFogColor(329011).skyColor(-16777216).foliageColorOverride(7842607).grassColorOverride(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            ORBIT = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.6f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(ORBIT.setRegistryName(BeyondEarthMod.MODID, "orbit"));
        }
    }

    public static void addDefaultCarvers(BiomeGenerationSettings.Builder p_194721_) {
        p_194721_.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
        p_194721_.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);
        p_194721_.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
    }

    public static void addLushCavesVegetationFeatures(BiomeGenerationSettings.Builder p_176851_) {
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_CEILING_VEGETATION);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.CAVE_VINES);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_CLAY);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_VEGETATION);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.SPORE_BLOSSOM);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.CLASSIC_VINES);
    }
}
