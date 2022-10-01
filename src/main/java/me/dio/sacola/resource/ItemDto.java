package me.dio.sacola.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Embeddable
public class ItemDto {
    private Long idProduto;
    private int quantidade;
    private Long idSacola;
}
