public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();

        new Thread(new CityChanger(graph)).start();
        new Thread(new EdgeChanger(graph)).start();
        new Thread(new PriceChanger(graph)).start();
        new Thread(new PriceChecker(graph)).start();
    }
}
