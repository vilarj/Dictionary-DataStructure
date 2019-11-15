package lab8Dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class TelephoneDirectory {
	private DictionaryInterface <Name, String> dictionary;
	//private DictionaryInterface <String, Name> reverseDictionary;		//BONUS IS IN OTHER CLASS
	public TelephoneDirectory (){
		//dictionary = new ArrayDictionary <>();
		dictionary = new SortedLinkedDictionary <> ();
	}

	public void display () {
		Iterator <Name> ni = dictionary.getKeyIterator();
		Iterator <String> si = dictionary.getValueIterator();

		while (ni.hasNext()) {
			System.out.println (ni.next() + " " + si.next());
		}

		System.out.println("Finished displaying");
	}

	public void readFile(Scanner data){

		while (data.hasNext()) {
			String [] line = data.nextLine().split("\\s"); //read line
			StringBuilder fname = new StringBuilder();
			String phone = "";
			
			for (int i = 0; i < line.length; i++) {
				if (line[i].charAt(0) >= '0' && line[i].charAt(0) <= '9') {
					fname.deleteCharAt(fname.length()-1); //delete extra space
					phone = line[i];
				} else {
					fname.append(line[i] + " "); //Compound name
				}
			}
			Name name = new Name(fname.toString());
			dictionary.add(name, phone);
		}


	}

	public  String getPhoneNumber(Name name){
		return dictionary.getValue(name);
	} 

	public void removePhoneNumber (Name name) {
		dictionary.remove (name);
	}

	public void removePhoneNumber (String fullName) {
		Name fname = new Name (fullName);
		dictionary.remove (fname);
	}

	public static void main(String[] args) {

		try {
			TelephoneDirectory td = new TelephoneDirectory();	
			File cinemas = new File ("movies.txt");

			Scanner fileSc = new Scanner (cinemas);

			td.readFile (fileSc);

			td.display();
			System.out.println("\n=============================");
			String theaterName = "West Newton Cinema";

			System.out.println(theaterName + " " + td.getPhoneNumber (new Name(theaterName)));

			td.removePhoneNumber ("Capitol Theatre");

			theaterName = "Museum Of Science";

			System.out.println(theaterName + " " + td.getPhoneNumber (new Name(theaterName)));

			td.removePhoneNumber (theaterName);

			System.out.println("\n=============================");
			
			td.display();
			
			System.out.println("\n=============================");

			String phoneNumber;
			Scanner input = new Scanner (System.in);
			do {
				System.out.println ("What name are you looking for to get phone number? ");
				System.out.println (" Type quit if none");
				String searchName = input.nextLine();
				if (searchName.equals("quit"))
					break;
				phoneNumber = td.getPhoneNumber(new Name(searchName));
				if (phoneNumber == null)
					System.out.println ("No such theater");
				else
					System.out.println (phoneNumber);
			} while (true);
			
			input.close();
		}
		catch (FileNotFoundException ex) {
			System.out.println ("File movies.txt not found in the " +
					"project directory");
		}
	}



}
