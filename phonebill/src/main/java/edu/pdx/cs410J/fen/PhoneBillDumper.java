package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.IOException;

public interface PhoneBillDumper <T extends AbstractPhoneBill> {

    public void dump(T bill) throws IOException;

}
