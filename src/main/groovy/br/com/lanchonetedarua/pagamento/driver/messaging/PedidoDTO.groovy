package br.com.lanchonetedarua.pagamento.driver.messaging

import com.fasterxml.jackson.annotation.JsonProperty

import groovy.transform.CompileStatic
import groovy.transform.ToString
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@CompileStatic
@Introspected
@Serdeable.Deserializable
@ToString
class PedidoDTO {

    @JsonProperty("Id")
    String id

    @JsonProperty("Total")
    BigDecimal total

    @JsonProperty("InformacaoDePagamento")
    InformacaoDePagamentoDTO informacaoDePagamento

    @JsonProperty("NomeCompleto")
    String nomeCompleto

    @JsonProperty("email")
    String email

    @Introspected
    @Serdeable.Deserializable
    static class InformacaoDePagamentoDTO {

        @JsonProperty("NumeroDoCartao")
        String numeroDoCartao

        @JsonProperty("NomeCompleto")
        String nomeCompleto

        @JsonProperty("DataExpiracao")
        String dataExpiracao

        @JsonProperty("Cvv")
        String cvv
    }
}
