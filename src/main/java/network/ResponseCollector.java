package network;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import data.ResponseDataDto;

public class ResponseCollector {

	/** Fecha del día actual. */
	private final Date formatedCurrentDate;

	public ResponseCollector() {
		this(0);
	}

	public ResponseCollector(int daysBefore) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		if(daysBefore > 0) {
			calendar.add(Calendar.DATE, -daysBefore);
		}

		formatedCurrentDate = calendar.getTime();
		System.out.println("Fecha actual usada para comparaciones: " + formatedCurrentDate);
	}

	/** Lista con las respuestas de los querys realizados. */
	private final ArrayList<ResponseDataDto> listaRespuestas = new ArrayList<>();

	/**
	 * Obtiene la lista de dtos de respuesta.
	 * 
	 * @return la lista con los dto de respuesta
	 */
	public ArrayList<ResponseDataDto> getResponseDataDtoList() {
		Comparator<? super ResponseDataDto> comparator = (dto1,
				dto2) -> (dto1.getFechaActuacionDate().compareTo(dto2.getFechaActuacionDate()) * (-1));

		listaRespuestas.sort(comparator);
		return listaRespuestas;
	}

	/**
	 * Añade el dto a la lista de dtos recolectados
	 * 
	 * @param dto
	 *            el dto a añadir
	 */
	public void addResponseDataDto(ResponseDataDto dto) {
		if (formatedCurrentDate.compareTo(dto.getFechaActuacionDate()) == 0) {
			dto.setDelDiaActual(true);
		}
		listaRespuestas.add(dto);
	}

}
