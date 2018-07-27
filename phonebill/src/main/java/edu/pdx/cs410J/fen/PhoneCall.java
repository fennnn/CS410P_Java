package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.AbstractPhoneCall;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;


public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {


    private String Caller;
    private String Callee;
    private String sTime;
    private String eTime;
    private String sDate;
    private String eDate;
    private String sM;
    private String eM;
    private Date startTime;
    private Date endTime;


    PhoneCall() {

    }

    PhoneCall(String caller, String callee, String startDate, String startTime, String sM, String endDate, String endTime, String eM) {
        super();

        this.Caller = caller;
        this.Callee = callee;
        this.sDate = startDate;
        this.sTime = startTime;
        this.sM = sM;
        this.eDate = endDate;
        this.eTime = endTime;
        this.eM = eM;
    }

    @Override
    public String getCaller() {
        return Caller;
    }


    @Override
    public String getCallee() {
        return Callee;
    }

    public String startDate(){
        return sDate;
    }

    public String startTime(){
        return sTime;
    }

    public String endDate(){
        return eDate;
    }

    public String endTime(){
        return eTime;
    }

    public String sM(){return sM;}

    public String eM(){return eM;}

    /**
     * The 'getStartTimeString' method is used to return the start date format string
     * @return
     */
    @Override
    public String getStartTimeString()  {
        SimpleDateFormat pretty =new SimpleDateFormat("MM/dd/yyyy hh:mm a",Locale.US);

        try {
            Date full = pretty.parse(sDate + " " + sTime+ " " + sM);
            String date = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.US).format(full);
            return date;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /**
     * The 'getEndTimeString' method is used to return the end date format string
     * @return
     */
    @Override
    public String getEndTimeString() {
        SimpleDateFormat pretty =new SimpleDateFormat("MM/dd/yyyy hh:mm a",Locale.US);

        try {
            Date full = pretty.parse(eDate + " " + eTime+ " " + eM);
            String date = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.US).format(full);
            return date;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /**
     * This 'getStartTime' method is used to return the start date by format
     * @return
     */
    @Override
    public Date getStartTime() {
        SimpleDateFormat pretty =new SimpleDateFormat("MM/dd/yyyy hh:mm a",Locale.US);

        try {
            startTime = pretty.parse(sDate + " " + sTime+ " " + sM);
            DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.US).format(startTime);
            return startTime;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /**
     * This 'getEndTime' method is used to return the end date by format
     * @return
     */
    @Override
    public Date getEndTime() {
        SimpleDateFormat pretty =new SimpleDateFormat("MM/dd/yyyy hh:mm a",Locale.US);

        try {
            endTime = pretty.parse(eDate + " " + eTime+ " " + eM);
            DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.US).format(endTime);
            return endTime;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /**
     * This 'compareTo' method is used to compare the start time and caller number
     * @param o
     * @return
     */
    @Override
    public int compareTo(PhoneCall o) {
        if (this.getStartTime().equals(o.getStartTime())){
            if (this.getCaller().compareTo(o.getCaller()) > 0){
                return 1;
            }else
                return -1;
        } else if (this.getStartTime().after(o.getStartTime()))
            return 1;
        else
            return -1;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null)
            return false;
        else if (!(obj instanceof PhoneCall))
            return false;
        PhoneCall other = (PhoneCall) obj;
        if (Callee.equals(other.Callee) && Caller.equals(other.Caller) && getStartTime().equals(other.getStartTime()) && getEndTime().equals(other.getEndTime()))
            return true;
        return false;
    }

}
