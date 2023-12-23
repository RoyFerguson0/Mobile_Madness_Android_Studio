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
        System.out.println("YOU GOT TO HERERERERERE");
        Log.i("FIRST MESSAGE", "YOU GOT TO HERERERERERERE");
        while(retry.equals("retry")) {
            this.firstNumber = randomNumberMaker.nextInt(upperLimit);
            this.secondNumber = randomNumberMaker.nextInt(upperLimit);

            System.out.println("FirstNumber is :::::::: " + this.firstNumber);
            Log.i("FIRST Number", "FirstNumber is :::::::: " + this.firstNumber);
            Log.i("SECOND Number", "SecondNumber is :::::::: " + this.secondNumber);

            // Get the Operator
            try {
                operator = RandomOperator(this.firstNumber, this.secondNumber);
                Log.i("Operator", "Operator is :::::::: " + operator);
                retry = operator;
            }catch (Exception ex){
                retry = "retry";
            }
        }


        if (!retry.equals("retry")) {

            // Get the Answer to Question
            this.answer = QuestionResult(this.firstNumber, operator, this.secondNumber);

           // this.answer = this.firstNumber + this.secondNumber;


            this.questionPhase = firstNumber + operator + secondNumber + " = ";

            this.answerPosition = randomNumberMaker.nextInt(4);
            this.answerArray = new int[]{0, 1, 2, 3};

            this.answerArray[0] = answer + 1;
            this.answerArray[1] = answer + 10;
            this.answerArray[2] = answer - 5;
            this.answerArray[3] = answer + 2;

            this.answerArray = shuffleArray(this.answerArray);

            answerArray[answerPosition] = answer;
        }
    }

    public int QuestionResult(int firstNumber, String operator, int secondNumber){

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

        switch (random) {
            case 1:
                //result.setText("+");
                // TextView answer = (TextView) findViewById(R.id.txtResult);
               // questionAnswer = firstNumber + secondNumber;

                Log.i("+ OPERATOR", "+ Operator");
                return "+";
            case 2:

               // result.setText("-");
                //TextView answer1 = (TextView) findViewById(R.id.txtResult);

                int ans = firstNumber - secondNumber;

                if (ans < 0) {
               //     startUp();
                    Log.i("- OPERATOR", "- Operator ::::::: " + ans);
                    return "retry";
                } else {
                    // answer1.setText(" = " + Integer.toString(ans));
               //     questionAnswer = Integer.parseInt(result1.getText().toString()) - Integer.parseInt(result2.getText().toString());
                    Log.i("- OPERATOR2", "- Operator ::::::: " + ans);
                    return "-";
                }
            case 3:
            //    result.setText("*");
                //TextView answer2 = (TextView) findViewById(R.id.txtResult);
            //    questionAnswer = Integer.parseInt(result1.getText().toString()) * Integer.parseInt(result2.getText().toString());
                Log.i("* OPERATOR", "* Operator ::::::: ");
                return "*";
             //   break;
            case 4:
           //     result.setText("/");
                // TextView answer3 = (TextView) findViewById(R.id.txtResult);
                questionAnswer = firstNumber / secondNumber;

                FindWholeNum = firstNumber % secondNumber;
                if (FindWholeNum != 0) {
                   // startUp();
                    Log.i("/ OPERATOR", "/ Operator ::::::: " + questionAnswer);
                    return "retry";
                } else {
                 //   questionAnswer = Integer.parseInt(result1.getText().toString()) / Integer.parseInt(result2.getText().toString());
                    if(questionAnswer >= 0) {
                        Log.i("/ OPERATOR2", "/ Operator ::::::: " + questionAnswer);
                        return "/";
                    }else{
                        Log.i("/ OPERATOR3", "/ Operator ::::::: " + questionAnswer);
                        return "retry";
                    }
                }

          //      break;
            default:
                //result.setText("Error");
                return "retry";
            //    break;
        }
    }

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
