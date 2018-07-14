package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill <PhoneCall>{
    private Collection<PhoneCall> calls = new ArrayList<>();
    private String customer = null;
    private PhoneCall call;

    PhoneBill(String customer, String caller, String callee, String startDate, String startTime, String endDate, String endTime)
    {
        this.customer = customer;
        call = new PhoneCall(caller,callee,startDate,startTime,endDate,endTime);
        calls.add(call);
    }

    @Override
    public String getCustomer() {

        return customer;
    }

    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);

    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {

        return this.calls;
    }
}
