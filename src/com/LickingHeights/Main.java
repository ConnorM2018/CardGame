package com.LickingHeights;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //TRUE = TAKEN
        boolean[] spades = new boolean[13];
        boolean[] clubs = new boolean[13];
        boolean[] hearts = new boolean[13];
        boolean[] diamonds = new boolean[13];
        int[] chosenCard = new int[6];
        int[] healthCounter = new int[2];
        healthCounter[0] = 13;
        healthCounter[1] = 13;
        mainGame(spades, clubs, hearts, diamonds, chosenCard, healthCounter);
    }

    public static void mainGame(boolean spades[], boolean clubs[], boolean hearts[], boolean diamonds[], int chosenCard[], int[] healthCounter) {
        boolean[] sacrifice = new boolean[2];
        System.out.println("You take the card at the top of your battle deck, and place it face-up on the table.");
        chosenCard[0] = drawCard(spades, chosenCard, true);
        System.out.println("You drew your " + cardIdentity(chosenCard[0]) + " card.");
        chosenCard[1] = drawCard(diamonds, chosenCard, false);
        System.out.println("Your opponent drew their " + cardIdentity(chosenCard[1]) + " card.");
        if (chosenCard[0] < chosenCard[1]) {
            sacrifice[1] = enemyChoice(healthCounter[1]);
            if (sacrifice[1]) {
                chosenCard[5] = cardSelector(diamonds);
                while (chosenCard[5] == chosenCard[1]) {
                    chosenCard[5] = cardSelector(diamonds);
                }
                diamonds[chosenCard[5]] = true;
                hearts[chosenCard[3]] = false;
                if (diamonds[12]) {
                    System.out.println("Your opponent accidentally sacrificed their KING!");
                } else if (diamonds[0]) {
                    System.out.println("Your opponent sacrificed their ACE!");
                    System.out.println("All your opponent's health cards have been recovered!");
                    hearts = new boolean[13];
                    healthCounter[1] = 13;
                } else {
                    System.out.println("Your opponent sacrificed one of their battle cards.");
                    healthCounter[1] = (healthCounter[1] + 1);
                }
            } else {
                chosenCard[2] = cardSelector(clubs);
                clubs[chosenCard[2]] = true;
                healthCounter[0] = (healthCounter[0] - 1);
                if (chosenCard[2] == 0 && healthCounter[0] < 11) {
                    clubs[aceTaken(clubs)] = false;
                    clubs[aceTaken(clubs)] = false;
                    System.out.println("Your opponent took your ACE health card!");
                    System.out.println("You recovered two of your health cards!");
                    healthCounter[0] = (healthCounter[0] + 2);
                } else {
                    System.out.println("Your opponent took one of your health cards.");
                }

            }
        } else if (chosenCard[0] > chosenCard[1]) {
            sacrifice[0] = choiceSpeaker(healthCounter[0]);
            if (sacrifice[0]) {
                chosenCard[4] = cardSelector(spades);
                while (chosenCard[4] == chosenCard[0]) {
                    chosenCard[4] = cardSelector(spades);
                }
                spades[chosenCard[4]] = true;
                clubs[chosenCard[2]] = false;
                if (spades[12]) {
                    System.out.println("You accidentally sacrificed your KING!");
                } else if (spades[0]) {
                    System.out.println("You sacrificed your ACE!");
                    System.out.println("All your health cards have been recovered!");
                    clubs = new boolean[13];
                    healthCounter[0] = 13;
                } else {
                    System.out.println("You sacrificed one of your battle cards.");
                    healthCounter[0] = (healthCounter[0] + 1);
                }
            } else {
                chosenCard[3] = cardSelector(hearts);
                hearts[chosenCard[3]] = true;
                healthCounter[1] = (healthCounter[1] - 1);
                if (chosenCard[3] == 0 && healthCounter[1] < 11) {
                    hearts[aceTaken(hearts)] = false;
                    hearts[aceTaken(hearts)] = false;
                    System.out.println("You accidentally took your opponent's ACE health card!");
                    System.out.println("Your opponent recovered two of their health cards!");
                    healthCounter[1] = (healthCounter[1] + 2);
                } else {
                    System.out.println("You took your opponent's " + cardIdentity(chosenCard[3]) + " health card.");
                }
            }
        }
        System.out.println("_______________________________________________________");
        System.out.println("Health Cards left: " + healthCounter[0] + " | Opponent Health Cards left: " + healthCounter[1]);
        System.out.println(royaltyCounter(hearts));
        System.out.println("_______________________________________________________");
        if (gameEnd(hearts, clubs, spades, diamonds)) {
            playAgain();
        } else {
            intermission(spades, clubs, hearts, diamonds, chosenCard, healthCounter);
        }
    }

    public static void playAgain() {
        Scanner keyboard = new Scanner(System.in);
        boolean[] spades = new boolean[13];
        boolean[] clubs = new boolean[13];
        boolean[] hearts = new boolean[13];
        boolean[] diamonds = new boolean[13];
        int[] chosenCard = new int[6];
        int[] healthCounter = new int[2];
        healthCounter[0] = 13;
        healthCounter[1] = 13;
        System.out.println("----------------------------------------");
        System.out.println("Do you wish to play again? ( [1] = Yes )");
        System.out.println("----------------------------------------");
        int response = keyboard.nextInt();
        if (response == 1) {
            mainGame(spades, clubs, hearts, diamonds, chosenCard, healthCounter);
        }
    }

    public static void intermission(boolean[] spades, boolean[] clubs, boolean[] hearts, boolean[] diamonds, int[] chosenCard, int[] healthCounter) {

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Do you wish to...");
        System.out.println("[1] Draw your next card");
        System.out.println("[2] Take a moment to read the rules");
        int input = keyboard.nextInt();
        switch (input) {
            case 1:
                mainGame(spades, clubs, hearts, diamonds, chosenCard, healthCounter);
            case 2:
                System.out.println("_____________________________________________________________");
                System.out.println("                          RULES                              ");
                System.out.println("(1) 2 player card game.");
                System.out.println("(2) Each player gets two separate decks of cards.");
                System.out.println("(3) One player gets all the spades & clubs, and the ");
                System.out.println("other player gets all the hearts & diamonds.");
                System.out.println("(4) The Spade & Diamond decks are called 'battle decks.'");
                System.out.println("The Club & Heart decks are called 'health decks.'");
                System.out.println("(5) Each round, each player takes the card at the top of ");
                System.out.println("their battle deck, and places it face-up on the table. ");
                System.out.println("(6) Whoever has the card with the higher value gets to ");
                System.out.println("make a choice - take one of their opponent's health cards,");
                System.out.println("or sacrifice their own next battle card to recover their most ");
                System.out.println("recently taken health card. Sacrificed battle cards are put to");
                System.out.println("the side, and cannot be recovered during the game.");
                System.out.println("(7) If somebody sacrifices their Ace battle card, they recover");
                System.out.println("all of their lost health cards.");
                System.out.println("(8) If someone's Ace health card is taken, they recover their last");
                System.out.println("two taken health cards. However, if less than 2 of their health cards");
                System.out.println("have been taken, then they do not recover any health cards at all.");
                System.out.println("(9) This process repeats until somebody loses.");
                System.out.println("(10) If you accidentally sacrifice your King battle card, or lose");
                System.out.println("your King, Queen, and Jack health cards, you lose.");
                System.out.println("_____________________________________________________________");
                System.out.println();
                System.out.println("Type [1] when you are finished reading.");
                int finished = keyboard.nextInt();
                while (finished != 1) {
                    finished = keyboard.nextInt();
                }
                mainGame(spades, clubs, hearts, diamonds, chosenCard, healthCounter);
            default:
                mainGame(spades, clubs, hearts, diamonds, chosenCard, healthCounter);
        }
    }

    public static int drawCard(boolean cards[], int previous[], boolean user) {
        int number = (int) (Math.random() * 100);
        if (user) {
            while (number > 12 || cards[number] || number == previous[0]) {
                number = (int) (Math.random() * 100);
            }
        } else {
            while (number > 12 || cards[number] || number == previous[1]) {
                number = (int) (Math.random() * 100);
            }
        }
        return number;
    }

    public static int cardSelector(boolean cards[]) {
        int number = (int) (Math.random() * 100);
        while (number > 12 || cards[number]) {
            number = (int) (Math.random() * 100);
        }
        return number;
    }

    public static String royaltyCounter(boolean hearts[]) {
        String[] royalty = new String[3];
        if (hearts[12]) {
            royalty[0] = "KING   ";
        } else {
            royalty[0] = "";
        }
        if (hearts[11]) {
            royalty[1] = "QUEEN   ";
        } else {
            royalty[1] = "";
        }
        if (hearts[10]) {
            royalty[2] = "JACK   ";
        } else {
            royalty[2] = "";
        }
        if (!hearts[10] && !hearts[11] && !hearts[12]) {
            return "Royalty Cards captured: NONE";
        } else {
            return ("Royalty Cards captured: " + royalty[0] + royalty[1] + royalty[2]);
        }
    }

    public static String cardIdentity(int card) {
        switch (card) {
            case 0:
                return "ACE";
            case 1:
                return "TWO";
            case 2:
                return "THREE";
            case 3:
                return "FOUR";
            case 4:
                return "FIVE";
            case 5:
                return "SIX";
            case 6:
                return "SEVEN";
            case 7:
                return "EIGHT";
            case 8:
                return "NINE";
            case 9:
                return "TEN";
            case 10:
                return "JACK";
            case 11:
                return "QUEEN";
            case 12:
                return "KING";
        }
        return "[ERROR]";
    }

    public static boolean choiceSpeaker(int health) {
        Scanner keyboard;
        keyboard = new Scanner(System.in);
        if (health == 13) {
            return false;
        } else {
            System.out.println("Do you wish to... ");
            System.out.println("[1] - Take one of your opponent's health cards");
            System.out.println("[2] - Sacrifice your next battle card to save one of your health cards");
            int answer = keyboard.nextInt();
            switch (answer) {
                case 1:
                    return false;
                case 2:
                    return true;
                default:
                    return false;
            }
        }
    }

    public static boolean gameEnd(boolean[] hearts, boolean[] clubs, boolean[] spades, boolean[] diamonds) {
        if ((hearts[10] && hearts[11] && hearts[12]) || diamonds[12]) {
            System.out.println("You won!");
            return true;
        } else if ((clubs[10] && clubs[11] && clubs[12]) || spades[12]) {
            System.out.println("You lost!");
            if (clubs[10] && clubs[11] && clubs[12]) {
                System.out.println("All your Royalty health cards were taken!");
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean enemyChoice(int health) {
        int number = (int) (Math.random() * 100);
        int probability;
        if (health > 9) {
            probability = 5;
        } else if (health > 5) {
            probability = 3;
        } else {
            probability = 2;
        }
        if (health == 13) {
            return false;
        } else {
            while (number > probability) {
                number = (int) (Math.random() * 100);
            }
            return (number == 1);
        }
    }
    public static int aceTaken (boolean[] cards){
        int number = (int) (Math.random() * 100);
        while (number == 0 || !cards[number] || number > 12) {
            number = (int) (Math.random() * 100);
        }
        return number;
    }
}
