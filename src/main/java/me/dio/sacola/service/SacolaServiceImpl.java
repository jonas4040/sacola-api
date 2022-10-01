package me.dio.sacola.service;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.enumeration.FormaPagamento;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.repository.ItemRepository;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.ItemDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService{
  private final SacolaRepository sacolaRepository;
  private final ProdutoRepository produtoRepository;
  private final ItemRepository itemRepository;
    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getIdSacola());

        if(sacola.isFechada()){
            throw new RuntimeExcep  tion("Esta sacola está fechada");
        }else {

            //item para ser inserido
            Item item = Item.builder().
                    quantidade(itemDto.getQuantidade()).
                    sacola(sacola).
                    produto(produtoRepository.findById(itemDto.getIdProduto()).orElseThrow(
                                    () -> {
                                        throw new RuntimeException("Este produto não existe!");
                                    }
                            )
                    ).
                    build();

            List<Item> itensSacola = sacola.getItens();
            if (itensSacola.isEmpty()) {
                itensSacola.add(item);
            } else {
                Restaurante restauranteAtual = itensSacola.get(0).getProduto().getRestaurante();
                Restaurante restauranteDoItem = item.getProduto().getRestaurante();

                if (restauranteAtual.equals(restauranteDoItem)) {
                    itensSacola.add(item);
                } else {
                    throw new RuntimeException("Não é possível adicionar produtos de restaurantes diferenetes. Feche a sacola ou a esvazie!");
                }
            }

            List<Double> valorItens = new ArrayList<>();
            for (Item itemSacola : itensSacola) {
                double valorTotalItem = itemSacola.getProduto().getValorUnitario() * itemSacola.getQuantidade();
                valorItens.add(valorTotalItem);
            }

        /*
        Double valorTotalSacola = 0.0;
        for(Double valorCadaItem : valorItens){
            valorTotalSacola += valorCadaItem;
        }*/

            Double valorTotalSacola = valorItens.stream().
                    mapToDouble(valorTotalCadaItem -> valorTotalCadaItem).
                    sum();

            sacola.setValorTotal(valorTotalSacola);

        sacolaRepository.save(sacola);
        return itemRepository.save(item);
        }//colocar talvez essa chave antes ou depois
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw  new RuntimeException("Essa sacola não existe!");
                }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroFormaPagamento) {
        Sacola sacola = verSacola(id);
        if(sacola.getItens().isEmpty()){
            throw new RuntimeException("Inclua itens na sacola!");
        }

        FormaPagamento formaPagamento = numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;
        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);
    }
}
