package teamproject.medclinic.doctor;

import teamproject.medclinic.contact.ContactInfo;

public record DoctorInfoRegister(String name, String email, Specialty specialty, ContactInfo address){
}
