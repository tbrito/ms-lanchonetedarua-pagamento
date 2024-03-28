package br.com.lanchonetedarua.pagamento.driver.messaging

import br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento

import groovy.transform.CompileStatic
import groovy.transform.ToString
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@CompileStatic
@Serdeable.Serializable
@Introspected
@ToString
class PedidoPagamentoRetornoDTO {

    String pedidoId
    StatusPagamento statusPagamento
}
