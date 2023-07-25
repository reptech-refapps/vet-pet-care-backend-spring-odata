package com.mycompany.group234.model.jointable;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;
import javax.persistence.*;

import com.mycompany.group234.model.Visit;
import com.mycompany.group234.model.Pet;
import com.mycompany.group234.model.PetOwner;
import com.mycompany.group234.model.VisitScheduler;
import com.mycompany.group234.model.Veterian;
import com.mycompany.group234.model.Appointment;
import com.mycompany.group234.model.VaccineScheduler;

@Entity(name = "VisitSchedulerVetvisit")
@Table(schema = "\"generated_app\"", name = "\"VisitSchedulerVetvisit\"")
@Data
public class VisitSchedulerVetvisit{

 	@Id
    @Column(name = "\"Id\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "\"Visit_id\"")
	private Integer visit_id;

    
    @Column(name = "\"Pet_id\"")
    private Integer pet_id;
 
}