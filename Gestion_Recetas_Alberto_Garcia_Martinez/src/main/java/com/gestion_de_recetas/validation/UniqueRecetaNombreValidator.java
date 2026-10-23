package com.gestion_de_recetas.validation;

import com.gestion_de_recetas.repo.RecetaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class UniqueRecetaNombreValidator
        implements ConstraintValidator<UniqueRecetaNombre, String> {

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Si está vacío, de eso se encarga @NotBlank (así no salen dos mensajes)
        if (!StringUtils.hasText(value)) {
            return true;
        }

        // En un PUT /recetas/{id}, la receta puede conservar su propio nombre
        Long idActual = getIdDeLaRuta();

        return recetaRepository.findByNombre(value)
                .map(receta -> receta.getId().equals(idActual))
                .orElse(true);
    }

    @SuppressWarnings("unchecked")
    private Long getIdDeLaRuta() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs) {
            Map<String, String> variables = (Map<String, String>) attrs.getRequest()
                    .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (variables != null && variables.get("id") != null) {
                try {
                    return Long.valueOf(variables.get("id"));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
}