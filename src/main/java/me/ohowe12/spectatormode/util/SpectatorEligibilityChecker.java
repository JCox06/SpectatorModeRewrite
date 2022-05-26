package me.ohowe12.spectatormode.util;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class SpectatorEligibilityChecker {

    private SpectatorEligibilityChecker() {}

    public static EligibilityStatus getStatus(Player player, boolean forced, ConfigManager configManager) {
        if (forced || player.hasPermission("smpspectator.bypass")) {
            return EligibilityStatus.CAN_GO;
        }
        if (configManager.getBoolean("prevent-falling") && player.getFallDistance() > 0) {
            return EligibilityStatus.FALLING;
        }
        if (player.getHealth() < configManager.getDouble("minimum-health")) {
            return EligibilityStatus.HEALTH;
        }
        if(configManager.getBoolean("enforce-y") && player.getLocation().getY() <= configManager.getInt("y-level")) {
            return EligibilityStatus.YLEVEL;
        }
        if(configManager.getBoolean("grief-prevention-support")) {
            GriefPrevention inst = GriefPrevention.instance;
            if(inst == null) {
                return EligibilityStatus.CAN_GO;
            }

            Claim claim = inst.dataStore.getClaimAt(player.getLocation(), false, null);

            if(claim == null) {
                return EligibilityStatus.CLAIM;
            }

            if(claim.hasExplicitPermission(player, ClaimPermission.Build)) {
                return EligibilityStatus.CAN_GO;
            }

            return EligibilityStatus.CLAIM;
        }


        double closestAllowed = configManager.getDouble("closest-hostile");
        if (closestAllowed != 0) {
            for (Entity entity : player.getNearbyEntities(closestAllowed, closestAllowed, closestAllowed)) {
                if (entity instanceof Monster) {
                    return EligibilityStatus.MOB;
                }
            }
        }
        if (configManager.getList("worlds-allowed").stream().map(Object::toString).noneMatch(s -> s.equalsIgnoreCase(player.getWorld().getName())) && configManager.getBoolean("enforce-worlds")) {
            return EligibilityStatus.WORLDS;
        }
        return EligibilityStatus.CAN_GO;
    }

    public enum EligibilityStatus {
        CAN_GO(null),
        FALLING("falling-message"),
        HEALTH("health-message"),
        MOB("mob-too-close-message"),
        WORLDS("world-message"),
        YLEVEL("y-level-limit-message"),
        CLAIM("not-in-claim-message");

        private final String message;

        EligibilityStatus(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
