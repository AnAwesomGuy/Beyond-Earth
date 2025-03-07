package com.st0x0ef.beyond_earth.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import com.st0x0ef.beyond_earth.BeyondEarth;
import com.st0x0ef.beyond_earth.common.world.structures.LargeJigsawStructure;

public class StructureRegistry {

    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, BeyondEarth.MODID);

    /** STRUCTURES */
    public static final RegistryObject<StructureType<LargeJigsawStructure>> LARGE_JIGSAW_STRUCTURE = STRUCTURES.register("large_jigsaw_structure", () -> () -> LargeJigsawStructure.CODEC);

}
