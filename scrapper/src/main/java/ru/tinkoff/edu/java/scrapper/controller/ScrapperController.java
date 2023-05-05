
package ru.tinkoff.edu.java.scrapper.controller;
import lombok.RequiredArgsConstructor;
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
import ru.tinkoff.edu.java.scrapper.service.LinksService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@RestController
@RequiredArgsConstructor
public class ScrapperController {

    private final LinksService linksService;
    private final TgChatService tgChatService;


    @PostMapping("/tg-chat/{id}")
    public void registerChat(@PathVariable Long id) {
        tgChatService.register(id);
    }

    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable Long id) {
        tgChatService.unregister(id);
    }

    @GetMapping("/links")
    public ListLinksResponse getLinks(@RequestHeader(value = "Tg-Chat-Id", required = true) Long id) {
        ListLinksResponse links = ListLinksResponse.fromLinkListToResponse(linksService.listAll(id));
        return links;
    }

    @PostMapping("/links")
    public LinkResponse addLink( @RequestHeader(value = "Tg-Chat-Id", required = true) Long id, @RequestBody AddLinkRequest request) {
        LinkResponse link =LinkResponse.fromLinkToLinkResponse(linksService.add(id, request.getUrl()));
        return link;
    }

    @DeleteMapping("/links")
    public LinkResponse removeLink(@RequestHeader(value = "Tg-Chat-Id", required = true) Long id, @RequestBody RemoveLinkRequest request) {
        return LinkResponse.fromLinkToLinkResponse(linksService.remove(id, request.getUrl()));
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