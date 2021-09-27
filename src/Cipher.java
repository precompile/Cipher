/************************************************************************************
 *                                                                                  *
 *  Author: https://github.com/precompile                                           *
 *  Written: 16/10/2020                                                             *
 *  Last updated: 26/10/2020                                                        *
 *                                                                                  *
 *  Compilation command:   javac Cipher2.java                                       *
 *  Execution command:   java Cipher2                                               *
 *                                                                                  *
 *  Description:    This program uses an encoding method to encrypt messages        *
 *                  that the user inputs, based on the key they give.               *
 *                  Using the decryption method. The user is asked to input         *
 *                  an encrypted message and the unique key in order to see         *
 *                  the original message.                                           *
 *                                                                                  *
 ************************************************************************************/

import java.util.Scanner;

public class Cipher {

    //Alphabetical table [5x5]
    private static final char[][] ENCRYPTION_TABLE = {
            {'A', 'B', 'C', 'D', 'E'},
            {'F', 'G', 'H', 'I', 'J'},
            {'K', 'L', 'M', 'N', 'O'},
            {'P', 'Q', 'R', 'S', 'T'},
            {'U', 'V', 'W', 'X', 'Y'}
    };
    static boolean exit;

    public static void main(String[] args) {
        Cipher cipher = new Cipher();
        cipher.runMenu();
    }

    //Prints the Main menu.
    private static void printMenu() {
        System.out.println("********************************");
        System.out.println("* Encryption/Decryption System *");
        System.out.println("********************************");
        System.out.println("1. Code the message");
        System.out.println("2. Decode the message");
        System.out.println("3. Exit");
        System.out.print("Make your choice: _");
    }

    //Checks if the key contains only letters and returns the result.
    private static boolean checkIfOnlyLetters(String keyword) {
        boolean isKeyOnlyLetters = true;
        for (int i = 0; i < keyword.length(); i++) {
            if (!Character.isLetter(keyword.charAt(i))) {
                isKeyOnlyLetters = false;
                break;
            }
        }
        return isKeyOnlyLetters;
    }

    //Checks if the key contains unique characters and returns the result.
    private static boolean checkIfUnique(String keyword) {
        boolean isKeyUnique = true;
        for (int i = 0; i < keyword.length() - 1; i++) {
            for (int j = i + 1; j < keyword.length(); j++) {
                if (keyword.charAt(i) == keyword.charAt(j)) {
                    isKeyUnique = false;
                    break;
                }
            }
        }
        return isKeyUnique;
    }

    //Encrypt separated characters and then print it in one whole string.
    private static String doEncryption(String plainText) {
        StringBuilder encodedText = new StringBuilder();
        StringBuilder newPlainText = new StringBuilder(plainText);

        for (int i = 0; i < plainText.length(); i++) {
            for (int j = 0; j < ENCRYPTION_TABLE.length; j++) {
                for (int k = 0; k < ENCRYPTION_TABLE.length; k++) {
                    if (plainText.charAt(i) == 'Z') {
                        newPlainText.setCharAt(i, 'Q');         //Makes the 'Z' to 'Q'
                    }
                    if (newPlainText.charAt(i) == ENCRYPTION_TABLE[ j ][ k ]) {
                        encodedText.append(j).append(k);         //Appends the corresponding number to the String.
                    }
                }
            }
        }
        System.out.println(plainText);          //Prints the users input as plain text.
        System.out.println(encodedText);        //Prints the firstly encoded text.

        return encodedText.toString();
    }

    //Checks if the plain text the user inputs is valid
    private static boolean checkIfValid(String plainTextDecode) {
        boolean isPlainTextValid = false;
        for (int i = 0; i < plainTextDecode.length(); i++) {
            if ((plainTextDecode.charAt(i) >= '0' && plainTextDecode.charAt(i) < '5') || (plainTextDecode.charAt(i) == ' ')) {
                isPlainTextValid = true;
            } else {
                isPlainTextValid = false;
            }
        }
        return isPlainTextValid;
    }

    //Sorts the Decrypted array based on the keyword for decryption.
    private static char[][] doDecryptedSorting(char[][] newDecryptedArray, String keywordDecode) {

        int cols = newDecryptedArray[ 0 ].length;
        int rows = newDecryptedArray.length;

        char[] charKeywordDecode = keywordDecode.toCharArray();

        //Bubble sort but the array gets sorted
        for (int i = 0; i < cols - 1; i++) {
            for (int j = i + 1; j < cols; j++) {
                if (charKeywordDecode[ i ] == newDecryptedArray[ 0 ][ j ]) {
                    for (int l = 0; l < rows; l++) {
                        char tempCh = newDecryptedArray[ l ][ j ];          //Temporary value for the character swap.

                        newDecryptedArray[ l ][ j ] = newDecryptedArray[ l ][ i ];
                        newDecryptedArray[ l ][ i ] = tempCh;
                    }
                }
            }
        }

        return newDecryptedArray;
    }

    //sorts the character array alphabetically
    private char[][] doSorting(char[][] newEncryptedArray) {

        int cols = newEncryptedArray[ 0 ].length;
        int rows = newEncryptedArray.length;

        //Bubble sort the array.
        for (int i = 0; i < cols - 1; i++) {
            for (int j = i + 1; j < cols; j++) {
                if (newEncryptedArray[ 0 ][ i ] > newEncryptedArray[ 0 ][ j ]) {        //Sorts the first row alphabetically.
                    char tempChar = newEncryptedArray[ 0 ][ j ];                //Temporary var for the character swap.

                    newEncryptedArray[ 0 ][ j ] = newEncryptedArray[ 0 ][ i ];
                    newEncryptedArray[ 0 ][ i ] = tempChar;

                    for (int k = 1; k < rows; k++) {
                        char tempNum = newEncryptedArray[ k ][ i ];

                        newEncryptedArray[ k ][ i ] = newEncryptedArray[ k ][ j ];
                        newEncryptedArray[ k ][ j ] = tempNum;
                    }
                }
            }
        }
        return newEncryptedArray;
    }

    //Menu function
    private void runMenu() {

        while (!exit) {
            Scanner scan = new Scanner(System.in);
            printMenu();

            String stringOption;

            stringOption = scan.nextLine();

            //Checking if the input is valid (between 1 and 3).
            while (!(stringOption.equals("1")) && !(stringOption.equals("2")) && !(stringOption.equals("3"))) {
                System.out.println("You must enter a number between 1 and 3 to indicate your selection.");
                System.out.print("Make your choice: _");
                stringOption = scan.nextLine();

            }
            int option = Integer.parseInt(stringOption);
            selection(option);          //Passing the option to the selection method.
        }
        System.exit(0);         //Program exit.
    }

    private void selection(int option) {
        Scanner scan = new Scanner(System.in);
        String plainText, keyword, plainTextDecode, keywordDecode;
        boolean isKeyOnlyLetters;
        boolean isKeyUnique;

        switch (option) {

            //Option 1: Code the message.
            case 1: {

                System.out.print("Please enter your message: ");
                plainText = scan.nextLine();
                plainText = plainText.toUpperCase();

                String plainTextNoSpecialChars = "";            //A string for the plain text without spaces and numbers or other symbols.

                for (int i = 0; i < plainText.length(); i++) {

                    if (plainText.charAt(i) < 65 || plainText.charAt(i) > 90) {
                        plainTextNoSpecialChars += "";

                    } else {
                        plainTextNoSpecialChars += plainText.charAt(i);
                    }
                }

                do {
                    System.out.print("Please enter your keyword: ");
                    keyword = scan.nextLine();
                    keyword = keyword.toUpperCase();

                    //Returns if keyword is only letters.
                    isKeyOnlyLetters = checkIfOnlyLetters(keyword);
                    //Returns if keyword is unique.
                    isKeyUnique = checkIfUnique(keyword);

                } while (!isKeyOnlyLetters || !isKeyUnique);


                System.out.println();
                //Encrypts the plain text based on the alphabetical character positions from ENCRYPTION_TABLE
                String encryptedText = doEncryption(plainTextNoSpecialChars);


                int col = keyword.length();
                int encryptedLength = encryptedText.length();

                int extraRows;      //Determines how many extra rows the Array needs.

                if (encryptedLength % col == 0) {
                    extraRows = 1;
                } else {
                    extraRows = 2;
                }
                int rows = (encryptedLength / col + extraRows);


                char[][] newEncryptedArray = new char[ rows + 2 ][ col ];

                //Inserts the keyword in the first row of the array.
                for (int j = 0; j < col; j++) {
                    newEncryptedArray[ 0 ][ j ] = keyword.charAt(j);
                }

                //Inserts the rest of the encrypted message in the rest of the array.
                int cnt = 0;
                for (int i = 1; i < rows; i++) {
                    for (int j = 0; j < col; j++) {
                        newEncryptedArray[ i ][ j ] = encryptedText.charAt(cnt);
                        cnt++;
                        if (cnt >= encryptedLength) {
                            break;
                        }

                    }
                }

                //Prints the unsorted array.
                System.out.println();
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < col; j++) {
                        System.out.print(newEncryptedArray[ i ][ j ] + " ");
                    }
                    System.out.println();
                }

                char[][] sortedEncryptedArr = doSorting(newEncryptedArray);

                //Prints the sorted array.
                System.out.println();
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < col; j++) {
                        System.out.print(sortedEncryptedArr[ i ][ j ] + " ");
                    }
                    System.out.println();
                }

                //Prints the encoded message.
                System.out.println("\nEncoded message is:");
                for (int j = 0; j < keyword.length(); j++) {
                    for (int i = 1; i < sortedEncryptedArr.length; i++) {
                        System.out.print(sortedEncryptedArr[ i ][ j ]);
                        if (i == sortedEncryptedArr.length - 1) {
                            System.out.print(" ");
                        }
                    }
                }
                System.out.println("\n");

                break;
            }

            //Option 2: Decode the message.
            case 2: {

                System.out.print("Please enter your encrypted message: ");
                plainTextDecode = scan.nextLine();

                boolean isPlainTextValid;
                isPlainTextValid = checkIfValid(plainTextDecode);

                while (!isPlainTextValid) {

                    System.out.print("Please enter your encrypted message: ");
                    plainTextDecode = scan.nextLine();

                    isPlainTextValid = checkIfValid(plainTextDecode);
                }

                do {
                    System.out.print("Please enter your keyword: ");
                    keywordDecode = scan.nextLine();
                    keywordDecode = keywordDecode.toUpperCase();


                    isKeyOnlyLetters = checkIfOnlyLetters(keywordDecode);

                    isKeyUnique = checkIfUnique(keywordDecode);

                    //Checks if the keyword is unique and only letters.
                }while (!isKeyOnlyLetters || !isKeyUnique);


                int col = keywordDecode.length();
                int decryptedLength = plainTextDecode.length();


                int rows = (decryptedLength / col) + 1;


                char[][] newDecryptedArray = new char[ rows ][ col ];

                //Inserts keyword into first row of array.
                for (int j = 0; j < col; j++) {
                    newDecryptedArray[ 0 ][ j ] = keywordDecode.charAt(j);
                }

                //Sorts the first line of the array (keyword) with bubble sort.
                char tempD;
                for (int i = 0; i < newDecryptedArray[ 0 ].length - 1; i++) {
                    for (int j = i + 1; j < newDecryptedArray[ 0 ].length; j++) {
                        if (newDecryptedArray[ 0 ][ i ] > newDecryptedArray[ 0 ][ j ]) {
                            tempD = newDecryptedArray[ 0 ][ j ];
                            newDecryptedArray[ 0 ][ j ] = newDecryptedArray[ 0 ][ i ];
                            newDecryptedArray[ 0 ][ i ] = tempD;
                        }
                    }
                }

                //Inserts the plain text in the array.
                int cnt = 0;
                for (int j = 0; j < col; j++) {
                    for (int i = 1; i < rows; i++) {
                        if (cnt >= plainTextDecode.length()) {
                            break;
                        }
                        if (plainTextDecode.charAt(cnt) == ' ' && i == 1) {
                            newDecryptedArray[ i ][ j ] = plainTextDecode.charAt(cnt + 1);
                            cnt++;
                        } else {
                            newDecryptedArray[ i ][ j ] = plainTextDecode.charAt(cnt);
                        }
                        cnt++;

                    }
                }

                //Prints the first array.
                System.out.println();
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < col; j++) {
                        System.out.print(newDecryptedArray[ i ][ j ] + " ");
                    }
                    System.out.println();
                }

                //Inserts the sorted Decrypted message based on the keyword the user gave in the array sortedDecryptedArr.
                char[][] sortedDecryptedArr = doDecryptedSorting(newDecryptedArray, keywordDecode);

                //Prints out the sorted decrypted array.
                System.out.println();
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < col; j++) {
                        System.out.print(sortedDecryptedArr[ i ][ j ] + " ");
                    }
                    System.out.println();
                }

                //Inserts the array row wise in the decodedText string.
                String decodedText = "";
                for (int i = 1; i < rows; i++) {
                    for (int j = 0; j < col; j++) {
                        decodedText += sortedDecryptedArr[ i ][ j ];
                    }
                }
                System.out.println();
                System.out.println(decodedText);

                //Prints the decoded message.
                System.out.println("\nDecoded message is: ");
                for (int i = 0; i < decodedText.length() - 1; i += 2) {
                    int a = (decodedText.charAt(i) - '0');
                    int b = (decodedText.charAt(i + 1) - '0');
                    if (a < ENCRYPTION_TABLE.length && b < ENCRYPTION_TABLE.length && a >= 0 && b >= 0) {
                        System.out.print(ENCRYPTION_TABLE[ a ][ b ]);
                    }
                }
                System.out.println("\n");

                break;
            }

            //Option 3: Exit.
            case 3:
                exit = true;
                break;
        }
    }
}