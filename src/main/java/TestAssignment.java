import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class TestAssignment {
    public static void main(String[] args) throws IOException {
        Map<Character, Integer[][]> mapMatrix;
        List<String> strings;
        String mathOperation;
        mathOperation = args[args.length - 1];

        strings = argsToList(args);
        mapMatrix = stringToMapMatrix(strings);

        Integer[][] result = multiplication(mapMatrix.get('A'), mapMatrix.get('B'));

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(" " + result[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(mathOperation);
    }

    public static List<String> argsToList(String[] array) {
        List<String> list = new ArrayList<>();
        for (String line : array) {
            if (!line.equals("")) {
                list.add(line);
            } else break;
        }
        return list;
    }

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

    public static Map<Character, Integer[][]> stringToMapMatrix(List<String> inputList) {
        Map<Character, Integer[][]> allMatrixMap = new HashMap<>();
        int matrixWidth =0;
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
