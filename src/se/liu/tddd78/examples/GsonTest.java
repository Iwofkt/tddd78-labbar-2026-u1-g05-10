package se.liu.tddd78.examples;

import com.google.gson.Gson;
import se.liu.jonkv82.annotations.BorrowedCode;

/**
 * This class simply verifies that the Gson library is correctly configured in IDEA. It contains examples from
 * https://github.com/google/gson/blob/main/UserGuide.md.
 */
public class GsonTest
{
    @BorrowedCode(source = "https://github.com/google/gson/blob/main/UserGuide.md") public static void main(String[] args) {
	Gson gson = new Gson();
	gson.toJson(1);            // ==> 1
	gson.toJson("abcd");       // ==> "abcd"
	gson.toJson(new Long(10)); // ==> 10
	int[] values = { 1 };
	gson.toJson(values);       // ==> [1]

	// Deserialization
	int i = gson.fromJson("1", int.class);
	Integer intObj = gson.fromJson("1", Integer.class);
	Long longObj = gson.fromJson("1", Long.class);
	Boolean boolObj = gson.fromJson("false", Boolean.class);
	String str = gson.fromJson("\"abc\"", String.class);
	String[] strArray = gson.fromJson("[\"abc\"]", String[].class);
    }
}
