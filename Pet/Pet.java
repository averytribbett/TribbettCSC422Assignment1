/*
 * Pet.java - Pet object (this does not change throughout releases).
 * Avery Tribbett - tribbeta@csp.edu
 * 11/04/23
 * CSC422 Assignement 1 - Version Control Practice
 * All Code written by Avery Tribbett 
 */

package Pet;

public class Pet {
  // Pet variables
  private String name;
  private int age;

  // Pet constructor method.
  public Pet (String name, int age) {
    this.name = name;
    this.age = age;
  }

  // Getter function for name
  public String getName() {
    return this.name;
  }

  // Setter function for name
  public void setName(String name) {
    this.name = name;
  }

  // Getter function for age
  public int getAge() {
    return this.age;
  }

  // Setter function for age
  public void setAge(int age) {
    this.age = age;
  }
}
