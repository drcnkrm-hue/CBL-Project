# CBL-Project
BUILDING THE CODE:
Open 4 classes excluding GUI, each one having a different task.
Tasks are:
1. Randomizing the word from a database;
2. Saving the user statistics to a file and reading them off to display them on the GUI;
3. Initializing / handling the logic behind the coloring of characters of the inputed 3. words and / handling the validity o users' guess;
4. Executing the whole program, which means running all the classes in a consisten manner.
2.1. Tracking user statistics requires file handling;
4.1 Executing the program is rather straight forward as all highly complex code blocks are in other classes but nevertheless, it is responsible from getting the user input.
1.1 Randomizing the word also requires file handling and using the built in function of java which allows us to randomly select a word from a database.
3.1 The logic behind guess evaluation could have been differently with JUnit Testing but our program works with rather complicated for and while loops to handle coloring of letters. It also uses arrays to keep track of the guesses and characters.

RUNNING THE CODE / PLAYING THE PROGRAM
Input a 5 letter word to start the game.
If a letter in the word becomes yellow, the order of the letter is wrong but the letter is in the mystery word.(randomly chosen by the program)
If a letter in the word lights up green, the order of the letter is correct.
If it does not light up at all / lights up as red, the letter is not in the word outright.
Your aim is to guess a word such that all its letters light up green.