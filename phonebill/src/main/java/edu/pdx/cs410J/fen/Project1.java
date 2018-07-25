package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {

    int indexOfPrint = -1;
    int indexOfReadme = -1;
    int indexOfCustomer = 0;
    int indexOfCaller = 0;
    int indexOfCallee = 0;
    int indexOfStartDate = 0;
    int indexOfStartTime = 0;
    int indexOfEndDate = 0;
    int indexOfEndTime = 0;

    int count = 0;

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-README"))
        indexOfReadme = i;
      else if (args[i].equals("-print"))
        indexOfPrint = i;
    }

    if (indexOfReadme >= 0) {
      readme();
      System.exit(1);
    }

    if (args.length > 8) {
      System.out.println("You entered too much command.");
      System.exit(1);
    }else if (args.length==0) {
      System.out.println("You didn't entered any command.");
      System.exit(1);
    }


    if (indexOfPrint < 0) {
      System.out.println("You didn't enter the [-print] option.\n");
    }

    if (indexOfPrint < 0 && args.length < 7 )
    {
      System.out.println("You entered too less args.");
      System.exit(1);
    }
    if (indexOfPrint < 0 && args.length >= 8 )
    {
      System.out.println("Error. You entered unknown args.");
      System.exit(1);
    }

    indexOfCustomer = indexOfPrint+1;
    indexOfCaller = indexOfCustomer+1;
    indexOfCallee = indexOfCaller+1;
    indexOfStartDate = indexOfCallee+1;
    indexOfStartTime = indexOfStartDate+1;
    indexOfEndDate = indexOfStartTime+1;
    indexOfEndTime = indexOfEndDate+1;


    if (checkNumber(args[indexOfCaller]) == false) {
      System.out.print("You entered wrong format phone number. --[" + args[indexOfCaller] +"]\n"   +
              "] Please follow this format: 1. 10 digits. 2. nnn-nnn-nnnn 3. n is a number from 0-9\n");
      count++;
    }

    if (checkNumber(args[indexOfCallee]) == false) {
      System.out.print("You entered wrong format phone number. --[" + args[indexOfCallee] +"]\n"   +
              "] Please follow this format: 1. 10 digits. 2. nnn-nnn-nnnn 3. n is a number from 0-9\n");
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

    if (count == 0) {
      //PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      PhoneBill bill = new PhoneBill(args[indexOfCustomer], args[indexOfCaller], args[indexOfCallee],
              args[indexOfStartDate], args[indexOfStartTime],args[indexOfEndDate], args[indexOfEndTime]);

      if (indexOfPrint >= 0)
        print(bill);
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
   * This 'print' method is used to print the object information.
   * @param phoneBill
   */
  public static void print(PhoneBill phoneBill) {

    System.out.println(phoneBill+"\n"+ phoneBill.getPhoneCalls());
  }

  /**
   *  This 'readme' method is ued to print the README.
   */

  public static void readme(){
    System.out.println("                    This is README:\n" +
            "This project prints the customer phone call information.\n" +
            "The print information includes the customer name, callerNumber, calleeNumber, startTime and endTime.\n" +
            "1. customer: Person whose phone bill we’re modeling\n" +
            "2. callerNumber: Phone number of caller -> nnn-nnn-nnnn\n" +
            "3. calleeNumber: Phone number of person who was called -> nnn-nnn-nnnn\n" +
            "4. startTime: Date and time call began (24-hour time) -> MM/DD/YYYY HH:MM\n" +
            "5. endTime: Date and time call ended (24-hour time) -> MM/DD/YYYY HH:MM\n" +
            "Please enter the information in the above order." );
  }
}