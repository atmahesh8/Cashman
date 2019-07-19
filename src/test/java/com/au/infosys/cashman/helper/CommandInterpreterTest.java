package com.au.infosys.cashman.helper;


import org.junit.Assert;
import org.junit.Test;

import com.au.infosys.cashman.controller.FundsController;

public class CommandInterpreterTest {

    @Test
    public void whenInitialisedThenReadyToParseCommandTrue() {
        CommandInterpreter commandInterpreter = new CommandInterpreter(new FundsController());
        Assert.assertTrue(commandInterpreter.isReadyToParseCommand());
    }

    @Test
    public void whenQuitThenReadyToParseCommandFalse() throws Exception {
        CommandInterpreter commandInterpreter = new CommandInterpreter(new FundsController());
        commandInterpreter.parseCommand("quit");
        Assert.assertFalse(commandInterpreter.isReadyToParseCommand());
    }

}
