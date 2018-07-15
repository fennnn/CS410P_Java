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

    int indexOfPrint = 0;
    int indexOfReadme = -1;
    int indexOfCustomer = 0;
    int indexOfCaller = 0;
    int indexOfCallee = 0;
    int indexOfStartDate = 0;
    int indexOfStartTime = 0;
    int indexOfEndDate = 0;
    int indexOfEndTime = 0;

    int count = 0;

    System.out.println("world");
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

    if (args.length != 8) {
      if (args.length > 7) {
        System.out.println("You entered too much command. Please enter correct number of commands.\n");
        System.exit(1);
      } else if (args.length == 0) {
        System.out.print("You didn't enter anything. Please enter correct number of commands.\n");
        System.exit(1);
      } else {
        System.out.print("You entered too less command. Please enter correct number of commands.\n");
        System.exit(1);
      }
    }

    indexOfCustomer = indexOfPrint+1;
    indexOfCaller = indexOfCustomer+1;
    indexOfCallee = indexOfCaller+1;
    indexOfStartDate = indexOfCallee+1;
    indexOfStartTime = indexOfStartDate+1;
    indexOfEndDate = indexOfStartTime+1;
    indexOfEndTime = indexOfEndDate+1;


    if (checkNumber(args[indexOfCaller]) == false || checkNumber(args[indexOfCallee]) == false) {
      System.out.print("You entered wrong format phone number.\n"  +
              "Please follow this format: 1. 10 digits. 2. nnn-nnn-nnnn 3. n is a number from 0-9\n\n");
      count++;
    }

    if (checkDate(args[indexOfStartDate]) == false || checkDate(args[indexOfEndDate]) == false) {
      System.out.println("You entered wrong format date. Please follow this format <mm/dd/yyyy hh:mm.>\n" +
              "Any format like those is accept: 1.01/2/1991 2.1/02/1991 3.1/2/1991 4.01/02/1991\n\n");
      count++;
    }

    if (checkTime(args[indexOfStartTime]) == false || checkTime(args[indexOfEndTime]) == false) {
      System.out.println("You entered wrong format time. Please follow this format <hh:mm>\n\n");
      count++;
    }

    if (count == 0) {
      //PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      PhoneBill bill = new PhoneBill(args[indexOfCustomer], args[indexOfCaller], args[indexOfCallee],
              args[indexOfStartDate], args[indexOfStartTime], args[indexOfEndDate], args[indexOfEndTime]);

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
    boolean result = false;

    try {
      Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?[-](\\d{3})[-](\\d{4})$");
      Matcher matcher = pattern.matcher(num);

      result = matcher.matches();
    }catch (Exception e) {
      result = false;
    }

    return result;
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