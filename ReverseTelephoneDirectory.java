package lab8Dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class ReverseTelephoneDirectory {
	//private DictionaryInterface <Name, String> dictionary;
	private DictionaryInterface <String, Name> reverseDictionary;		//FOR BONUS
	public ReverseTelephoneDirectory (){
		//dictionary = new ArrayDictionary <>();
		reverseDictionary = new SortedLinkedDictionary <> ();
	}

	public void display () {
		Iterator <String> ni = reverseDictionary.getKeyIterator();
		Iterator <Name> si = reverseDictionary.getValueIterator();

		while (ni.hasNext()) {
			System.out.println (ni.next() + " " + si.next());
		}

		System.out.println("Finished displaying");
	}

	public void readFile(Scanner data){
		//        String firstName, lastName, phoneNumber;
		//        Name fullName;
		//        String first, last;
		//        while (data.hasNext()) {
		//            first = data.next();
		//            last = data.next();
		//            phoneNumber = data.next();
		//            fullName = new Name(firstName, lastName);
		//            dictionary.add(fullName, phoneNumber);
		//        }

		//One way to do it is to make extractName and extractPhone methods
		
		while (data.hasNext()) {
			String [] line = data.nextLine().split("\\s"); //read line
			StringBuilder fname = new StringBuilder();
			String tPhone = "";
			for (int i = 0; i < line.length; i++) {
				if (line[i].charAt(0) >= '0' && line[i].charAt(0) <= '9') {
					fname.deleteCharAt(fname.length()-1); //delete extra space
					tPhone = line[i];
				} else {
					fname.append(line[i] + " "); //Compound name
				}
			}
			Name name = new Name(fname.toString());
			String phone = tPhone;
			reverseDictionary.add(phone, name);
		}


	}

	public Name getName(String number){
		return reverseDictionary.getValue(number);
	} 

	public void removeName (String number) {
		reverseDictionary.remove(number);
	}


	public static void main(String[] args) {

		try {
			ReverseTelephoneDirectory rtd = new ReverseTelephoneDirectory();	
			File cinemas = new File ("movies.txt");

			Scanner fileSc = new Scanner (cinemas);

			rtd.readFile (fileSc);

			rtd.display();
			System.out.println("=============================");
			String theaterName = "617-964-6060";

			System.out.println(theaterName + " " + rtd.getName("617-964-6060"));

			//removing Capitol Theater
			rtd.removeName ("781-648-4340");

			theaterName = "617-723-2500";

			System.out.println(theaterName + " " + rtd.getName ("617-723-2500"));

			rtd.removeName ("617-723-2500");

			System.out.println("=============================");
			
			rtd.display();
			
			System.out.println("=============================");

			Name resultName;
			Scanner input = new Scanner (System.in);
			do {
				System.out.println ("Enter a phone number to get it's name in our directory:");
				System.out.println (" Type quit if none");
				String searchNum = input.nextLine();
				if (searchNum.equals("quit"))
					break;
				resultName = rtd.getName(searchNum);
				if (resultName == null)
					System.out.println ("No such theater");
				else
					System.out.println (resultName);
			} while (true);
			
			input.close();
		}
		catch (FileNotFoundException ex) {
			System.out.println ("File movies.txt not found in the " +
					"project directory");
		}
	}

	
}