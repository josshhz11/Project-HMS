import java.time.LocalDateTime;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime dateTime;
    private String status;

    public Appointment(String appointmentID, String patientID, String doctorID, LocalDateTime dateTime) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.status = "Pending";
    }

    // Getter for doctorID
    public String getDoctorID() {
        return doctorID;
    }

    // Getter for dateTime
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void confirm() {
        this.status = "Confirmed";
    }

    public void cancel() {
        this.status = "Canceled";
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Appointment [Appointment ID=" + appointmentID + ", Patient ID=" + patientID + ", Doctor ID=" + doctorID
                + ", Date and Time=" + dateTime + ", Status=" + status + "]";
    }
}
