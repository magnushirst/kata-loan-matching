package com.kata.loan.repository;

import com.kata.loan.dto.Lender;

import java.io.*;
import java.util.ArrayList;

public class LenderRepository {
    public static final String DELIMITER = ",";
    String csvPath;

    public LenderRepository(String csvPath) {
        this.csvPath = csvPath;
    }

    public ArrayList<Lender> getLenders() throws IOException {
        ArrayList<Lender> lenders =  new ArrayList<>();

        InputStream is = new FileInputStream(csvPath);
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        boolean headers = true;
        while (line != null) {
            if (!headers) {
                String[] elements = line.split(DELIMITER);
                lenders.add(new Lender(elements[0], elements[1], elements[2]));
            } else {
                headers = false;
            }
            line = br.readLine();
        }

        return lenders;
    }
}
