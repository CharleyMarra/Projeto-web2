package br.ifg.urt.gamercatalog_api.dto.response;

public record JogoResponseDTO(
    Long id,
    String nome,
    Double preco, // Mapearemos para extrair apenas o "valor" do VO
    String precoFormatado, // Novo campo útil vindo da lógica do VO [cite: 2143]
    String genero,
    Integer classificacaoIndicativa
) {      
}