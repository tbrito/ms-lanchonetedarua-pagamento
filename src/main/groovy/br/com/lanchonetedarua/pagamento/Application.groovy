package br.com.lanchonetedarua.pagamento

import groovy.transform.CompileStatic
import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
        info = @Info(
                title = "lanchontedarua-pagamento",
                version = "1.0",
                description = "Microserviço Pagamento LanchonteDaRua"
        ), servers = @Server(url = "http://localhost:8080")
)
@CompileStatic
class Application {

    static void main(String[] args) {
        Micronaut.run(Application, args)
    }
}
