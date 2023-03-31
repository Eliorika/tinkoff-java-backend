
package ru.tinkoff.edu.java.scrapper.controller;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@RestController
public class ScrapperController {

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader(value = "Tg-Chat-Id", required = true) Long id) {

        ListLinksResponse links = new ListLinksResponse(null ,0);
        return ResponseEntity.ok(links);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink( @RequestHeader(value = "Tg-Chat-Id", required = true) Long id, @RequestBody AddLinkRequest request) {
        LinkResponse link = new LinkResponse(id, "Link added successfully");
        return ResponseEntity.ok(link);
    }

    public ResponseEntity<Void> removeLink(@RequestHeader(value = "Tg-Chat-Id", required = true) Long id, @RequestBody RemoveLinkRequest request) {
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ApiErrorResponse response = new ApiErrorResponse("Validation failed", "400", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InstanceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatNotFoundException(InstanceNotFoundException e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        ApiErrorResponse response = new ApiErrorResponse("Chat not found", "404", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AttributeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkNotFoundException(InstanceNotFoundException e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        ApiErrorResponse response = new ApiErrorResponse("Link not found", "404", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        ApiErrorResponse response = new ApiErrorResponse("Internal server error", "500", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}