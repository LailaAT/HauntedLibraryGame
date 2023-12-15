import java.util.*;

public class HauntedLibrary {
    private static int numBooks; //stores number of books needed to win
    private static int currentBooks;
    private static boolean winning; //stores if user is winning
    private static  boolean losing; //stores if user is losing
    private static Random rand = new Random(); //to generate random ints throughout program
    private static Set<String> books; //will store book names
    //needs to have at least 10 books
    private static HashMap<Integer, String> booksInRooms; // will store which books are in whcih rooms -> 4 rooms in the library
    final static String[] BOOKS_AVAILABLE = {"The Great Gatsby", "1984", "To Kill a Mockingbird" , "The Lord of the Rings",
    "Jane Eyre", "Pride and Prejudice", "Animal Farm", "Emma", "One Hundred Years of Solitude", "Frankenstein",
    "Crime and Punishment", "The Secret History", "Great Expectations", "Oliver Twist", "A Tale of Two Cities",
    "The Count of Monte Cristo"};
    private static ArrayList<String> listshuffled;
    final static int NUM_BOOKS = BOOKS_AVAILABLE.length;

    public HauntedLibrary()
    {
        //generate number between 0 and 10
        this.numBooks = rand.nextInt(11);
        if(numBooks == 0) numBooks++; //ensure number of books needed is at least 1
        this.currentBooks = 0;
        this.winning = false;
        this.losing = false;
        this.books = new HashSet<>();
        this.listshuffled = new ArrayList<>(List.of(BOOKS_AVAILABLE));
        Collections.shuffle(listshuffled); //shuffling array of books

    }

    /** Taking a book off the shelf can lead to winning or continuing the game*/
    public static void getBook()
    {
        int bookIndex = rand.nextInt(NUM_BOOKS);
        if(books.contains(BOOKS_AVAILABLE[bookIndex]))
        {
            System.out.println("Oh no, you already have the " + BOOKS_AVAILABLE[bookIndex]);
            System.out.println("Maybe try going into a different aisle or room");
        } else
        {
            System.out.println("Great! You found a new book");
            books.add(listshuffled.get(bookIndex));
            currentBooks++;
            winning = currentBooks == numBooks;
        }
        Collections.shuffle(listshuffled);
    }
    /** Moving Aisles means you might encounter a ghost*/
    public static void moveAisle()
    {
        int encounterGhost = rand.nextInt(2); // 0 means no ghost encounter, 1 means ghost encounter
        if(encounterGhost == 0) System.out.println("Phew! No ghost in sight!");
        else
        {
            Scanner input = new Scanner(System.in);
            System.out.println("Oh no! There's a ghost, what do you do???");
            int option = 1;
            System.out.println("Type 1 if you want to stay silent");
            System.out.println("Type 2 if you want to talk to it");
            try
            {
                option = input.nextInt();
            } catch (Exception e)
            {
                System.out.println("Oops wrong input! We'll just assume you want to stay silent");
            }

            //in case user wants to exit the game
            if(option < 1 || option > 2)
            {
                System.out.println("Sorry to see you leave!");
                System.exit(0);
            }

            boolean friendlyGhost = rand.nextBoolean();
            if(!friendlyGhost)
            {
                if(option == 1) System.out.println("It's good that you stayed silent. This was not a friendly ghost");
                else
                {
                    System.out.println("Oh no! This isn't a friendly ghost, you shouldn't have talked to it. It's going to attack!");
                    losing = true;
                }
            } else
            {
                System.out.println("That was close! Good thing this was a friendly ghost.");
            }
        }
    }

    /** Going into a different room */
    public static void leaveRoom()
    {
        int encounterOwner = rand.nextInt(2); //1 for encounter, 0 for not
        if(encounterOwner == 0) System.out.println("The owner is not in the hallway, Good, you've escaped his wrath ;)");
        else
        {
            Scanner input = new Scanner(System.in);
            int option = 1;

            System.out.println("Uh oh, the owner is right there, what do you do???");
            System.out.println("Type 1 if you want to stay still");
            System.out.println("Type 2 if you want to walk past it");

            try
            {
                option = input.nextInt();
            } catch(Exception e)
            {
                System.out.println("Oops! Wrong input, we'll just assume you want to stay still");
            }

            if(option < 1 || option > 2)
            {
                System.out.println("Sorry to see you leave!");
                System.exit(0);
            }

            boolean ownerNoticed = rand.nextBoolean();

            if(ownerNoticed)
            {
                if (option == 1)
                {
                    System.out.println("The owner noticed you! But you stayed still so he just ignored you :)");
                } else
                {
                    System.out.println("Oh no! You shouldn't have walked past the owner, he noticed you!");
                    losing = true;
                }
            } else System.out.println("That was a close one! The owner didn't seem to notice you");
        }
    }


    public static void start()
    {
        Scanner input = new Scanner(System.in);
        int option = 1; //set it as 1 initially
        System.out.println("Welcome to the Haunted Library!");
        System.out.println("You need to collect: " + numBooks + " books to win!");
        System.out.println("Be careful though! You might encounter ghosts along the way or the Owner of the library ;)");
        System.out.println("Let the game begin!");

        do
        {
            System.out.println("You have " + (numBooks - currentBooks) + " left to collect");
            System.out.println("Type 1 if you want to get a book off the shelf");
            System.out.println("Type 2 if you want to move to the next aisle");
            System.out.println("Type 3 if you want to leave the room and go to a different one");
            System.out.println("Type any other integer if you want to exit the game at any point");

            System.out.println("What's your choice: ");
            try //in case of any wrong input types entered
            {
                option = input.nextInt();
            } catch(Exception e)
            {
                System.out.println("Oops wrong input!");
                System.out.println("We'll just assume you want to get a book off the shelf");
            }

            switch (option)
            {
                case 1:
                    getBook();
                    continue;
                case 2:
                    moveAisle();
                    continue;
                case 3:
                    leaveRoom();
                    continue;
                default:
                    System.exit(0);
            }
        } while (!winning && !losing);

        if(winning) System.out.println("Congratulations! You've won and escaped this Haunted Library :)");
        else System.out.println("Oh no! You've lost, I guess you're stuck in the library for now. Play again to see if you can escape");
    }

    public static void main(String[] args)
    {
        HauntedLibrary hauntedLibrary = new HauntedLibrary(); //create new object for HauntedLibrary class
        hauntedLibrary.start(); //call start method to start game
    }

}

/**
 * Base program: you're in a haunted library and to escape you must collect a certain amount of books (all unique)
 *
 * You can choose to collect a book from the shelf (a book is chosen randomly, might already be collected or not)
 * You can choose to go to a different aisle (might encounter a ghost and lose)
 * You can choose to go to a different room (might encounter the owner and lose)
 *
 * Possible idea to make program more interesting:
 * Add books to random rooms -> there are 4 rooms in total
 * The user starts in room 1 and can collect the books that are available in that room
 * The user can move between rooms where the books will be different in that following room
 * */
