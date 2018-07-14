package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;


public class PhoneCall extends AbstractPhoneCall {

    private String Caller;
    private String Callee;
    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate;

    PhoneCall() {

    }

    PhoneCall(String caller, String callee, String startDate, String startTime, String endDate, String endTime) {
        super();

        this.Caller = caller;
        this.Callee = callee;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    @Override
    public String getCaller() {
        if (this.Callee.isEmpty())
            return "This method is not implemented yet";
        else
            return Caller;
    }


    @Override
    public String getCallee() {
        if (this.Callee.isEmpty())
            return "This method is not implemented yet";
        else
            return Callee;
    }

    @Override
    public String getStartTimeString() {

        return startDate + " " + startTime;
    }

    @Override
    public String getEndTimeString() {
        return endDate + " " + endTime;
    }
}
