package fullstack4


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/calculator/")
class AuthorBookController{

    @PostMapping
    fun create( @RequestBody input: Input): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.OK).body<Output>(Output(calc(input)))
    }


    fun calc(input: Input): Int {
        if (input.sign == "/" && input.nr2 == 0)return 0
        when(input.sign){
            "+" -> return input.nr1 + input.nr2
            "-" -> return input.nr1 - input.nr2
            "*" -> return input.nr1 * input.nr2
            "/" -> return input.nr1 / input.nr2
        }
        return 0
    }
}