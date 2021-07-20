package learn.lodging.data;

import learn.lodging.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationFileRepository implements ReservationRepository {

    private final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(@Value("${reservationDataFilePath}")String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByHostId(String hostId){
        ArrayList<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))){
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()){

                String[] fields = line.split(",",-1);
                if (fields.length == 5){
                    reservations.add(deserialize(fields,hostId));
                }
            }

        }catch (IOException ex){
            //don't throw on read
        }
        return reservations;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHostId(reservation.getHostId());
        int nextId = 0;
        for (Reservation r: reservations){
            nextId = Math.max(nextId,r.getId());
        }
        nextId++;
        reservation.setId(nextId);
        reservations.add(reservation);
        writeAll(reservations, reservation.getHostId());
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHostId(reservation.getHostId());
        for (int i = 0; i < reservations.size(); i++){
            if (reservations.get(i).getId() == reservation.getId()){
                reservations.set(i,reservation);
                writeAll(reservations, reservation.getHostId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHostId(reservation.getHostId());
        for (int i = 0; i < reservations.size(); i++){
            if (reservations.get(i).getId() == reservation.getId()){
                reservations.remove(i);
                writeAll(reservations,reservation.getHostId());
                return true;
            }
        }
        return false;
    }

    private String getFilePath(String hostId){
        return Paths.get(directory, hostId+".csv").toString();
    }

    private void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))){
            writer.println(HEADER);

            for (Reservation r: reservations){
                writer.println(serialize(r));
            }

        }catch (FileNotFoundException ex){
            throw new DataException(ex);
        }
    }

    private String serialize(Reservation reservation){
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuestId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, String hostId){
        Reservation reservation = new Reservation();
        int[] start = deserializeDate(fields[1]);
        int[] end = deserializeDate(fields[2]);

        reservation.setId(Integer.parseInt(fields[0]));
        reservation.setStartDate(LocalDate.of(start[0],start[1],start[2]));
        reservation.setEndDate(LocalDate.of(end[0],end[1],end[2]));
        reservation.setGuestId(Integer.parseInt(fields[3]));
        reservation.setTotal(new BigDecimal(fields[4]));

        return reservation;
    }

    private int[] deserializeDate(String date){
        String[] fields = date.split("-");
        int[] dateFields = new int[3];

        for (int i = 0; i < fields.length; i++){
            dateFields[i] = Integer.parseInt(fields[i]);
        }

        return dateFields;
    }


}
