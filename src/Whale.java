import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a whale.
 * Whales age, move, eat krill, and die.
 * <p>
 * based on:
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 * <p>
 * refactored by: Steffen Holanger, Sander Hurlen and Emil Elton Nilsen
 */
public class Whale extends Animal {
    // Characteristics shared by all whales (class variables).

    // The age at which a whale can start to breed.
    private static final int BREEDING_AGE = 120;
    // The age to which a whale can live in months
    private static final int MAX_AGE = 1080;
    // The likelihood of a whale breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;

    // how long the whale can go without food
    public static final int MONTHS_IN_YEAR = 12;
    public static final int MAX_MONTH_WITHOUT_FOOD = 7;
    public static final int MAX_TIME_WITHOUT_FOOD = Math.floorDiv(MONTHS_IN_YEAR, MAX_MONTH_WITHOUT_FOOD);

    // The amount of krill a whale eats in tons
    public static final int FOOD_PR_MONTH = 108;
    public static final int FOOD_PR_YEAR = MONTHS_IN_YEAR * FOOD_PR_MONTH;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a whale can go before it has to eat again.
    private static final int KRILL_FOOD_VALUE = Math.floorDiv(FOOD_PR_YEAR, MAX_TIME_WITHOUT_FOOD);

    public static final int MONTHS_BEFORE_HUNGRY = 1;
    public static final int HUNGER_LEVEL = Math.floorDiv(FOOD_PR_YEAR, MONTHS_BEFORE_HUNGRY);
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The whale's age.
    private int age;
    // The whale's food level, which is increased by eating krill.
    private int foodLevel;

    private boolean isPregnant = false;
    private int monthsPregnant = 0;

    /**
     * Create a whale. A whale can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the whale will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Whale(boolean randomAge, Field field, Location location) {
        super(field, location);
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(KRILL_FOOD_VALUE);
        } else {
            age = 0;
            foodLevel = KRILL_FOOD_VALUE;
        }
    }

    /**
     * This is what the whale does most of the time: it hunts for
     * krill. In the process, it might breed, die of hunger,
     * or die of old age.
     *
     * @param newWhales A list to return newly born whales.
     */
    public void act(List<Animal> newWhales) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newWhales);
            Location newLocation = null;

            if (foodLevel < HUNGER_LEVEL) {
                // Move towards a source of food if found.
                newLocation = findFood();
            }

            // Not hungry or no food found.
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                //setDead();
            }
        }
    }

    /**
     * Increase the age. This could result in the whale's death.
     */
    private void incrementAge() {
        age++;
        if (age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this whale more hungry. This could result in the whale's death.
     */
    private void incrementHunger() {
        foodLevel -= FOOD_PR_MONTH;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for krill adjacent to the current location.
     * Only the first live rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Krill) {
                Krill krill = (Krill) animal;
                if (krill.isAlive()) {
                    krill.setDead();
                    foodLevel += krill.getSwarmSize();
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Check whether or not this whale is to give birth at this step.
     * New births will be made into free adjacent locations.
     *
     * @param newWhale A list to return newly born whales.
     */
    private void giveBirth(List<Animal> newWhale) {
        // New whales are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Whale young = new Whale(false, field, loc);
            newWhale.add(young);
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

        if (canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY && !isPregnant) {
            isPregnant = true;
        }

        if(isPregnant) {
            monthsPregnant++;
            if (monthsPregnant >= 12) {
                births = rand.nextInt(MAX_LITTER_SIZE) + 1;
                resetPregnancy();
            }
        }
        return births;
    }

    private void resetPregnancy() {
        monthsPregnant = 0;
        isPregnant = false;
    }

    /**
     * A whales can breed if it has reached the breeding age.
     */
    private boolean canBreed() {
        return age >= BREEDING_AGE;
    }
}
