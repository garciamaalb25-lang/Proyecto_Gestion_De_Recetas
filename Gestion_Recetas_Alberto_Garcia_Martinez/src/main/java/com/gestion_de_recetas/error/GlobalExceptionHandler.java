package com.gestion_de_recetas.error;

import com.gestion_de_recetas.error.notfound.EntidadNoEncontradaException;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ProblemDetail handleNoEncontrada(EntidadNoEncontradaException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Recurso no encontrado");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/not-found"));
        return problem;
    }

    @ExceptionHandler(IngredienteYaAnadidoException.class)
    public ProblemDetail handleIngredienteYaAnadido(IngredienteYaAnadidoException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Ingrediente ya añadido");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/ingrediente-ya-anadido"));
        return problem;
    }

    @ExceptionHandler(TiempoInvalidoException.class)
    public ProblemDetail handleTiempoInvalido(TiempoInvalidoException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Tiempo de preparación inválido");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/tiempo-invalido"));
        return problem;
    }

    @ExceptionHandler(CategoriaConRecetasException.class)
    public ProblemDetail handleCategoriaConRecetas(CategoriaConRecetasException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Categoría con recetas asociadas");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/categoria-con-recetas"));
        return problem;
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Error de validación");
        problem.setDetail("La petición contiene campos inválidos");
        problem.setType(URI.create("https://example.com/errors/validacion"));

        List<ApiValidationSubError> subErrors = ex.getAllErrors().stream()
                .map(ApiValidationSubError::from)
                .toList();
        problem.setProperty("invalid-params", subErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
}