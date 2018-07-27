package edu.pdx.cs410J.fen;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private String path;
    private String filename;
    BufferedWriter bWriter;
    File file;
    PhoneCall call = new PhoneCall();
    PrettyPrinter(){

    }
    PrettyPrinter(String path){
        this.path = path;
        this.filename = new File(path).getName().trim();
    }
    public String getFilename(){
        return filename;
    }

    /**
     * This 'dump' method is used to dump sorted phone call to file
     * @param phoneBill
     * @throws IOException
     */
    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        Collection<PhoneCall> phoneCalls = phoneBill.getPhoneCalls();

        List<PhoneCall> calls = new ArrayList<>(phoneCalls);
        List<PhoneCall> phoneCallList = new ArrayList<>();

        for (int i = 0; i < calls.size(); i++){
            if (!phoneCallList.contains(calls.get(i)))
                phoneCallList.add(calls.get(i));
        }


        Collections.sort(phoneCallList);    //Sort the phone call

        try {
            file = new File(path);
            if (!file.exists())
                file.createNewFile();

            bWriter = new  BufferedWriter(new FileWriter(path));

            for (int i = 0; i < phoneCallList.size(); i++) {

                long d = 1000*24*60*60;
                long h = 1000*60*60;
                long m = 1000*60;

                long diff = (phoneCallList.get(i).getEndTime().getTime() - phoneCallList.get(i).getStartTime().getTime());
                long day = diff / d;
                long hour = diff % d / h;
                long minute = diff % d % h / m;
                String duration = day + " day(s) " + hour + " hour(s) " + minute + " minute(s)";    //Get phone call duration

                //Write to the file by pretty format
                String pretty = "["+phoneBill.getCustomer()+"] had a phone call from ["+phoneCallList.get(i).getCaller()+"] to ["+phoneCallList.get(i).getCallee()
                        + "], started at ["+phoneCallList.get(i).getStartTimeString()+"], ended at ["+phoneCallList.get(i).getEndTimeString()
                        + "], duration: ["+ duration + "]";
                bWriter.write(pretty+'\n');
            }
            bWriter.close();
        }catch (IOException error){
            return;
        }
    }

    /**
     * This 'prettyPrint' method is ued to print the file
     */
    public void prettyPrint(){
        BufferedReader bReader;

        try {
            bReader = new BufferedReader(new FileReader(path));

        }catch (FileNotFoundException error){
            return;
        }

        try{
            String line;
            while ((line = bReader.readLine()) != null) {
                System.out.println(line+'\n');
            }

            bReader.close();
        }catch (IOException e){
            return;
        }
    }
}
