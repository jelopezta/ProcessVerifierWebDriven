package driver;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.htmlunit.HtmlUnitWebElement;
import org.openqa.selenium.support.ui.Select;

public class MainWebDriver {

	public static void main(String[] args) {
		long tiempoInicial = System.currentTimeMillis();
		System.out.println("Inicializando el driver");
		HtmlUnitDriver unitDriver = new HtmlUnitDriver(true);
		unitDriver.setJavascriptEnabled(true);

		// Desactivar el logueado de warnings en el driver
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);

		System.out.println("Abriendo la página principal en el navegador");
		unitDriver.get("http://procesos.ramajudicial.gov.co/consultaprocesos/");
		String titulo = unitDriver.getTitle();
		System.out.println("El título de la página es: " + titulo);
		String urlAbierta = unitDriver.getCurrentUrl();
		System.out.println("La url resultado es: " + urlAbierta);

		final String cityToSelect = "POPAYAN";
		System.out.println("Obteniendo el campo Ciudad");
		HtmlUnitWebElement citySelectElement = (HtmlUnitWebElement) unitDriver.findElementByName("ddlCiudad");
		System.out.println("Seleccionando la ciudad: " + cityToSelect);
		Select citySelectDomElement = new Select(citySelectElement);
		citySelectDomElement.selectByVisibleText(cityToSelect);
		WebElement selectedCity = citySelectDomElement.getFirstSelectedOption();
		System.out.println("El elemento seleccionado para la ciudad es:" + selectedCity.getText());

		int standardJSExecutionWait = 3_000;
		try {
			Thread.sleep(standardJSExecutionWait);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		final String specialtyToSelect = "TRIBUNAL SUPERIOR DE POPAYAN - SALA CIVIL - FAMILIA";
		System.out.println("Obteniendo el campo Entidad/Especialidad");
		HtmlUnitWebElement entitySelectElement = (HtmlUnitWebElement) unitDriver
				.findElementByName("ddlEntidadEspecialidad");
		System.out.println("Seleccionando la especiaidad: " + specialtyToSelect);
		Select specialtySelectDomElement = new Select(entitySelectElement);
		specialtySelectDomElement.selectByVisibleText(specialtyToSelect);
		WebElement selectedSpecialty = specialtySelectDomElement.getFirstSelectedOption();
		System.out.println("El elemento seleccionado para la especialidad es:" + selectedSpecialty.getText());

		try {
			Thread.sleep(standardJSExecutionWait);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		final String radicadoToFind = "19001310300520050028402";
		System.out.println("Escribiendo el número del proceso");
		WebElement divNumRadicacion = unitDriver.findElementById("divNumRadicacion");
		List<WebElement> inputsRadicado = divNumRadicacion.findElements(By.tagName("input"));
		inputsRadicado.get(0).sendKeys(radicadoToFind);

		System.out.println("Moviendo el slider");
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

		System.out.println("\n");
		for (WebElement spanInterno : fila1) {
			System.out.print(spanInterno.getText() + "\t");
		}

		System.out.println("\n");
		for (WebElement spanInterno : fila2) {
			System.out.print(spanInterno.getText() + "\t");
		}

		System.out.println(
				"\n\nEl tiempo total de ejecución fue: " + (System.currentTimeMillis() - tiempoInicial) / 1_000 + "\n");
	}
}
