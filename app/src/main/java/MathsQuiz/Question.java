package MathsQuiz;

import android.util.Log;

import java.util.Random;

public class Question {
    private int firstNumber;
    private int secondNumber;



    private int answer;

    // there are four possible choices for the user to pick from.
    private int[] answerArray;

    // which of the four positions is correct, 0,1,2 or 3.
    private int answerPosition;

    // the Maximum value of firstNumber or secondNumber
    private int upperLimit;

    //string output of the problem. e.g. "4 + 9 ="
    private String questionPhase;

    public Question(int upperLimit){
        this.upperLimit = upperLimit;
        Random randomNumberMaker = new Random();

        String retry = "retry";
        String operator = "";

        while(retry.equals("retry")) {
            this.firstNumber = randomNumberMaker.nextInt(upperLimit);
            this.secondNumber = randomNumberMaker.nextInt(upperLimit);

            // Get the Operator
            try {
                operator = RandomOperator(this.firstNumber, this.secondNumber);
                retry = operator;
            }catch (Exception ex){
                retry = "retry";
            }
        }


        if (!retry.equals("retry")) {

            // Get the Answer to Question
            this.answer = QuestionResult(this.firstNumber, operator, this.secondNumber);

            // Get the Question
            this.questionPhase = firstNumber + operator + secondNumber + " = ";

            // Get the random number out of four which is where the correct answer will be stored.
            this.answerPosition = randomNumberMaker.nextInt(4);
            this.answerArray = new int[]{0, 1, 2, 3};

            // Add to the answer values
            this.answerArray[0] = answer + 1;
            this.answerArray[1] = answer + 10;
            this.answerArray[2] = answer - 5;
            this.answerArray[3] = answer + 2;

            // Shuffle array
            this.answerArray = shuffleArray(this.answerArray);
            // add the answerPosition as the correct answer to question
            answerArray[answerPosition] = answer;
        }
    }

    public int QuestionResult(int firstNumber, String operator, int secondNumber){

        // Returning the Question answer depending on operator
        switch (operator){
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "/":
                return firstNumber / secondNumber;
            case "*":
                return firstNumber * secondNumber;
            default:
                return 1;
        }
    }


    public String RandomOperator(int firstNumber, int secondNumber){
        double FindWholeNum;
        final int min = 1;
        final int max = 4;
        double questionAnswer = 0;
        final int random = new Random().nextInt((max - min) + 1) + min;

        // Getting the random operator making sure that calculation can be done as it shouldn't give negative answers or decimal answers.
        switch (random) {
            case 1:
                return "+";
            case 2:
                int ans = firstNumber - secondNumber;
                if (ans < 0) {
                    return "retry";
                } else {
                    return "-";
                }
            case 3:
                return "*";
            case 4:
                questionAnswer = firstNumber / secondNumber;

                FindWholeNum = firstNumber % secondNumber;
                if (FindWholeNum != 0) {
                    return "retry";
                } else {
                    if(questionAnswer >= 0) {
                        return "/";
                    }else{
                        return "retry";
                    }
                }
            default:
                return "retry";
        }
    } // end RandomOperator

    private int [] shuffleArray(int[] array){
        int index, temp;
        Random randomNumberGenerator = new Random();

        for(int i = array.length - 1; i > 0; i--){
            index = randomNumberGenerator.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public int getFirstNumber(){
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber){
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber(){
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber){
        this.secondNumber = secondNumber;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int[] getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(int[] answerArray) {
        this.answerArray = answerArray;
    }

    public int getAnswerPosition() {
        return answerPosition;
    }

    public void setAnswerPosition(int answerPosition) {
        this.answerPosition = answerPosition;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getQuestionPhase() {
        return questionPhase;
    }

    public void setQuestionPhase(String questionPhase) {
        this.questionPhase = questionPhase;
    }



}
