/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ru.tinkoff.edu.java.bot.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import jakarta.validation.Valid;

import java.util.Optional;
import jakarta.annotation.Generated;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-27T23:06:36.983276500+03:00[Europe/Moscow]")
@Validated
@Tag(name = "updates", description = "the updates API")
@RequestMapping("${openapi.bot.base-path:}")
public interface IApiController {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /updates : Отправить обновление
     *
     * @param linkUpdateRequest  (required)
     * @return Обновление обработано (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     */
    @Operation(
        operationId = "updatesPost",
        summary = "Отправить обновление",
        responses = {
            @ApiResponse(responseCode = "200", description = "Обновление обработано"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/updates",
        produces = { "application/json" },
        consumes = { "application/json" }
    )

    default ResponseEntity<Void> updatesPost(
        @Parameter(name = "LinkUpdateRequest", description = "", required = true) @Valid @RequestBody LinkUpdateRequest linkUpdateRequest
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
