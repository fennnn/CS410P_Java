package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextParser implements PhoneBillParser <PhoneBill> {

    private String path;
    File readFile;
    private String filename;

    TextParser() {

    }

    /**
     * TextParse Constructor with argument path
     * @param path
     */
    TextParser(String path) {
        this.path = path;
        this.filename = new File(path).getName().trim();
    }

    /**
     * This 'getFilename method is used to return filename
     * @return filename
     */
    public String getFilename(){
        return filename;
    }
    @Override
    public PhoneBill parse() throws ParserException {
        return null;
    }

    /**
     * This 'readFile' method is used to read from file
     * @return
     */
    public PhoneBill readFile(){
        BufferedReader bReader;
        String []addInfo;
        PhoneBill bill = new PhoneBill();


        String customer, caller, callee, startTime, startM, endTime, startDate, endDate, endM;
        try {
            bReader = new BufferedReader(new FileReader(path));

        }catch (FileNotFoundException error){
            return null;
        }

        try{
            String line;
            while ((line = bReader.readLine()) != null) {

                addInfo = line.split(",");

                if (addInfo.length != 9){
                    System.out.println("Error, the phone call information in file is malformatted.");
                    System.exit(1);
                }

                customer = addInfo[0];
                caller = addInfo[1];
                callee = addInfo[2];
                startDate = addInfo[3];
                startTime = addInfo[4];
                startM = addInfo[5];
                endDate = addInfo[6];
                endTime = addInfo[7];
                endM = addInfo[8];
                int count = 0;

                if (checkNumber(caller) == false) {
                    System.out.println("Reading in wrong format phone number. --[" + caller +"]\n"  +
                            "Please follow this format: 1. 10 digits. 2. nnn-nnn-nnnn 3. n is a number from 0-9\n");
                    count++;
                }else if (checkNumber(callee) == false) {
                    System.out.println("Reading in wrong format phone number. --[" + callee + "]\n" +
                            "Please follow this format: 1. 10 digits. 2. nnn-nnn-nnnn 3. n is a number from 0-9\n");
                    count++;
                }

                if (checkDate(startDate) == false) {
                    System.out.println("Reading in wrong format date. --["+ startDate +
                            "] Please follow this format <mm/dd/yyyy>\n" +
                            "Any format like those is accept: 1.01/2/1991 2.1/02/1991 3.1/2/1991 4.01/02/1991\n");
                    count++;
                }else if (checkDate(endDate) == false) {
                    System.out.println("Reading in wrong format date. --[" + endDate +
                            "] Please follow this format <mm/dd/yyyy>\n" +
                            "Any format like those is accept: 1.01/2/1991 2.1/02/1991 3.1/2/1991 4.01/02/1991\n");
                    count++;
                }

                if (checkTime(startTime) == false) {
                    System.out.println("Reading in wrong format time. --[" + startTime +
                            "\nPlease follow this format <hh:mm>\n");
                    count++;
                }else if (checkTime(endTime) == false){
                    System.out.println("Reading in wrong format time. --[" + endTime +
                            "\nPlease follow this format <hh:mm>\n");
                    count++;
                }

                if (checkValid(startDate,startTime,startM,endDate,endTime,endM)==false){
                    System.out.println("Reading the start time doesn't before the end time.");
                    count++;
                }
                if (count == 0)
                    bill.add(customer, caller,callee,startDate,startTime,startM,endDate,endTime,endM);
                else {
                    System.out.println("Format error from reading in file.");
                    return null;
                }
            }
            bReader.close();
            return bill;
        }catch (IOException error){
            return null;
        }
    }


    /**
     * This 'checkValid' method is used to check if the start time before the end time
     * @param sDate
     * @param sTime
     * @param sM
     * @param eDate
     * @param eTime
     * @param eM
     * @return
     */
    public boolean checkValid(String sDate, String sTime, String sM, String eDate, String eTime, String eM){
        SimpleDateFormat pretty =new SimpleDateFormat("MM/dd/yyyy hh:mm a",Locale.US);

        try {
            Date start = pretty.parse(sDate + " " + sTime+ " " + sM);
            Date end = pretty.parse(eDate + " " + eTime+ " " + eM);

            DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.US).format(start);
            DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.US).format(end);

            if (start.getTime()>end.getTime())
                return false;
            else
                return true;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return false;
        }
    }
    /**
     * This 'checkFile' method is used to check file exists or not
     * @return
     */
    public boolean checkFile(){
       readFile= new File(path);
        if (readFile.exists())
            return true;
        else
            return false;
    }

    /**
     * This 'ceateFile' method is used to create new file if file is not exist
     */
    public void createFile(){
        try {
            readFile.createNewFile();

        }catch (IOException error){
            return;
        }
    }

    /**
     * This 'checkEmpty' method is used to check file is empty or not
     * @return
     */
    public boolean checkEmpty(){
       readFile= new File(path);

       if (readFile.exists() && readFile.length() == 0)
           return true;
       else
           return false;
    }
    /**
     * This 'checkTime' method is used to check the valid of time format.
     * @param timeString
     * @return
     */
    public boolean checkTime(String timeString) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");

        try
        {
            format.setLenient(false);
            format.parse(timeString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * This 'checkDate' method is ued to check the valid of date format.
     * @param dateString
     * @return
     */

    public boolean checkDate(String dateString) {
        try {
            Pattern pattern = Pattern.compile("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)");
            Matcher matcher = pattern.matcher(dateString);

            if (matcher.matches())
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * This 'checkNumber' method is used to check the valid phone number format.
     * @param num
     * @return
     */
    public boolean checkNumber(String num) {

        try {
            Pattern pattern = Pattern.compile("(\\d{3})[-](\\d{3})[-](\\d{4})$");
            Matcher matcher = pattern.matcher(num);

            if( matcher.matches()){
                return true;
            }
        }catch (Exception e) {
            return false;
        }

        return false;
    }
}
