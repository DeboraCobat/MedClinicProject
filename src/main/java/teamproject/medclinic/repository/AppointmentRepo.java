package teamproject.medclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.medclinic.entity.Appointments;

public interface AppointmentRepo extends JpaRepository <Appointments, Long> {
}
