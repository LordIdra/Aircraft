package org.metamechanists.aircraft.vehicles;

import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;


public class CollisionListener implements Listener {
    @EventHandler
    public void onVehicleBlockCollide(final VehicleBlockCollisionEvent event) {
        if (event.getVehicle() instanceof final Pig pig) {

        }
    }
}
