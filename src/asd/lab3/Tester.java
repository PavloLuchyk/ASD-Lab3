package asd.lab3;

import java.util.Random;

public class Tester {
    public void testAverage(DataStructure dataStructure, int numberOfElements){
        System.out.println("-----------------Average-----------------");
        System.out.println(dataStructure.getClass());
        long start = System.nanoTime();
        for (int i = 0; i < numberOfElements; i++){
            dataStructure.insert((int)(Math.random()*numberOfElements));
        }
        long end = System.nanoTime();
        System.out.println("Inserting " +  numberOfElements + " elements. Time elapsed: "+ ((end-start)/1_000_000.0) + " ms");
        System.out.println("Memory used: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " bytes");
        start = System.nanoTime();
        for (int i = 0; i < 10000; i++){
            dataStructure.search((int)(Math.random()*numberOfElements));
        }
        end = System.nanoTime();
        System.out.println("Searching " +  numberOfElements + " elements. Time elapsed: "+ ((end-start)/1_000_000.0) + "ms");
        start = System.nanoTime();
        for (int i = 0; i < 10000; i++){
            dataStructure.delete((int)(Math.random()*numberOfElements));
        }
        end = System.nanoTime();
        System.out.println("Deleting " +  numberOfElements + " elements. Time elapsed: "+ ((end-start)/1_000_000.0) + "ms");
    }

    public void testWorst(DataStructure dataStructure, int numberOfElements){
        System.out.println("-----------------Worst Case-----------------");
        System.out.println(dataStructure.getClass());
        long start = System.nanoTime();
        for (int i = 0; i < numberOfElements; i++){
            dataStructure.insert(new Random().nextInt(numberOfElements)*(int)Math.pow(2, i));
        }
        long end = System.nanoTime();
        System.out.println("Inserting " +  numberOfElements + " elements. Time elapsed: "+ ((end-start)/1_000_000.0) + " ms");
        System.out.println("Memory used: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " bytes");
        start = System.nanoTime();
        for (int i = 0; i < 10000; i++){
            dataStructure.search(new Random().nextInt(numberOfElements)*(int)Math.pow(2, i));
        }
        end = System.nanoTime();
        System.out.println("Searching " +  numberOfElements + " elements. Time elapsed: "+ ((end-start)/1_000_000.0) + "ms");
        start = System.nanoTime();
        for (int i = 0; i < 10000; i++){
            dataStructure.delete(new Random().nextInt(numberOfElements)*(int)Math.pow(2, i));
        }
        end = System.nanoTime();
        System.out.println("Deleting " +  numberOfElements + " elements. Time elapsed: "+ ((end-start)/1_000_000.0) + "ms");
    }
}
