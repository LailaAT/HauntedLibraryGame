/**
* Goal: collect the number of books needed to win the game without losing all your hearts
 * User starts off with 3 hearts
 *
 * Story goes as follows:
 * You're in a haunted library and must collect n amount of books
 * You have 3 options:
 * 1. get a book off the shelf (if you've already collected it then that does nothing)
 *  1.1 if you've collected a book then you can either move to the next aisle or go to a different room
 * 2. Move onto a different isle (could encounter a ghost)
 *  2.1 if it's a friendly ghost talking to it means you're saved, staying silent means you lose one heart
 *  2.2 if it's not a friendly ghost talking to it means you lost a life, staying silent means you're saved and can continue
 * 3. Go to a different room (could encounter the owner)
 *  3.1 if the owner notices you, and you stay still you're saved, if you walk past it, you lose a life
 *  3.2 if the owner doesn't notice you, and you stay still you lose a life, if you walk past it, you're saved
 * */

import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class HauntedLibraryFinal
{
    /** Values that will remain constant */
    final static String[] BOOKS_AVAILABLE = {"The Great Gatsby", "1984", "To Kill a Mockingbird" , "The Lord of the Rings",
            "Jane Eyre", "Pride and Prejudice", "Animal Farm", "Emma", "One Hundred Years of Solitude", "Frankenstein",
            "Crime and Punishment", "The Secret History", "Great Expectations", "Oliver Twist", "A Tale of Two Cities",
            "The Count of Monte Cristo"};
    final static int NUM_BOOKS = BOOKS_AVAILABLE.length;
    final static Random rand = new Random();

    /** Normal attributes needed*/
    private static int numBooks;
    private static int currentBooks;
    private static int hearts;
    private static boolean won;
    private static Set<String> books;
    private static ArrayList<String> booksShuffled;


    /** Constructor */
    public HauntedLibraryFinal()
    {
        numBooks = rand.nextInt(11);
        numBooks = numBooks == 0 ? 1 : numBooks; //ensure there is at least 1 book to collect
        currentBooks = 0;
        hearts = 3;
        won = false;
        books = new HashSet<>(); //books the user has collected
        booksShuffled = new ArrayList<>(List.of(BOOKS_AVAILABLE));
    }

    /** Getting a book off the shelf */
    public static void getBook()
    {
        int index = rand.nextInt(NUM_BOOKS); //generate a random index
        if(books.contains(booksShuffled.get(index)))
        {
            System.out.println("Oh no! You already have " + booksShuffled.get(index));
            System.out.println("Try moving to a different room!");
        } else
        {
            System.out.println("Great! You've found a new book titled: " + booksShuffled.get(index));
            books.add(booksShuffled.get(index));
            currentBooks++;
        }
        won = currentBooks == numBooks;
    }

    /** Moving between Aisles */
    public static void moveAisle()
    {
        Scanner input = new Scanner(System.in);
        boolean encounterGhost = rand.nextBoolean();
        if(encounterGhost)
        {
            System.out.println("Oh no! There's a ghost right there, what do you do???");
            System.out.println("Type 1 to stay silent");
            System.out.println("Type 2 to talk to it");
            int option = 1;
            try
            {
                option = input.nextInt();
            } catch (Exception e)
            {
                System.out.println("Oops! Wrong type of input, we'll assume you want to stay silent");
            }

            if(option < 1 || option > 2)
            {
                System.out.println("Sorry to see you leave!");
                System.exit(0);
            }

            boolean friendlyGhost = rand.nextBoolean();
            if(friendlyGhost)
            {
                if (option == 1)
                {
                    System.out.println("Uh oh! This is a friendly ghost, you should've talked to it");
                    hearts--;
                    System.out.println("You've lost one heart! You have " + hearts + " heart(s) left");
                } else System.out.println("Good thinking! It's a friendly ghost and didn't attack you because you talked :)");
            } else
            {
                if (option == 1) System.out.println("This is not a friendly ghost, it's good that you stayed silent");
                else
                {
                    System.out.println("Uh oh, should've stayed silent, this isn't a friendly ghost");
                    hearts--;
                    System.out.println("You've lost one heart! You have " + hearts + " heart(s) left");
                }
            }

        } else System.out.println("Phew! No ghosts in sight");
    }

    /** Leaving the room */
    public static void leaveRoom()
    {
        Scanner input = new Scanner(System.in);
        boolean encounterOwner = rand.nextBoolean();
        if (encounterOwner)
        {
            System.out.println("Uh oh, the owner is right there, what do you do???");
            System.out.println("Type 1 if you want to stay still");
            System.out.println("Type 2 if you want to walk past him");
            int option = 1;

            try
            {
                option = input.nextInt();
            } catch (Exception e)
            {
                System.out.println("Oops! Wrong input type, we'll just assume you want to stay still");
            }

            if(option < 1 || option > 2)
            {
                System.out.println("Sorry to see you leave!");
                System.exit(0);
            }

            boolean ownerNoticed = rand.nextBoolean();

            if(ownerNoticed)
            {
                if (option == 1) System.out.println("Good choice there, he ignored you");
                else
                {
                    System.out.println("Oh no! You should've stayed still, the owner noticed you and he attacked!");
                    hearts--;
                    System.out.println("You've lost one heart! You have " + hearts + " heart(s) left");
                }
            } else
            {
                if (option == 1)
                {
                    System.out.println("Uh oh! The owner didn't notice you but you stayed still, he's noticed you now!");
                    hearts--;
                    System.out.println("You've lost one heart! You have " + hearts + " heart(s) left");
                } else System.out.println("Good thinking! The owner didn't notice you and you escaped quickly :)");
            }
        } else System.out.println("You've escaped the owner's wrath, he isn't in the hallway ;)");
    }

    public static void start()
    {
        Scanner input = new Scanner(System.in);
        int option = 1;
        System.out.println("Welcome to the Haunted Library!");
        System.out.println("You need to collect: " + numBooks + " books to win! You have 3 hearts (make sure you don't lose them all)");
        System.out.println("Be careful though! You might encounter ghosts along the way or the Owner of the library ;)");
        System.out.println("Let the game begin!");
        do
        {
            System.out.println("You have " + (numBooks - currentBooks) + " books left to collect");
            System.out.println("Type 1 if you want to move to the next aisle");
            System.out.println("Type 2 if you want to leave the room and go to a different one");
            System.out.println("Type 3 if you want to get a book off the shelf (you'll have to move aisles or rooms after)");
            System.out.println("Type any other integer if you want to exit the game at any point");

            System.out.println("What's your choice: ");
            try
            {
                option = input.nextInt();
            } catch (Exception e)
            {
                System.out.println("Oops! Wrong input type, we'll just assume you want to move to the next isle");
            }

            switch(option)
            {
                case 1:
                    moveAisle();
                    continue;
                case 2:
                    leaveRoom();
                    continue;
                case 3:
                    getBook();
                    System.out.println("You now have to choose to go to a different aisle or leave the room: ");
                    System.out.println("Type 1 if you want to move to the next aisle");
                    System.out.println("Type 2 if you want to leave the room and go to a different one");
                    System.out.println("What's your choice: ");
                    try
                    {
                        option = input.nextInt();
                    } catch (Exception e)
                    {
                        System.out.println("Oops! Wrong input type, we'll just assume you want to move to the next isle");
                    }
                    if (option == 1) moveAisle();
                    else if (option == 2) leaveRoom();
                    else System.exit(0);
                    continue;
                default:
                    System.out.println("Sorry to see you leave!");
                    System.exit(0);
            }
        } while (hearts > 0 && !won);

        if (hearts <= 0) System.out.println("Oh no! You've lost all your hearts, better luck next time");
        else if (won)
        {
            System.out.println("Congrats! You've won and collected all the books you need");
            System.out.println("These are the books you've collected, happy reading!!!");
            for(String book : books)
            {
                System.out.println(book);
            }
        }

    }

    public static void main(String[] args) {
        HauntedLibraryFinal hauntedLibraryFinal = new HauntedLibraryFinal();
        hauntedLibraryFinal.start();
    }

}