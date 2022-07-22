package me.ohowe12.spectatormode.util.claim;


import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ClaimCheck {

   //If no compatible land claiming plugin is found or the option is disabled, then this method is not overridden
   //This means the default behaviour is to allow anyone to enter spectator regardless of their location / the claim they are in
   public boolean hasPermissionInClaim(Player player, Location location) {
      return true;
   }
}
