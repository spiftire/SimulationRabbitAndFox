public class Main {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.simulate(500);
        simulator.reset();
        simulator.simulate(500);
    }
}
