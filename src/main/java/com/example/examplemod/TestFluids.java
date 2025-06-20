package com.example.examplemod;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class TestFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, ExampleMod.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ExampleMod.MODID);
    public static final DeferredHolder<FluidType, FluidType> LIFE_ESSENCE_FLUID_TYPE = FLUID_TYPES.register("life_essence_fluid_type", () -> new FluidType(FluidType.Properties.create().descriptionId("life_essence_fluid_type").fallDistanceModifier(0F).canExtinguish(false).canConvertToSource(false).supportsBoating(false).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH).canHydrate(false).viscosity(1000)));



    //public static final DeferredHolder<Fluid, Fluid> LIFE_ESSENCE_FLUID_FLOWING = FLUIDS.register("env_flowing", resourceLocation -> new BaseFlowingFluid.Flowing(new BaseFlowingFluid.Properties(ETFluidTypes.ENV, ENV_STILL, ENV_FLOWING)
     //       .slopeFindDistance(2).levelDecreasePerBlock(2)));
    //public static final DeferredHolder<Fluid, Fluid> LIFE_ESSENCE_FLUID_STILL = FLUIDS.register("env", resourceLocation -> new BaseFlowingFluid.Source(ETFluids.ENV_PROPERTIES));

    public static void registerClientExtensionsEvent(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID,"block/lifeessencestill");
            }

            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID,"block/lifeessenceflowing");
            }
        }, LIFE_ESSENCE_FLUID_TYPE);
    }

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(TestFluids::registerClientExtensionsEvent);
        FLUID_TYPES.register(modEventBus);
    }
}
