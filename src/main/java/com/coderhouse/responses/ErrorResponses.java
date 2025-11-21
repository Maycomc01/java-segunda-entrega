package com.coderhouse.responses;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponses {
	private String message;
	private String detail;

}
