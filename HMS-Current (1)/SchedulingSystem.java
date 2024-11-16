import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SchedulingSystem {
    private Map<Doctor, Map<LocalDateTime, Boolean>> doctorAvailability;

    public SchedulingSystem() {
        doctorAvailability = new HashMap<>();
    }

    public void addDoctor(Doctor doctor) {
        doctorAvailability.put(doctor, new HashMap<>());
    }

    public void setAvailability(Doctor doctor, LocalDateTime slot) {
        doctorAvailability.get(doctor).put(slot, true);
    }

    public void displayAvailableSlots() {
        for (Doctor doctor : doctorAvailability.keySet()) {
            System.out.println("Doctor: " + doctor.name + " (Specialty: " + doctor.getSpecialty() + ")");
            for (Map.Entry<LocalDateTime, Boolean> entry : doctorAvailability.get(doctor).entrySet()) {
                if (entry.getValue()) {
                    System.out.println("Available Slot: " + entry.getKey());
                }
            }
        }
    }

    public boolean bookSlot(Patient patient, Appointment appointment) {
        Doctor doctor = findDoctorById(appointment.getDoctorID());
        if (doctor != null && doctorAvailability.containsKey(doctor)) {
            Map<LocalDateTime, Boolean> slots = doctorAvailability.get(doctor);
            if (slots.containsKey(appointment.getDateTime()) && slots.get(appointment.getDateTime())) {
                slots.put(appointment.getDateTime(), false);  // Mark slot as booked
                doctor.addAppointment(appointment); // Add to doctor's schedule
                appointment.confirm();
                return true;
            }
        }
        return false;
    }

    // Method to find a doctor by ID
    public Doctor getDoctorById(String doctorID) {
        for (Doctor doctor : doctorAvailability.keySet()) {
            if (doctor.getuserID().equals(doctorID)) {
                return doctor;
            }
        }
        return null;
    }

    private Doctor findDoctorById(String doctorID) {
        for (Doctor doctor : doctorAvailability.keySet()) {
            if (doctor.getuserID().equals(doctorID)) {
                return doctor;
            }
        }
        return null;
    }
}
