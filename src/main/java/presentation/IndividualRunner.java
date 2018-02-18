package presentation;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import data.QueryDataRetriever;
import data.ResponseDataDto;
import network.RequestProcessor;

/**
 * Clase para realizar la prueba de un proceso indiviual.
 * 
 * @author jelopezta
 *
 */
public class IndividualRunner {

	/**
	 * Disparador de verificación para un proceso individual que se encuentra en
	 * source/radicados.individual.csv
	 * 
	 * @param args
	 *            argumentos de línea de comandos
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) {
		RequestProcessor processor = new RequestProcessor(
				new QueryDataRetriever("source" + File.separator + "radicados.individual.csv"));
		ArrayList<ResponseDataDto> resultList = processor.processRequestData();
		ResultsPrinter printer = new ResultsPrinter(resultList);
		printer.printToConsole();
		printer.printToHtml();
	}
}
