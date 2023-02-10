import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class Main {
    static int romToDig(char c, char b){
        int s = 0, sub = 0;
        switch (c) {
            case 'I' -> s = 1;
            case 'V' -> s = 5;
            case 'X' -> s = 10;
            case 'L' -> s = 50;
            case 'C' -> s = 100;
            case 'D' -> s = 500;
            case 'M' -> s = 1000;
        }

        sub = switch (b) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> 0;
        };

        if(s >= sub)
            return s;
        else
            return -s;
    }

    static int romanToInt(String s) {
        int sum = 0;
        s += " ";
        if(s.length() == 1)
            return switch (s.charAt(0)) {
                case 'I' -> 1;
                case 'V' -> 5;
                case 'X' -> 10;
                case 'L' -> 50;
                case 'C' -> 100;
                default -> 1000;
            };

        for(int i=0;i<s.length()-1;i++)
        {
            int dig = 0;
            dig = romToDig(s.charAt(i), s.charAt(i+1));
            sum = sum + dig;
        }
        return sum;
    }

    public static String intToRoman(int num) {
        return List.of("", "M", "MM", "MMM").get(num / 1000) +
                List.of("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM").get(num / 100 % 10) +
                List.of("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC").get(num / 10 % 10) +
                List.of("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX").get(num % 10);
    }

    static boolean checkForRom(String number){
        String tmp = "IVXLCM";
        for(int i=0;i<5;i++)
            if(tmp.charAt(i) == number.charAt(0)){
                return true;
            }
        return false;
    }

    static boolean checkInput(String[] partsOfExpression){
        return !(checkForRom(partsOfExpression[0]) != checkForRom(partsOfExpression[2]) | partsOfExpression[1].length() > 1 | partsOfExpression.length>3);
    }

    static boolean checkAnswer(int answer, boolean isRomExpression) {
        if(isRomExpression){
            if(answer<=0) {
                return false;
            }
        }
        return true;
    }

    static String calc(String input){
        String[] partsOfExpression = input.split(" ");

        boolean isRomExpression = checkForRom(input);

        boolean isRightInput = checkInput(partsOfExpression);
        if(!isRightInput) {
            return "false";
        }

        int firstNumber, secondNumber;

        char sign = partsOfExpression[1].charAt(0);

        if(isRomExpression){
            firstNumber = romanToInt(partsOfExpression[0]);
            secondNumber = romanToInt(partsOfExpression[2]);
        }
        else{
            firstNumber = Integer.parseInt(partsOfExpression[0]);
            secondNumber = Integer.parseInt(partsOfExpression[2]);
        }

        if(firstNumber>10 | secondNumber>10|firstNumber<0|secondNumber<0)
            return "false";

        int answer = switch (sign) {
            case '+' -> firstNumber + secondNumber;
            case '-' -> firstNumber - secondNumber;
            case '*' -> firstNumber * secondNumber;
            default -> firstNumber / secondNumber;
        };

        if(!checkAnswer(answer, isRomExpression)){
            return "false";
        }

        if(isRomExpression)
            return intToRoman(answer);
        else
            return Integer.toString(answer);
    }

    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        while(true){
            String expression = sc.nextLine();
            String answer = calc(expression);
            if(answer.equals("false"))
                try{
                    throw new IOException();
                }catch (IOException e) {
                    System.out.println("Неправильно введены исходные данные!");
                    break;
                }
            else
                System.out.println(answer);
        }
    }
}