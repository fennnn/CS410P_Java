package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.PhoneBillDumper;


import java.io.*;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Collection;

public class TextDumper implements PhoneBillDumper<PhoneBill>{

    private String path;
    private String filename;
    BufferedWriter bWriter;

    public TextDumper() {

    }

    /**
     * TextDumper Constructor with argument path
     * @param path
     */
    public TextDumper(String path){
        this.path = path;

    }

    /**
     * This 'dump' method is used to write new phone call to exist file
     * @param phoneBill
     * @throws IOException
     */
    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        try{

            StringBuilder sWriter = new StringBuilder();
            Collection<PhoneCall> calls = phoneBill.getPhoneCalls();

            PhoneCall []Call = new PhoneCall[calls.size()];
            Call = calls.toArray(Call);
            PhoneCall call = Call[calls.size()-1];
            sWriter.append("\n");
            sWriter.append(phoneBill.getCustomer()+","+call.getCaller() + "," + call.getCallee() + "," +
                call.startDate() + "," + call.startTime() + "," + call.endDate() + "," + call.endTime());


            bWriter = new BufferedWriter(new FileWriter(path,true));
            bWriter.write(sWriter.toString());
            bWriter.close();
        }catch (IOException error){
            return;
        }
    }

    /**
     * This 'writeFile' method is used to write new phone bill to new file
     * @param phoneBill
     * @throws IOException
     */
    public void writeFile(PhoneBill phoneBill) throws IOException{

        try {
            bWriter = new  BufferedWriter(new FileWriter(path));
            Collection<PhoneCall> calls = phoneBill.getPhoneCalls();


            for (PhoneCall call: calls) {
                call = calls.iterator().next();

                bWriter.write(phoneBill.getCustomer() + "," + call.getCaller() + "," + call.getCallee() + "," +
                        call.startDate() + "," + call.startTime() + "," + call.endDate() + "," + call.endTime());
            }
            bWriter.close();
        }catch (IOException error){
            return;
        }

    }
}
