package se.liu.simjo878.lab1;

public class Exercise2 {
    public static void main(String[] args) {
	final int min = 10;
	final int max = 20;

	System.out.println(sumFor(min, max));
	System.out.println(sumWhile(min, max));
    }

    public static int sumFor(int min, int max){
	int returnVal = 0;
	for (int i = min; i <= max; i++){
	    returnVal += i;
	}
	return returnVal;
    }

    public static int sumWhile(int min, int max){
	int returnValue = 0;
	int counter = min;

	while (counter <= max){
	    returnValue += counter;
	    counter += 1;
	}
	return returnValue;
    }
}
