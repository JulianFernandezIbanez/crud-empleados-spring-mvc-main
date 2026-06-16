package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.models.Genero;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class CrudEmpleadosSpringMvcApplication implements CommandLineRunner {

	private final EmpleadoService empleadoService;
	private final DepartamentoService departamentoService;
	//private final CorreoService correoService;
	//private final TelefonoService telefonoService;

	public static void main(String[] args) {
		SpringApplication.run(CrudEmpleadosSpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		//Crear registros de ejemplo para la BBDD, lo
		//cual nos permite comprobar la funcionalidad de la aplicacion
		//concretamente la capa de servicios
		
		/*Crear departamento */
		Departamento departamento1 = Departamento.builder()
			.nombre("RRHH")
			.build();

		Departamento departamento2 = Departamento.builder()
			.nombre("IT")
			.build();
		
		Departamento departamento3 = Departamento.builder()
			.nombre("MARKETING")
			.build();
		
		Departamento departamento4 = Departamento.builder()
			.nombre("VENTAS")
			.build();
		
		//Persistir los departamentos en la BBDD
		departamentoService.saveDepartamento(departamento1);
		departamentoService.saveDepartamento(departamento2);
		departamentoService.saveDepartamento(departamento3);
		departamentoService.saveDepartamento(departamento4);

		//Crear Empleados
		Empleado empleado1 = Empleado.builder()
			.nombre("Pepe")
			.primerApellido("Perez")
			.segundApellido("Rodriguez")
			.fechaAlta(LocalDate.of(2020, 6, 01))
			.genero(Genero.HOMBRE)
			.salario(new BigDecimal(1500))
			.departamento(departamento2)
			.emails(Set.of(
				Correo.builder()
					.email("correo1@gmail.com")
					.build(),
				Correo.builder()
					.email("correo2@gmail.com")
					.build()
				))
			.telefonos(Set.of(
				Telefono.builder()
					.numero("111111111")
					.build(),
				Telefono.builder()
					.numero("222222222")
					.build()
				))
			.build();
		
		//Sincronizar los correos y telefonos para que el id 
		//del empleado para que no quede a null
		
		empleado1.getEmails().forEach(Correo -> Correo.setEmpleado(empleado1));
		empleado1.getTelefonos().forEach(Telefono -> Telefono.setEmpleado(empleado1));

		empleadoService.saveEmpleado(empleado1);

		Empleado empleado2 = Empleado.builder()
			.nombre("Josefa")
			.primerApellido("Martinez")
			.segundApellido("Mora")
			.fechaAlta(LocalDate.of(2019, 6, 01))
			.genero(Genero.MUJER)
			.salario(new BigDecimal(3000))
			.departamento(departamento3)
			.emails(Set.of(
				Correo.builder()
					.email("JMM1@gmail.com")
					.build(),
				Correo.builder()
					.email("JMM2@gmail.com")
					.build()
				))
			.telefonos(Set.of(
				Telefono.builder()
					.numero("123456789")
					.build(),
				Telefono.builder()
					.numero("987654321")
					.build()
				))
			.build();
		
		empleado2.getEmails().forEach(Correo -> Correo.setEmpleado(empleado2));
		empleado2.getTelefonos().forEach(Telefono -> Telefono.setEmpleado(empleado2));

		empleadoService.saveEmpleado(empleado2);

	}

}
