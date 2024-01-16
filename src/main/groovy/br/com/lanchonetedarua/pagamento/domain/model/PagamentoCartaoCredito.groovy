package br.com.lanchonetedarua.pagamento.domain.model

import groovy.transform.CompileStatic
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity


@CompileStatic
@Entity
@DiscriminatorValue("cartao_credito")
class PagamentoCartaoCredito extends Pagamento {

    String numeroCartao
    String dataExpiracao
    String cvv
}
