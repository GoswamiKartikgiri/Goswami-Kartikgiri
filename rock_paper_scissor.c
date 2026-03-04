#include <stdio.h>


// Function to get computer's choice
int getComputerChoice() {
    srand(time(0));
    return rand() % 3; // Generate a random number between 0 and 2
}

// Function to convert choice index to a string
const char* choiceToString(int choice) {
    switch (choice) {

        case 0: 
        return "Rock";

        case 1:
        return "Paper";

        case 2:
        return "Scissors";

        default: return "Invalid";
    }
}

// Function to determine the winner
int determineWinner(int userChoice, int computerChoice) {
    // 0 -> Rock, 1 -> Paper, 2 -> Scissors
    // Returns -1 for a draw, 0 for user win, 1 for computer win
    if (userChoice == computerChoice)
        return -1;
    if (userChoice == 0 && computerChoice == 2 ||
        userChoice == 1 && computerChoice == 0 ||
        userChoice == 2 && computerChoice == 1)
        return 0;
    return 1;
}

int main() {
    int userChoice, computerChoice, result;
    int userScore = 0, computerScore = 0, winStreak = 0;
    char playAgain;
    char playerName[50];

    printf("Welcome to Rock, Paper, Scissors!\n");

    printf("Enter your name: ");
    scanf("%s", playerName);

    // Rules
    printf("Hello, %s!\n", playerName);
    printf("Rules:\n");
    printf("0 - Rock\n1 - Paper\n2 - Scissors\n");
    printf("You will be playing against the computer.\n");
    printf("The first to reach 3 wins will be declared the overall winner.\n");

    do {
        printf("\nEnter your choice: ");
        if (scanf("%d", &userChoice) != 1) {
            // Handle invalid input
            printf("Invalid input. Please enter a number.\n");
            fflush(stdin); // Clear input buffer
            continue;
        }

        // Validate user input
        if (userChoice < 0 || userChoice > 2) {
            printf("Invalid choice. Please choose between 0 and 2.\n");
            continue;
        }

        computerChoice = getComputerChoice();

        printf("You chose: %s\n", choiceToString(userChoice));
        printf("Computer chose: %s\n", choiceToString(computerChoice));

        result = determineWinner(userChoice, computerChoice);

        if (result == -1) {
            printf("It's a draw!\n");
        } else if (result == 0) {
            printf("Congratulations, %s! You win this round!\n", playerName);
            userScore++;
            winStreak++;
        } else {
            printf("Computer wins this round! Better luck next time, %s!\n", playerName);
            computerScore++;
            winStreak = 0;
        }

        printf("Your Score: %d | Computer Score: %d\n", userScore, computerScore);
        printf("Current Win Streak: %d\n", winStreak);

        if (userScore == 3) {
            printf("Congratulations, %s! You are the winner!\n", playerName);
            break;
        } else if (computerScore == 3) {
            printf("Computer is the overall winner! Better luck next time, %s!\n", playerName);
            break;
        }

        printf("Do you want to play again? (y/n): ");
        scanf(" %c", &playAgain); // Notice the space before %c to consume any leftover newline character

        while (playAgain != 'y' && playAgain != 'Y' && playAgain != 'n' && playAgain != 'N') {
            printf("Invalid response. Please enter 'y' or 'n': ");
            scanf(" %c", &playAgain);
        }

        if (playAgain == 'n' || playAgain == 'N')
            break;

    } while (1);

    printf("Thank you for playing Rock, Paper, Scissors, %s! See you next time!\n", playerName);

    return 0;
}
