package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.VendaCompraLojaVirtual;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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


    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "begin;"
            + "UPDATE vd_cp_loja_virt set excluido = true where id =?1;" +
            "commit;")
    void exclusaoLogicaVendaBanco(Long idVenda);


    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "begin;"
            + "UPDATE vd_cp_loja_virt set excluido = false where id =?1;" +
            "commit;")
    void restaurarVendaBanco(Long idVenda);

    @Query("select v from VendaCompraLojaVirtual v where id = ?1 and excluido = false")
    VendaCompraLojaVirtual consultaVendaId(Long id);


    @Query("select i.vendaCompraLojaVirtual from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and i.produto.id = ?1")
    List<VendaCompraLojaVirtual>vendaPorProduto(Long idProduto1);


}
