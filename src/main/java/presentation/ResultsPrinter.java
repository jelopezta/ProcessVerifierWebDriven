package presentation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import data.ResponseDataDto;

public class ResultsPrinter {

	private final ArrayList<ResponseDataDto> resultList;

	public ResultsPrinter(ArrayList<ResponseDataDto> resultList) {
		this.resultList = resultList;
	}

	private void garanteeFileForWriting(Path path) {
		File resultsFile = path.toFile();
		resultsFile.mkdirs();
		if (resultsFile.exists()) {
			resultsFile.delete();
		}
		try {
			resultsFile.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException("Ocurrió un error al intentar escribir el archivo de resultados", e);
		}
	}

	public void printToConsole() {
		System.out.println("\n\n\t\t\tInicio de Resultados.\n\n");
		System.out.println("--------------------------------------------------------------------------------------\n");
		for (ResponseDataDto responseData : resultList) {
			System.out.println("La ciudad es: " + responseData.getCiudad());
			System.out.println("La entidad es: " + responseData.getEntidad());
			System.out.println("El radicado es: " + responseData.getNoRadicado());

			System.out.println("\n");

			System.out.println("La fecha de la última actuación es: " + responseData.getFechaActuacion());
			System.out.println("Las actuaciones son:");
			System.out.println(responseData.getPrimeraFilaActuacion());
			System.out.println(
					"--------------------------------------------------------------------------------------\n");
		}
		System.out.println("--------------------------------------------------------------------------------------\n");
		System.out.println("\n\n\t\t\tFin de Resultados.\n\n");

	}

	public void printToFile() {
		final String resultsFilePath = "results" + File.separator + "results.txt";
		Path path = Paths.get(resultsFilePath);
		garanteeFileForWriting(path);
		String lineBreak = System.lineSeparator();
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(lineBreak + lineBreak + "\t\t\tInicio de Resultados." + lineBreak + lineBreak);
			writer.write("--------------------------------------------------------------------------------------"
					+ lineBreak);
			writer.write(lineBreak);

			for (ResponseDataDto responseData : resultList) {
				writer.write("La ciudad es: " + responseData.getCiudad() + lineBreak);
				writer.write("La entidad es: " + responseData.getEntidad() + lineBreak);
				writer.write("El radicado es: " + responseData.getNoRadicado() + lineBreak);

				writer.write(lineBreak);

				writer.write("La fecha de la última actuación es: " + responseData.getFechaActuacion() + lineBreak);
				writer.write("Las últimas actuaciones son:" + lineBreak);
				writer.write(responseData.getPrimeraFilaActuacion() + lineBreak);

				writer.write(lineBreak);
				writer.write("--------------------------------------------------------------------------------------"
						+ lineBreak);
				writer.write(lineBreak);

			}
			writer.write("--------------------------------------------------------------------------------------"
					+ lineBreak);
			writer.write(lineBreak + lineBreak + "\t\t\tFin de Resultados." + lineBreak + lineBreak);
		} catch (IOException e) {
			throw new RuntimeException("Ocurrió un error al intentar escribir el archivo de resultados", e);
		}
	}

	public void printToHtml() {
		final String resultsFilePath = "results" + File.separator + "results.html";
		Path path = Paths.get(resultsFilePath);
		garanteeFileForWriting(path);

		final String styleForTodayAct = "style=\"color:#d60a3a;\"";
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("utf-8"))) {
			writer.write("<html><head></head>");
			writer.write("<body>");
			writer.write("<h1>Inicio de Resultados.</h1>");
			writer.write("<hr></hr>");
			writer.write("<hr></hr>");

			for (ResponseDataDto responseData : resultList) {
				String dateStyle = responseData.isDelDiaActual() ? styleForTodayAct : "";
				String dateSize = responseData.isDelDiaActual() ? "6" : "3";
				writer.write("<h2>El radicado es: " + responseData.getNoRadicado() + "</h2>");
				writer.write("<h3>La ciudad es: " + responseData.getCiudad() + "</h3>");
				writer.write("<h3>La entidad es: " + responseData.getEntidad() + "</h3>");

				writer.write("<ul>");
				writer.write(
						"<li><b>La fecha de la &#250;ltima actuaci&#243;n es: <span " + dateStyle + "><font size=\""
								+ dateSize + "\">" + responseData.getFechaActuacion() + "</font></span></b></li>");
				writer.write("<li><b>Las actuaciones son:</b></li>");
				writer.write("<ul>");
				writer.write("<li>" + responseData.getPrimeraFilaActuacion().replace("@\n", "</li><li>") + "</li>");
				writer.write("</ul>");
				writer.write("</ul>");

				writer.write("<hr></hr>");
			}

			writer.write("<hr></hr>");
			writer.write("<h1>Fin de Resultados.</h1>");
			writer.write("<h3>N&#250;mero de procesos consultados: " + resultList.size() + "</h3>");

			writer.write("</body></html>");
		} catch (IOException e) {
			throw new RuntimeException("Ocurrió un error al intentar escribir el archivo de resultados", e);
		}
	}

}
