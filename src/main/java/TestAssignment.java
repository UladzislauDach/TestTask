import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TestAssignment {
    public static void main(String[] args) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        String operation;
        Map<Character, Integer[][]> mapMatrix;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = br.readLine();
            if (!input.equals("")) { //it's ok?
                System.out.println(matrixValidate(input));
                strings.add(input);
            } else break;
        }
        operation = br.readLine();
        mapMatrix = stringToMapMatrix(strings);

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

    public static boolean matrixValidate(String s) {
        return Pattern.matches("[A-Z]=\\[(\\d+;?\\s?)+]", s);
    }

}
