package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;

/**
  takes the contents of an airline and writes it out to a text file
 */
public class TextDumper implements AirlineDumper {
    String fileName;

    /**
     * name of the file is parse into constructor
     * @param fileName
     *        file name
     */
    public TextDumper(String fileName) {
        this.fileName = fileName;
    }

    /**
     * check if the file exists, if it does, add the listOfFlights to file
     * if it doesn't exist, create it automatically, copy the
     * flight list into text file
     * @param airline
     *        dumps the airline
     * @throws IOException
     *         throws an exception if text file malformed
     */
    public void dump(AbstractAirline airline) throws IOException{

        File outFile;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        String read;

        try {

            outFile = new File(fileName);
            fileWriter = new FileWriter(outFile);
            bufferedWriter = new BufferedWriter(fileWriter);


            //if ()

            bufferedWriter.flush();
            bufferedWriter.close();

        } catch(IOException ex) {
            System.err.println("File Write Error");
        }
    }
}
