package ru.tinkoff.edu.java.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.tinkoff.edu.java.bot.Bot;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;

import java.util.List;

@RestController
@RestControllerAdvice
public class BotController implements IApiController{
    private final Bot bot;

    public BotController(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void update(LinkUpdateRequest linkUpdateRequest){
        SendMessage sm = new SendMessage();
        sm.setText("Link " + linkUpdateRequest.getUrl() + " received an update: " + linkUpdateRequest.getDescription());
        for(Long id: linkUpdateRequest.getTgChatIds()){
            sm.setChatId(id);
            bot.sendUpdateMessage(sm);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleException(MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ApiErrorResponse response = new ApiErrorResponse("Validation failed", "400", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.badRequest().body(response);
    }
}
