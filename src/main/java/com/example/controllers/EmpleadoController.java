package com.example.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entities.Correo;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;

import jakarta.validation.Valid;
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
	public String mostrarformularioAlta(Model model,
		@ModelAttribute Empleado empleado){

		model.addAttribute("departamentos", departamentoService.getAllDepartamentos());
		
		//Se enecesita enviar un objeto empleado vacio para que se vinculen sus propiedades
		//con cada control (elemento input, select, etc) del formulario
		//model.addAttribute("empleado", new Empleado());

		return "formularioAltaModificacion";
	}

	//Metodo para recibir los datos del formulario por el metodo post
	@PostMapping("/persistir")
	public String procesarFormularioAltaModificacion(
		@Valid
		@ModelAttribute Empleado empleado,
		BindingResult result,
		@RequestParam(name = "numerostlf") String numtlf, 
		@RequestParam(name = "correos") String emails,
		Model model,
		@RequestParam(name = "file", required = false) MultipartFile file)
	{

		//Comprobacion de errores en la informacion recibida del formulario
		if(result.hasErrors()){

			model.addAttribute("departamentos",departamentoService.getAllDepartamentos());

			return "formularioAltaModificacion";

		}

		//Preguntar si me ha llegado una imagen para el empleado
		//Si es asi se guardara el nombre de la imagen en la propiedad, atributo o variable miembro de la clase foto
		//Y guardar el contenido como un archivo en el sistema de archivos (file system) del servidor

		if (file != null && !file.isEmpty()) {
			
			Path relativePath = Paths.get("src/main/resources/static/imagenes/");
			String absolutePath = relativePath.toFile().getAbsolutePath();
			Path completePath = Paths.get(absolutePath + "/" + file.getOriginalFilename());

			try {

				byte[] bytesImagenRecibida = file.getBytes();
				Files.write(completePath, bytesImagenRecibida);
				empleado.setFoto(file.getOriginalFilename());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
			
		/*LOG.info("Empleado recibido :");
		LOG.info(empleado.toString());
		LOG.info(numtlf);
		LOG.info(emails);*/

		Set<Telefono> numerostlf = new HashSet<>();
		Set<Correo> dirCorreos = new HashSet<>();
	
		if(!numtlf.isEmpty() && !numtlf.isBlank()){
			
			String[] arraynumTlf = numtlf.split(";");
			List<String> listadoNumeros = Arrays.asList(arraynumTlf);
			listadoNumeros.forEach(numero -> {numerostlf.add(Telefono.builder().numero(numero).empleado(empleado).build());});
			empleado.setTelefonos(numerostlf);

		}

		if(!emails.isEmpty() && !emails.isBlank()){
			
			String[] direcciones = emails.split(";");
			List<String> listadoCorreos = Arrays.asList(direcciones);
			listadoCorreos.forEach(correo -> {dirCorreos.add(Correo.builder().email(correo).empleado(empleado).build());});
			empleado.setEmails(dirCorreos);
		}

		empleadoService.saveEmpleado(empleado);

		return "redirect:/empleados/listar";
	}

}
