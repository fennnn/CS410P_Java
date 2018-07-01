package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;


public class PhoneCall extends AbstractPhoneCall {
  private int phoneNumber = 0;
  private Date startTime = getStartTime();
  private Date endTime = getEndTime();

  PhoneCall()
  {}

  PhoneCall(int phoneNumber,Date startTime, Date endTime)
  {
    super();
    this.phoneNumber = phoneNumber;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public String getCaller() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getCallee() {
    return "This method is not implemented yet";
  }

  @Override
  public String getStartTimeString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getEndTimeString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
