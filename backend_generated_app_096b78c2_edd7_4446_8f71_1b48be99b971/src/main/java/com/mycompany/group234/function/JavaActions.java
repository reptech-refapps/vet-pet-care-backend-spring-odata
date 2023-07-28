package com.mycompany.group234.function;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.group234.model.Appointment;
import com.mycompany.group234.utils.NotificationClient;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmAction;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmParameter;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.ODataAction;

@Component
public class JavaActions implements ODataAction {
    private final EntityManager entityManager;

    public JavaActions(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	@EdmAction(name = "BookAppointment", isBound = false)
	public boolean bookAppointment(@EdmParameter(name = "DateOfappointment") Date dateOfAppointment,
			@EdmParameter(name = "ReasonForappoinment") String reasonForAppoinment) throws JsonProcessingException {

		try {
			Appointment appointmentBooked = new Appointment();
			appointmentBooked.setDateOfappointment(dateOfAppointment);
			appointmentBooked.setReasonForappoinment(reasonForAppoinment);

			entityManager.getTransaction().begin();
			entityManager.persist(appointmentBooked);
			entityManager.getTransaction().commit();

			sendMailAboutAppointment(dateOfAppointment);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void sendMailAboutAppointment(Date dateOfAppointment) {
		try {

			Map<String, Object> map = new HashMap<>();
			map.put("type", "EMAIL");
			map.put("status", "NEW");
			map.put("createdDate", new Date().toString());
			
			Map<String, String> payload = new HashMap<>();
			payload.put("owner_name", "Wellwin");
			payload.put("dr_name", "Deepak");
			payload.put("time", dateOfAppointment.toString());

			//For owner
			payload.put("username", "wellwin");
			payload.put("to", "wellwininfotech@yahoo.in");
			payload.put("template", "owner_template.html");
			map.put("payload", payload);
			System.out.println(NotificationClient.sendEmail(map));

			//For Doctor
			payload.put("username", "wellwin123");
			payload.put("to", "wellwininfotech@yahoo.in");
			payload.put("template", "vet_template.html");
			map.put("payload", payload);
			System.out.println(NotificationClient.sendEmail(map));
		} catch (Exception e) {
			System.out.println("Error cause:"+e.getMessage());
		}

	}

}
