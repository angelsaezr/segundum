package segundum.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "localDateTimeConverter", forClass = LocalDateTime.class)
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	@Override
	public String getAsString(FacesContext context, UIComponent component, LocalDateTime value) {
		return value == null ? "" : value.format(FORMATTER);
	}

	@Override
	public LocalDateTime getAsObject(FacesContext context, UIComponent component, String value) {
		return (value == null || value.isBlank()) ? null : LocalDateTime.parse(value, FORMATTER);
	}
}
