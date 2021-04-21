import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestAssignment {
    public static void main(String[] args) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        String mathOperation;
        Map<Character, Integer[][]> mapMatrix;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = br.readLine();
            if (!input.equals("")) { //it's ok?
                strings.add(input);
            } else break;
        }
        mathOperation = br.readLine();
        mapMatrix = stringToMapMatrix(strings);

        Integer[][] result = multiplication(mapMatrix.get('E'), mapMatrix.get('E'));
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(" " + result[i][j] + " ");
            }
            System.out.println();
        }
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

    public static Map<Character, Integer[][]> stringToMapMatrix(ArrayList<String> inputList) {
        Map<Character, Integer[][]> allMatrixMap = new HashMap<>();
        int matrixWidth;
        int matrixHeight;
        Integer[][] matrix;
        for (String input : inputList) {
            String[] matrixLine = input.substring(input.indexOf('[') + 1, input.indexOf(']')).split("; ");
            String[] matrixLineElement;
            matrixWidth = matrixLine[0].split(" ").length;
            matrixHeight = matrixLine.length;
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
