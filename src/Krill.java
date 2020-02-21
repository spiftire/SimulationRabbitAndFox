import java.util.List;
import java.util.Random;

/**
 * A simple model of a krill.
 * Krills age, move, breed, and die.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 * <p>
 * refactored by: Steffen Holanger, Sander Hurlen and Emil Elton Nilsen
 */
public class Krill extends Animal {
    // Characteristics shared by all krill (class variables).

    // The age at which a krill can start to breed.
    private static final int BREEDING_AGE = 1;
    // The age to which a krill can live.
    private static final int MAX_SWARM_SIZE = 1000;
    // The likelihood of a krill breeding.
    private static final double BREEDING_PROBABILITY = 0.4;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 10;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    public static final double MOVE_PROBABILITY = 0.1;

    // Individual characteristics (instance fields).

    // The swarm size.
    private int swarmSize;

    /**
     * Create a new krill. A krill may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomSwarmSize If true, the krill will have a random age.
     * @param field           The field currently occupied.
     * @param location        The location within the field.
     */
    public Krill(boolean randomSwarmSize, Field field, Location location) {
        super(field, location);
        swarmSize = 0;
        if (randomSwarmSize) {
            swarmSize = rand.nextInt(MAX_SWARM_SIZE);
        }
    }

    public Krill(Field field, Location location, int swarmSize) {
        super(field, location);
        this.swarmSize = swarmSize;
    }

    /**
     * This is what the krill does most of the time - it runs
     * around. Sometimes it will breed or die of old age.
     *
     * @param newKrill A list to return newly born krill.
     */
    public void act(List<Animal> newKrill) {
        if (isAlive()) {
            giveBirth(newKrill);
            // Try to move
            if (rand.nextDouble() < MOVE_PROBABILITY) {
                // Try to move into a free location.
                Location newLocation = getField().freeAdjacentLocation(getLocation());
                if (newLocation != null) {
                    setLocation(newLocation);
                } else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
    }

    /**
     * Check whether or not this krill is to give birth at this step.
     * New births will be made into free adjacent locations.
     *
     * @param newKrill A list to return newly born krill.
     */
    private void giveBirth(List<Animal> newKrill) {
        // New krill are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        swarmSize += births; // adding newborn to swarm
        if (swarmSize >= MAX_SWARM_SIZE) {
            splitSwarm(newKrill, field, free);
        }
    }

    private void splitSwarm(List<Animal> newKrill, Field field, List<Location> free) {
        int splitSize = Math.floorDiv(swarmSize, free.size());
        while (free.size() > 0){
            Location loc = free.remove(0);
            Krill young = new Krill(field, loc, splitSize);
            swarmSize -= splitSize; // decrement the swarm by the part that splits of
            newKrill.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return The number of births (may be zero).
     */
    private int breed() {
        int births = 0;
        if (rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    public int getSwarmSize() {
        return swarmSize;
    }
}
