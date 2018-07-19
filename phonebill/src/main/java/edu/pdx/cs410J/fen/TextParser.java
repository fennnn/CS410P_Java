package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;


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
                    System.out.println("Error, the phone  call information is incomplete.");
                    return null;
                }

                customer = addInfo[0];
                caller = addInfo[1];
                callee = addInfo[2];
                startDate = addInfo[3];
                startTime = addInfo[4];
                endDate = addInfo[5];
                endTime = addInfo[6];

                bill.add(customer, caller,callee,startDate,startTime,endDate,endTime);
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
}
