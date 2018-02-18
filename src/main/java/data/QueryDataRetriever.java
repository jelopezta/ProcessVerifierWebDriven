package data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Encargado de obtener la lista de datos a consultar.
 * 
 * @author jelopezta
 *
 */
public class QueryDataRetriever {

	/**
	 * Ruta por defecto del archivo con los parámetros de los procesos a
	 * revisar.
	 */
	private static final String SOURCE_FILE = "source" + File.separator + "radicados.csv";

	/** La ruta del archivo a usar. */
	private final String sourceFile;

	/**
	 * Constructor con la ruta por defecto.
	 */
	public QueryDataRetriever() {
		this.sourceFile = SOURCE_FILE;
	}

	/**
	 * Constructor con la ruta a usar para los parámetros de búsqueda.
	 * 
	 * @param sourceFilePath
	 */
	public QueryDataRetriever(String sourceFilePath) {
		this.sourceFile = sourceFilePath;
	}

	/**
	 * Obtiene la lista de dto con los datos a consultar.
	 * 
	 * @return la lista con los dto a consultar
	 */
	public ArrayList<QueryDataDto> getQueryDataList() {
		ArrayList<QueryDataDto> dtoList = new ArrayList<>();

		Path path = Paths.get(sourceFile);
		try (Stream<String> stream = Files.lines(path)) {
			stream.parallel().filter(x -> x != null && x.contains(","))
					.forEach(x -> dtoList.add(new QueryDataDto(x.split(","))));
		} catch (IOException e) {
			throw new RuntimeException("Ocurrió un error intentando obtener los datos para las consultas", e);
		}
		return dtoList;
	}
}
