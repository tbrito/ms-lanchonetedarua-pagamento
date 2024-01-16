package br.com.lanchonetedarua.pagamento.driver.web.dtos

import br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

@CompileStatic
@Serdeable
class PagamentoStatusDTO {

    String id
    StatusPagamento status
}
