package teamproject.medclinic.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
//@Table(name = "users")
@Data
@NoArgsConstructor
public class Doctor extends User {

    @ElementCollection
    private List<Date> availableTimes;

}
