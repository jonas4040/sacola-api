INSERT INTO restaurante (id, cep, complemento, nome) VALUES
(1L, '13333-000', 'Complemento Endereço Restaurante 1', 'Lanchonete Burguer Joe'),
(2L, '14500-123', 'Complemento Endereço Restaurante 2', 'Restaurante Bella Pasta');

INSERT INTO cliente (id, cep, complemento, nome) VALUES
(1L, '13887-432', 'Complemento Endereço Cliente 1', 'Jonas');

INSERT INTO produto (id, disponivel, nome, valor_unitario, restaurante_id) VALUES
(1L, true, 'X-burguer triplo', 5.75, 1L),
(2L, true, 'Salada Gourmet', 6.30, 1L),
(3L, true, 'Batata frita', 7.80, 2L);

INSERT INTO sacola (id, forma_pagamento, fechada, valor_total, cliente_id) VALUES
(1L, 0, false, 0.0, 1L),
(2L, 1, false, 0.0, 1L);