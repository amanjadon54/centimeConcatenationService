package com.centime.concatenation.controller;

import com.centime.concatenation.model.NameRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class ConcatenationController {

    @PostMapping("/concatenate")
    public Object hello(@RequestHeader Map<String, String> headers, String logId,
                        @Valid @RequestBody NameRequest nameRequest) {
        return new ResponseEntity<String>(nameRequest.getName() + " " + nameRequest.getSirNmae(), HttpStatus.OK);
    }
}
