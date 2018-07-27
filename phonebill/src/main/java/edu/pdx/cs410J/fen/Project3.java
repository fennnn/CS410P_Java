package edu.pdx.cs410J.fen;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class Project3 {

    public static void main(String[] args) throws IOException {
        int indexOfPrint = -1;
        int indexOfReadme = -1;
        int indexOfCustomer = -1;
        int indexOfCaller = -1;
        int indexOfCallee = -1;
        int indexOfStartDate = -1;
        int indexOfStartTime = -1;
        int indexOfStartM = -1;
        int indexOfEndDate = -1;
        int indexOfEndTime = -1;
        int indexOfEndM = -1;
        int indexOfTextFile = -1;
        int indexOfPath = -1;
        int indexOfPretty = -1;
        int indexOfPrettyPath = -1;

        TextDumper dumper;
        TextParser parser;
        PrettyPrinter prettyPrinter;

        int count = 0;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-README")){
                indexOfReadme = i;
            }
            else if (args[i].equals("-print")) {
                indexOfPrint = i;
            }
            else if (args[i].equals("-textFile")){
                indexOfTextFile = i;
                indexOfPath = indexOfTextFile +1;
            }
            else if (args[i].equals("-pretty")){
                indexOfPretty = i;
                indexOfPrettyPath = indexOfPretty +1;
            }
        }

        if (indexOfReadme >= 0) {
            readme();
            System.exit(1);
        }

        if (args.length > 14) {
            System.out.println("You entered too much command.");
            System.exit(1);
        }else if (args.length==0) {
            System.out.println("You didn't entered any command.");
            System.exit(1);
        }

        if (indexOfPrint >= 0 && indexOfPretty >= 0 && indexOfTextFile >=0){
            indexOfCustomer = 5;
        }else if (indexOfPrint < 0 && indexOfTextFile >= 0 && indexOfPretty >= 0){
            indexOfCustomer = 4;
        }else if (indexOfPrint < 0 && ((indexOfPretty < 0 && indexOfTextFile >=0) || (indexOfPretty >= 0 && indexOfTextFile < 0))){
            indexOfCustomer = 2;
        }else if (indexOfPrint >= 0 && ((indexOfPretty < 0 && indexOfTextFile >=0) || (indexOfPretty >= 0 && indexOfTextFile < 0))){
            indexOfCustomer = 3;
        }

        indexOfCaller = indexOfCustomer+1;
        indexOfCallee = indexOfCaller+1;
        indexOfStartDate = indexOfCallee+1;
        indexOfStartTime = indexOfStartDate+1;
        indexOfStartM = indexOfStartTime+1;
        indexOfEndDate = indexOfStartM+1;
        indexOfEndTime = indexOfEndDate+1;
        indexOfEndM = indexOfEndTime+1;


        if (indexOfPrint < 0) {
            System.out.println("You didn't enter the [-print] option.\n");
        }
        if (indexOfTextFile < 0) {
            System.out.println("You didn't enter the [-textFile] option.\n");
        }
        if (indexOfPath < 0) {
            System.out.println("You didn't enter the [textFile path] option.\n");
        }
        if (indexOfPretty < 0){
            System.out.println("You didn't enter the [-pretty] option.\n");
        }
        if (indexOfPrettyPath < 0){
            System.out.println("You didn't enter the [pretty path] option.\n");
        }



        if ((indexOfPrint >= 0 && indexOfPretty >=0 && indexOfTextFile >= 0) && args.length <14){
            System.out.println("You entered too less args.");
            System.exit(1);
        }
        if ((indexOfPrint >= 0 && indexOfPretty >=0 && indexOfTextFile >= 0) && args.length >14){
            System.out.println("Error. You entered extra args.");
            System.exit(1);
        }
        if ((indexOfPrint < 0) && (indexOfTextFile >= 0) && (indexOfPretty >= 0) && args.length < 13 ) {
            System.out.println("You entered too less args.");
            System.exit(1);
        }

        if ((indexOfPrint < 0) && (indexOfTextFile >=0) && (indexOfPretty >= 0) && args.length >= 14 ) {
            System.out.println("Error. You entered extra args.");
            System.exit(1);
        }


        if (checkNumber(args[indexOfCaller]) == false) {
            System.out.print("You entered wrong format phone number. --[" + args[indexOfCaller] +"]\n"   +
                    "Please follow this format: 1. 10 digits. 2. nnn-nnn-nnnn 3. n is a number from 0-9\n");
            count++;
        }

        if (checkNumber(args[indexOfCallee]) == false) {
            System.out.print("You entered wrong format phone number. --[" + args[indexOfCallee] +"]\n"   +
                    "Please follow this format: 1. 10 digits. 2. nnn-nnn-nnnn 3. n is a number from 0-9\n");
            count++;
        }

        if (checkDate(args[indexOfStartDate]) == false) {
            System.out.println("You entered wrong format date.  --["+ args[indexOfStartDate] +
                    "] Please follow this format <mm/dd/yyyy>\n" +
                    "Any format like those is accept: 1.01/2/1991 2.1/02/1991 3.1/2/1991 4.01/02/1991\n");
            count++;
        }
        if (checkDate(args[indexOfEndDate]) == false) {
            System.out.println("You entered wrong format date.  --["+ args[indexOfEndDate] +
                    "] Please follow this format <mm/dd/yyyy>\n" +
                    "Any format like those is accept: 1.01/2/1991 2.1/02/1991 3.1/2/1991 4.01/02/1991\n");
            count++;
        }

        if (checkTime(args[indexOfStartTime]) == false ) {
            System.out.println("You entered wrong format time. --[" + args[indexOfStartTime] +
                    "] Please follow this format <hh:mm> and all should be integer number\n");
            count++;
        }

        if (checkTime(args[indexOfEndTime]) == false ) {
            System.out.println("You entered wrong format time. --[" + args[indexOfEndTime] +
                    "] Please follow this format <hh:mm> and all should be integer number\n");
            count++;
        }

        if (count > 0){
            System.exit(1);
        }

        //Check if the start time is before the end time
        if (checkValid(args[indexOfStartDate],args[indexOfStartTime],args[indexOfStartM],args[indexOfEndDate],args[indexOfEndTime],args[indexOfEndM])==false){
            System.out.println("Error. The start time you entered didn't before the end time.");
            System.exit(1);
        }

        PhoneBill bill = null;
        parser = new TextParser(args[indexOfPath]);
        dumper = new TextDumper(args[indexOfPath]);

        if (indexOfPrint >= 0){
            //Create new phonebill and print it
            PhoneBill temp = new PhoneBill(args[indexOfCustomer], args[indexOfCaller], args[indexOfCallee],
                    args[indexOfStartDate], args[indexOfStartTime], args[indexOfStartM],
                    args[indexOfEndDate], args[indexOfEndTime], args[indexOfEndM]);
            System.out.println("You entered [-print] option");
            print(temp);
        }
        if (indexOfTextFile >= 0) {
            if (parser.checkFile()) { //Check the file exists
                if (parser.checkEmpty() == true) { //Check the file is empty
                    System.out.println("Error. The file exists but the file is empty.");
                    System.exit(1);
                }

                if (parser.readFile() == null)
                    System.exit(1);

                bill = parser.readFile();   //File exists and is not empty, parse file
                if (indexOfPrint >= 0)
                    printFile(bill, parser.getFilename()); //Print file name and file content
                else
                    System.out.println("Parser file success. (Read from ["+parser.getFilename()+"])");

                //Check the new custom phone call name matches name in file
                if (bill.getCustomer().equals(args[indexOfCustomer])) {
                    //Name matches, add new phone call to phone bill
                    PhoneCall call = new PhoneCall(args[indexOfCaller], args[indexOfCallee], args[indexOfStartDate],
                            args[indexOfStartTime], args[indexOfStartM], args[indexOfEndDate], args[indexOfEndTime], args[indexOfEndM]);
                    bill.addPhoneCall(call);

                    //Dump new phone bill to the file
                    dumper.dump(bill);

                    if (indexOfPrint >= 0) {
                        System.out.println("After add new phonecall to the end of exist file: ");
                        printFile(bill, parser.getFilename());
                    }else
                        System.out.println("Dump file success. (Add new phone call to ["+parser.getFilename()+"])");
                } else {
                    System.out.println("Error. The customer's name ["+args[indexOfCustomer]+
                            "] does not match the name [" +bill.getCustomer()+"] in file. " +
                            "\nThus, cannot add new phone call.");
                    System.exit(1);
                }
            }else {
                parser.createFile();    //This is a website for teachers to upload student results

                //Create new phone bill
                bill = new PhoneBill(args[indexOfCustomer], args[indexOfCaller], args[indexOfCallee],
                        args[indexOfStartDate], args[indexOfStartTime], args[indexOfStartM],
                        args[indexOfEndDate], args[indexOfEndTime], args[indexOfEndM]);

                dumper.writeFile(bill); //Dump new phone bill to file

                if (indexOfPrint >= 0) {
                    System.out.println("After create a new phonebill file");
                    printFile(bill,parser.getFilename());
                }else
                    System.out.println("Dump to a new file success. (Create new ["+parser.getFilename()+"] file.)");
                System.exit(1);
            }
        }
        if (indexOfPretty >= 0){
            //If not parse a file, create a new bill to dump to  file
            if (bill == null)
                bill = new PhoneBill(args[indexOfCustomer], args[indexOfCaller], args[indexOfCallee],
                        args[indexOfStartDate], args[indexOfStartTime], args[indexOfStartM],
                        args[indexOfEndDate], args[indexOfEndTime], args[indexOfEndM]);

            prettyPrinter = new PrettyPrinter(args[indexOfPrettyPath]);
            prettyPrinter.dump(bill);   //Dump phone bill with phone call to pretty printer file
            System.out.println("            Content of Pretty Printer from ["+prettyPrinter.getFilename()+"]");
            prettyPrinter.prettyPrint();    //Display pretty printer
            System.exit(1);
        }

        if (indexOfPrint < 0 && indexOfTextFile <0 && indexOfPretty < 0){
            System.out.println("No command option.");
            System.exit(1);
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
    public static boolean checkValid(String sDate, String sTime, String sM, String eDate, String eTime, String eM){
        SimpleDateFormat pretty =new SimpleDateFormat("MM/dd/yyyy hh:mm a",Locale.US);

        try {
            Date start = pretty.parse(sDate + " " + sTime+ " " + sM);
            Date end = pretty.parse(eDate + " " + eTime+ " " + eM);

            DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.US).format(start);
            DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.US).format(end);

            if (start.getTime() > end.getTime())
                return false;
            else
                return true;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return false;
        }
    }
    /**
     * This 'checkTime' method is used to check the valid of time format.
     * @param timeString
     * @return
     */
    public static boolean checkTime(String timeString) {
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

    public static boolean checkDate(String dateString) {
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
    public static boolean checkNumber(String num) {

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

    /**
     * This 'printFile' method is used to print the content of file.
     * @param phoneBill
     */
    public static void printFile(PhoneBill phoneBill, String file) {

        System.out.println("\n            Content of [" +file+"]\n");
        System.out.println(phoneBill+"\n"+ phoneBill.getPhoneCalls()+'\n');
    }

    /**
     * This 'print' method is used to print the object information.
     * @param phoneBill
     */
    public static void print(PhoneBill phoneBill){
        System.out.println(phoneBill+"\n"+phoneBill.getPhoneCalls());
    }

    /**
     *  This 'readme' method is ued to print the README.
     */

    public static void readme(){
        System.out.println("                    This is README:\n" +
                "This project write to and read from the file\n" +
                "[1]. If the file exists, \n" +
                "       (1). The file is empty, throw an error and exit \n" +
                "       (2). 1. The name doesn't match name in file, exit \n" +
                "            2. The name matches name in file, write this new phone call to the end of file\n" +
                "[2]. If the file doesn't exist, create a new file then write this new phone call to the file\n"+
                "[3]. Prints the customer phone call information.\n" +
                "     The print information includes the customer name, callerNumber, calleeNumber, startTime and endTime.\n" +
                "       (1). customer: Person whose phone bill we’re modeling\n" +
                "       (2). callerNumber: Phone number of caller -> nnn-nnn-nnnn\n" +
                "       (3). calleeNumber: Phone number of person who was called -> nnn-nnn-nnnn\n" +
                "       (4). startTime: Date and time call began (24-hour time) -> MM/DD/YYYY HH:MM\n" +
                "       (5). endTime: Date and time call ended (24-hour time) -> MM/DD/YYYY HH:MM\n" +
                "       (Please enter the information in the above order." );
    }
}

