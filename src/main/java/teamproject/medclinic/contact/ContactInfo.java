package teamproject.medclinic.contact;
import teamproject.medclinic.contact.Gender;
import java.util.Date;

public record ContactInfo(String first_name, String last_name, Date date_of_birth, Gender gender, String address, String phone_number, String email) {
}
