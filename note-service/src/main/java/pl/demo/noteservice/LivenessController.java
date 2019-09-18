package pl.demo.noteservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthz")
public class LivenessController {

    @GetMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void isAlive() {
    }
}
