import org.junit.Test;

import java.awt.*;
import java.util.Scanner;

public class Tests {

    @Test
    public void test1(){
        Scanner scanner = new Scanner(System.in);
        Cluedo cluedo = new Cluedo(true);
        cluedo.testSetup(3, new Point(7,6), 12, scanner);
        scanner.close();
    }



}
