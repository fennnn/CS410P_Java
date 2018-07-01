package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill {

    private String Customer = null;
    PhoneCall PhoneCalls = null;

    PhoneBill()
    {}

    PhoneBill(String customer, PhoneCall phoneCalls)
    {
        this.Customer = customer;
        this.PhoneCalls = phoneCalls;
    }

    @Override
    public String getCustomer() {
        return null;
    }

    @Override
    public void addPhoneCall(AbstractPhoneCall abstractPhoneCall) {

    }

    @Override
    public Collection getPhoneCalls() {
        return null;
    }
}
