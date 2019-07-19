package com.au.infosys.cashman;
/**
 * Created by Atul Maheshwari
 */
import java.util.Scanner;
import java.util.logging.Logger;

import com.au.infosys.cashman.controller.FundsController;
import com.au.infosys.cashman.enumeration.Coin;
import com.au.infosys.cashman.enumeration.Note;
import com.au.infosys.cashman.exceptions.CurrencyCombinationException;
import com.au.infosys.cashman.exceptions.InsufficientFundsException;
import com.au.infosys.cashman.helper.CommandInterpreter;

public class Application {
	final static java.util.logging.Logger	 log = Logger.getLogger(Application.class.getName());
	
    public static void main(String[] args) {
        final String PROMPT = "> ";
        Scanner input = new Scanner(System.in);

        FundsController fundsController = new FundsController();
        CommandInterpreter commandInterpreter = new CommandInterpreter(fundsController);

        int count;
        for (Note denomination : Note.values()) {
        	System.out.printf("> Enter the number of %s: ", denomination.toString());
        	//log.info("> Enter the number of "+denomination.toString()+ ":");
        	
        	// Logger will be used instead of Sysout
        	
            count = input.nextInt();
            fundsController.add(denomination, count);
        }
        for (Coin denomination : Coin.values()) {
        	System.out.printf("> Enter the number of %s: ", denomination.toString());
            count = input.nextInt();
            fundsController.add(denomination, count);
        }
        while (commandInterpreter.isReadyToParseCommand()) {
            System.out.print(PROMPT);
            try {
                commandInterpreter.parseCommand(input.nextLine());
            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
            } catch (CurrencyCombinationException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                commandInterpreter.printUsage();
            }
        }
    }
}
