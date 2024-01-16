package br.com.lanchonetedarua.pagamento.domain.model

import br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.Table

@CompileStatic
@Serdeable
@Entity
@Table(name = "pagamento")
@Inheritance
class Pagamento {

    @Id
    String id
    String pedidoId
    BigDecimal valorTotal
    FormaPagamento formaPagamento
    StatusPagamento status
}
