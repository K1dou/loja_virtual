package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.NotaFiscalCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {


    @Query("select n from NotaFiscalCompra n where upper(trim(n.descricaoObs)) like upper(trim(concat('%',?1,'%')))")
    public List<NotaFiscalCompra> buscarPorDesc(String desc);

    @Query("select n from NotaFiscalCompra n where n.pessoa.id = ?1")
    public NotaFiscalCompra buscarPorPessoaJuridica(Long id);

    @Query("select n from NotaFiscalCompra n where n.empresa.id = ?1")
    public NotaFiscalCompra buscarPorEmpresa(Long id);

    @Query("select n from NotaFiscalCompra n where n.contaPagar.id = ?1")
    public NotaFiscalCompra buscarPorContaPagar(Long id);


//    @Query(nativeQuery = true,value = "delete from nota_item_produto where nota_fiscal_compra_id = ?1")
//    public void deleteItemNotaFiscalCompra(Long idNotaFiscalCompra);
//

    //itemNotaFiscal


}
