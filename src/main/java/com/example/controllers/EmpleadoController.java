package com.example.controllers;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entities.Empleado;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/empleados")
public class EmpleadoController {

	private static final Logger LOG = Logger.getLogger("EmpleadoController");

	private final EmpleadoService empleadoService;
	private final DepartamentoService departamentoService;

    @GetMapping("/listar")
    public String listarempleados(Model model){

		model.addAttribute("empleados", empleadoService.getAllEmpleados());

        return "listadoEmpleados";

    }

	//Metodo que muestra el formulario de cracion de empleado
	@GetMapping("/alta")
	public String mostrarformularioAlta(Model model){

		model.addAttribute("departamentos", departamentoService.getAllDepartamentos());
		
		//Se enecesita enviar un objeto empleado vacio para que se vinculen sus propiedades
		//con cada control (elemento input, select, etc) del formulario
		model.addAttribute("empleado", new Empleado());

		return "formularioAltaModificacion";
	}

	//Metodo para recibir los datos del formulario por el metodo post
	@PostMapping("/persistir")
	public String procesarFormularioAltaModificacion(@ModelAttribute Empleado empleado, 
		@RequestParam(name = "numerostlf") String numtlf, 
		@RequestParam(name = "correos") String emails){

		LOG.info("Empleado recibido :");
		LOG.info(empleado.toString());
		LOG.info(numtlf);
		LOG.info(emails);

		return "redirect:/empleados/listar";
	}

}
