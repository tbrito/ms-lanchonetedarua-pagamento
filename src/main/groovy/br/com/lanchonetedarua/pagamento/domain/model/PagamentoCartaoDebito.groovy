package br.com.lanchonetedarua.pagamento.domain.model

import groovy.transform.CompileStatic
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity


@CompileStatic
@Entity
@DiscriminatorValue("cartao_debito")
class PagamentoCartaoDebito extends Pagamento {

    // poderia ter outros atributos relacionados ao cartão de débito
}
