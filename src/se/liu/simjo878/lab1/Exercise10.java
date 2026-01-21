package se.liu.simjo878.lab1;

public class Exercise10 {
    public static void main(String[] args) {
	int number = 16777216;
	printer(number);
	number = 16777217;
	printer(number);

	int big = 2147483647;
	System.out.println("big " + big);

	int bigger = big+1;
	System.out.println("bigger " + bigger);

	long biggest = big + 1L;
	System.out.println("biggest " + biggest);

	long biggester = (long) big + 1;
	System.out.println("biggester " + biggester);
    }
    public static void printer(int i){
	double decimal = i;
	int integerAgain = (int) decimal;
	System.out.println(i);
	System.out.println(decimal);
	System.out.println(integerAgain + "\n");
    }
}
