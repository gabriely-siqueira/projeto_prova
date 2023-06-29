package br.com.trier.spring_matutino;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.spring_matutino.services.CityService;
import br.com.trier.spring_matutino.services.AddressService;
import br.com.trier.spring_matutino.services.AppointmentService;
import br.com.trier.spring_matutino.services.SpecialtyService;
import br.com.trier.spring_matutino.services.DoctorService;
import br.com.trier.spring_matutino.services.PhoneNumberService;
import br.com.trier.spring_matutino.services.impl.CityServiceImpl;
import br.com.trier.spring_matutino.services.impl.AddressServiceImpl;
//import br.com.trier.spring_matutino.services.impl.AppointmentServiceImpl;
import br.com.trier.spring_matutino.services.impl.SpecialtyServiceImpl;
import br.com.trier.spring_matutino.services.impl.DoctorServiceImpl;
import br.com.trier.spring_matutino.services.impl.PhoneNumberServiceImpl;


@TestConfiguration
@SpringBootTest
@ActiveProfiles("teste")

public class TestBase {


	@Bean
	public CityService cityService() {
		return new CityServiceImpl();
	}
	@Bean
	public SpecialtyService specialtyService() {
		return new SpecialtyServiceImpl();
	}
	@Bean
	public AddressService addressService() {
		return new AddressServiceImpl();
	}
	@Bean
	public DoctorService doctorService() {
		return new DoctorServiceImpl();
	}
	@Bean
	public PhoneNumberService phoneNumberService() {
		return new PhoneNumberServiceImpl();
	}/*
	@Bean
	public AppointmentService appointmentService() {
		return new AppointmentServiceImpl();
	}*/
}
