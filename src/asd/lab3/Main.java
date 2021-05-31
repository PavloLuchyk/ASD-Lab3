package asd.lab3;

public class Main {
    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.testAverage(new RBTree(), 100_000);
        tester.testAverage(new AVLTree(), 100_000);
        tester.testWorst(new RBTree(), 100_000);
        tester.testWorst(new AVLTree(), 100_000);

    }
}
