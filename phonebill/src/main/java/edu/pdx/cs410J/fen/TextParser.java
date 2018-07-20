package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


        String customer, caller, callee, startTime, endTime, startDate, endDate;
        try {
            bReader = new BufferedReader(new FileReader(path));

        }catch (FileNotFoundException error){
            return null;
        }

        try{
            String line;
            while ((line = bReader.readLine()) != null) {

                addInfo = line.split(",");

                if (addInfo.length != 7){
                    System.out.println("Error, the phone call information in file is malformatted.");
                    System.exit(1);
                }

                customer = addInfo[0];
                caller = addInfo[1];
                callee = addInfo[2];
                startDate = addInfo[3];
                startTime = addInfo[4];
                endDate = addInfo[5];
                endTime = addInfo[6];
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

                if (count == 0)
                    bill.add(customer, caller,callee,startDate,startTime,endDate,endTime);
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
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

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
        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

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
