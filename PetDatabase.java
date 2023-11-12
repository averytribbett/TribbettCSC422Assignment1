/*
 * PetDatabase.java - Allow user to manage a database of Pets.
 * Avery Tribbett - tribbeta@csp.edu
 * 11/12/23
 * CSC422 Assignement 2 - GitHub Issues, Milestones, and Projects Practice
 * All Code written by Avery Tribbett 
 */

import java.util.Scanner;
import Pet.Pet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Integer;

public class PetDatabase {
  // Static variables used throughout the program.
  static Pet[] pets = new Pet[5];
  static Scanner s = new Scanner(System.in);
  static int petCount = 0;
  static File dbFile = new File("my_pets.txt");

  public static void main(String[] args) {
    System.out.println("Pet Database Program");

    loadDatabase();

    // Main loop get user choice and call corresponding method.
    int userChoice;
    do {
      System.out.println();
      // Because of error handling this will always be 1 - 4
      userChoice = getUserChoice();

      switch (userChoice) {
        case 1 : viewAllPets(); break;
        case 2 : addPets(); break; 
        case 3 : removePet(); break;
        default : System.out.println("Goodbye!"); 
      }
    // Run while user does not enter 4
    } while (userChoice != 4);

    saveDatabase();
  }

  // Method to load in a previously saved database.
  private static void loadDatabase() {
    try {
      // Open a scanner on our file.
      Scanner reader = new Scanner(dbFile);
      
      // Iterate over each line of our file we are reading.
      while (reader.hasNextLine()) {
        // ERROR HANDLING: Print message and break from method if database if full.
        if (petCount >= 5) {
          System.out.println("Error: Database is full.");
          break;
        }

        // Save the current line and split each item.
        String line = reader.nextLine();
        String[] lineItems = line.split(" ");

        // The first lineItem is the name, the second is the age. 
        // Set the current pet equal to the current values.
        pets[petCount] = new Pet(lineItems[0], Integer.parseInt(lineItems[1]));
        // Increment petCount.
        petCount++;
      }
      reader.close();
    } catch (Exception ex) {
      // If we can't load the database print message and exit program.
      System.out.println("Error loading database. " + ex);
      System.exit(1);
    }  
  }

  // Method to save the state of the database at the end of the program.
  private static void saveDatabase() {
    // Delete the old file.
    dbFile.delete();

    // Create new blank file to write the current pet database state to.
    try {
      File newFile = new File("my_pets.txt");
      newFile.createNewFile();

      PrintWriter writer = new PrintWriter(newFile);

      // Iterate over pets adding each name and age to a new line. 
      // FORMAT: name age
      for (int i = 0; i < petCount; i++) {
        writer.println(pets[i].getName() + " " + pets[i].getAge());
      }
      writer.close();
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }

  // Method to get the choice of the method the user wants to run.
  private static int getUserChoice() {
    int userChoice = 0;
    // Make sure user enters 1 - 4
    while (userChoice < 1 || userChoice > 4) {
      // Print options
      System.out.println("What would you like to do?");
      System.out.println(" 1) View all pets");
      System.out.println(" 2) Add more pets");
      System.out.println(" 3) Remove an existing pet");
      System.out.println(" 4) Exit program");
      /* Options Not currently needed.
      System.out.println(" 3) Update an existing pet");
      System.out.println(" 5) Search pets by name");
      System.out.println(" 6) Search pets by age");
      */
      System.out.print("Your choice: ");
      userChoice = s.nextInt();

      // Print error handling message if they enter an invalid number
      if (userChoice < 1 || userChoice > 4) {
        System.out.println("Enter a number 1 - 4");
      }
      System.out.println();
    }
    return userChoice;
  }

  // Method to allow user to add pets.
  // Stop when user types done or database is full.
  private static void addPets() {
    // Input one is the name, two is the age.
    String input1 = "";
    int input2 = 0;
    
    // Run until user types done
    while (!input1.equals("done")) {
      // Get name and age
      System.out.print("add pet (name, age) or done when finished: ");

      input1 = s.next();
      // Only run if user enters a name
      if (!input1.equals("done")) {
        input2 = s.nextInt();

        // ERROR HANDLING: Print message and break from method if database if full.
        if (petCount >= 5) {
          System.out.println("Error: Database is full.");
          break;
        }

        // Find the first null value (ie first empty pet)
        // Set that null value to newly created pet
        for (int i = 0; i < pets.length; i++) {
          if (pets[i] == null) {
            // Set new pet 
            pets[i] = new Pet(input1, input2);
            // Increment pet count.
            petCount++;
            break;
          }
        }
      }
    }
  }

  // Method to change a pet 
  /* Not needed for this assignment 
  private static void updatePet() {
    // Update user on current pets available to update.
    viewAllPets();

    // Get Pet info 
    System.out.print("Enter the pet ID you want to update: ");
    int updateIndex = s.nextInt();
    System.out.print("Enter new name and new age (name, age): ");
    String newName = s.next();
    int newAge = s.nextInt();

    // Update pet 
    pets[updateIndex].setAge(newAge);
    pets[updateIndex].setName(newName);
  }
  */

  // Method to remove an existing pet. 
  private static void removePet() {
    // Update the user on available pets to removee.
    viewAllPets();

    // Get index of pet to remove
    System.out.print("Enter the pet ID to remove: ");
    int delIndex = s.nextInt();

    // Shift each item after the pet deleted to the left (effectively deleting it)
    for (int i = delIndex; i < pets.length - 1; i++) {
      pets[i] = pets[i + 1];
    }
    // Set the last item to null (we do this in case the database is full).
    // Because if DB is full the final item wont get shifted.
    pets[pets.length - 1] = null;

    // Remove one from pet count.
    petCount --;
  }

  // Method to search all pets and display only those with matching name.
  /* Not needed for this assignment 
  private static void searchPetsByName() {
    Pet currentPet;

    // Get pet name to search and store in lowercase for consistancy.
    System.out.print("Enter a name to search: ");
    String searchName = s.next().toLowerCase();

    // We need a search count to display the number of rows.
    int searchCount = 0;

    printTableHeader();

    // Iterate over each pet in the database.
    for (int i = 0; i < pets.length; i++) {
      currentPet = pets[i];

      // Once we hit a null value break from the loop
      if (currentPet == null) break;
  
      // Check whether currentPets name is the searched name if so print row.
      if (currentPet.getName().toLowerCase().equals(searchName)) {
        printTableRow(i, currentPet.getName(), currentPet.getAge());
        searchCount++;
      }
    }
    printTableFooter(searchCount);
  }

  // Method to search all pets and display only those with matching age
  private static void searchPetsByAge() {
    Pet currentPet;

    // Get pet age to search and store.
    System.out.print("Enter age to search: ");
    int searchAge = s.nextInt();

    // We need a search count to display the number of rows.
    int searchCount = 0;

    printTableHeader();

    // Iterate over every pet in the database.
    for (int i = 0; i < pets.length; i++) {
      currentPet = pets[i];

      // Once we hit a null value break from the loop
      if (currentPet == null) break;
      
      // If the searched age matches the current pets age print the row.
      if (currentPet.getAge() == searchAge) {
        printTableRow(i, currentPet.getName(), currentPet.getAge());
        searchCount++;
      }
    }
    printTableFooter(searchCount);
  }
  */

  // Method to print out a table with all pets in the database.
  private static void viewAllPets() {
    Pet currentPet;

    printTableHeader();

    // Iterate through every pet in the database.
    for (int i = 0; i < pets.length; i++) {
      currentPet = pets[i];

      // Once we hit a null value break from the loop
      if (currentPet == null) break;
      
      // Print the row with details of the current pet
      printTableRow(i, currentPet.getName(), currentPet.getAge());
    }
    printTableFooter(petCount);
  }

  // Method to print formatted table header.
  private static void printTableHeader() {
    System.out.printf("+-------------------------+%n");
    System.out.printf("| %-3s | %-10s | %-4s |%n", "ID", "NAME", "AGE");
    System.out.printf("+-------------------------+%n");
  }

  // Method to print formatted row with Pet details.
  private static void printTableRow(int id, String name, int age) {
    System.out.printf("| %-3d | %-10s | %-4d |%n", id, name, age);
  }

  // Method to print formatted table footer.
  private static void printTableFooter(int rowCount) {
    System.out.printf("+-------------------------+%n");
    System.out.println(rowCount + " rows in set.");
  }
}