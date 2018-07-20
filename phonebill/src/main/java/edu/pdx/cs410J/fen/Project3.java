package edu.pdx.cs410J.fen;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Project3 {

    public static void main(String[] args) throws IOException {
        int indexOfPrint = -1;
        int indexOfReadme = -1;
        int indexOfCustomer = -1;
        int indexOfCaller = -1;
        int indexOfCallee = -1;
        int indexOfStartDate = -1;
        int indexOfStartTime = -1;
        int indexOfEndDate = -1;
        int indexOfEndTime = -1;
        int indexOfTextFile = -1;
        int indexOfPath = -1;

        TextDumper dumper;
        TextParser parser;

        System.out.println("3");
        int count = 0;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-README"))
                indexOfReadme = i;
            else if (args[i].equals("-print"))
                indexOfPrint = i;
            else if (args[i].equals("-textFile"))
                indexOfTextFile = i;
        }

        if (indexOfReadme >= 0) {
            readme();
            System.exit(1);
        }

        if (indexOfTextFile >= 0) {
            if (indexOfPrint > indexOfTextFile)
                indexOfCustomer = indexOfPrint + 1;
            else
                indexOfCustomer = indexOfTextFile + 2;

            indexOfPath = indexOfTextFile+1;
        }else
            indexOfCustomer = indexOfPrint+1;


        indexOfCaller = indexOfCustomer+1;
        indexOfCallee = indexOfCaller+1;
        indexOfStartDate = indexOfCallee+1;
        indexOfStartTime = indexOfStartDate+1;
        indexOfEndDate = indexOfStartTime+1;
        indexOfEndTime = indexOfEndDate+1;

        if (args.length != 10) {
            if (args.length < 10) {
                if (indexOfPrint < 0) {
                    System.out.println("You didn't enter the [-print] option.\n");
                }
                if (indexOfTextFile < 0) {
                    System.out.println("You didn't enter the [-textFile] option.\n");
                }
                if (indexOfPath < 0) {
                    System.out.println("You didn't enter the [path] option.\n");
                }

                if ((indexOfPrint < 0 && indexOfTextFile < 0 && indexOfPath < 0) && args.length <7 )
                {
                    System.out.println("You entered too less args.");
                    System.exit(1);
                }else if ((indexOfPrint < 0 && indexOfTextFile < 0 && indexOfPath < 0) && args.length >7 )
                {
                    System.out.println("Error. You entered extra args.");
                    System.exit(1);
                }
            }else if (args.length > 10) {
                System.out.println("You entered too much command.");
            }else if (args.length==0) {
                System.out.println("You didn't entered any command.");
            }
        }


        if (checkNumber(args[indexOfCaller]) == false || checkNumber(args[indexOfCallee]) == false) {
            System.out.print("You entered wrong format phone number.\n"  +
                    "Please follow this format: 1. 10 digits. 2. nnn-nnn-nnnn 3. n is a number from 0-9\n\n");
            count++;
        }

        if (checkDate(args[indexOfStartDate]) == false || checkDate(args[indexOfEndDate]) == false) {
            System.out.println("You entered wrong format date. Please follow this format <mm/dd/yyyy>\n" +
                    "Any format like those is accept: 1.01/2/1991 2.1/02/1991 3.1/2/1991 4.01/02/1991\n\n");
            count++;
        }

        if (checkTime(args[indexOfStartTime]) == false || checkTime(args[indexOfEndTime]) == false) {
            System.out.println("You entered wrong format time. Please follow this format <hh:mm>\n\n");
            count++;
        }

        if (count == 0) { //After check all phone number/date/time format are correct

            PhoneBill bill;

            if(indexOfTextFile >= 0)
            {
                parser = new TextParser(args[indexOfPath]);
                dumper = new TextDumper(args[indexOfPath]);


                if (parser.checkFile()) { //Check the file exists

                    if (parser.checkEmpty() == true) { //Check the file is empty
                        System.out.println("Error. The file exists but the file is empty.");
                        System.exit(1);
                    }
                    bill = parser.readFile();   //File exists and is not empty, parse file

                    printFile(bill, parser.getFilename()); //Print file name and file content

                    //Check the new custom phone call name matches name in file
                    if (bill.getCustomer().equals(args[indexOfCustomer])) {
                        //Name matches, add new phone call to phone bill
                        PhoneCall call = new PhoneCall(args[indexOfCaller], args[indexOfCallee], args[indexOfStartDate],
                                args[indexOfStartTime], args[indexOfEndDate], args[indexOfEndTime]);
                        bill.addPhoneCall(call);

                        //Dump new phone bill to the file
                        dumper.dump(bill);

                        System.out.println("After add new phonecall to the end of exist file: ");
                        printFile(bill, parser.getFilename());
                    } else {
                        System.out.println("Error. The customer's name does not match the name in file. " +
                                "\nThus, cannot add new phone call.");
                        System.exit(1);
                    }
                }else {
                    //The file doesn't exist, create new file
                    parser.createFile();

                    //Create new phone bill
                    bill = new PhoneBill(args[indexOfCustomer], args[indexOfCaller], args[indexOfCallee],
                            args[indexOfStartDate], args[indexOfStartTime], args[indexOfEndDate], args[indexOfEndTime]);

                    dumper.writeFile(bill); //Dump new phone bill to file

                    System.out.println("After create a new phonebill file");
                    printFile(bill,parser.getFilename());
                    System.exit(1);
                }
            }else if (indexOfPrint>=0 && indexOfTextFile <0 && indexOfPath <0){
                //If user only enter -print command, do nothing with file
                //Create new phonebill and print it
                bill = new PhoneBill(args[indexOfCustomer], args[indexOfCaller], args[indexOfCallee],
                        args[indexOfStartDate], args[indexOfStartTime], args[indexOfEndDate], args[indexOfEndTime]);

                print(bill);
                System.exit(1);
            }else if (indexOfPrint < 0 && indexOfTextFile <0){
                System.out.println("No command option.");
                System.exit(1);
            }
        }else
            System.exit(1);
    }

    /**
     * This 'checkTime' method is used to check the valid of time format.
     * @param timeString
     * @return
     */
    public static boolean checkTime(String timeString) {
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

    public static boolean checkDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        try {
            format.setLenient(false);
            format.parse(dateString);
        } catch (ParseException e) {
            return false;
        }

        return true;
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


