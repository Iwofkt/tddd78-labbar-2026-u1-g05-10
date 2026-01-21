package se.liu.simjo878.lab1;

public class Exercise6 {
    public static boolean isPrime(int number){
	for(int i=2; i <= (int) Math.sqrt(number); i++){
	    int rest = number % i;
	    if (rest == 0) {
		return false;
	    }
	}
	return true;
    }

    public static void main(String[] args) {
	for (int i = 0; i < 100; i++) {
	    System.out.println("number " + i + " prime: " + isPrime(i));
	}
    }
}

