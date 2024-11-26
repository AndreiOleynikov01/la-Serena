import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Parser {
    private static final Map<Character, Double> noteValues;
    static
    {
        noteValues = new HashMap<>();
        noteValues.put('C', 16.352);
        noteValues.put('D', 18.354);
        noteValues.put('E', 20.602);
        noteValues.put('F', 21.827);
        noteValues.put('G', 24.500);
        noteValues.put('A', 27.500);
        noteValues.put('B', 30.868);
    }
    public static List<Channel> run(Scanner input)
    {
        List<String[]> tokens = new LinkedList<>();
        while (input.hasNextLine())
        {
            tokens.add(input.next().split(", "));
        }
        List<Channel> channels = new ArrayList<>();
        for (int i = 0; i < tokens.getFirst().length; i++)
        {
            channels.add(new Channel());
        }
        for (String[] line : tokens)
        {
            int i = 0;
            for (String token : line)
            {
                channels.get(i).add(tokenToNote(token));
                i++;
            }
        }
        return channels;
    }

    private static Note tokenToNote(String token)
    {
        int length = Integer.getInteger(token.substring(0, 2));
        double value;
        if (token.charAt(2)==' ')
        {
            value = noteValues.get(token.charAt(3)) * Math.pow(2, Integer.getInteger(token.substring(4)));
        }
        else
        {
            value = diesBemol(token.substring(2));
        }
        return new Note(value, length);
    }

    private static double diesBemol(String token)
    {
        char nextNote = ' ';
        int power = Integer.getInteger(token.substring(2));
        if (token.startsWith("b"))
        {
            if (token.charAt(1)== 'C')
            {
                return noteValues.get((char) (token.charAt(1) - 1)) * Math.pow(2, (power - 1));
            }
            else if(token.charAt(1)== 'F')
            {
                return noteValues.get((char) (token.charAt(1) - 1)) * Math.pow(2, power);
            }
            else if (token.charAt(1)== 'A')
            {
                return noteValues.get('G') * Math.pow(2, power);
            }
            else
            {
                nextNote = (char)(token.charAt(1) - 1);
            }
        } else if (token.startsWith("#"))
        {
            if (token.charAt(1)== 'B')
            {
                return noteValues.get((char) (token.charAt(1) + 1)) * Math.pow(2, power + 1);
            }
            else if(token.charAt(1)== 'E')
            {
                return noteValues.get((char) (token.charAt(1) + 1)) * Math.pow(2, power);
            }
            else if (token.charAt(1)== 'G')
            {
                return noteValues.get('A') * Math.pow(2, power);
            }
            else
            {
                nextNote = (char)(token.charAt(1) + 1);
            }
        }
        return (noteValues.get(token.charAt(1)) + (noteValues.get(nextNote) - noteValues.get(token.charAt(1)))) * Math.pow(2, power);
    }
}
