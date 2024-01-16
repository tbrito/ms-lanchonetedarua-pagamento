package br.com.lanchonetedarua.pagamento.domain.model.valueobject

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@CompileStatic
class InfoPagamento {

    String pedidoId
    BigDecimal valorTotal
    FormaPagamento formaPagamento
    String numeroCartao
    String dataExpiracao
    String cvv
}
