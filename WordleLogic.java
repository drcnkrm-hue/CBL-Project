public class WordleLogic {

    // Calculate the feedback for a guess
    public String[] getFeedback(
            String secretWord, String guess) {

        char[] answer = secretWord.toCharArray();

        boolean[] used = new boolean[5];

        String[] result = {
                "GRAY", "GRAY", "GRAY", "GRAY", "GRAY"
        };

        // Step 1: Find letters in the correct position
        for (int i = 0; i < 5; i++) {

            if (guess.charAt(i) == answer[i]) {

                result[i] = "GREEN";
                used[i] = true;
            }
        }

        // Step 2: Find correct letters in the wrong position
        for (int i = 0; i < 5; i++) {

            if (result[i].equals("GREEN")) {
                continue;
            }

            for (int j = 0; j < 5; j++) {

                if (!used[j]
                        && guess.charAt(i) == answer[j]) {

                    result[i] = "YELLOW";
                    used[j] = true;
                    break;
                }
            }
        }

        return result;
    }
}