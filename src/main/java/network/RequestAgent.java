package network;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.htmlunit.HtmlUnitWebElement;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindowEvent;
import com.gargoylesoftware.htmlunit.WebWindowListener;

import data.QueryDataDto;
import data.ResponseDataDto;

public class RequestAgent implements Runnable {

	private static final int standardJSExecutionWait = 3_000;

	private final ResponseCollector collector;

	private final QueryDataDto data;

	private String fechaActuacion;

	private String primeraFilaActuaciones = "";

	ExtendedHtmlUnitDriver unitDriver;

	public RequestAgent(ResponseCollector collector, QueryDataDto data) {
		this.collector = collector;
		this.data = data;
		unitDriver = new ExtendedHtmlUnitDriver(true);
		unitDriver.setJavascriptEnabled(true);
	}

	@Override
	public void run() {
		obtenerProceso();
		addResponseDataDtoToCollector();
	}

	private void addResponseDataDtoToCollector() {
		ResponseDataDto responseData = new ResponseDataDto();
		responseData.setFechaActuacion(fechaActuacion);
		responseData.setCiudad(data.getNombreCiudad());
		responseData.setEntidad(data.getNombreEntidad());
		responseData.setNoRadicado(data.getNoRadicacion());
		responseData.setPrimeraFilaActuacion(primeraFilaActuaciones);
		responseData.setFechaActuacionDate(getFechaActuacion(fechaActuacion));
		collector.addResponseDataDto(responseData);

	}

	public void obtenerProceso() {
//		long tiempoInicial = System.currentTimeMillis();
		// System.out.println("Inicializando el driver");

		// Desactivar el logueado de warnings en el driver
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);

		try {
			procesarConDriver(unitDriver);
		} catch (Exception e) {
			e.printStackTrace();
			primeraFilaActuaciones += "No fue posible obtener las actuaciones";
		} finally {
			unitDriver.close();
			System.gc();
			// System.out.println("\n\nEl tiempo total de ejecución fue: "
			// + (System.currentTimeMillis() - tiempoInicial) / 1_000 + "\n");
		}
	}

	private void procesarConDriver(HtmlUnitDriver unitDriver) {
		// System.out.println("Abriendo la página principal en el navegador");
		unitDriver.get("http://procesos.ramajudicial.gov.co/consultaprocesos/");

		final String cityToSelect = data.getNombreCiudad();
		// System.out.println("Obteniendo el campo Ciudad");
		HtmlUnitWebElement citySelectElement = (HtmlUnitWebElement) unitDriver.findElementByName("ddlCiudad");
		System.out.println("Seleccionando la ciudad: " + cityToSelect);
		Select citySelectDomElement = new Select(citySelectElement);
		citySelectDomElement.selectByVisibleText(cityToSelect);
		// WebElement selectedCity =
		// citySelectDomElement.getFirstSelectedOption();
		// System.out.println("El elemento seleccionado para la ciudad es:" +
		// selectedCity.getText());

		try {
			Thread.sleep(standardJSExecutionWait);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		final String specialtyToSelect = data.getNombreEntidad();
		// System.out.println("Obteniendo el campo Entidad/Especialidad");
		HtmlUnitWebElement entitySelectElement = (HtmlUnitWebElement) unitDriver
				.findElementByName("ddlEntidadEspecialidad");
		System.out.println("Seleccionando la especialidad: " + specialtyToSelect);
		Select specialtySelectDomElement = new Select(entitySelectElement);
		specialtySelectDomElement.selectByVisibleText(specialtyToSelect);
		// WebElement selectedSpecialty =
		// specialtySelectDomElement.getFirstSelectedOption();
		// System.out.println("El elemento seleccionado para la especialidad
		// es:" + selectedSpecialty.getText());

		try {
			Thread.sleep(standardJSExecutionWait);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		final String radicadoToFind = data.getNoRadicacion();
		System.out.println("Escribiendo el número del proceso:" + radicadoToFind);
		WebElement divNumRadicacion = unitDriver.findElementById("divNumRadicacion");
		List<WebElement> inputsRadicado = divNumRadicacion.findElements(By.tagName("input"));
		inputsRadicado.get(0).sendKeys(radicadoToFind);

		// System.out.println("Moviendo el slider");
		JavascriptExecutor javascript = (JavascriptExecutor) unitDriver;
		javascript.executeScript("arguments[0].disabled = false", inputsRadicado.get(1));

		// Se envía la petición
		System.out.println("Enviando la petición del proceso");
		inputsRadicado = divNumRadicacion.findElements(By.tagName("input"));
		WebElement inputButtonSubmitByRadicado = inputsRadicado.get(1);
		if (inputButtonSubmitByRadicado.isEnabled()) {
			inputButtonSubmitByRadicado.click();
		} else {
			throw new RuntimeException("No fue posible activar el botón mediante javascript");
		}

		try {
			Thread.sleep(standardJSExecutionWait * 5);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Obteniendo la tabla de actuaciones");
		List<WebElement> fila1 = unitDriver
				.findElements(By.xpath("//table[@class='ActuacionesDetalle']/tbody/tr[2]/td/div/span"));
		List<WebElement> fila2 = unitDriver
				.findElements(By.xpath("//table[@class='ActuacionesDetalle']/tbody/tr[3]/td/div/span"));
		List<WebElement> fila3 = unitDriver
				.findElements(By.xpath("//table[@class='ActuacionesDetalle']/tbody/tr[4]/td/div/span"));
		List<WebElement> fila4 = unitDriver
				.findElements(By.xpath("//table[@class='ActuacionesDetalle']/tbody/tr[5]/td/div/span"));
		List<WebElement> fila5 = unitDriver
				.findElements(By.xpath("//table[@class='ActuacionesDetalle']/tbody/tr[6]/td/div/span"));

		
		// System.out.println("\n");
		StringBuilder builder = new StringBuilder();
		for (WebElement spanInterno : fila1) {
			builder.append(spanInterno.getText()).append("\t");
			fechaActuacion = spanInterno.getText();
		}
		primeraFilaActuaciones = builder.toString();
		// System.out.println(primeraFilaActuaciones);

		// System.out.println("\n");
		builder = new StringBuilder("@\n");
		for (WebElement spanInterno : fila2) {
			builder.append(spanInterno.getText()).append("\t");
		}
		primeraFilaActuaciones += builder.toString();
		// System.out.println(segundaFilaActuaciones);
		
		builder = new StringBuilder("@\n");
		for (WebElement spanInterno : fila3) {
			builder.append(spanInterno.getText()).append("\t");
		}
		
		primeraFilaActuaciones += builder.toString();

		builder = new StringBuilder("@\n");
		for (WebElement spanInterno : fila4) {
			builder.append(spanInterno.getText()).append("\t");
		}
		
		primeraFilaActuaciones += builder.toString();

		
		builder = new StringBuilder("@\n");
		for (WebElement spanInterno : fila5) {
			builder.append(spanInterno.getText()).append("\t");
		}
		
		primeraFilaActuaciones += builder.toString();

	}

	private Date getFechaActuacion(String fecha) {
		if (fecha == null) {
			return new Date(0L);
		}

		String[] splittedDate = fecha.split(" ");
		String month = getFechaActuacionMonth(splittedDate[1]);
		String fullDate = splittedDate[0] + "/" + month + "/" + splittedDate[2];
		SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
		Date parsedDate;
		try {
			parsedDate = format.parse(fullDate);
		} catch (ParseException e) {
			parsedDate = new Date(0L);
		}

		return parsedDate;
	}

	/**
	 * Resuelve el mes del servicio para normalizarlo en español.
	 * 
	 * @param monthString
	 *            el string con el mes a resolver.
	 * @return el mes resuelto en español
	 */
	private String getFechaActuacionMonth(String monthString) {
		switch (monthString) {
		case "Jan":
			return "Ene";
		case "Apr":
			return "Abr";
		case "Aug":
			return "Ago";
		case "Dec":
			return "Dic";
		default:
			return monthString;
		}
	}

	class ExtendedHtmlUnitDriver extends HtmlUnitDriver {
		public ExtendedHtmlUnitDriver(boolean b) {
			super(b);
		}

		@Override
		protected WebClient modifyWebClient(WebClient client) {
			try {
				Field field = getClass().getSuperclass().getDeclaredField("elementsMap");
				field.setAccessible(true);

				client.addWebWindowListener(new WebWindowListener() {
					@Override
					public void webWindowOpened(WebWindowEvent webWindowEvent) {
					}

					@Override
					public void webWindowContentChanged(WebWindowEvent event) {

						try {
							Map map = (Map) field.get(ExtendedHtmlUnitDriver.this);
							map.clear();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void webWindowClosed(WebWindowEvent event) {
					}
				});

			} catch (NoSuchFieldException e) {
				// e.printStackTrace(); No se logueará, se continúa la ejecución
			}

			return client;
		}
	}
}
