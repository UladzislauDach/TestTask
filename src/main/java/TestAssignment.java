import java.io.IOException;
import java.util.*;

public class TestAssignment {
    public static void main(String[] args) throws IOException {
        Map<Character, Integer[][]> mapMatrix;
        String mathOperation;
        mapMatrix = stringToMapMatrix(argsToList(args));
        mathOperation = backPolishWritten(args[args.length - 1]);
        System.out.println(mathOperation);

//        Integer[][] result = getResult(mapMatrix, mathOperation);
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result[0].length; j++) {
//                System.out.print(" " + result[i][j] + " ");
//            }
//            System.out.println();
//        }
    }

    // преобразование в обртную польскую запись
    public static String backPolishWritten(String operation) {
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
    public static List<String> argsToList(String[] array) {
        List<String> list = new ArrayList<>();
        for (String line : array) {
            if (!line.equals("")) {
                list.add(line);
            } else break;
        }
        return list;
    }

    // умножение матриц a и b
    public static Integer[][] multiplication(Integer[][] a, Integer[][] b) {
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
                Integer s = 0;
                for (int k = 0; k < a[0].length; k++) {
                    s += rowI[k] * columnJ[k];
                }
                result[i][j] = s;
            }
        }
        return result;
    }

    // из строки преобразую в Map<Character, Integer[][]> (ключ - имя матрицы, значение - матрица)
    public static Map<Character, Integer[][]> stringToMapMatrix(List<String> inputList) {
        Map<Character, Integer[][]> allMatrixMap = new HashMap<>();
        int matrixWidth = 0;
        int matrixHeight = 0;
        Integer[][] matrix;
        for (String input : inputList) {
            String[] matrixLine = input.substring(input.indexOf('[') + 1, input.indexOf(']')).split("; ");
            String[] matrixLineElement;
            matrixHeight = matrixLine[0].split(" ").length;
            matrixWidth = matrixLine.length;
            matrix = new Integer[matrixWidth][matrixHeight];
            for (int i = 0; i < matrixLine.length; i++) {
                matrixLineElement = matrixLine[i].split(" ");
                for (int j = 0; j < matrixLineElement.length; j++) {
                    matrix[i][j] = Integer.parseInt(matrixLineElement[j]);
                }
            }
            allMatrixMap.put(input.charAt(0), matrix);
        }
        return allMatrixMap;
    }
}
