package com.au.infosys.cashman.controller;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.au.infosys.cashman.enumeration.Coin;
import com.au.infosys.cashman.enumeration.Note;
import com.au.infosys.cashman.exceptions.CurrencyCombinationException;

public class FundsControllerTest {

    private FundsController fundsController;

    @Before
    public void setup() {
        fundsController = new FundsController();
    }

    @Test
    public void whenAddThenMoneyAdded() {
        fundsController.add(Note.FIFTY, 26);
        Assert.assertEquals(26, fundsController.getCount(Note.FIFTY));
        fundsController.add(Note.FIFTY, 1);
        Assert.assertEquals(27, fundsController.getCount(Note.FIFTY));
        fundsController.add(Note.TWENTY, 3);
        Assert.assertEquals(3, fundsController.getCount(Note.TWENTY));
    }

    @Test(expected = IllegalStateException.class)
    public void whenWithdrawOnNotInitialisedThenIllegalStateException() throws Exception {
        fundsController.withdraw(new BigDecimal(100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWithdrawNegativeThenIllegalArgumentException() throws Exception {
        fundsController.add(Note.FIFTY, 26);
        fundsController.withdraw(new BigDecimal(-100));
    }

    @Test
    public void whenGetCountThenReturnAmount() {
        Assert.assertEquals(0, fundsController.getCount(Note.FIFTY));
        Assert.assertEquals(0, fundsController.getCount(Note.TWENTY));
        fundsController.add(Note.FIFTY, 1);
        fundsController.add(Note.TWENTY, 3);
        Assert.assertEquals(1, fundsController.getCount(Note.FIFTY));
        Assert.assertEquals(3, fundsController.getCount(Note.TWENTY));
        fundsController.add(Note.FIFTY, 150);
        fundsController.add(Note.TWENTY, 200);
        Assert.assertEquals(151, fundsController.getCount(Note.FIFTY));
        Assert.assertEquals(203, fundsController.getCount(Note.TWENTY));
    }

    @Test
    public void whenNotInitialisedThenReturnFalse() {
        Assert.assertFalse(fundsController.isInitialised());
    }

    @Test
    public void whenInitialisedThenReturnTrue() {
        fundsController.add(Note.FIFTY, 26);
        Assert.assertTrue(fundsController.isInitialised());
    }

    @Test
    public void whenWithdrawCoinsThenWithdrawCorrectAmount() throws Exception {
        fundsController.add(Coin.FIFTY, 100);
        fundsController.add(Coin.TWENTY, 100);
        fundsController.withdraw(BigDecimal.valueOf(0.50));
        Assert.assertEquals(99, fundsController.getCount(Coin.FIFTY));
        fundsController.withdraw(BigDecimal.valueOf(0.70));
        Assert.assertEquals(98, fundsController.getCount(Coin.FIFTY));
        Assert.assertEquals(99, fundsController.getCount(Coin.TWENTY));
    }

    @Test
    public void whenWithdrawOtherCoinsThenWithdrawCorrectAmount() throws Exception {
        fundsController.add(Coin.FIFTY, 100);
        fundsController.add(Coin.TWENTY, 100);
        fundsController.add(Coin.TEN, 100);
        fundsController.add(Coin.FIVE, 100);
        fundsController.add(Coin.ONE, 100);
        fundsController.add(Coin.TWO, 100);
        fundsController.withdraw(BigDecimal.valueOf(0.05));
        Assert.assertEquals(99, fundsController.getCount(Coin.FIVE));
        fundsController.withdraw(BigDecimal.valueOf(0.25));
        Assert.assertEquals(99, fundsController.getCount(Coin.TWENTY));
        Assert.assertEquals(98, fundsController.getCount(Coin.FIVE));
        fundsController.withdraw(BigDecimal.valueOf(2.45));
        Assert.assertEquals(99, fundsController.getCount(Coin.TWO));
        Assert.assertEquals(97, fundsController.getCount(Coin.TWENTY));
        Assert.assertEquals(97, fundsController.getCount(Coin.FIVE));
        fundsController.withdraw(BigDecimal.valueOf(100));
        Assert.assertEquals(49, fundsController.getCount(Coin.TWO));
    }

    @Test
    public void whenWithdrawThenWithdrawCorrectAmount() throws Exception {
        fundsController.add(Note.FIFTY, 100);
        fundsController.add(Note.TWENTY, 100);
        fundsController.withdraw(new BigDecimal(20));
        Assert.assertEquals(99, fundsController.getCount(Note.TWENTY));
        fundsController.withdraw(new BigDecimal(40));
        Assert.assertEquals(97, fundsController.getCount(Note.TWENTY));
        fundsController.withdraw(new BigDecimal(50));
        Assert.assertEquals(99, fundsController.getCount(Note.FIFTY));
        fundsController.withdraw(new BigDecimal(70));
        Assert.assertEquals(98, fundsController.getCount(Note.FIFTY));
        Assert.assertEquals(96, fundsController.getCount(Note.TWENTY));
        fundsController.withdraw(new BigDecimal(80));
        Assert.assertEquals(92, fundsController.getCount(Note.TWENTY));
        fundsController.withdraw(new BigDecimal(100));
        Assert.assertEquals(96, fundsController.getCount(Note.FIFTY));
        fundsController.withdraw(new BigDecimal(150));
        Assert.assertEquals(93, fundsController.getCount(Note.FIFTY));
        fundsController.withdraw(new BigDecimal(60));
        Assert.assertEquals(89, fundsController.getCount(Note.TWENTY));
        fundsController.withdraw(new BigDecimal(110));
        Assert.assertEquals(92, fundsController.getCount(Note.FIFTY));
        Assert.assertEquals(86, fundsController.getCount(Note.TWENTY));
        fundsController.withdraw(new BigDecimal(200));
        Assert.assertEquals(88, fundsController.getCount(Note.FIFTY));
    }

    @Test
    public void whenWithdrawUnavailableOnFirstDenomThenWithdrawCorrectOnOther() throws Exception {
        fundsController.add(Note.FIFTY, 0);
        fundsController.add(Note.TWENTY, 10);
        fundsController.withdraw(new BigDecimal(200));
        Assert.assertEquals(0, fundsController.getCount(Note.TWENTY));
        Assert.assertEquals(0, fundsController.getCount(Note.FIFTY));
    }

    @Test(expected = CurrencyCombinationException.class)
    public void whenCantMakeAmountThenCurrencyComboException() throws Exception {
        fundsController.add(Note.FIFTY, 10);
        fundsController.add(Note.TWENTY, 10);
        fundsController.withdraw(new BigDecimal(30));
    }

}
