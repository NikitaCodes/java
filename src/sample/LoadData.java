package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * Created by Polovenko on 7/9/16.
 */
public class LoadData {
    private static LoadData instance = new LoadData();
    private static String filename = "guestList.txt";

    private ObservableList<Guest> guestFiles;
    private DateTimeFormatter formatter;

    public LoadData() {
        formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    }

    public static LoadData getInstance() {
        return instance;
    }

    public ObservableList getFiles() {
        return guestFiles;
    }

    public void addGuest(Guest newFile){
        guestFiles.add(newFile);
    }

    public void loadFiles() throws IOException {
        guestFiles = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null){
                String[] infoPieces = input.split("\t");

                String roomNum = infoPieces[0];
                String booked = infoPieces[1];
                String firstName = infoPieces[2];
                String lastName = infoPieces[3];
                String phoneNum = infoPieces[4];
                String in = infoPieces[5];
                String out = infoPieces[6];
                String bill = infoPieces[7];

                LocalDate checkIn = LocalDate.parse(in,formatter);
                LocalDate checkOut = LocalDate.parse(out,formatter);

                Guest guest = new Guest(roomNum, Boolean.valueOf(booked),firstName,lastName,
                        phoneNum,checkIn, checkOut,bill);

                guestFiles.add(guest);
            }
        } finally {
            if(br != null) {
                br.close();
            }
        }

    }

    public void storeFiles() throws IOException {

        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {
            Iterator<Guest> iter = guestFiles.iterator();
            while(iter.hasNext()) {
                Guest piece = iter.next();
                bw.write(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                        piece.getRoomNum(),
                        Boolean.toString(piece.isBooked()),
                        piece.getFirstName(),
                        piece.getLastName(),
                        piece.getPhoneNum(),
                        piece.getCheckIn().format(formatter),
                        piece.getCheckOut().format(formatter),
                        piece.getBill()));
                bw.newLine();
            }

        } finally {
            if(bw != null) {
                bw.close();
            }
        }
    }

    public void deleteGuest(Guest guest) {
        guestFiles.remove(guest);
    }


}















