package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill <PhoneCall>{
    private Collection<PhoneCall> calls = new ArrayList<>();
    private String customer;
    private PhoneCall call;

    PhoneBill(){

    }

    PhoneBill(String customer, String caller, String callee, String startDate, String startTime, String sM, String endDate, String endTime,String eM)
    {
        this.customer = customer;
        call = new PhoneCall(caller,callee,startDate,startTime,sM,endDate,endTime,eM);
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

    /**
     * This 'add' method is used to add new phone call to phone bill when write to file
     * @param customer
     * @param caller
     * @param callee
     * @param startDate
     * @param startTime
     * @param sM
     * @param endDate
     * @param endTime
     * @param eM
     */
    public void add(String customer, String caller, String callee, String startDate, String startTime, String sM,String endDate, String endTime, String eM){
        this.customer = customer;
        call = new PhoneCall(caller,callee,startDate,startTime,sM,endDate,endTime,eM);
        calls.add(call);
    }
}
