package org.metamechanists.aircraft.vehicles;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;


public class AircraftSurface {
    private static final double AIR_DENSITY = 1.204;

    private final double aerodynamicCoefficient;
    private final double area;
    private final Vector3d normal;
    private final Vector3d relativeLocation;

    public AircraftSurface(final double aerodynamicCoefficient, final double area, final Vector3d normal, final Vector3d relativeLocation) {
        this.aerodynamicCoefficient = aerodynamicCoefficient;
        this.area = area;
        this.normal = normal;
        this.relativeLocation = relativeLocation;
    }

    public double getRelativeArea(final @NotNull Vector3d airflowVelocity) {
        return new Vector3d(normal).dot(airflowVelocity) * area;
    }

    public SpatialForce getAerodynamicForce(final @NotNull Vector3d aircraftVelocity) {
        final Vector3d airflowVelocity = new Vector3d(aircraftVelocity).mul(-1);

        // Check the airflow isn't coming *out* of the surface as opposed to going into it
        if (normal.angle(airflowVelocity) > Math.PI / 2) {
            return new SpatialForce(new Vector3d(), relativeLocation);
        }

        // D = 0.5 * Cd * ρ * A * V^2, where
        // D = drag force
        // Cd = coefficient of drag
        // ρ = air density
        // A = surface area facing airflow
        // V = aircraft velocity
        final double aircraftSpeed = aircraftVelocity.length();
        final Vector3d force = normal.mul(0.5 * aerodynamicCoefficient * AIR_DENSITY * getRelativeArea(airflowVelocity) * aircraftSpeed * aircraftSpeed);
        return new SpatialForce(force, relativeLocation);
    }

//    public SpatialForce getLiftForce(final @NotNull Vector3d aircraftVelocity) {
//        final Vector3d airflowVelocity = new Vector3d(aircraftVelocity).mul(-1);
//
//        // Check the airflow isn't coming *out* of the surface as opposed to going into it
//        if (normal.angle(airflowVelocity) > Math.PI / 2) {
//            return new SpatialForce(new Vector3d(), relativeLocation);
//        }
//
//        // Check the airflow and normal are not in the same direction - this causes NaN values
//        if (normal.angle(airflowVelocity) < 0.001) {
//            return new SpatialForce(new Vector3d(), relativeLocation);
//        }
//
//        // L = 0.5 * Cl * ρ * A * V^2,
//        // L = lift force
//        // Cl = coefficient of lift
//        // ρ = air density
//        // A = surface area facing airflow
//        // V = aircraft velocity
//        final double aircraftSpeed = aircraftVelocity.length();
//        final Vector3d perpendicularDirection = new Vector3d(airflowVelocity).cross(normal);
//        final Vector3d liftDirection = new Vector3d(perpendicularDirection).cross(airflowVelocity).normalize();
//
//        final Vector3d force = liftDirection.mul(0.5 * liftCoefficient * AIR_DENSITY * getRelativeArea(airflowVelocity) * aircraftSpeed * aircraftSpeed);
//        return new SpatialForce(force, relativeLocation);
//    }
}
