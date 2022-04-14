package net.mrscauthd.beyond_earth.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entities.*;
import net.mrscauthd.beyond_earth.events.forgeevents.EntityTickEvent;
import net.mrscauthd.beyond_earth.events.forgeevents.ItemTickEvent;
import net.mrscauthd.beyond_earth.mixin.EntityTick;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID)
public class Events {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Level world = player.level;

            //Lander Teleport System
            if (player.getVehicle() instanceof LanderEntity) {
                Methods.landerTeleportOrbit(player, world);
            }

            //Planet Gui Open
            Methods.openPlanetGui(player);

            //Oxygen System
            OxygenSystem.OxygenSystem(player);

            //Drop Off Hand Item
            Methods.dropRocket(player);
        }
    }

    @SubscribeEvent
    public static void onLivingEntityTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level world = entity.level;

        //Entity Oxygen System
        Methods.entityOxygen(entity,world);

        //Gravity Method Call
        if (entity instanceof Player) {
            Gravity.gravity(entity, Gravity.GravityType.PLAYER, world);
        } else {
            Gravity.gravity(entity, Gravity.GravityType.LIVING, world);
        }

        //Venus Rain
        Methods.venusRain(entity, Methods.venus);

        //Planet Fire
        Methods.planetFire(entity, Methods.venus);
        Methods.planetFire(entity, Methods.mercury);
    }

    @SubscribeEvent
    public static void entityTick(EntityTickEvent event) {
        Entity entity = event.getEntity();

        // ORBIT TELEPORT
        if (entity.getY() < 1 && !(entity.getVehicle() instanceof LanderEntity)) {

            if ((entity instanceof LanderEntity) && entity.isVehicle()) {
                return;
            }

            Methods.entityFallToPlanet(entity.level, entity);
        }
    }

    @SubscribeEvent
    public static void onItemEntityTick(ItemTickEvent event) {
        ItemGravity.itemGravity(event.getEntityItem());
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Level world = event.world;

            if (Methods.worldsWithoutRain.contains(world.dimension())) {
                world.thunderLevel = 0;
                world.rainLevel = 0;
            } else if (Methods.isWorld(world, Methods.venus)) {
                world.thunderLevel = 0;
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!event.getSource().isFire()) {
            return;
        }

        Player entity = (Player) event.getEntity();

        if (!Methods.netheriteSpaceSuitCheck(entity)) {
            return;
        }

        entity.setRemainingFireTicks(0);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void fishingProjectile(ProjectileImpactEvent event) {
        if (event.getRayTraceResult().getType() != HitResult.Type.ENTITY) {
            return;
        }

        Entity entity = ((EntityHitResult) event.getRayTraceResult()).getEntity();

        if (Methods.isVehicle(entity)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player && event.getEntity().getPersistentData().getBoolean(BeyondEarthMod.MODID + ":planet_selection_gui_open")) {

            ((Player) event.getEntity()).closeContainer();
            Methods.cleanUpPlayerNBT((Player) event.getEntity());
            event.getEntity().setNoGravity(false);
        }
    }

    @SubscribeEvent
    public static void onFallDamage(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level level = entity.level;

        if (Methods.isWorld(level, Methods.moon)) {
            event.setDistance(event.getDistance() - 5.5F);
        }
        else if (Methods.isWorld(level, Methods.mars)) {
            event.setDistance(event.getDistance() - 5.0F);
        }
        else if (Methods.isWorld(level, Methods.glacio)) {
            event.setDistance(event.getDistance() - 5.0F);
        }
        else if (Methods.isWorld(level, Methods.mercury)) {
            event.setDistance(event.getDistance() - 5.5F);
        }
        else if (Methods.isWorld(level, Methods.venus)) {
            event.setDistance(event.getDistance() - 5.0F);
        }
        else if (Methods.isOrbitWorld(level)) {
            event.setDistance(event.getDistance() - 8.5F);
        }
    }
}
