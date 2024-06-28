package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.VendaCompraLojaVirtual;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendaCompraLojaVirtualRepository extends JpaRepository<VendaCompraLojaVirtual, Long> {

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "begin;"
            + "UPDATE nota_fiscal_venda set venda_compra_loja_virtual_id = null where venda_compra_loja_virtual_id =?1;" +
            "delete from nota_fiscal_venda where venda_compra_loja_virtual_id = ?1;" +
            "delete from status_rastreio where venda_compra_loja_virtual_id = ?1;" +
            "delete from item_venda_loja where venda_compra_loja_virtual_id = ?1;" +
            "delete from vd_cp_loja_virt where id = ?1;" +
            "commit;")
    void exclusaoTotalVendaBanco(Long idVenda);
}
