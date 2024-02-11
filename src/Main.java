import com.jdbc.connection.ATMExecution;
import com.jdbc.vaild.ValidDetails;

import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        Console console = System.console();

        ATMExecution atmExecution = new ATMExecution();
        ValidDetails validDetails = new ValidDetails();

        String acc = "";
        String pin = "";

        System.out.print("Enter account no : ");
        acc = input.next();
        if(!atmExecution.checkAcc(acc)){
            System.out.println("Account no is invalid");
        }else {
            System.out.print("Enter secret pin : ");
            pin = input.next();
            if(!atmExecution.checkPin(acc, pin)){
                System.out.println("Invalid Pin !!");
            }else{
                System.out.println("Login Successful :)");
                atmExecution.menu(acc);
//                atmExecution.withdraw("100","1904327658");
            }
        }



        atmExecution.closeResources();


    }
}