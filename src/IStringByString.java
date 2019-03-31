import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public interface IStringByString {
	public static List<String> getStrings(String fileName, List<String> out) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		while (sc.hasNextLine()) {
			out.add(sc.nextLine());
		}
		sc.close();
		return out;
	}
}