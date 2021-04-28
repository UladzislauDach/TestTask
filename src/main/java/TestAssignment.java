import java.util.*;

public class TestAssignment {
    public static void main(String[] args) {
        Map<Character, Integer[][]> mapMatrix = stringToMapMatrix(argsToList(args));
        String mathOperationInReversePolishWritten = reversePolishWritten(args[args.length - 1]);
        Integer[][] result = calculate(mathOperationInReversePolishWritten, mapMatrix);
        matrixToString(result);
    }

    //matrix to string
    public static void matrixToString(Integer[][] result) {
        System.out.print("[");
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j]);
                if (j != result[0].length - 1) System.out.print(" ");
            }
            if (i != result.length - 1) System.out.print("; ");
        }
        System.out.print("]");
    }

    // считаем обратную польскую запись
    public static Integer[][] calculate(String mathOperation, Map<Character, Integer[][]> mapMatrix) {
        char[] operationArr = mathOperation.toCharArray();
        Stack<Integer[][]> stack = new Stack<>();

        for (char c : operationArr) {
            if (Character.isUpperCase(c)) {
                stack.push(mapMatrix.get(c));
            } else if (c == '+' || c == '-' || c == '*') {
                switch (c) {
                    case '+':
                        stack.push(addition(stack.pop(), stack.pop()));
                        break;
                    case '-':
                        stack.push(subtraction(stack.pop(), stack.pop()));
                        break;
                    case '*':
                        Integer[][] a = stack.pop();
                        Integer[][] b = stack.pop();
                        stack.push(multiplication(b, a));
                        break;
                }
            } else throw new IllegalArgumentException("Incorrect math operation input");

        }
        return stack.pop();
    }

    // преобразование в обртную польскую запись
    private static String reversePolishWritten(String operation) {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        char[] input = operation.toCharArray();
        for (int i = 0; i < input.length; i++) {
            switch (input[i]) {
                case '+':
                case '-':
                    while (!stack.empty() && stack.peek() == '*') {
                        while (!stack.empty()) output.append(stack.pop());
                    }
                    if (!stack.empty()) output.append(stack.pop());
                    stack.push(input[i]);
                    break;

                case '*':
                    if (!stack.isEmpty() && stack.peek() == '*') output.append(input[i]);
                    else stack.push(input[i]);
                    break;
                default:
                    output.append(input[i]);
            }
        }
        while (!stack.isEmpty()) output.append(stack.pop());
        return output.toString();
    }

    //массив в лист до пустой строки
    private static List<String> argsToList(String[] array) {
        List<String> list = new ArrayList<>();
        for (String line : array) {
            if (!line.equals("")) {
                list.add(line);
            } else break;
        }
        return list;
    }

    // сложение матриц
    private static Integer[][] addition(Integer[][] a, Integer[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("matrices cannot be addition");
        }
        Integer[][] result = new Integer[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    // разность матриц
    private static Integer[][] subtraction(Integer[][] a, Integer[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("matrices cannot be subtraction");
        }
        Integer[][] result = new Integer[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    // произведение матриц a и b
    private static Integer[][] multiplication(Integer[][] a, Integer[][] b) {
        if (a[0].length != b.length) {
            throw new IllegalArgumentException("matrices cannot be multiplied");
        }
        Integer[][] result = new Integer[a.length][b[0].length];
        Integer[] columnJ = new Integer[a[0].length];
        for (int j = 0; j < b[0].length; j++) {
            for (int k = 0; k < a[0].length; k++) {
                columnJ[k] = b[k][j];
            }
            for (int i = 0; i < a.length; i++) {
                Integer[] rowI = a[i];
                int temp = 0;
                for (int k = 0; k < a[0].length; k++) {
                    temp += rowI[k] * columnJ[k];
                }
                result[i][j] = temp;
            }
        }
        return result;
    }

    // из строки преобразую в Map<Character, Integer[][]> (ключ - имя матрицы, значение - матрица)
    private static Map<Character, Integer[][]> stringToMapMatrix(List<String> inputList) {
        Map<Character, Integer[][]> allMatrixMap = new HashMap<>();
        int matrixWidth = 0;
        int matrixHeight = 0;
        Integer[][] matrix;
        for (String input : inputList) {
            String[] matrixLine;
            try {
                matrixLine = input.substring(input.indexOf('[') + 1, input.indexOf(']')).split("; ");
            } catch (StringIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("incorrect matrix format");
            }

            String[] matrixLineElement;
            matrixHeight = matrixLine[0].split(" ").length;
            matrixWidth = matrixLine.length;
            matrix = new Integer[matrixWidth][matrixHeight];
            for (int i = 0; i < matrixLine.length; i++) {
                matrixLineElement = matrixLine[i].split(" ");
                for (int j = 0; j < matrixLineElement.length; j++) {
                    try {
                        matrix[i][j] = Integer.parseInt(matrixLineElement[j]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("incorrect symbols into matrix");
                    }
                }
            }
            allMatrixMap.put(input.charAt(0), matrix);
        }
        return allMatrixMap;
    }
}
