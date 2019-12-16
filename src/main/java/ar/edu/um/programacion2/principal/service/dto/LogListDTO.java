package ar.edu.um.programacion2.principal.service.dto;

import java.util.List;

public class LogListDTO {
	private List<LogDTO> logDTOList;

	public LogListDTO() {
		super();
	}

	public LogListDTO(List<LogDTO> logDTOList) {
		super();
		this.logDTOList = logDTOList;
	}

	public List<LogDTO> getLogDTOList() {
		return logDTOList;
	}

	public void setLogDTOList(List<LogDTO> logDTOList) {
		this.logDTOList = logDTOList;
	}
	
}
