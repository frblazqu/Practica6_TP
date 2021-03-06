
package es.ucm.fdi.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import es.ucm.fdi.model.TrafficSimulator;

/**
 * Controla toda la simulación que se va a ejecutar. Tiene las siguientes
 * responsabilidades: -> Crear el simulador. -> Leer los eventos de la
 * simulación. -> Manejo de gran parte de excepciones lanzadas en niveles
 * inferiores.
 * 
 * @author Francisco Javier Blázquez
 * @version 23/03/18
 */
public class Controller {
	
	private int ticksSimulacion; // Duración de la simulación
	private OutputStream outputStream; // Flujo de salida de informes de la
										// simulación
	private InputStream inputStream; // Flujo de entrada de datos para la
										// simulación
	private TrafficSimulator simulador; // Simulador a controlar
	private String inputPath;

	/**
	 * Crea un nuevo simulador y nuevos flujos de entrada salida con los
	 * parámetros recibidos.
	 * 
	 * @param loadFilePath
	 *            localización del fichero de entrada de eventos
	 * @param saveFilePath
	 *            localización del fichero de escritura de informes
	 * @param numTicks
	 *            duración de la simulación a ejecutar
	 * @throws Exception 
	 * 
	 */
	public Controller(String loadFilePath, String saveFilePath, int numTicks) throws Exception {
		try {
			inputPath = loadFilePath;
			inputStream = new FileInputStream(new File(loadFilePath));
			outputStream = new FileOutputStream(new File(saveFilePath));
			simulador = new TrafficSimulator();
			ticksSimulacion = numTicks;
		} catch (Exception e) {
			throw new Exception("Error al crear el controlador.", e);
		}
	}

	
	
	public Controller(String loadFilePath, int numTicks) throws Exception {
		try {
			inputPath = loadFilePath;
			outputStream = null;
			simulador = new TrafficSimulator();
			ticksSimulacion = numTicks;
		} catch (Exception e) {
			throw new Exception("Error al crear el controlador.", e);
		}
	}

	/**
	 * Lee el fichero .ini del flujo de entrada y parsea cada una de sus
	 * secciones en eventos que inserta en el simulador. Dejamos que se lancen
	 * las excepciones sin ser modificadas para que lleguen a donde se crea el
	 * objeto controller.
	 * 
	 * @throws IOException
	 *             Si no se consigue leer correctamente el fichero de entrada.
	 * @throws IllegalArgumentException
	 *             Si alguna sección no se consigue parsear bien.
	 */
	public void leerDatosSimulacion() {
		try {
			simulador.leerDatosSimulacion(inputStream);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ejecuta la simulación.
	 * 
	 * @see TrafficSimulator#ejecuta(int, OutputStream)
	 * @throws IOException
	 *             si hay problemas de escritura de reports de la simulación.
	 * @throws IllegalArgumentException
	 *             si algún parámetro de la simulación no es válido.
	 */
	public void run() {
		simulador.ejecuta(ticksSimulacion, outputStream);
	}

	public TrafficSimulator simulador() {
		return simulador;
	}
	public void ejecutaKPasos(int k) {
		simulador.ejecuta(k, outputStream);
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public OutputStream getOutputStream() {
		return outputStream;
	}
	public int getTicksSim() {
		return ticksSimulacion;
	}
	public String getInputPath() {
		return inputPath;
	}
	public void setOutputStream(OutputStream flujoEscritura) {
		outputStream = flujoEscritura;
	}
}