package uff.dac.depraia.apidepraia;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Test {
        
    @GetMapping
    @ResponseBody
    public ResponseEntity teste() {
        return ResponseEntity.ok().body("Teste");
    }
}
