import java.util.List;
import java.util.Random;

/**
 * A simple model of a krill.
 * Krills age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 *
 * refactored by: Steffen Holanger, Sander Hurlen and Emil Elton Nilsen
 */
public class Krill extends Animal
{
    // Characteristics shared by all krills (class variables).

    // The age at which a krill can start to breed.
    private static final int BREEDING_AGE = 1;
    // The age to which a krill can live.
    private static final int MAX_AGE = 72;
    // The likelihood of a krill breeding.
    private static final double BREEDING_PROBABILITY = 0.9;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The krill's age.
    private int age;

    /**
     * Create a new krill. A krill may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the krill will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Krill(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * This is what the krill does most of the time - it runs
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born krills.
     */
    public void act(List<Animal> newRabbits)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Increase the age.
     * This could result in the krill's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this krill is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born krills.
     */
    private void giveBirth(List<Animal> newRabbits)
    {
        // New krills are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Krill young = new Krill(false, field, loc);
            newRabbits.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A krill can breed if it has reached the breeding age.
     * @return true if the krill can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
