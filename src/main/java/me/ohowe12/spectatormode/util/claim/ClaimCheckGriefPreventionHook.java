package me.ohowe12.spectatormode.util.claim;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ClaimCheckGriefPreventionHook extends ClaimCheck {


    private final GriefPrevention API;
    private final ClaimPermission permissionLevel;

    public ClaimCheckGriefPreventionHook() {
        this.API = GriefPrevention.instance;
        this.permissionLevel = ClaimPermission.Build;
    }


    @Override
    public boolean hasPermissionInClaim(Player player, Location location) {
        if(API == null) {
            return false;
        }

        Claim claim = API.dataStore.getClaimAt(location, false, null);

        if(claim == null) {
            return false;
        }

        return claim.hasExplicitPermission(player, permissionLevel);
    }
}
