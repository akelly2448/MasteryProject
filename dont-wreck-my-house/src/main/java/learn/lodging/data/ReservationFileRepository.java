package learn.lodging.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationFileRepository {

    private final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(@Value("${reservationDataFilePath}")String directory) {
        this.directory = directory;
    }

    //add(reservation)
    //  -get filePath
    //  -write to file
}
