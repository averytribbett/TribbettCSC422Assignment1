/*
 * PetDatabase.java - Allow user to manage a database of Pets.
 * Avery Tribbett - tribbeta@csp.edu
 * 11/04/23
 * CSC422 Assignement 1 - Version Control Practice
 * All Code written by Avery Tribbett 
 */

/* * * * * * * * * *
 *    Release 2    *
 * * * * * * * * * */

/* Allow Users to add pets, view pets, search names and search ages */

import java.util.Scanner;
import Pet.Pet;

public class PetDatabase {
  // Static variables used throughout the program.
  static Pet[] pets = new Pet[100];
  static Scanner s = new Scanner(System.in);
  static int petCount = 0;

  public static void main(String[] args) {
    System.out.println("Pet Database Program");

    // Main loop get user choice and call corresponding method.
    int userChoice;
    do {
      System.out.println();
      userChoice = getUserChoice();

      switch (userChoice) {
        case 1 -> {
          viewAllPets(); 
          break;
        }
        case 2 -> {
          addPets();
          break;
        }
        case 3 -> {
          searchPetsByName();
          break;
        }
        case 4 -> {
          searchPetsByAge();
          break;
        }
        default -> {
          System.out.println("Goodbye!");
        }
      }
    // Run while user does not enter 5
    } while (userChoice != 5);
  }

  // Method to get the choice of the method the user wants to run.
  private static int getUserChoice() {
    int userChoice = 0;
    // Make sure user enters 1 - 5
    while (userChoice < 1 || userChoice > 5) {
      // Print options
      System.out.println("What would you like to do?");
      System.out.println(" 1) View all pets");
      System.out.println(" 2) Add more pets");
      System.out.println(" 3) Search pets by name");
      System.out.println(" 4) Search pets by age");
      System.out.println(" 5) Exit program");
      System.out.print("Your choice: ");
      userChoice = s.nextInt();

      // Print error handling message if they enter an invalid number
      if (userChoice < 1 || userChoice > 5) {
        System.out.println("Enter a number 1 - 5");
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

  // Method to search all pets and display only those with matching name.
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

  // Method to search all pets and display only those with matching age.
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