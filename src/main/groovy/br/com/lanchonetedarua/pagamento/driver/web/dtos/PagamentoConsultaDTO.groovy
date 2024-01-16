package br.com.lanchonetedarua.pagamento.driver.web.dtos

import br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

@CompileStatic
@Serdeable
class PagamentoConsultaDTO {

    String id
    String pedidoId
    BigDecimal valorTotal
    FormaPagamento formaPagamento
    StatusPagamento status
}
